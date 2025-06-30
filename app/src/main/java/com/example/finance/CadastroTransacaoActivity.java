package com.example.finance;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import com.example.finance.data.*;

import java.util.List;
public class CadastroTransacaoActivity extends AppCompatActivity{
    private Spinner spinnerContas;
    private RadioGroup radioGroupTipo;
    private EditText editValor, editDescricao;
    private Button buttonSalvar;
    private BankDao bankDao;       // stub temporário
    private TransacaoDao transacaoDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_bill);

        spinnerContas = findViewById(R.id.spinnerContas);
        radioGroupTipo = findViewById(R.id.radioGroupTipo);
        editValor = findViewById(R.id.editValor);
        editDescricao = findViewById(R.id.editDescricao);
        buttonSalvar = findViewById(R.id.buttonSalvar);

        Integer userId = getIntent().getIntExtra("userId", -1);

        AppDatabase db = AppDatabase.getInstance(getApplicationContext());
        transacaoDao = db.transacaoDao();

        carregarContas();

        buttonSalvar.setOnClickListener(v -> salvarTransacao());
    }

    private void carregarContas() {
        // Stub já é síncrono, lista mock
        List<Bank> contas = bankDao.getBanks().getValue();

        ArrayAdapter<Bank> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, contas);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerContas.setAdapter(adapter);
    }

    private void salvarTransacao() {
        Bank contaSelecionada = (Bank) spinnerContas.getSelectedItem();
        if (contaSelecionada == null) {
            Toast.makeText(this, "Selecione uma conta", Toast.LENGTH_SHORT).show();
            return;
        }

        int tipoId = radioGroupTipo.getCheckedRadioButtonId();
        if (tipoId == -1) {
            Toast.makeText(this, "Selecione o tipo da transação", Toast.LENGTH_SHORT).show();
            return;
        }

        RadioButton radioSelecionado = findViewById(tipoId);
        String tipo = radioSelecionado.getText().toString().toUpperCase();

        String valorStr = editValor.getText().toString();
        if (valorStr.isEmpty()) {
            Toast.makeText(this, "Informe o valor", Toast.LENGTH_SHORT).show();
            return;
        }

        double valor;
        try {
            valor = Double.parseDouble(valorStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Valor inválido", Toast.LENGTH_SHORT).show();
            return;
        }

        String descricao = editDescricao.getText().toString();
        long data = System.currentTimeMillis();

        Transacao transacao = new Transacao(contaSelecionada.getId(), tipo, valor, descricao, data);

        new AsyncTask<Transacao, Void, Long>() {
            @Override
            protected Long doInBackground(Transacao... transacoes) {
                return transacaoDao.inserirTransacao(transacoes[0]);
            }

            @Override
            protected void onPostExecute(Long id) {
                if (id > 0) {
                    Toast.makeText(CadastroTransacaoActivity.this, "Transação salva!", Toast.LENGTH_SHORT).show();
                    limparCampos();
                } else {
                    Toast.makeText(CadastroTransacaoActivity.this, "Erro ao salvar transação", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute(transacao);
    }

    private void limparCampos() {
        editValor.setText("");
        editDescricao.setText("");
        radioGroupTipo.clearCheck();
        spinnerContas.setSelection(0);
    }

}
