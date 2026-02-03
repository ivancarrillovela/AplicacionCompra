package com.ivancarrillo.carrillovela_ivn_examenev2.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ivancarrillo.carrillovela_ivn_examenev2.R;
import com.ivancarrillo.carrillovela_ivn_examenev2.models.Store;

import java.util.List;
import java.util.Locale;

public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.StoreViewHolder> {

    private List<Store> stores;
    private Context context;

    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Store store);
        void onItemLongClick(Store store);
    }
    public StoreAdapter(Context context, List<Store> stores, OnItemClickListener listener) {
        this.context = context;
        this.stores = stores;
        this.listener = listener;
    }

    @NonNull
    @Override
    public StoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_store, parent, false);
        return new StoreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StoreViewHolder holder, int position) {
        holder.assignData(stores.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return stores != null ? stores.size() : 0;
    }

    public static class StoreViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvAddress, tvLocation, tvActiveBadge;
        // Mantener referencia a la vista para acceso al contexto si es necesario
        // View itemView ya está disponible vía super

        public StoreViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvStoreName);
            tvAddress = itemView.findViewById(R.id.tvStoreAddress);
            tvLocation = itemView.findViewById(R.id.tvStoreLocation);
            tvActiveBadge = itemView.findViewById(R.id.tvActiveBadge);
        }

        public void assignData(final Store store, final OnItemClickListener listener) {
            Context context = itemView.getContext();
            tvName.setText(store.getName());
            tvAddress.setText(store.getAddress());
            tvLocation.setText(String.format(Locale.getDefault(), "Lat: %.4f Lon: %.4f", store.getLat(), store.getLon()));

            com.google.android.material.card.MaterialCardView card = itemView.findViewById(R.id.cardStore);

            if (store.isActive()) {
                tvActiveBadge.setVisibility(View.VISIBLE);
                card.setStrokeColor(context.getResources().getColor(R.color.primary));
                card.setStrokeWidth(4);
                card.setCardBackgroundColor(context.getResources().getColor(R.color.white));
                card.setCardElevation(8);
            } else {
                tvActiveBadge.setVisibility(View.GONE);
                card.setStrokeColor(context.getResources().getColor(R.color.divider));
                card.setStrokeWidth(2);
                card.setCardBackgroundColor(context.getResources().getColor(R.color.white));
                card.setCardElevation(2);
            }

            card.setOnClickListener(v -> listener.onItemClick(store));
            card.setOnLongClickListener(v -> {
                listener.onItemLongClick(store);
                return true;
            });
        }
    }
}
