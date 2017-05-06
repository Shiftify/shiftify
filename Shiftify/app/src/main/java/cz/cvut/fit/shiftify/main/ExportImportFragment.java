package cz.cvut.fit.shiftify.main;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import cz.cvut.fit.shiftify.R;
import cz.cvut.fit.shiftify.data.backup.StorageBackuper;
import cz.cvut.fit.shiftify.databinding.ExportImportFragmentBinding;
import cz.cvut.fit.shiftify.helpers.CustomSnackbar;

/**
 * Created by Ilia on 04-May-17.
 */

public class ExportImportFragment extends Fragment {

    private static final String FILENAME = "data.shiftify";
    private static final String APP_FOLDER = "/Shiftify";
    private static final String MIME_TYPE = "vnd.android.cursor.dir/email";
    private static final String TMP_FILE_PREFIX = "data";
    private static final String TMP_FILE_SUFFIX = ".shiftify";
    private static final String EXPORT_EMAIL_SUBJECT = "Exported shiftify data";
    private static final int SEND_EXPORT_EMAIL_REQUEST = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ExportImportFragmentBinding binding = DataBindingUtil.inflate(inflater, R.layout.export_import_fragment, container, false);
        binding.setHandler(this);
        return binding.getRoot();
    }

    public void onExportSdButtonClick(View view) {
        if (!StorageBackuper.isExternalStorageWritable()) {
            Toast.makeText(getActivity(), R.string.cant_write_external_storage, Toast.LENGTH_SHORT).show();
            return;
        }

        File outFile = new File(Environment.getExternalStoragePublicDirectory(APP_FOLDER), FILENAME);
        if (!outFile.getParentFile().mkdirs()) {
            Log.e(this.getClass().getCanonicalName(), "Directory not created");
        }
        try {
            outFile.createNewFile();
            StorageBackuper.backup(outFile);
        } catch (IOException e) {
            Toast.makeText(getActivity(), R.string.export_failed, Toast.LENGTH_LONG).show();
            return;
        }

        new CustomSnackbar(getActivity(), getString(R.string.export_success_to, APP_FOLDER, FILENAME)).show();
    }

    public void onExportEmailButtonClick(View view) {
        File temporaryFile = null;
        try {
            temporaryFile = File.createTempFile(TMP_FILE_PREFIX, TMP_FILE_SUFFIX, getContext().getExternalCacheDir());
            StorageBackuper.backup(temporaryFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Uri path = Uri.fromFile(temporaryFile);
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType(MIME_TYPE);
        emailIntent.putExtra(Intent.EXTRA_STREAM, path);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, EXPORT_EMAIL_SUBJECT);
        startActivityForResult(Intent.createChooser(emailIntent, "Send email..."), SEND_EXPORT_EMAIL_REQUEST);
    }

    public void onImportButtonClick(View view) {
        File inFile = new File(Environment.getExternalStoragePublicDirectory(APP_FOLDER), FILENAME);
        showDialogAndImport(inFile);
    }

    private void showDialogAndImport(final File inFile) {
        new AlertDialog.Builder(getActivity())
                .setTitle(R.string.import_alert_dialog_title)
                .setMessage(getString(R.string.import_alert_dialog_message_from, inFile.getName()))
                .setPositiveButton(R.string.import_dialog_positive_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (!StorageBackuper.isExternalStorageReadable()) {
                            Toast.makeText(getActivity(), R.string.cant_read_external_storage, Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (!inFile.exists()) {
                            Toast.makeText(getActivity(), getString(R.string.file_was_not_found, APP_FOLDER, FILENAME), Toast.LENGTH_SHORT).show();
                            return;
                        }

                        try {
                            StorageBackuper.restore(inFile);
                        } catch (IOException e) {
                            Toast.makeText(getActivity(), R.string.import_failed, Toast.LENGTH_LONG).show();
                            return;
                        }

                        new CustomSnackbar(getActivity(), R.string.import_success).show();
                    }
                })
                .setNegativeButton(R.string.import_dialog_negative_button, null).show();
    }

    private void showDialogAndImport(final InputStream is) {
        new AlertDialog.Builder(getActivity())
                .setTitle(R.string.import_alert_dialog_title)
                .setMessage(R.string.import_alert_dialog_message)
                .setPositiveButton(R.string.import_dialog_positive_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            StorageBackuper.restore(is);
                        } catch (IOException e) {
                            Toast.makeText(getActivity(), R.string.import_failed, Toast.LENGTH_LONG).show();
                            return;
                        }
                        new CustomSnackbar(getActivity(), R.string.import_success).show();
                    }
                })
                .setNegativeButton(R.string.import_dialog_negative_button, null).show();
    }

    public void onImportRequested(Uri fileUri) {
        String scheme = fileUri.getScheme();
        switch (scheme) {
            case ContentResolver.SCHEME_FILE:
                showDialogAndImport(new File(fileUri.getPath()));
                break;
            case ContentResolver.SCHEME_CONTENT:
                try {
                    showDialogAndImport(getActivity().getContentResolver().openInputStream(fileUri));
                } catch (FileNotFoundException e) {
                    Toast.makeText(getActivity(), R.string.import_failed, Toast.LENGTH_LONG).show();
                }
                break;
        }
    }
}
