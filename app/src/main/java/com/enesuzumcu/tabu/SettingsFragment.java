package com.enesuzumcu.tabu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.enesuzumcu.tabu.databinding.FragmentSettingsBinding;

public class SettingsFragment extends Fragment {
    private FragmentSettingsBinding binding;
    private NavController navController;

    public static int secilenTakimSayisi = 0, secilenSure = 0, secilenTurSayisi = 0, secilenPasHakki = 0;
    int geciciSecilenTakimSayisi, geciciSecilenSure, geciciSecilenTurSayisi, geciciSecilenPasHakki;
    ArrayAdapter<Integer> takimSayisiAdapter, sureAdapter, turSayisiAdapter, pasHakkiAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tanimlamalar();

        binding.btnSave.setOnClickListener(v -> {
            secilenTakimSayisi = geciciSecilenTakimSayisi;
            secilenSure = geciciSecilenSure;
            secilenTurSayisi = geciciSecilenTurSayisi;
            secilenPasHakki = geciciSecilenPasHakki;

            Toast.makeText(requireContext(), "Kaydedildi.", Toast.LENGTH_SHORT).show();
            navController.navigate(R.id.action_settingsFragment_to_setTeamNameFragment);
        });
    }

    private void tanimlamalar() {
        navController = NavHostFragment.findNavController(this);

        setTeamNumberSpinnerAdapter();
        setTimeSpinnerAdapter();
        setRoundNumberSpinnerAdapter();
        setPassNumberSpinnerAdapter();
    }

    private void setTeamNumberSpinnerAdapter() {
        Integer[] takimDegerleri = new Integer[]{2, 3, 4};
        takimSayisiAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, takimDegerleri);
        takimSayisiAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.sTeamNumber.setAdapter(takimSayisiAdapter);

        binding.sTeamNumber.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                geciciSecilenTakimSayisi = Integer.parseInt(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //seçilen değerin sonraki açılışta seçili gelmesi için.
        binding.sTeamNumber.setSelection(takimSayisiAdapter.getPosition(secilenTakimSayisi));
        binding.sTeamNumber.setDropDownVerticalOffset(100);
    }

    private void setTimeSpinnerAdapter() {
        Integer[] sureDegerleri = new Integer[]{5, 60, 120, 150, 180, 210, 240};
        sureAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, sureDegerleri);
        sureAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.sTime.setAdapter(sureAdapter);

        binding.sTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                geciciSecilenSure = Integer.parseInt(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //seçilen değerin sonraki açılışta seçili gelmesi için.
        binding.sTime.setSelection(sureAdapter.getPosition(secilenSure));
        binding.sTime.setDropDownVerticalOffset(100);
    }

    private void setRoundNumberSpinnerAdapter() {
        Integer[] turDegerleri = new Integer[]{1, 3, 5, 7, 9, 15};
        turSayisiAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, turDegerleri);
        turSayisiAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.sRoundNumber.setAdapter(turSayisiAdapter);

        binding.sRoundNumber.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                geciciSecilenTurSayisi = Integer.parseInt(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //seçilen değerin sonraki açılışta seçili gelmesi için.
        binding.sRoundNumber.setSelection(turSayisiAdapter.getPosition(secilenTurSayisi));
        binding.sRoundNumber.setDropDownVerticalOffset(100);
    }

    private void setPassNumberSpinnerAdapter() {
        Integer[] pasDegerleri = new Integer[]{5, 10, 15, 20, 25, 100};
        pasHakkiAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, pasDegerleri);
        pasHakkiAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.sPassNumber.setAdapter(pasHakkiAdapter);

        binding.sPassNumber.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                geciciSecilenPasHakki = Integer.parseInt(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //seçilen değerin sonraki açılışta seçili gelmesi için.
        binding.sPassNumber.setSelection(pasHakkiAdapter.getPosition(secilenPasHakki));
        binding.sPassNumber.setDropDownVerticalOffset(100);
    }
}