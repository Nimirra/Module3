package ru.obessonova.module3;

import java.io.Serializable;

public interface Storage extends Serializable {
    void setStorage(String title, String descript);
}
