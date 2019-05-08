package ru.obessonova.module3;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.io.Serializable;

@Entity
public class Task implements Serializable {
    @PrimaryKey
    @NonNull
    private String title;
    private String descript;
    @Ignore
    private Storage myStorage;
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getDescript() {
        return descript;
    }
    
    public void setDescript(String descript) {
        this.descript = descript;
    }
    
    public Storage getMyStorage() {
        return myStorage;
    }
    
    public void setMyStorage(Storage storage) {
        myStorage = storage;
        myStorage.setStorage(title, descript);
    }
}
