package ru.obessonova.module3;

import java.io.Serializable;

public class Task implements Serializable {
    private String mTitle;
    private String mDescript;
    private Storage mStorage;
    
    public String getDescript() {
        return mDescript;
    }
    
    public void setDescript(String descript) {
        this.mDescript = descript;
    }
    
    public Storage getStorage() {
        return mStorage;
    }
    
    public String getTitle() {
        return mTitle;
    }
    
    public void setTitle(String title) {
        this.mTitle = title;
    }
    
    public void setMyStorage(Storage storage) {
        this.mStorage = storage;
        storage.setStorage(mTitle, mDescript);
    }
}
