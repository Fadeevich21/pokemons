package com.example.pokemons.Activities;

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

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private Fragment homeFragment;
    private Fragment settingFragment;
    private Fragment usingFragment;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            homeFragment = getSupportFragmentManager().getFragment(savedInstanceState, "home_fragment");
            settingFragment = getSupportFragmentManager().getFragment(savedInstanceState, "setting_fragment");
            usingFragment = getSupportFragmentManager().getFragment(savedInstanceState, "fragment");
        } else {
            homeFragment = new HomeFragment();
            settingFragment = new SettingsFragment();
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
                    usingFragment = settingFragment;
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
        getSupportFragmentManager().putFragment(outState, "setting_fragment", settingFragment);
    }
}
