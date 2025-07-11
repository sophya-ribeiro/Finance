package com.example.finance.ui.bill;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.finance.data.Transacao;
import com.example.finance.repositories.TransacaoRepository;

import java.util.List;

public class BillViewModel extends AndroidViewModel {

    private final TransacaoRepository repository;

    public BillViewModel(@NonNull Application application) {
        super(application);
        repository = new TransacaoRepository(application);
    }

    public void insert(Transacao transacao) {
        repository.insert(transacao);
    }
    public LiveData<List<Transacao>> getTransacoesPorConta(long contaId) {
        return repository.listarTransacoesPorConta(contaId);
    }
}