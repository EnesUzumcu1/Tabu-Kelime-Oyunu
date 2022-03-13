package com.example.tabu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.tabu.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Eğer daha önceden atama yapılmadıysa 0 olarak kalıyor ve default değerler atanıyor.
        if(Ayarlar.secilenSure == 0) {
            //default değerleri atanıyor.
            Ayarlar.secilenTakimSayisi = getResources().getInteger(R.integer.Takim_Sayisi);
            Ayarlar.secilenSure = getResources().getInteger(R.integer.Oyun_Suresi);
            Ayarlar.secilenPasHakki = getResources().getInteger(R.integer.Pas_Hakki);
            Ayarlar.secilenTurSayisi = getResources().getInteger(R.integer.Tur_Sayisi);
        }
        binding.girisBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(TakimAdiBelirle.class);
            }
        });
        binding.ayarlarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(Ayarlar.class);
            }
        });
    }
    public void openActivity(Class<?> classAdi) {
        Intent intent = new Intent(this, classAdi);
        startActivity(intent);
    }
}
