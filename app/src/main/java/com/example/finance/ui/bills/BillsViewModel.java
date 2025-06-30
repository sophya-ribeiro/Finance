package com.example.finance.ui.bills;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.finance.data.Transacao;
import com.example.finance.repositories.TransacaoRepository;

import java.util.List;

public class BillsViewModel extends AndroidViewModel {

    private final TransacaoRepository repository;

    public BillsViewModel(@NonNull Application application) {
        super(application);
        repository = new TransacaoRepository(application);
    }

    public LiveData<List<Transacao>> getTransacoesPorConta(long contaId) {
        Log.d("BillsFragment", "Função chamada");
        return repository.listarTransacoesPorConta(contaId);
    }
}
