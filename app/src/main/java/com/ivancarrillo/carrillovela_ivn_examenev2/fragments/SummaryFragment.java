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
    private View emptyStateLayout, contentLayout, bottomActions; // Cambiado a View para ser genérico
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
        
        // Nuevas Vistas para Estado Vacío
        emptyStateLayout = view.findViewById(R.id.emptyStateLayout);
        contentLayout = view.findViewById(R.id.contentLayout);
        bottomActions = view.findViewById(R.id.bottomActions);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        btnShare.setOnClickListener(v -> shareList());
        btnClear.setOnClickListener(v -> clearList());

        loadActiveStore();

        // Escuchar cambios en tienda (si cambia tienda activa o items actualizados)
        realm.addChangeListener(new RealmChangeListener<Realm>() {
            @Override
            public void onChange(Realm realm) {
                loadActiveStore();
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadActiveStore();
    }



    private void loadActiveStore() {
        // Prevención de crash en actualizaciones asíncronas de Realm
        if (!isAdded()) return;
        activeStore = realm.where(Store.class).equalTo("isActive", true).findFirst();

        if (activeStore != null) {
            tvActiveStoreName.setText(activeStore.getName());

            // Filtrar items con cantidad > 0
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
            
            // Lógica de alternancia
            if (summaryItems.isEmpty()) {
                 if (contentLayout != null) contentLayout.setVisibility(View.GONE);
                 if (bottomActions != null) bottomActions.setVisibility(View.GONE);
                 if (emptyStateLayout != null) emptyStateLayout.setVisibility(View.VISIBLE);
            } else {
                 if (contentLayout != null) contentLayout.setVisibility(View.VISIBLE);
                 if (bottomActions != null) bottomActions.setVisibility(View.VISIBLE);
                 if (emptyStateLayout != null) emptyStateLayout.setVisibility(View.GONE);
            }

            // Actualizar textos de UI
            tvTotalItems.setText(getString(R.string.format_total_items, summaryItems.size()));
            tvTotalUnits.setText(getString(R.string.format_total_units, totalUnits));
            tvTotalPrice.setText(String.format(Locale.getDefault(), "%.2f €", totalPrice));

            if (adapter == null) {
                adapter = new SummaryAdapter(getContext(), summaryItems, item -> {
                    togglePurchased(item);
                });
                recyclerView.setAdapter(adapter);
            } else {
                adapter.notifyDataSetChanged();
            }

        } else {
            tvActiveStoreName.setText(getString(R.string.msg_no_active_store));
            summaryItems.clear();
            if (adapter != null) adapter.notifyDataSetChanged();
            
            // Mostrar Estado Vacío
            if (contentLayout != null) contentLayout.setVisibility(View.GONE);
            if (bottomActions != null) bottomActions.setVisibility(View.GONE);
            if (emptyStateLayout != null) emptyStateLayout.setVisibility(View.VISIBLE);

            tvTotalItems.setText(getString(R.string.format_total_items, 0));
            tvTotalUnits.setText(getString(R.string.format_total_units, 0));
            tvTotalPrice.setText("0.00 €");
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
            loadActiveStore();
            Toast.makeText(getContext(), getString(R.string.msg_list_cleared), Toast.LENGTH_SHORT).show();
        }
    }

    private void shareList() {
        if (activeStore != null && !summaryItems.isEmpty()) {
            String body = Utils.buildShoppingListEmailBody(activeStore, summaryItems);
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:"));
            intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.subject_share_list, activeStore.getName()));
            intent.putExtra(Intent.EXTRA_TEXT, body);
            
            if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                startActivity(Intent.createChooser(intent, getString(R.string.chooser_share_title)));
            } else {
                 Toast.makeText(getContext(), getString(R.string.error_no_email_app), Toast.LENGTH_SHORT).show();
            }
        } else {
             Toast.makeText(getContext(), getString(R.string.error_list_empty), Toast.LENGTH_SHORT).show();
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
