package com.enesuzumcu.tabu.ui.setteamname;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;

import com.enesuzumcu.tabu.R;
import com.enesuzumcu.tabu.data.model.Settings;
import com.enesuzumcu.tabu.databinding.FragmentSetTeamNameBinding;
import com.enesuzumcu.tabu.ui.setteamname.viewmodel.SetTeamNameViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SetTeamNameFragment extends Fragment {
    private FragmentSetTeamNameBinding binding;
    private NavController navController;
    private SetTeamNameViewModel viewModel;

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

        binding.btnSave.setOnClickListener(v -> {
            boolean check1 = !binding.etTeam1.getText().toString().equals("");
            boolean check2 = !binding.etTeam2.getText().toString().equals("");
            boolean check3 = !binding.etTeam3.getText().toString().equals("");
            boolean check4 = !binding.etTeam4.getText().toString().equals("");

            if (check1 && check2) {
                viewModel.setTeam1andTeam2Names(binding.etTeam1.getText().toString(), binding.etTeam2.getText().toString());
            }
            if (Settings.settings.getTeamCount() >= 3) {
                if(check3) {
                    viewModel.setTeam3Name(binding.etTeam3.getText().toString());
                }
            }
            if (Settings.settings.getTeamCount() == 4) {
                if(check4) {
                    viewModel.setTeam4Name(binding.etTeam4.getText().toString());
                }
            }
            navController.navigate(R.id.action_setTeamNameFragment_to_gameFragment);
        });

        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // Handle the back button event
                NavOptions options = new NavOptions.Builder().setPopUpTo(R.id.nav_graph, true).build();
                navController.navigate(R.id.homeFragment, null, options);
            }
        });
    }

    private void tanimlamalar() {
        navController = NavHostFragment.findNavController(this);
        viewModel = new ViewModelProvider(this).get(SetTeamNameViewModel.class);

        if (Settings.settings.getTeamCount() == 2) {
            binding.llTeam3.setVisibility(View.GONE);
            binding.llTeam4.setVisibility(View.GONE);
        } else if (Settings.settings.getTeamCount() == 3) {
            binding.llTeam4.setVisibility(View.GONE);
        }
    }
}
