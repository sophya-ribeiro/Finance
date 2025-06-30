package com.example.finance.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Delete;
import androidx.room.Update;
import androidx.lifecycle.LiveData;

import com.example.finance.data.Bank;

import java.util.List;

@Dao
public interface BankDao {

    @Insert
    void insert(Bank bank);

    @Update
    void update(Bank bank);

    @Delete
    void delete(Bank bank);


    @Query("SELECT * FROM banks WHERE user_id = :userId")
    LiveData<List<Bank>> getBanksByUser(int userId);

    @Query("SELECT * FROM banks WHERE id = :id LIMIT 1")
    Bank getBankById(int id);

    @Query("SELECT * FROM banks")
    LiveData<List<Bank>> getBanks();
}

