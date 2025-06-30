package com.example.finance.adapters;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finance.R;
import com.example.finance.data.Bank;

import java.util.List;

public class BankAdapter extends RecyclerView.Adapter<BankAdapter.BankViewHolder> {

    private List<Bank> bankList;

    public void setBankList(List<Bank> banks) {
        this.bankList = banks;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BankViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_bank, parent, false);
        return new BankViewHolder(itemView);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull BankViewHolder holder, int position) {
        Bank bank = bankList.get(position);
        holder.tvBankName.setText(bank.name);
        holder.tvBankType.setText(bank.type);
        holder.tvBankBalance.setText("Saldo: R$ " + String.format("%.2f", bank.balance));
        Log.d("BankAdapter", "Banco: " + bank.name + " | Saldo: " + bank.balance);
    }

    @Override
    public int getItemCount() {
        return bankList != null ? bankList.size() : 0;
    }

    static class BankViewHolder extends RecyclerView.ViewHolder {
        TextView tvBankName, tvBankType, tvBankBalance;

        public BankViewHolder(@NonNull View itemView) {
            super(itemView);
            tvBankName = itemView.findViewById(R.id.tvBankName);
            tvBankType = itemView.findViewById(R.id.tvBankType);
            tvBankBalance = itemView.findViewById(R.id.tvBankBalance);
        }
    }
}
