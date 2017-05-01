package cz.cvut.fit.shiftify.data.backup;

import android.app.backup.BackupAgentHelper;
import android.app.backup.FileBackupHelper;
import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOError;
import java.io.IOException;
import java.io.OutputStream;

import cz.cvut.fit.shiftify.data.App;

/**
 * Created by ondra on 1.5.17.
 */

public class StorageBackuper {
    private static final String DB_NAME = "shiftify.db";

    public static void backup(File out) throws IOException {
        File dbFile = App.getsContext().getDatabasePath(DB_NAME);

        transfer(dbFile, out);
    }

    public static void restore(File in) throws IOException {
        File dbFile = App.getsContext().getDatabasePath(DB_NAME);

        transfer(in, dbFile);
    }

    private static void transfer(File in, File out) throws IOException {
        try (FileInputStream fis = new FileInputStream(in);
             OutputStream output = new FileOutputStream(out);
        ) {
            byte[] buffer = new byte[1024];
            int length;

            while ((length = fis.read(buffer)) > 0){
                output.write(buffer, 0, length);
            }

            output.flush();
        }
    }
}
