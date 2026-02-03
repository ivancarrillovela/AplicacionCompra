package com.ivancarrillo.carrillovela_ivn_examenev2.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ivancarrillo.carrillovela_ivn_examenev2.R;
import com.ivancarrillo.carrillovela_ivn_examenev2.adapters.SummaryAdapter;
import com.ivancarrillo.carrillovela_ivn_examenev2.app.Utils;
import com.ivancarrillo.carrillovela_ivn_examenev2.models.Item;
import com.ivancarrillo.carrillovela_ivn_examenev2.models.Store;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.realm.Realm;
import io.realm.RealmChangeListener;

public class SummaryFragment extends Fragment {

    private Realm realm;
    private RecyclerView recyclerView;
    private TextView tvActiveStoreName, tvTotalItems, tvTotalUnits, tvTotalPrice;
    private Button btnShare, btnClear;
    private SummaryAdapter adapter;
    private Store activeStore;
    private List<Item> summaryItems = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_summary, container, false);

        realm = Realm.getDefaultInstance();
        recyclerView = view.findViewById(R.id.rvSummary);
        tvActiveStoreName = view.findViewById(R.id.tvActiveStoreNameSummary);
        tvTotalItems = view.findViewById(R.id.tvTotalItems);
        tvTotalUnits = view.findViewById(R.id.tvTotalUnits);
        tvTotalPrice = view.findViewById(R.id.tvTotalPrice);
        btnShare = view.findViewById(R.id.btnShare);
        btnClear = view.findViewById(R.id.btnClear);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        btnShare.setOnClickListener(v -> shareList());
        btnClear.setOnClickListener(v -> clearList());

        loadActiveStore();

        // Listen for store changes (if active store changes elsewhere or items updated)
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

            // Filter items with quantity > 0
            summaryItems.clear();
            double totalPrice = 0;
            int totalUnits = 0;

            for (Item item : activeStore.getItems()) {
                if (item.getQuantity() > 0) {
                    summaryItems.add(item);
                    totalUnits += item.getQuantity();
                    totalPrice += (item.getPrice() * item.getQuantity());
                }
            }

            // Update UI
            tvTotalItems.setText("Productos añadidos: " + summaryItems.size());
            tvTotalUnits.setText("Unidades totales: " + totalUnits);
            tvTotalPrice.setText(String.format(Locale.getDefault(), "Total: %.2f €", totalPrice));

            if (adapter == null) {
                adapter = new SummaryAdapter(getContext(), summaryItems, item -> {
                    togglePurchased(item);
                });
                recyclerView.setAdapter(adapter);
            } else {
                adapter.notifyDataSetChanged();
            }

        } else {
            tvActiveStoreName.setText("No hay tienda activa");
            summaryItems.clear(); // Clear list
            if (adapter != null) adapter.notifyDataSetChanged();
            tvTotalItems.setText("Productos añadidos: 0");
            tvTotalUnits.setText("Unidades totales: 0");
            tvTotalPrice.setText("Total: 0.00 €");
        }
    }

    private void togglePurchased(Item item) {
        realm.executeTransaction(r -> {
            item.setPurchased(!item.isPurchased());
        });
        adapter.notifyDataSetChanged();
    }

    private void clearList() {
        if (activeStore != null) {
            realm.executeTransaction(r -> {
                for (Item item : activeStore.getItems()) {
                    item.setQuantity(0);
                    item.setPurchased(false);
                }
            });
            loadActiveStore(); // Refresh UI
            Toast.makeText(getContext(), "Lista limpiada", Toast.LENGTH_SHORT).show();
        }
    }

    private void shareList() {
        if (activeStore != null && !summaryItems.isEmpty()) {
            String body = Utils.buildShoppingListEmailBody(activeStore, summaryItems);
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:")); // only email apps should handle this
            intent.putExtra(Intent.EXTRA_SUBJECT, "Lista de compra - " + activeStore.getName());
            intent.putExtra(Intent.EXTRA_TEXT, body);
            
            if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                startActivity(intent);
            } else {
                 Toast.makeText(getContext(), "No hay aplicación de correo instalada", Toast.LENGTH_SHORT).show();
            }
        } else {
             Toast.makeText(getContext(), "La lista está vacía", Toast.LENGTH_SHORT).show();
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
