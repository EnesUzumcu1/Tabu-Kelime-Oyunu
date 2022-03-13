package com.example.tabu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.tabu.databinding.ActivityTakimAdiBelirleBinding;

public class TakimAdiBelirle extends AppCompatActivity {
    private ActivityTakimAdiBelirleBinding binding;
    int takimSayisi;
    public static Takimlar takim1, takim2, takim3, takim4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityTakimAdiBelirleBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        takim1 = new Takimlar();
        takim2 = new Takimlar();
        takim3 = new Takimlar();
        takim4 = new Takimlar();

        takim1.setTakimPuani(0);
        takim2.setTakimPuani(0);
        takim3.setTakimPuani(0);
        takim4.setTakimPuani(0);

        if(Ayarlar.secilenTakimSayisi == 2){
            takimSayisi = 2;
            binding.takim3Layout.setVisibility(View.GONE);
            binding.takim4Layout.setVisibility(View.GONE);
        }
        else if(Ayarlar.secilenTakimSayisi == 3){
            takimSayisi = 3;
            binding.takim4Layout.setVisibility(View.GONE);
        }
        else if(Ayarlar.secilenTakimSayisi == 4){
            takimSayisi= 4;
        }
        else{
            takimSayisi = 2;
            binding.takim3Layout.setVisibility(View.GONE);
            binding.takim4Layout.setVisibility(View.GONE);
        }

        binding.takim1EditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.takim1EditText.setText("");
            }
        });
        binding.takim2EditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.takim2EditText.setText("");
            }
        });
        binding.takim3EditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.takim3EditText.setText("");
            }
        });
        binding.takim4EditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.takim4EditText.setText("");
            }
        });

        binding.kaydetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean kontrol1 = !binding.takim1EditText.getText().toString().equals("");
                boolean kontrol2 = !binding.takim2EditText.getText().toString().equals("");
                boolean kontrol3 = !binding.takim3EditText.getText().toString().equals("");
                boolean kontrol4 = !binding.takim4EditText.getText().toString().equals("");
                if(takimSayisi == 2){
                    if(kontrol1 && kontrol2){
                        takim1.setTakimAdi(binding.takim1EditText.getText().toString());
                        takim2.setTakimAdi(binding.takim2EditText.getText().toString());
                        openActivity(oyunEkrani.class);
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Takım adı boş bırakılmaz.",Toast.LENGTH_SHORT).show();
                    }
                }
                else if(takimSayisi == 3){
                    if(kontrol1 && kontrol2 && kontrol3) {
                        takim1.setTakimAdi(binding.takim1EditText.getText().toString());
                        takim2.setTakimAdi(binding.takim2EditText.getText().toString());
                        takim3.setTakimAdi(binding.takim3EditText.getText().toString());
                        openActivity(oyunEkrani.class);
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Takım adı boş bırakılmaz.",Toast.LENGTH_SHORT).show();
                    }
                }
                else if(takimSayisi == 4){
                    if(kontrol1 && kontrol2 && kontrol3 && kontrol4) {
                        takim1.setTakimAdi(binding.takim1EditText.getText().toString());
                        takim2.setTakimAdi(binding.takim2EditText.getText().toString());
                        takim3.setTakimAdi(binding.takim3EditText.getText().toString());
                        takim4.setTakimAdi(binding.takim4EditText.getText().toString());
                        openActivity(oyunEkrani.class);
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Takım adı boş bırakılmaz.",Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(),"Bir hata oluştu. Bir daha deneyin.",Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
    public void openActivity(Class<?> classAdi) {
        Intent intent = new Intent(this, classAdi);
        startActivity(intent);
    }
}