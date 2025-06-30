package com.example.finance.data;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "transacoes",
        foreignKeys = @ForeignKey(entity = Bank.class,
                parentColumns = "id",
                childColumns = "contaId",
                onDelete = ForeignKey.CASCADE))
public class Transacao {

    @PrimaryKey(autoGenerate = true)
    private long id;
    private long contaId;
    private String tipo;  // "ENTRADA" ou "SAIDA"
    private double valor;
    private String descricao;
    private long data;  // timestamp em millis

    // Construtor
    public Transacao(long contaId, String tipo, double valor, String descricao, long data) {
        this.contaId = contaId;
        this.tipo = tipo;
        this.valor = valor;
        this.descricao = descricao;
        this.data = data;
    }

    // Getters e Setters
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public long getContaId() { return contaId; }
    public void setContaId(long contaId) { this.contaId = contaId; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public double getValor() { return valor; }
    public void setValor(double valor) { this.valor = valor; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public long getData() { return data; }
    public void setData(long data) { this.data = data; }
}

