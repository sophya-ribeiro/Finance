package com.example.finance.ui.bank;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.finance.data.Bank;
import com.example.finance.repositories.BankRepository;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class BankViewModel extends AndroidViewModel {

    private BankRepository repository;
    private Executor executor = Executors.newSingleThreadExecutor();

    public BankViewModel(@NonNull Application application) {
        super(application);
        repository = new BankRepository(application);
    }

    public void insert(Bank bank) {
        executor.execute(() -> repository.insert(bank));
    }

    public LiveData<List<Bank>> getBanksByUser(int userId) {
        return repository.getBanksByUser(userId);
    }

}
