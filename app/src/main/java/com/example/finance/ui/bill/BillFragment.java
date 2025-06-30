package com.example.finance.ui.bill;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.finance.R;
import com.example.finance.data.Bank;
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
        bankViewModel = new ViewModelProvider(this).get(BankViewModel.class);

        spinnerAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerContas.setAdapter(spinnerAdapter);

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
    }
}
