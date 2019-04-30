package ru.obessonova.module3;

import java.io.Serializable;

public class InternalStorage implements Storage, Serializable {
    @Override
    public void setStorage(String title, String descript) {
        try {
            Tasks.sOutputStream.write(title.getBytes());
            Tasks.sOutputStream.write(descript.getBytes());
            Tasks.sOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
