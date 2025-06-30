package com.example.finance.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;
@Dao
public interface TransacaoDao {
    @Insert
    long inserirTransacao(Transacao transacao);

    @Query("SELECT * FROM transacoes WHERE contaId = :contaId")
    LiveData<List<Transacao>> listarTransacoesPorConta(long contaId);
}
