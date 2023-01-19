package com.enesuzumcu.tabu.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.enesuzumcu.tabu.R;
import com.enesuzumcu.tabu.ui.home.viewmodel.HomeViewModel;
import com.enesuzumcu.tabu.databinding.FragmentHomeBinding;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
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
        HomeViewModel viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
    }
}
