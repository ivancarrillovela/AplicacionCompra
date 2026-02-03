package com.ivancarrillo.carrillovela_ivn_examenev2.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.ivancarrillo.carrillovela_ivn_examenev2.R;
import com.ivancarrillo.carrillovela_ivn_examenev2.adapters.StoreAdapter;
import com.ivancarrillo.carrillovela_ivn_examenev2.app.Utils;
import com.ivancarrillo.carrillovela_ivn_examenev2.models.Store;

import io.realm.Realm;
import io.realm.RealmResults;

public class StoreFragment extends Fragment {

    private Realm realm;
    private RecyclerView recyclerView;
    private StoreAdapter adapter;
    private RealmResults<Store> stores;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_store, container, false);

        realm = Realm.getDefaultInstance();
        recyclerView = view.findViewById(R.id.rvStores);
        recyclerView.setLayoutManager(new androidx.recyclerview.widget.GridLayoutManager(getContext(), 2));

        stores = realm.where(Store.class).findAll();

        adapter = new StoreAdapter(getContext(), stores, new StoreAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Store store) {
                setActiveStore(store);
            }

            @Override
            public void onItemLongClick(Store store) {
                openMap(store);
            }
        });

        recyclerView.setAdapter(adapter);

        // Escuchar cambios (ej. cuando una se activa/desactiva)
        stores.addChangeListener(stores -> adapter.notifyDataSetChanged());

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    // Interfaz para comunicación
    private void setActiveStore(Store selectedStore) {
        // Transacción Realm para asegurar consistencia:
        // Primero desactivamos toas y luego activamos la seleccionada.
        realm.executeTransaction(r -> {
            // Desactivar todas
            RealmResults<Store> allStores = r.where(Store.class).findAll();
            for (Store s : allStores) {
                s.setActive(false);
            }
            // Activar seleccionada
            selectedStore.setActive(true);
        });
    }

    private void openMap(Store store) {
        String uriString = Utils.openStoreInMaps(store);
        Uri gmmIntentUri = Uri.parse(uriString);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        if (mapIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(mapIntent);
        } else {
            // Alternativa si no hay maps instalados, o intentar vista genérica
             Intent fallbackIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
             startActivity(fallbackIntent);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (realm != null) {
            realm.close();
        }
    }
}
