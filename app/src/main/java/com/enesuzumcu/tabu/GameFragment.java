package com.enesuzumcu.tabu;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;

import com.enesuzumcu.tabu.databinding.FragmentGameBinding;

import java.util.ArrayList;
import java.util.Random;

public class GameFragment extends Fragment {
    private FragmentGameBinding binding;
    private NavController navController;

    int saniye, saniyeHatirla, miliSaniye, pasSayisi, alinanPuan, siraBelirle = 0, suankiTurSayisi = 1, index = siraBelirle, databaseUzunlugu;
    private long geriTusuBasilisSuresi;
    ArrayList<Takimlar> takimlarList;
    ArrayList<TextView> takimPuanlariList;
    DatabaseAccess databaseAccess;
    boolean takimAdiYazdir = false;

    ArrayList<Integer> uretilenRakamlar = new ArrayList<>();
    private final Random _random = new Random();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentGameBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tanimlamalar();

        binding.btnStart.setOnClickListener(v -> {
            kelimeleriYazdir(randomNumberGenerator(databaseUzunlugu));

            binding.llResult.setVisibility(View.GONE);
            binding.pasPuanLayout.setVisibility(View.VISIBLE);
            binding.takimSaniyeLayout.setVisibility(View.VISIBLE);
            pasSayisi = SettingsFragment.secilenPasHakki;
            binding.pasHakkiTextView.setText(String.valueOf(pasSayisi));
            alinanPuan = 0;
            binding.puanTextView.setText(String.valueOf(alinanPuan));
            //eger siraBelirle takim sayisina esit olursa 1 sonraki tura gecildigi anlasiliyor
            if (takimlarList.size() == siraBelirle) {
                siraBelirle = 0;
                suankiTurSayisi++;
            }
            binding.takimAdiYazTextView.setText(takimlarList.get(siraBelirle).getTakimAdi());
            siraBelirle++;

            binding.llWords.setVisibility(View.VISIBLE);
            binding.llButtons.setVisibility(View.VISIBLE);
            //miliSaniyeye +100 eklenmesinin sebebi Android 10'dan önceki sürümlerde zamanlayıcıda oluşan bugu önlemek.
            //eğer eklenmezse Android 10 öncesinde saniye 1 olunca duruyor ve 0 değerini almıyor.
            //Android 10'daki çalışan sistemi bozmamak için artık saniye kontrolü yaparak azaltma işlemi yapılıyor.
            new CountDownTimer(miliSaniye + 100, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    if (saniye > 0) {
                        binding.saniyeTxtView.setText(String.valueOf(saniye--));
                    }
                }

                @Override
                public void onFinish() {
                    binding.saniyeTxtView.setText(String.valueOf(saniye));
                    if (SettingsFragment.secilenTurSayisi == suankiTurSayisi && takimlarList.size() == siraBelirle) {
                        binding.btnStart.setEnabled(false);
                        binding.btnStart.setText("Oyun Bitti");
                        binding.llResult.setVisibility(View.VISIBLE);
                        binding.llWords.setVisibility(View.GONE);
                        binding.llButtons.setVisibility(View.GONE);
                        binding.pasPuanLayout.setVisibility(View.GONE);
                        binding.takimSaniyeLayout.setVisibility(View.GONE);
                        takimAdiYazdir = true;
                        databaseAccess.close();
                    } else {
                        binding.llResult.setVisibility(View.VISIBLE);
                        binding.llWords.setVisibility(View.GONE);
                        binding.llButtons.setVisibility(View.GONE);
                        saniye = saniyeHatirla;
                        binding.btnStart.setText("Sonraki Takım");
                    }
                    //tur sonucu alınan puan ile önceki puan toplanarak yeni puan elde ediliyor. index deger araligi takım sayısı kadardır.
                    int oncekiPuan;
                    if (takimlarList.size() <= index) {
                        index = 0;
                    }
                    oncekiPuan = takimlarList.get(index).getTakimPuani();
                    takimlarList.get(index).setTakimPuani(alinanPuan + oncekiPuan);
                    takimPuanlariList.get(index).setText(String.valueOf(takimlarList.get(index).getTakimPuani()));
                    index++;
                    //önceki puanla toplama sonu.
                    if (takimAdiYazdir) {
                        winnerTeam();
                    }
                }
            }.start();
        });

        binding.btnPass.setOnClickListener(v -> {
            if (pasSayisi > 0) {
                pasSayisi--;
                binding.pasHakkiTextView.setText(String.valueOf(pasSayisi));
                kelimeleriYazdir(randomNumberGenerator(databaseUzunlugu));
            }
        });

        binding.btnTaboo.setOnClickListener(v -> {
            alinanPuan--;
            binding.puanTextView.setText(String.valueOf(alinanPuan));
            kelimeleriYazdir(randomNumberGenerator(databaseUzunlugu));
        });

        binding.btnTrue.setOnClickListener(v -> {
            alinanPuan++;
            binding.puanTextView.setText(String.valueOf(alinanPuan));
            kelimeleriYazdir(randomNumberGenerator(databaseUzunlugu));
        });

        //backpress basildiginda gerekli durum icin callback cagirildi
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // Handle the back button event
                onBackPressed();
            }
        });
    }

    public void onBackPressed() {
        Toast toast = Toast.makeText(requireContext(), "Giriş sayfasına gitmek için bir daha tıklayın.", Toast.LENGTH_SHORT);
        if (geriTusuBasilisSuresi + 2000 > System.currentTimeMillis()) {
            toast.cancel();
            NavOptions options = new NavOptions.Builder().setPopUpTo(R.id.nav_graph,true).build();
            navController.navigate(R.id.action_gameFragment_to_homeFragment,null,options);
        } else {
            toast.show();
        }
        geriTusuBasilisSuresi = System.currentTimeMillis();
    }

    private void tanimlamalar() {
        navController = NavHostFragment.findNavController(this);
        saniye = SettingsFragment.secilenSure;
        miliSaniye = saniye * 1000;
        saniyeHatirla = saniye;
        pasSayisi = SettingsFragment.secilenPasHakki;
        alinanPuan = 0;

        binding.llButtons.setVisibility(View.GONE);

        takimlarList = new ArrayList<>();
        takimPuanlariList = new ArrayList<>();

        databaseAccess = DatabaseAccess.getInstance(requireContext());
        databaseAccess.open();
        databaseUzunlugu = databaseAccess.databaseUzunlugu();
        databaseAccess.close();

        binding.pasHakkiTextView.setText(String.valueOf(pasSayisi));
        binding.saniyeTxtView.setText(String.valueOf(saniye));
        binding.puanTextView.setText(String.valueOf(alinanPuan));

        //takım sayısına göre oyun arasında skor tablosunu hazırlıyor
        binding.tvTeamName1.setText(SetTeamNameFragment.takim1.getTakimAdi());
        binding.tvTeamName2.setText(SetTeamNameFragment.takim2.getTakimAdi());
        binding.tvTeamScore1.setText(String.valueOf(SetTeamNameFragment.takim1.getTakimPuani()));
        binding.tvTeamScore2.setText(String.valueOf(SetTeamNameFragment.takim2.getTakimPuani()));
        binding.takim3BilgiLayout.setVisibility(View.GONE);
        binding.takim4BilgiLayout.setVisibility(View.GONE);
        takimlarList.add(SetTeamNameFragment.takim1);
        takimlarList.add(SetTeamNameFragment.takim2);
        takimPuanlariList.add(binding.tvTeamScore1);
        takimPuanlariList.add(binding.tvTeamScore2);

        if (SettingsFragment.secilenTakimSayisi >= 3) {
            binding.tvTeamName3.setText(SetTeamNameFragment.takim3.getTakimAdi());
            binding.tvTeamScore3.setText(String.valueOf(SetTeamNameFragment.takim3.getTakimPuani()));
            binding.takim3BilgiLayout.setVisibility(View.VISIBLE);
            takimlarList.add(SetTeamNameFragment.takim3);
            takimPuanlariList.add(binding.tvTeamScore3);
        }
        if (SettingsFragment.secilenTakimSayisi == 4) {
            binding.tvTeamName4.setText(SetTeamNameFragment.takim4.getTakimAdi());
            binding.tvTeamScore4.setText(String.valueOf(SetTeamNameFragment.takim4.getTakimPuani()));
            binding.takim4BilgiLayout.setVisibility(View.VISIBLE);
            takimlarList.add(SetTeamNameFragment.takim4);
            takimPuanlariList.add(binding.tvTeamScore4);
        }
        //Skor tablosu hazırlıgı sonu

        binding.takimAdiYazTextView.setText(takimlarList.get(0).getTakimAdi());

    }

    public void kelimeleriYazdir(int id) {
        databaseAccess.open();
        binding.tvTargetWord.setText(databaseAccess.getKelimeler(id, 1));
        binding.tvTabooWord1.setText(databaseAccess.getKelimeler(id, 2));
        binding.tvTabooWord2.setText(databaseAccess.getKelimeler(id, 3));
        binding.tvTabooWord3.setText(databaseAccess.getKelimeler(id, 4));
        binding.tvTabooWord4.setText(databaseAccess.getKelimeler(id, 5));
        binding.tvTabooWord5.setText(databaseAccess.getKelimeler(id, 6));
        databaseAccess.close();
    }

    public int randomNumberGenerator(int bitis) {
        boolean donguKontrol = true;
        int x = _random.nextInt(bitis + 1);
        if (!uretilenRakamlar.contains(x)) {
            uretilenRakamlar.add(x);
            donguKontrol = false;
        }
        while (donguKontrol) {
            x = _random.nextInt(bitis + 1);
            if (!uretilenRakamlar.contains(x)) {
                uretilenRakamlar.add(x);
                donguKontrol = false;
                break;
            }
        }
        return x;
    }

    @SuppressLint("SetTextI18n")
    public void winnerTeam() {
        String kazanan;
        int buyukluk_kontrol;
        boolean beraberlik = true;
        for (int i = 0; i < takimlarList.size(); i++) {
            buyukluk_kontrol = 0;
            for (int j = 0; j < takimlarList.size(); j++) {
                if (takimlarList.get(i).getTakimPuani() > takimlarList.get(j).getTakimPuani()) {
                    buyukluk_kontrol++;
                    if (buyukluk_kontrol == takimlarList.size() - 1) {
                        beraberlik = false;
                        kazanan = takimlarList.get(i).getTakimAdi();
                        binding.tvWinner.setText("Kazanan <" + kazanan + "> Tebrikler!");
                    }
                }
            }
        }
        if (beraberlik) {
            binding.tvWinner.setText("Kazanan Yok.");
        }
    }
}
