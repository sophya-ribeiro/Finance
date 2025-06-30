package com.example.finance.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.finance.data.TransacaoDao;
import com.example.finance.data.Transacao;
import com.example.finance.data.AppDatabase;

import java.util.List;
import java.util.concurrent.Executors;

public class TransacaoRepository {
    private final TransacaoDao transacaoDao;

    public TransacaoRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        transacaoDao = db.transacaoDao();
    }

    public void insert(Transacao transacao) {
        Executors.newSingleThreadExecutor().execute(() -> {
            transacaoDao.inserirTransacao(transacao);
        });
    }
    public LiveData<List<Transacao>> listarTransacoesPorConta(long contaId) {
        return transacaoDao.listarTransacoesPorConta(contaId);
    }

}
