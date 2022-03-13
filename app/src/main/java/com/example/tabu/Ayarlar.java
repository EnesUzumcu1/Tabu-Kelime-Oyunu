package com.example.tabu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.tabu.databinding.ActivityAyarlarBinding;

public class Ayarlar extends AppCompatActivity {
    private ActivityAyarlarBinding binding;

    public static int secilenTakimSayisi =0,secilenSure=0,secilenTurSayisi=0,secilenPasHakki=0;
    int geciciSecilenTakimSayisi, geciciSecilenSure, geciciSecilenTurSayisi, geciciSecilenPasHakki;
    ArrayAdapter<Integer> takimSayisiAdapter,sureAdapter,turSayisiAdapter,pasHakkiAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAyarlarBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        final Integer[] takimDegerleri = new Integer[]{2, 3, 4};
        Integer[] sureDegerleri = new Integer[]{5, 60, 120, 150, 180, 210, 240};
        Integer[] turDegerleri = new Integer[]{1, 3, 5, 7 , 9, 15};
        Integer[] pasDegerleri = new Integer[]{5, 10, 15, 20, 25, 100};

        takimSayisiAdapter = new ArrayAdapter<>(Ayarlar.this,android.R.layout.simple_list_item_1,takimDegerleri);
        takimSayisiAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.takimSayisiSpinner.setAdapter(takimSayisiAdapter);

        binding.takimSayisiSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                geciciSecilenTakimSayisi = Integer.parseInt(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //seçilen değerin sonraki açılışta seçili gelmesi için.
        binding.takimSayisiSpinner.setSelection(takimSayisiAdapter.getPosition(secilenTakimSayisi));
        binding.takimSayisiSpinner.setDropDownVerticalOffset(100);

        sureAdapter = new ArrayAdapter<>(Ayarlar.this,android.R.layout.simple_list_item_1,sureDegerleri);
        sureAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.sureSpinner.setAdapter(sureAdapter);

        binding.sureSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                geciciSecilenSure = Integer.parseInt(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //seçilen değerin sonraki açılışta seçili gelmesi için.
        binding.sureSpinner.setSelection(sureAdapter.getPosition(secilenSure));
        binding.sureSpinner.setDropDownVerticalOffset(100);

        turSayisiAdapter = new ArrayAdapter<>(Ayarlar.this,android.R.layout.simple_list_item_1,turDegerleri);
        turSayisiAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.turSayisiSpinner.setAdapter(turSayisiAdapter);

        binding.turSayisiSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                geciciSecilenTurSayisi = Integer.parseInt(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //seçilen değerin sonraki açılışta seçili gelmesi için.
        binding.turSayisiSpinner.setSelection(turSayisiAdapter.getPosition(secilenTurSayisi));
        binding.turSayisiSpinner.setDropDownVerticalOffset(100);

        pasHakkiAdapter = new ArrayAdapter<>(Ayarlar.this,android.R.layout.simple_list_item_1,pasDegerleri);
        pasHakkiAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.pasSayisiSpinner.setAdapter(pasHakkiAdapter);

        binding.pasSayisiSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                geciciSecilenPasHakki = Integer.parseInt(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //seçilen değerin sonraki açılışta seçili gelmesi için.
        binding.pasSayisiSpinner.setSelection(pasHakkiAdapter.getPosition(secilenPasHakki));
        binding.pasSayisiSpinner.setDropDownVerticalOffset(100);

        binding.kaydetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                secilenTakimSayisi = geciciSecilenTakimSayisi;
                secilenSure = geciciSecilenSure;
                secilenTurSayisi = geciciSecilenTurSayisi;
                secilenPasHakki = geciciSecilenPasHakki;

                Toast.makeText(getApplicationContext(),"Kaydedildi.",Toast.LENGTH_SHORT).show();
                openActivity(MainActivity.class);
            }
        });
    }
    public void openActivity(Class<?> classAdi) {
        Intent intent = new Intent(this, classAdi);
        startActivity(intent);
    }
}