package com.enesuzumcu.tabu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;

import com.enesuzumcu.tabu.databinding.FragmentSetTeamNameBinding;

public class SetTeamNameFragment extends Fragment {
    private FragmentSetTeamNameBinding binding;
    private NavController navController;

    int takimSayisi;
    public static Takimlar takim1, takim2, takim3, takim4;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSetTeamNameBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tanimlamalar();

        binding.etTeam1.setOnClickListener(v -> binding.etTeam1.setText(""));
        binding.etTeam2.setOnClickListener(v -> binding.etTeam2.setText(""));
        binding.etTeam3.setOnClickListener(v -> binding.etTeam3.setText(""));
        binding.etTeam4.setOnClickListener(v -> binding.etTeam4.setText(""));

        binding.btnSave.setOnClickListener(v -> {
            boolean kontrol1 = !binding.etTeam1.getText().toString().equals("");
            boolean kontrol2 = !binding.etTeam2.getText().toString().equals("");
            boolean kontrol3 = !binding.etTeam3.getText().toString().equals("");
            boolean kontrol4 = !binding.etTeam4.getText().toString().equals("");
            if (takimSayisi == 2) {
                if (kontrol1 && kontrol2) {
                    takim1.setTakimAdi(binding.etTeam1.getText().toString());
                    takim2.setTakimAdi(binding.etTeam2.getText().toString());
                    navController.navigate(R.id.action_setTeamNameFragment_to_gameFragment);
                } else {
                    Toast.makeText(requireContext(), "Takım adı boş bırakılmaz.", Toast.LENGTH_SHORT).show();
                }
            } else if (takimSayisi == 3) {
                if (kontrol1 && kontrol2 && kontrol3) {
                    takim1.setTakimAdi(binding.etTeam1.getText().toString());
                    takim2.setTakimAdi(binding.etTeam2.getText().toString());
                    takim3.setTakimAdi(binding.etTeam3.getText().toString());
                    navController.navigate(R.id.action_setTeamNameFragment_to_gameFragment);
                } else {
                    Toast.makeText(requireContext(), "Takım adı boş bırakılmaz.", Toast.LENGTH_SHORT).show();
                }
            } else if (takimSayisi == 4) {
                if (kontrol1 && kontrol2 && kontrol3 && kontrol4) {
                    takim1.setTakimAdi(binding.etTeam1.getText().toString());
                    takim2.setTakimAdi(binding.etTeam2.getText().toString());
                    takim3.setTakimAdi(binding.etTeam3.getText().toString());
                    takim4.setTakimAdi(binding.etTeam4.getText().toString());
                    navController.navigate(R.id.action_setTeamNameFragment_to_gameFragment);
                } else {
                    Toast.makeText(requireContext(), "Takım adı boş bırakılmaz.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(requireContext(), "Bir hata oluştu. Bir daha deneyin.", Toast.LENGTH_SHORT).show();
            }
        });

        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(),new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // Handle the back button event
                NavOptions options = new NavOptions.Builder().setPopUpTo(R.id.nav_graph,true).build();
                navController.navigate(R.id.homeFragment,null,options);
            }
        });
    }

    private void tanimlamalar() {
        navController = NavHostFragment.findNavController(this);
        takim1 = new Takimlar();
        takim2 = new Takimlar();
        takim3 = new Takimlar();
        takim4 = new Takimlar();

        takim1.setTakimPuani(0);
        takim2.setTakimPuani(0);
        takim3.setTakimPuani(0);
        takim4.setTakimPuani(0);

        if (SettingsFragment.secilenTakimSayisi == 2) {
            takimSayisi = 2;
            binding.llTeam3.setVisibility(View.GONE);
            binding.llTeam4.setVisibility(View.GONE);
        } else if (SettingsFragment.secilenTakimSayisi == 3) {
            takimSayisi = 3;
            binding.llTeam4.setVisibility(View.GONE);
        } else if (SettingsFragment.secilenTakimSayisi == 4) {
            takimSayisi = 4;
        } else {
            takimSayisi = 2;
            binding.llTeam3.setVisibility(View.GONE);
            binding.llTeam4.setVisibility(View.GONE);
        }
    }
}
