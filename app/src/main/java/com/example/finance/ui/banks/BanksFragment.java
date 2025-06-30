package com.example.finance.ui.banks;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finance.R;
import com.example.finance.adapters.BankAdapter;
import com.example.finance.data.Bank;
import com.example.finance.ui.bank.BankViewModel;

import java.util.List;

public class BanksFragment extends Fragment {

    private RecyclerView recyclerView;
    private BankAdapter adapter;
    private BankViewModel bankViewModel;

    private int userId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_banks, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        recyclerView = view.findViewById(R.id.recyclerViewBanks);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new BankAdapter();
        recyclerView.setAdapter(adapter);

        userId = 1;
        bankViewModel = new ViewModelProvider(this).get(BankViewModel.class);
        bankViewModel.getBanksByUser(userId).observe(getViewLifecycleOwner(), new Observer<List<Bank>>() {
            @Override
            public void onChanged(List<Bank> banks) {
                adapter.setBankList(banks);
            }
        });
    }
}
