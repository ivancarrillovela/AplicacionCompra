package com.ivancarrillo.carrillovela_ivn_examenev2.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ivancarrillo.carrillovela_ivn_examenev2.R;
import com.ivancarrillo.carrillovela_ivn_examenev2.adapters.ProductAdapter;
import com.ivancarrillo.carrillovela_ivn_examenev2.models.Item;
import com.ivancarrillo.carrillovela_ivn_examenev2.models.Store;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

public class ListFragment extends Fragment {

    private Realm realm;
    private RecyclerView recyclerView;
    private TextView tvActiveStoreName;
    private ProductAdapter adapter;
    private Store activeStore;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        realm = Realm.getDefaultInstance();
        recyclerView = view.findViewById(R.id.rvProducts);
        tvActiveStoreName = view.findViewById(R.id.tvActiveStoreName);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        loadActiveStore();

        // Listen for store changes (if active store changes elsewhere)
        realm.addChangeListener(new RealmChangeListener<Realm>() {
            @Override
            public void onChange(Realm realm) {
                loadActiveStore();
            }
        });

        return view;
    }

    private void loadActiveStore() {
        activeStore = realm.where(Store.class).equalTo("isActive", true).findFirst();

        if (activeStore != null) {
            tvActiveStoreName.setText("Tienda Activa: " + activeStore.getName());
            
            // Need to setup adapter with the items of THIS store
            // Since items is a RealmList, it's live. But adapter expects List<Item>.
            // We can pass the RealmList directly as it implements List.
            
            if (adapter == null) {
                 adapter = new ProductAdapter(getContext(), activeStore.getItems(), (item, newQuantity) -> {
                    updateQuantity(item, newQuantity);
                });
                recyclerView.setAdapter(adapter);
            } else {
                // If adapter exists, we might need to swap data if the store changed completely
                // But simplified: recreating adapter is safer for exam context to ensure correct list binding 
                // Alternatively, ProductAdapter could have a updateData method.
                // Let's just recreate for simplicity if store changes.
                adapter = new ProductAdapter(getContext(), activeStore.getItems(), (item, newQuantity) -> {
                    updateQuantity(item, newQuantity);
                });
                recyclerView.setAdapter(adapter);
            }
            
        } else {
            tvActiveStoreName.setText("No hay tienda activa seleccionada");
            recyclerView.setAdapter(null);
        }
    }

    private void updateQuantity(Item item, int newQuantity) {
        realm.executeTransaction(r -> {
            item.setQuantity(newQuantity);
            // Also reset purchased status if quantity becomes 0?
            // "Si la cantidad de un producto vuelve a 0 desde el fragment Lista: ... Su estado de marcado se reinicia automáticamente."
            if (newQuantity == 0) {
                item.setPurchased(false);
            }
        });
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(adapter != null) adapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (realm != null) {
            realm.close();
        }
    }
}
