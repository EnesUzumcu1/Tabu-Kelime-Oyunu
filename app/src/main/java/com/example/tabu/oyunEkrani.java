package com.example.tabu;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tabu.databinding.ActivityOyunekraniBinding;

import java.util.ArrayList;
import java.util.Random;

public class oyunEkrani extends AppCompatActivity {
    private ActivityOyunekraniBinding binding;

    int saniye, saniyeHatirla, miliSaniye, pasSayisi, alinanPuan, siraBelirle = 0, suankiTurSayisi = 1, index =siraBelirle, databaseUzunlugu;
    private long geriTusuBasilisSuresi;
    Toast geriTusuToast;
    ArrayList<Takimlar> takimlarList;
    ArrayList<TextView> takimPuanlariList;
    DatabaseAccess databaseAccess;
    boolean takimAdiYazdir = false;

    ArrayList<Integer> uretilenRakamlar = new ArrayList<Integer>();
    private final Random _random = new Random();

    @Override
    public void onBackPressed() {

        if(geriTusuBasilisSuresi +2000 > System.currentTimeMillis()){
            geriTusuToast.cancel();
            openActivity(MainActivity.class);
            super.onBackPressed();
            return;
        }
        else{
            geriTusuToast = Toast.makeText(getApplicationContext(),"Giriş sayfasına gitmek için bir daha tıklayın.",Toast.LENGTH_SHORT);
            geriTusuToast.show();
        }
        geriTusuBasilisSuresi = System.currentTimeMillis();
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityOyunekraniBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        saniye = Ayarlar.secilenSure;
        miliSaniye = saniye*1000;
        saniyeHatirla = saniye;
        pasSayisi =Ayarlar.secilenPasHakki;
        alinanPuan = 0;

        binding.butonlarLayout.setVisibility(View.GONE);

        takimlarList = new ArrayList<>();
        takimPuanlariList = new ArrayList<>();

        databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
        databaseAccess.open();
        databaseUzunlugu = databaseAccess.databaseUzunlugu();
        databaseAccess.close();

        binding.pasHakkiTextView.setText(""+ pasSayisi);
        binding.saniyeTxtView.setText(""+ saniye);
        binding.puanTextView.setText(""+ alinanPuan);

        //takım sayısına göre oyun arasında skor tablosunu hazırlıyor
        binding.takimAdi1TextView.setText(TakimAdiBelirle.takim1.getTakimAdi());
        binding.takimAdi2TextView.setText(TakimAdiBelirle.takim2.getTakimAdi());
        binding.takimPuani1TextView.setText(String.valueOf(TakimAdiBelirle.takim1.getTakimPuani()));
        binding.takimPuani2TextView.setText(String.valueOf(TakimAdiBelirle.takim2.getTakimPuani()));
        binding.takim3BilgiLayout.setVisibility(View.GONE);
        binding.takim4BilgiLayout.setVisibility(View.GONE);
        takimlarList.add(TakimAdiBelirle.takim1);
        takimlarList.add(TakimAdiBelirle.takim2);
        takimPuanlariList.add(binding.takimPuani1TextView);
        takimPuanlariList.add(binding.takimPuani2TextView);

        if(Ayarlar.secilenTakimSayisi >= 3 ){
            binding.takimAdi3TextView.setText(TakimAdiBelirle.takim3.getTakimAdi());
            binding.takimPuani3TextView.setText(String.valueOf(TakimAdiBelirle.takim3.getTakimPuani()));
            binding.takim3BilgiLayout.setVisibility(View.VISIBLE);
            takimlarList.add(TakimAdiBelirle.takim3);
            takimPuanlariList.add(binding.takimPuani3TextView);
        }
        if(Ayarlar.secilenTakimSayisi == 4){
            binding.takimAdi4TextView.setText(TakimAdiBelirle.takim4.getTakimAdi());
            binding.takimPuani4TextView.setText(String.valueOf(TakimAdiBelirle.takim4.getTakimPuani()));
            binding.takim4BilgiLayout.setVisibility(View.VISIBLE);
            takimlarList.add(TakimAdiBelirle.takim4);
            takimPuanlariList.add(binding.takimPuani4TextView);
        }
        //Skor tablosu hazırlıgı sonu

        binding.takimAdiYazTextView.setText(takimlarList.get(0).getTakimAdi());
        binding.baslatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseAccess.open();
                kelimeleriYazdir(random_Sayi_uretici(databaseUzunlugu));
                databaseAccess.close();

                binding.sonucLayout.setVisibility(View.GONE);
                binding.pasPuanLayout.setVisibility(View.VISIBLE);
                binding.takimSaniyeLayout.setVisibility(View.VISIBLE);
                pasSayisi = Ayarlar.secilenPasHakki;
                binding.pasHakkiTextView.setText(""+ pasSayisi);
                alinanPuan = 0;
                binding.puanTextView.setText(""+ alinanPuan);
                //eger siraBelirle takim sayisina esit olursa 1 sonraki tura gecildigi anlasiliyor
                if(takimlarList.size() == siraBelirle){
                    siraBelirle =0;
                    suankiTurSayisi++;
                }
                binding.takimAdiYazTextView.setText(takimlarList.get(siraBelirle).getTakimAdi());
                siraBelirle++;

                binding.kelimelerLayout.setVisibility(View.VISIBLE);
                binding.butonlarLayout.setVisibility(View.VISIBLE);
                //miliSaniyeye +100 eklenmesinin sebebi Android 10'dan önceki sürümlerde zamanlayıcıda oluşan bugu önlemek.
                //eğer eklenmezse Android 10 öncesinde saniye 1 olunca duruyor ve 0 değerini almıyor.
                //Android 10'daki çalışan sistemi bozmamak için artık saniye kontrolü yaparak azaltma işlemi yapılıyor.
                new CountDownTimer(miliSaniye+100,1000){
                    @Override
                    public void onTick(long millisUntilFinished) {
                        if(saniye>0) {
                            binding.saniyeTxtView.setText(String.valueOf(saniye--));
                        }
                    }
                    @Override
                    public void onFinish() {
                        binding.saniyeTxtView.setText(String.valueOf(saniye));
                        if(Ayarlar.secilenTurSayisi == suankiTurSayisi && takimlarList.size() == siraBelirle){
                            binding.baslatBtn.setEnabled(false);
                            binding.baslatBtn.setText("Oyun Bitti");
                            binding.sonucLayout.setVisibility(View.VISIBLE);
                            binding.kelimelerLayout.setVisibility(View.GONE);
                            binding.butonlarLayout.setVisibility(View.GONE);
                            binding.pasPuanLayout.setVisibility(View.GONE);
                            binding.takimSaniyeLayout.setVisibility(View.GONE);
                            takimAdiYazdir = true;
                            databaseAccess.close();
                        }
                        else{
                            binding.sonucLayout.setVisibility(View.VISIBLE);
                            binding.kelimelerLayout.setVisibility(View.GONE);
                            binding.butonlarLayout.setVisibility(View.GONE);
                            saniye = saniyeHatirla;
                            binding.baslatBtn.setText("Sonraki Takım");
                        }
                        //tur sonucu alınan puan ile önceki puan toplanarak yeni puan elde ediliyor. index deger araligi takım sayısı kadardır.
                        int oncekiPuan;
                        if (takimlarList.size() <= index) {
                            index = 0;
                        }
                        oncekiPuan = takimlarList.get(index).getTakimPuani();
                        takimlarList.get(index).setTakimPuani(alinanPuan+oncekiPuan);
                        takimPuanlariList.get(index).setText(String.valueOf(takimlarList.get(index).getTakimPuani()));
                        index++;
                        //önceki puanla toplama sonu.
                        if(takimAdiYazdir){
                            kazanan_takim();
                        }
                    }
                }.start();
            }
        });

        binding.pasButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pasSayisi>0){
                    pasSayisi--;
                    binding.pasHakkiTextView.setText(""+ pasSayisi);
                    databaseAccess.open();
                    kelimeleriYazdir(random_Sayi_uretici(databaseUzunlugu));
                    databaseAccess.close();
                }
            }
        });

        binding.tabuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alinanPuan--;
                binding.puanTextView.setText(""+alinanPuan);
                databaseAccess.open();
                kelimeleriYazdir(random_Sayi_uretici(databaseUzunlugu));
                databaseAccess.close();
            }
        });

        binding.dogruButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alinanPuan++;
                binding.puanTextView.setText(""+alinanPuan);
                databaseAccess.open();
                kelimeleriYazdir(random_Sayi_uretici(databaseUzunlugu));
                databaseAccess.close();
            }
        });

    }
    public void openActivity(Class<?> classAdi) {
        Intent intent = new Intent(this, classAdi);
        startActivity(intent);
    }
    public void kelimeleriYazdir(int id){
        binding.anlatilanKelimeTextView.setText(databaseAccess.getKelimeler(id,1));
        binding.tabu1TextView.setText(databaseAccess.getKelimeler(id,2));
        binding.tabu2TextView.setText(databaseAccess.getKelimeler(id,3));
        binding.tabu3TextView.setText(databaseAccess.getKelimeler(id,4));
        binding.tabu4TextView.setText(databaseAccess.getKelimeler(id,5));
        binding.tabu5TextView.setText(databaseAccess.getKelimeler(id,6));
    }
    public int random_Sayi_uretici(int bitis){
        boolean donguKontrol =true;
        int x = _random.nextInt(bitis+1);
        if(!uretilenRakamlar.contains(x)){
            uretilenRakamlar.add(x);
            donguKontrol = false;
        }
        while(donguKontrol){
            x = _random.nextInt(bitis+1);
            if(!uretilenRakamlar.contains(x)){
                uretilenRakamlar.add(x);
                donguKontrol = false;
                break;
            }
        }
        return x;
    }
    @SuppressLint("SetTextI18n")
    public void kazanan_takim(){
        String kazanan;
        int buyukluk_kontrol;
        boolean beraberlik = true;
        for(int i=0; i<takimlarList.size();i++){
            buyukluk_kontrol = 0;
            for(int j=0;j<takimlarList.size();j++){
                if(takimlarList.get(i).getTakimPuani() >takimlarList.get(j).getTakimPuani()){
                    buyukluk_kontrol++;
                    if(buyukluk_kontrol ==takimlarList.size()-1){
                        beraberlik = false;
                        kazanan = takimlarList.get(i).getTakimAdi();
                        binding.kazananTextView.setText("Kazanan <"+kazanan+"> Tebrikler!");
                    }
                }
            }
        }
        if(beraberlik){
            binding.kazananTextView.setText("Kazanan Yok.");
        }
    }
}
