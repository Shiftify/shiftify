package cz.cvut.fit.shiftify.data.backup;

import android.app.backup.BackupAgentHelper;
import android.app.backup.FileBackupHelper;
import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import cz.cvut.fit.shiftify.data.App;

/**
 * Created by ondra on 1.5.17.
 */

public class StorageBackuper {
    private static final String DB_NAME = "shiftify.db";

    public static void backup(File out) throws IOException {
        File dbFile = App.getsContext().getDatabasePath(DB_NAME);

        try (InputStream dbStream = new FileInputStream(dbFile);
             OutputStream fileStream = new FileOutputStream(out)) {
            transfer(dbStream, fileStream);
        }
    }

    public static void restore(File in) throws IOException {
        File dbFile = App.getsContext().getDatabasePath(DB_NAME);

        try (InputStream fileStream = new FileInputStream(in);
             OutputStream dbStream = new FileOutputStream(dbFile)) {
            transfer(fileStream, dbStream);
        }
    }

    public static void restore(InputStream is) throws IOException {
        File dbFile = App.getsContext().getDatabasePath(DB_NAME);

        try(OutputStream dbStream = new FileOutputStream(dbFile)){
            transfer(is, dbStream);
        }
    }

    private static void transfer(InputStream is, OutputStream os) throws IOException {
        byte[] buffer = new byte[1024];
        int length;

        while ((length = is.read(buffer)) > 0) {
            os.write(buffer, 0, length);
        }

        os.flush();
    }

    /* Checks if external storage is available for read and write */
    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    /* Checks if external storage is available to at least read */
    public static boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state);
    }
}
