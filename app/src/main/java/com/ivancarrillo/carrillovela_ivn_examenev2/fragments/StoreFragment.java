package com.ivancarrillo.carrillovela_ivn_examenev2.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ivancarrillo.carrillovela_ivn_examenev2.R;
import com.ivancarrillo.carrillovela_ivn_examenev2.adapters.StoreAdapter;
import com.ivancarrillo.carrillovela_ivn_examenev2.app.Utils;
import com.ivancarrillo.carrillovela_ivn_examenev2.models.Store;

import io.realm.Realm;
import io.realm.RealmChangeListener;
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
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        stores = realm.where(Store.class).findAll();

        adapter = new StoreAdapter(getContext(), stores, new StoreAdapter.OnStoreClickListener() {
            @Override
            public void onStoreClick(Store store) {
                setActiveStore(store);
            }

            @Override
            public void onStoreLongClick(Store store) {
                openMap(store);
            }
        });

        recyclerView.setAdapter(adapter);

        // Listen for changes (e.g. when one becomes active/inactive)
        stores.addChangeListener(new RealmChangeListener<RealmResults<Store>>() {
            @Override
            public void onChange(RealmResults<Store> stores) {
                adapter.notifyDataSetChanged();
            }
        });

        return view;
    }

    private void setActiveStore(Store selectedStore) {
        realm.executeTransaction(r -> {
            // Desactivate all
            RealmResults<Store> allStores = r.where(Store.class).findAll();
            for (Store s : allStores) {
                s.setActive(false);
            }
            // Activate selected
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
            // Fallback if maps not installed, or try generic view
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
