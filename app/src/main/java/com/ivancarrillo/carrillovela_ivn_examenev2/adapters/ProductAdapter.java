package com.ivancarrillo.carrillovela_ivn_examenev2.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ivancarrillo.carrillovela_ivn_examenev2.R;
import com.ivancarrillo.carrillovela_ivn_examenev2.models.Item;

import java.util.List;
import java.util.Locale;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private List<Item> items;
    private Context context;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onQuantityChange(Item item, int newQuantity);
    }

    public ProductAdapter(Context context, List<Item> items, OnItemClickListener listener) {
        this.context = context;
        this.items = items;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        holder.bind(items.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return items != null ? items.size() : 0;
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvCategory, tvPrice, tvQuantity;
        ImageButton btnIncrease, btnDecrease;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvProductName);
            tvCategory = itemView.findViewById(R.id.tvProductCategory);
            tvPrice = itemView.findViewById(R.id.tvProductPrice);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            btnIncrease = itemView.findViewById(R.id.btnIncrease);
            btnDecrease = itemView.findViewById(R.id.btnDecrease);
        }

        public void bind(final Item item, final OnItemClickListener listener) {
            tvName.setText(item.getName());
            tvCategory.setText(item.getCategory());
            tvPrice.setText(String.format(Locale.getDefault(), "%.2f €", item.getPrice()));
            tvQuantity.setText(String.valueOf(item.getQuantity()));

            btnIncrease.setOnClickListener(v -> {
                listener.onQuantityChange(item, item.getQuantity() + 1);
            });

            btnDecrease.setOnClickListener(v -> {
                if (item.getQuantity() > 0) {
                    listener.onQuantityChange(item, item.getQuantity() - 1);
                }
            });
        }
    }
}
