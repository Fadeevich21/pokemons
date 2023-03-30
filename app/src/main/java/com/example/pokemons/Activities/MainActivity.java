package com.example.pokemons.Activities;

import android.annotation.SuppressLint;
import android.os.Bundle;

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
    private final Fragment homeFragment = new HomeFragment();
    private final Fragment settingFragment = new SettingsFragment();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(homeFragment);

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
                    replaceFragment(homeFragment);
                    break;

                case R.id.navigation_settings:
                    replaceFragment(settingFragment);
                    break;
            }

            return true;
        });
    }
}
