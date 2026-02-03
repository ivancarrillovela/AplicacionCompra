package com.ivancarrillo.carrillovela_ivn_examenev2.adapters;

import android.content.Context;
import android.graphics.Color;
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
    private OnStoreClickListener listener;

    public interface OnStoreClickListener {
        void onStoreClick(Store store);
        void onStoreLongClick(Store store);
    }

    public StoreAdapter(Context context, List<Store> stores, OnStoreClickListener listener) {
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
        Store store = stores.get(position);

        holder.tvName.setText(store.getName());
        holder.tvAddress.setText(store.getAddress());
        holder.tvLocation.setText(String.format(Locale.getDefault(), "Lat: %.4f Lon: %.4f", store.getLat(), store.getLon()));

        if (store.isActive()) {
            holder.tvActiveBadge.setVisibility(View.VISIBLE);
            holder.itemView.setBackgroundColor(Color.parseColor("#E8F5E9")); // Light Green background
        } else {
            holder.tvActiveBadge.setVisibility(View.GONE);
            holder.itemView.setBackgroundColor(Color.WHITE);
        }

        holder.itemView.setOnClickListener(v -> listener.onStoreClick(store));
        holder.itemView.setOnLongClickListener(v -> {
            listener.onStoreLongClick(store);
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return stores != null ? stores.size() : 0;
    }

    public static class StoreViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvAddress, tvLocation, tvActiveBadge;

        public StoreViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvStoreName);
            tvAddress = itemView.findViewById(R.id.tvStoreAddress);
            tvLocation = itemView.findViewById(R.id.tvStoreLocation);
            tvActiveBadge = itemView.findViewById(R.id.tvActiveBadge);
        }
    }
}
