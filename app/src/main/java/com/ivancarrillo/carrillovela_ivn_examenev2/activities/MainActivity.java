package com.ivancarrillo.carrillovela_ivn_examenev2.activities;

import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.ivancarrillo.carrillovela_ivn_examenev2.R;
import com.ivancarrillo.carrillovela_ivn_examenev2.adapters.ViewPagerAdapter;

public class MainActivity extends AppCompatActivity implements com.ivancarrillo.carrillovela_ivn_examenev2.fragments.StoreFragment.OnStoreSelectedListener {

    private ViewPager2 viewPager;
    private BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.viewPager);
        bottomNavigation = findViewById(R.id.bottom_navigation);

        // Configurar Adapter del ViewPager
        ViewPagerAdapter adapter = new ViewPagerAdapter(this);
        viewPager.setAdapter(adapter);

        // Sincronizar BottomNav -> ViewPager
        bottomNavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.nav_tiendas) {
                    viewPager.setCurrentItem(0);
                    return true;
                } else if (item.getItemId() == R.id.nav_lista) {
                    viewPager.setCurrentItem(1);
                    return true;
                } else if (item.getItemId() == R.id.nav_resumen) {
                    viewPager.setCurrentItem(2);
                    return true;
                }
                return false;
            }
        });

        // Sincronizar ViewPager -> BottomNav (Swipe)
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position) {
                    case 0: bottomNavigation.setSelectedItemId(R.id.nav_tiendas); break;
                    case 1: bottomNavigation.setSelectedItemId(R.id.nav_lista); break;
                    case 2: bottomNavigation.setSelectedItemId(R.id.nav_resumen); break;
                }
            }
        });
    }

    @Override
    public void onStoreSelected() {
        // Cambiar automáticamente al Fragment List cuando se selecciona una tienda
        viewPager.setCurrentItem(1);
    }
}