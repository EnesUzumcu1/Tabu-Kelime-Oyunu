package com.enesuzumcu.tabu.ui.game;

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
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;

import com.enesuzumcu.tabu.R;
import com.enesuzumcu.tabu.data.model.Settings;
import com.enesuzumcu.tabu.data.model.Words;
import com.enesuzumcu.tabu.ui.game.viewmodel.GameViewModel;
import com.enesuzumcu.tabu.data.model.Takimlar;
import com.enesuzumcu.tabu.databinding.FragmentGameBinding;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class GameFragment extends Fragment {
    private FragmentGameBinding binding;
    private NavController navController;

    private GameViewModel viewModel;

    private long backPressTime;
    private CountDownTimer mCountDownTimer;

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

        if (viewModel.turn > viewModel.maxTurn) {
            binding.btnStart.setOnClickListener(v -> navigateHomeFragment());
            binding.btnStart.setText(R.string.gameOver);
            binding.pasPuanLayout.setVisibility(View.GONE);
            binding.takimSaniyeLayout.setVisibility(View.GONE);
            binding.tvWinner.setText(viewModel.winner());
        } else {
            binding.btnStart.setOnClickListener(v -> {
                kelimeleriYazdir();

                binding.llResult.setVisibility(View.GONE);
                binding.pasPuanLayout.setVisibility(View.VISIBLE);
                binding.takimSaniyeLayout.setVisibility(View.VISIBLE);
                binding.pasHakkiTextView.setText(String.valueOf(viewModel.gameStatus.getPass()));

                binding.puanTextView.setText(String.valueOf(viewModel.gameStatus.getScore()));

                binding.takimAdiYazTextView.setText(viewModel.getTeamName());

                binding.llWords.setVisibility(View.VISIBLE);
                binding.llButtons.setVisibility(View.VISIBLE);

                binding.btnSuccess.setEnabled(true);
                binding.btnTaboo.setEnabled(true);
                binding.btnPass.setEnabled(true);
                timer();
            });
        }

        binding.btnPass.setOnClickListener(v -> {
            if (viewModel.gameStatus.getPass() > 0) {
                viewModel.decreasePass();
                binding.pasHakkiTextView.setText(String.valueOf(viewModel.gameStatus.getPass()));
                viewModel.getWords();
                kelimeleriYazdir();
            }
        });

        binding.btnTaboo.setOnClickListener(v -> {
            viewModel.decreaseScore();
            binding.puanTextView.setText(String.valueOf(viewModel.gameStatus.getScore()));
            viewModel.getWords();
            kelimeleriYazdir();
        });

        binding.btnSuccess.setOnClickListener(v -> {
            viewModel.increaseScore();
            binding.puanTextView.setText(String.valueOf(viewModel.gameStatus.getScore()));
            viewModel.getWords();
            kelimeleriYazdir();
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
        if (backPressTime + 2000 > System.currentTimeMillis()) {
            toast.cancel();
            navigateHomeFragment();
        } else {
            toast.show();
        }
        backPressTime = System.currentTimeMillis();
    }

    private void timer() {
        //miliSaniyeye +100 eklenmesinin sebebi Android 10'dan önceki sürümlerde zamanlayıcıda oluşan bugu önlemek.
        //eğer eklenmezse Android 10 öncesinde saniye 1 olunca duruyor ve 0 değerini almıyor.
        //Android 10'daki çalışan sistemi bozmamak için artık saniye kontrolü yaparak azaltma işlemi yapılıyor.
        mCountDownTimer = new CountDownTimer((viewModel.second * 1000L) + 100, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (viewModel.second > 0) {
                    binding.saniyeTxtView.setText(String.valueOf(viewModel.second));
                    viewModel.decreaseSecond();
                }
            }

            @Override
            public void onFinish() {
                binding.saniyeTxtView.setText(String.valueOf(viewModel.second));
                binding.llResult.setVisibility(View.VISIBLE);
                binding.llWords.setVisibility(View.GONE);
                binding.llButtons.setVisibility(View.GONE);
                getTeamScoreTV().setText(String.valueOf(viewModel.updateScore()));
                if (viewModel.turn == viewModel.maxTurn) {
                    binding.btnStart.setOnClickListener(v -> navigateHomeFragment());
                    binding.btnStart.setText(R.string.gameOver);
                    binding.pasPuanLayout.setVisibility(View.GONE);
                    binding.takimSaniyeLayout.setVisibility(View.GONE);
                    binding.tvWinner.setText(viewModel.winner());
                } else {
                    binding.btnSuccess.setEnabled(false);
                    binding.btnTaboo.setEnabled(false);
                    binding.btnPass.setEnabled(false);
                    binding.btnStart.setText(R.string.nextTeam);
                }

                viewModel.increaseTurn();
                viewModel.resetState();
            }
        }.start();

    }

    private void tanimlamalar() {
        navController = NavHostFragment.findNavController(this);
        viewModel = new ViewModelProvider(this).get(GameViewModel.class);

        binding.pasHakkiTextView.setText(String.valueOf(viewModel.gameStatus.getPass()));
        binding.saniyeTxtView.setText(String.valueOf(viewModel.second));
        binding.puanTextView.setText(String.valueOf(viewModel.gameStatus.getScore()));

        if (viewModel.inTheGame) {
            binding.llWords.setVisibility(View.VISIBLE);
            binding.llButtons.setVisibility(View.VISIBLE);
            binding.llResult.setVisibility(View.GONE);
        } else {
            binding.llButtons.setVisibility(View.GONE);
        }
        //takım sayısına göre oyun arasında skor tablosunu hazırlıyor
        binding.tvTeamName1.setText(Takimlar.team1.getTakimAdi());
        binding.tvTeamName2.setText(Takimlar.team2.getTakimAdi());
        binding.tvTeamScore1.setText(String.valueOf(Takimlar.team1.getTakimPuani()));
        binding.tvTeamScore2.setText(String.valueOf(Takimlar.team2.getTakimPuani()));
        binding.takim3BilgiLayout.setVisibility(View.GONE);
        binding.takim4BilgiLayout.setVisibility(View.GONE);

        if (Settings.settings.getTeamCount() >= 3) {
            binding.tvTeamName3.setText(Takimlar.team3.getTakimAdi());
            binding.tvTeamScore3.setText(String.valueOf(Takimlar.team3.getTakimPuani()));
            binding.takim3BilgiLayout.setVisibility(View.VISIBLE);
        }
        if (Settings.settings.getTeamCount() == 4) {
            binding.tvTeamName4.setText(Takimlar.team4.getTakimAdi());
            binding.tvTeamScore4.setText(String.valueOf(Takimlar.team4.getTakimPuani()));
            binding.takim4BilgiLayout.setVisibility(View.VISIBLE);
        }
        //Skor tablosu hazırlıgı sonu

        binding.takimAdiYazTextView.setText(viewModel.getTeamName());

    }

    private void navigateHomeFragment() {
        NavOptions options = new NavOptions.Builder().setPopUpTo(R.id.nav_graph, true).build();
        navController.navigate(R.id.action_gameFragment_to_homeFragment, null, options);
        viewModel.resetState();
    }

    public void kelimeleriYazdir() {
        Words words = viewModel.words;
        binding.tvTargetWord.setText(words.AnlatilanKelime);
        binding.tvTabooWord1.setText(words.Tabu1);
        binding.tvTabooWord2.setText(words.Tabu2);
        binding.tvTabooWord3.setText(words.Tabu3);
        binding.tvTabooWord4.setText(words.Tabu4);
        binding.tvTabooWord5.setText(words.Tabu5);
    }

    public TextView getTeamScoreTV() {
        switch (viewModel.turn % Settings.settings.getTeamCount()) {
            case 0:
                return binding.tvTeamScore1;
            case 1:
                return binding.tvTeamScore2;
            case 2:
                return binding.tvTeamScore3;
            case 3:
                return binding.tvTeamScore4;
            default:
                return null;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (viewModel.inTheGame) {
            kelimeleriYazdir();
            timer();
        }
    }
}
