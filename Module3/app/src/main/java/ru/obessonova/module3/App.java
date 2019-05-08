package ru.obessonova.module3;

import android.app.Application;
import android.arch.persistence.room.Room;

public class App extends Application {
    
    public static App instance;
    
    private TaskRoomDatabase database;
    
    public static App getInstance() {
        return instance;
    }
    
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        database = Room.databaseBuilder(this, TaskRoomDatabase.class, "database")
                .build();
    }
    
    public TaskRoomDatabase getDatabase() {
        return database;
    }
}
