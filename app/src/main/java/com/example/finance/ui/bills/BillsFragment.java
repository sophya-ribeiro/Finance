package com.example.finance.ui.bills;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finance.R;
import com.example.finance.adapters.TransacaoAdapter;
import com.example.finance.data.Transacao;

public class BillsFragment extends Fragment {

    private BillsViewModel viewModel;
    private TransacaoAdapter adapter;
    private long contaId = 1; // Exemplo

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Toast.makeText(getContext(), "BillsFragment iniciado", Toast.LENGTH_SHORT).show();
        Log.d("BillsFragment", "onViewCreated chamado");
        RecyclerView recycler = view.findViewById(R.id.recyclerViewTransacoes);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new TransacaoAdapter();
        recycler.setAdapter(adapter);


        viewModel = new ViewModelProvider(this,
                new ViewModelProvider.AndroidViewModelFactory(requireActivity().getApplication()))
                .get(BillsViewModel.class);

        long contaId = 1; // Use o ID da conta correta
        Log.d("BillsFragment", "Iniciando observação das transações");
        viewModel.getTransacoesPorConta(contaId).observe(getViewLifecycleOwner(), transacoes -> {
            Log.d("BillsFragment", "Observer chamado com " + (transacoes == null ? "null" : transacoes.size()) + " transações");
            adapter.setLista(transacoes);
            if (transacoes != null && !transacoes.isEmpty()) {
                StringBuilder builder = new StringBuilder();
                for (Transacao t : transacoes) {
                    builder.append(t.getTipo())
                            .append(" - R$ ")
                            .append(t.getValor())
                            .append(" (")
                            .append(t.getDescricao())
                            .append(")\n");
                }
                Toast.makeText(getContext(), builder.toString(), Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getContext(), "Nenhuma transação encontrada", Toast.LENGTH_SHORT).show();
            }
        });
    }
}