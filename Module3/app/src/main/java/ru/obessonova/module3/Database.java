package ru.obessonova.module3;

import java.io.Serializable;

public class Database implements Storage, Serializable {
    @Override
    public void setStorage(String title, String descript) {
        TaskRoomDatabase db = App.getInstance().getDatabase();
        TaskDao taskDao = db.taskDao();
        
        Task databaseTask = new Task();
        databaseTask.setTitle(title);
        databaseTask.setDescript(descript);
        taskDao.insert(databaseTask);
    }
}
