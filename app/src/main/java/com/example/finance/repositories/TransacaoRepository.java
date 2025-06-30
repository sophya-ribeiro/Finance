package com.example.finance.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.finance.data.AppDatabase;
import com.example.finance.data.Bank;
import com.example.finance.data.BankDao;
import com.example.finance.data.Transacao;
import com.example.finance.data.TransacaoDao;

import java.util.List;
import java.util.concurrent.Executors;

public class TransacaoRepository {

    private final TransacaoDao transacaoDao;
    private final BankDao bankDao;

    public TransacaoRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        transacaoDao = db.transacaoDao();
        bankDao = db.bankDao(); // ← agora está inicializado corretamente
    }

    public void insert(Transacao transacao) {
        Executors.newSingleThreadExecutor().execute(() -> {
            // Atualizar saldo antes de inserir a transação
            Bank conta = bankDao.getBankById((int) transacao.getContaId());

            if (conta != null) {
                double saldoAtual = conta.getBalance();
                if (transacao.getTipo().equals("entrada")) {
                    conta.setBalance(saldoAtual + transacao.getValor());
                } else {
                    conta.setBalance(saldoAtual - transacao.getValor());
                }
                bankDao.updateBalance(conta.getId(), conta.getBalance());
            }

            transacaoDao.inserirTransacao(transacao);
        });
    }

    public LiveData<List<Transacao>> listarTransacoesPorConta(long contaId) {
        return transacaoDao.listarTransacoesPorConta(contaId);
    }
}
