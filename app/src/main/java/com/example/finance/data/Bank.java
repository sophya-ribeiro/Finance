package com.example.finance.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.ColumnInfo;

@Entity(tableName = "banks")
public class Bank {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "type")
    public String type;

    @ColumnInfo(name = "user_id")
    public int userId;

    public Bank(String name, String type, int userId) {
        this.name = name;
        this.type = type;
        this.userId = userId;
    }
}
