package com.example.finance.ui.bill;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.finance.R;
import com.example.finance.data.Bank;
import com.example.finance.data.Transacao;
import com.example.finance.databinding.FragmentBillBinding;
import com.example.finance.ui.bank.BankViewModel;

import java.util.ArrayList;
import java.util.List;

public class BillFragment extends Fragment {

    private Spinner spinnerContas;
    private BankViewModel bankViewModel;
    private ArrayAdapter<String> spinnerAdapter;
    private List<Bank> listaDeBancos;
    private int userId = 1;

    private BillViewModel billViewModel;

    private EditText editValor;
    private EditText editDescricao;
    private RadioGroup radioGroupTipo;
    private RadioButton radioEntrada;
    private RadioButton radioSaida;
    private Button buttonSalvar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bill, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        spinnerContas = view.findViewById(R.id.spinnerContas);
        editValor = view.findViewById(R.id.editValor);
        editDescricao = view.findViewById(R.id.editDescricao);
        radioGroupTipo = view.findViewById(R.id.radioGroupTipo);
        radioEntrada = view.findViewById(R.id.radioEntrada);
        radioSaida = view.findViewById(R.id.radioSaida);
        buttonSalvar = view.findViewById(R.id.buttonSalvar);

        spinnerAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerContas.setAdapter(spinnerAdapter);

        BankViewModel bankViewModel = new ViewModelProvider(this).get(BankViewModel.class);
        billViewModel = new ViewModelProvider(
                this,
                new ViewModelProvider.AndroidViewModelFactory(requireActivity().getApplication())
        ).get(BillViewModel.class);

        bankViewModel.getBanksByUser(userId).observe(getViewLifecycleOwner(), banks -> {
            listaDeBancos = banks; // Armazena lista completa caso precise usar os IDs depois
            List<String> nomesBancos = new ArrayList<>();
            for (Bank banco : banks) {
                nomesBancos.add(banco.getName()); // Supondo que o método seja getNome()
            }
            spinnerAdapter.clear();
            spinnerAdapter.addAll(nomesBancos);
            spinnerAdapter.notifyDataSetChanged();
        });

        buttonSalvar.setOnClickListener(v -> {
            try {
                String tipo = radioEntrada.isChecked() ? "entrada" : "saida";
                double valor = Double.parseDouble(editValor.getText().toString());
                String descricao = editDescricao.getText().toString();
                int bancoId = listaDeBancos.get(spinnerContas.getSelectedItemPosition()).getId();

                Transacao transacao = new Transacao();
                transacao.setTipo(tipo);
                transacao.setValor(valor);
                transacao.setDescricao(descricao);
                transacao.setContaId(bancoId);
                transacao.setData(System.currentTimeMillis());

                billViewModel.insert(transacao);
                Toast.makeText(getContext(), "Transação salva com sucesso", Toast.LENGTH_SHORT).show();

                // Limpar campos após salvar (opcional)
                editValor.setText("");
                editDescricao.setText("");
                radioGroupTipo.clearCheck();
                spinnerContas.setSelection(0);

            } catch (Exception e) {
                Toast.makeText(getContext(), "Erro ao salvar transação", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        });
    }
}
