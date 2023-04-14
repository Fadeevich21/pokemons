package com.example.pokemons.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.pokemons.R;
import com.example.pokemons.databinding.ActivityMainBinding;
import com.example.pokemons.ui.HomeFragment;
import com.example.pokemons.ui.SettingsFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private String activeFragmentTag = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);

//        NetworkConnect.setConnectStates(NetworkConnectStates.NOT_CONNECT);

        String fragmentTag = "home";
        if (savedInstanceState != null)
            fragmentTag = savedInstanceState.getString("active_fragment_tag");
        else
            setupFragmentManager();

        detachAllFragments(new ArrayList<>(Arrays.asList("home", "settings")));
        changeFragment(fragmentTag);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setOnItemSelectedListener();
    }

    private void setupFragmentManager() {
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.frame_layout, HomeFragment.class, null, "home")
                .setReorderingAllowed(true)
                .add(R.id.frame_layout, SettingsFragment.class, null, "settings")
                .commit();
        getSupportFragmentManager().executePendingTransactions();
    }

    private void detachAllFragments(List<String> fragmentTags) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        for (String fragmentTag : fragmentTags) {
            Fragment fragment = fragmentManager.findFragmentByTag(fragmentTag);
            fragmentTransaction.detach(Objects.requireNonNull(fragment));
        }

        fragmentTransaction.commit();
    }

    @SuppressLint("DetachAndAttachSameFragment")
    private void changeFragment(String fragmentTag) {
        if (Objects.equals(activeFragmentTag, fragmentTag))
            return;

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (activeFragmentTag != null)
            fragmentTransaction.detach(Objects.requireNonNull(fragmentManager.findFragmentByTag(activeFragmentTag)));
        fragmentTransaction.attach(Objects.requireNonNull(fragmentManager.findFragmentByTag(fragmentTag)))
                .commit();

        activeFragmentTag = fragmentTag;
    }

    @SuppressLint("NonConstantResourceId")
    private void setOnItemSelectedListener() {
        binding.navigationBar.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    changeFragment("home");
                    break;

                case R.id.navigation_settings:
                    changeFragment("settings");
                    break;
            }

            return true;
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("active_fragment_tag", activeFragmentTag);
    }
}
