package com.example.finance;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import com.example.finance.data.*;

import java.util.List;

public class CadastroTransacaoActivity extends AppCompatActivity {
    private Spinner spinnerContas;
    private RadioGroup radioGroupTipo;
    private EditText editValor, editDescricao;
    private Button buttonSalvar;
    private BankDao bankDao;
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
        bankDao = db.bankDao();

        carregarContas();

        buttonSalvar.setOnClickListener(v -> salvarTransacao());
    }

    private void carregarContas() {
        List<Bank> contas = bankDao.getBanks().getValue();

        ArrayAdapter<Bank> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, contas);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerContas.setAdapter(adapter);
    }

    @SuppressLint("StaticFieldLeak")
    private void salvarTransacao() {
        Log.d("DEBUG", "Botão salvar clicado!");
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

        new AsyncTask<Transacao, Void, Boolean>() {
            @SuppressLint("StaticFieldLeak")
            @Override
            protected Boolean doInBackground(Transacao... transacoes) {
                Transacao t = transacoes[0];
                transacaoDao.inserirTransacao(t);

                Bank conta = bankDao.getBankById((int) t.getContaId());

                if (conta != null) {
                    Log.d("Transacao", "Conta encontrada: " + conta.getName() + " (ID: " + conta.getId() + ")");
                    Double saldoAtual = conta.getBalance();

                    if (saldoAtual==null){
                        saldoAtual = (double) 0;
                    }

                    if (t.getTipo().equals("RECEITA")) {
                        conta.setBalance(saldoAtual + t.getValor());
                    } else {
                        conta.setBalance(saldoAtual - t.getValor());
                    }

                    bankDao.updateBalance(conta.getId(), conta.getBalance());
                    Log.d("Transacao", "Saldo atualizado para: " + conta.getBalance());

                    // Verificação final
                    Bank contaConfirmacao = bankDao.getBankById(conta.getId());
                    Log.d("Transacao", "Saldo confirmado no banco: " + contaConfirmacao.getBalance());
                } else {
                    Log.e("Transacao", "Conta não encontrada com ID: " + t.getContaId());
                    return false;
                }

                return true;
            }

            @Override
            protected void onPostExecute(Boolean sucesso) {
                if (sucesso) {
                    Toast.makeText(CadastroTransacaoActivity.this, "Transação salva!", Toast.LENGTH_SHORT).show();
                    setResult(RESULT_OK);
                    finish();
                } else {
                    Toast.makeText(CadastroTransacaoActivity.this, "Erro ao salvar transação", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute(transacao);
    }
}
