package com.ivancarrillo.carrillovela_ivn_examenev2.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.ivancarrillo.carrillovela_ivn_examenev2.R;
import com.ivancarrillo.carrillovela_ivn_examenev2.models.Item;

import java.util.List;
import java.util.Locale;

public class SummaryAdapter extends RecyclerView.Adapter<SummaryAdapter.SummaryViewHolder> {

    private List<Item> items;
    private Context context;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Item item);
    }

    public SummaryAdapter(Context context, List<Item> items, OnItemClickListener listener) {
        this.context = context;
        this.items = items;
        this.listener = listener;
    }

    @NonNull
    @Override
    public SummaryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_summary, parent, false);
        return new SummaryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SummaryViewHolder holder, int position) {
        Item item = items.get(position);

        holder.tvName.setText(item.getName());
        holder.tvDetails.setText(String.format(Locale.getDefault(), "x%d · %.2f €", item.getQuantity(), item.getPrice()));
        double subtotal = item.getQuantity() * item.getPrice();
        holder.tvSubtotal.setText(String.format(Locale.getDefault(), "Subtotal: %.2f €", subtotal));

        if (item.isPurchased()) {
            holder.cardView.setCardBackgroundColor(Color.parseColor("#C8E6C9")); // Light Green for purchased
        } else {
            holder.cardView.setCardBackgroundColor(Color.WHITE);
        }

        holder.itemView.setOnClickListener(v -> listener.onItemClick(item));
    }

    @Override
    public int getItemCount() {
        return items != null ? items.size() : 0;
    }

    public static class SummaryViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvDetails, tvSubtotal;
        CardView cardView;

        public SummaryViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvSummaryName);
            tvDetails = itemView.findViewById(R.id.tvSummaryDetails);
            tvSubtotal = itemView.findViewById(R.id.tvSummarySubtotal);
            cardView = (CardView) itemView.findViewById(R.id.cardView);
        }
    }
}
