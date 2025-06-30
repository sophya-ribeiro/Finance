package com.example.finance.data;

import android.annotation.SuppressLint;

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

     @ColumnInfo(name = "balance")
     public double balance;

//    public Bank(String name, String type, int userId) {
//        this.name = name;
//        this.type = type;
//        this.userId = userId;
//    }

    public Bank(String name, String type, int userId, double balance) {
        this.name = name;
        this.type = type;
        this.userId = userId;
        this.balance = balance;
    }

    public double getBalance() {
        return balance;
    }

    @SuppressLint("DefaultLocale")
    @Override
    public String toString() {
        return this.name + " (Saldo: R$ " + String.format("%.2f", balance) + ")";
    }


    public void setBalance(double balance) {
        this.balance = balance;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
