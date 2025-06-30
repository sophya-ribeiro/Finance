package com.example.finance.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "users")
public class User {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String username;
    public String nome;
    public String email;
    public byte[] foto;
    public String hashedPassword;
}