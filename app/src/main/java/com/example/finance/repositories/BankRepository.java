package com.example.finance.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.finance.data.AppDatabase;
import com.example.finance.data.BankDao;
import com.example.finance.data.Bank;

import java.util.List;

public class BankRepository {

    private BankDao bankDao;

    public BankRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        bankDao = db.bankDao();
    }

    public void update(Bank bank) {
        bankDao.update(bank);
    }


    public void insert(Bank bank) {
        bankDao.insert(bank);
    }

    public LiveData<List<Bank>> getBanksByUser(int userId) {
        return bankDao.getBanksByUser(userId);
    }
}
