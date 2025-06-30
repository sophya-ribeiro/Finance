package com.example.finance.ui.bank;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.finance.R;
import com.example.finance.data.Bank;

public class BankFragment extends Fragment {

    private EditText etBankName;
    private Spinner spinnerBankType;
    private Button btnSaveBank;

    private BankViewModel bankViewModel;

    private int userId; // Pegar do bundle ou SharedPreferences conforme fluxo

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bank, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        etBankName = view.findViewById(R.id.etBankName);
        spinnerBankType = view.findViewById(R.id.spinnerBankType);
        btnSaveBank = view.findViewById(R.id.btnSaveBank);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.bank_types_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBankType.setAdapter(adapter);

        bankViewModel = new ViewModelProvider(this).get(BankViewModel.class);

        userId = 1; // Ajustar conforme fluxo real

        btnSaveBank.setOnClickListener(v -> {
            String name = etBankName.getText().toString().trim();
            String type = spinnerBankType.getSelectedItem().toString();

            if (name.isEmpty()) {
                Toast.makeText(getContext(), getString(R.string.error_empty_bank_name), Toast.LENGTH_SHORT).show();
                return;
            }

            Bank bank = new Bank(name, type, userId, 0);
            bankViewModel.insert(bank);

            Toast.makeText(getContext(), getString(R.string.bank_saved_success), Toast.LENGTH_SHORT).show();

            etBankName.setText("");
            spinnerBankType.setSelection(0);
        });
    }
}
