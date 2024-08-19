package com.example.projecteve;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.projecteve.activity.login;
import com.example.projecteve.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private ActivityMainBinding binding;
    private NavHostFragment navHostFragment;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
        initNavigation();
    }



    private void initNavigation() {
        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        navController = navHostFragment.getNavController();
        NavigationUI.setupWithNavController(binding.bottomNavigation, navController);

        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            // Get the current destination
            int currentDestinationId = navController.getCurrentDestination().getId();

            // Check if the selected item is the current one, if so, don't navigate
            if (id == currentDestinationId) {
                return true;
            }

            // Navigate and clear back stack
            navigateToFragment(id);
            return true;
        });
    }

    private void navigateToFragment(int fragmentId) {
        // Check the current destination and pop back stack until we reach the main destination
        // If we are navigating to a new fragment, pop back stack until the start destination
        if (fragmentId != navController.getCurrentDestination().getId()) {
            // Pop back stack to remove previous fragments
            navController.popBackStack(navController.getGraph().getStartDestination(), false);
            // Navigate to the selected fragment
            navController.navigate(fragmentId);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null) {
            Intent intent = new Intent(MainActivity.this, login.class);
            startActivity(intent);
            finish();
        }
    }
}
