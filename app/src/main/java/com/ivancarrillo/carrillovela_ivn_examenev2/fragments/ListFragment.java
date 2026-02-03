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

        return view;
    }

    private void loadActiveStore() {
        activeStore = realm.where(Store.class).equalTo("isActive", true).findFirst();

        if (activeStore != null) {
            // Si la tienda mostrada es la misma que la activa, no recargamos la lista
            // Esto evita que el scroll salte al modificar cantidades
            if (activeStore.getName().equals(tvActiveStoreName.getText().toString()) && adapter != null) {
                return;
            }

            tvActiveStoreName.setText(activeStore.getName());

            // Configurar el adaptador con los items de ESTA tienda
            adapter = new ProductAdapter(getContext(), activeStore.getItems(), new ProductAdapter.OnItemClickListener() {
                @Override
                public void onQuantityChange(Item item, int newQuantity) {
                    updateQuantity(item, newQuantity);
                }
            });
            recyclerView.setAdapter(adapter);

        } else {
            tvActiveStoreName.setText("No hay tienda activa seleccionada");
            recyclerView.setAdapter(null);
        }
    }

    private void updateQuantity(Item item, int newQuantity) {
        realm.executeTransaction(r -> {
            item.setQuantity(newQuantity);
            // También reiniciar estado de comprado si la cantidad llega a 0
            // "Si la cantidad de un producto vuelve a 0 desde el fragment Lista: ... Su estado de marcado se reinicia automáticamente."
            if (newQuantity == 0) {
                item.setPurchased(false);
            }
        });
        // Buscar índice del ítem en la lista de la tienda activa
        if (activeStore != null && activeStore.getItems() != null) {
            int index = activeStore.getItems().indexOf(item);
            if (index != -1) {
                adapter.notifyItemChanged(index);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        loadActiveStore();
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
