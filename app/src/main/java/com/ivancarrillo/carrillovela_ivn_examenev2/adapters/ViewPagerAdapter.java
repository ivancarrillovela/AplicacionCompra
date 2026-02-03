package com.ivancarrillo.carrillovela_ivn_examenev2.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.ivancarrillo.carrillovela_ivn_examenev2.fragments.ListFragment;
import com.ivancarrillo.carrillovela_ivn_examenev2.fragments.StoreFragment;
import com.ivancarrillo.carrillovela_ivn_examenev2.fragments.SummaryFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new StoreFragment();
            case 1:
                return new ListFragment();
            case 2:
                return new SummaryFragment();
            default:
                return new StoreFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
