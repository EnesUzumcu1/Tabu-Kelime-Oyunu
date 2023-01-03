package com.enesuzumcu.tabu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.enesuzumcu.tabu.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private NavController navController;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tanimlamalar();
        binding.btnStart.setOnClickListener(v -> navController.navigate(R.id.action_homeFragment_to_setTeamNameFragment));
        binding.btnSettings.setOnClickListener(v -> navController.navigate(R.id.action_homeFragment_to_settingsFragment));
    }

    private void tanimlamalar() {
        navController = NavHostFragment.findNavController(this);
        //Eğer daha önceden atama yapılmadıysa 0 olarak kalıyor ve default değerler atanıyor.
        if (SettingsFragment.secilenSure == 0) {
            //default değerleri atanıyor.
            SettingsFragment.secilenTakimSayisi = getResources().getInteger(R.integer.Takim_Sayisi);
            SettingsFragment.secilenSure = getResources().getInteger(R.integer.Oyun_Suresi);
            SettingsFragment.secilenPasHakki = getResources().getInteger(R.integer.Pas_Hakki);
            SettingsFragment.secilenTurSayisi = getResources().getInteger(R.integer.Tur_Sayisi);
        }
    }
}
