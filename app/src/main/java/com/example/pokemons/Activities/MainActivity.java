package com.example.pokemons.Activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.pokemons.R;
import com.example.pokemons.databinding.ActivityMainBinding;
import com.example.pokemons.ui.HomeFragment;
import com.example.pokemons.ui.SettingsFragment;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private Fragment homeFragment = null;
    private Fragment settingsFragment = null;
    private Fragment usingFragment = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);

        if (getSupportFragmentManager().isStateSaved()) {
            homeFragment = getSupportFragmentManager().getFragment(savedInstanceState, "home_fragment");
            usingFragment = getSupportFragmentManager().getFragment(savedInstanceState, "fragment");
            settingsFragment = getSupportFragmentManager().getFragment(savedInstanceState, "settings_fragment");
        }

        Log.d("flog", "onCreate: " + homeFragment);
        if (homeFragment == null) {
            homeFragment = new HomeFragment();
        }

        if (settingsFragment == null) {
            settingsFragment = new SettingsFragment();
        }

        if (usingFragment == null) {
            usingFragment = homeFragment;
        }

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(usingFragment);

        setOnItemSelectedListener();
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @SuppressLint("NonConstantResourceId")
    private void setOnItemSelectedListener() {
        binding.navigationBar.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    usingFragment = homeFragment;
                    break;

                case R.id.navigation_settings:
                    usingFragment = settingsFragment;
                    break;
            }

            replaceFragment(usingFragment);
            return true;
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        getSupportFragmentManager().putFragment(outState, "fragment", usingFragment);
        getSupportFragmentManager().putFragment(outState, "home_fragment", homeFragment);
        if (settingsFragment.isAdded()) {
            getSupportFragmentManager().putFragment(outState, "settings_fragment", settingsFragment);
        }
    }
}
