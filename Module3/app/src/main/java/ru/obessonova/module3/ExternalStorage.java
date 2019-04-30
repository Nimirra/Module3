package ru.obessonova.module3;

import android.os.Environment;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;

public class ExternalStorage implements Storage, Serializable {
    @Override
    public void setStorage(String title, String descript) {
            final String LOG_TAG = "myLogs";
        final String DIR_SD = "MyFiles";
        final String FILENAME_SD = "fileSD";
        
            if (!Environment.getExternalStorageState().equals(
                    Environment.MEDIA_MOUNTED)) {
                Log.d(LOG_TAG, "Хранилище недоступно" + Environment.getExternalStorageState());
                return;
            }
            File sdPath = Environment.getExternalStorageDirectory();
            sdPath = new File(sdPath.getAbsolutePath() + "/" + DIR_SD);
            sdPath.mkdirs();
            File sdFile = new File(sdPath, FILENAME_SD);
            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter(sdFile));
                bw.write(title);
                bw.write(descript);
                bw.close();
                Log.d(LOG_TAG, "Файл добавлен в хранилище" + sdFile.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
}
