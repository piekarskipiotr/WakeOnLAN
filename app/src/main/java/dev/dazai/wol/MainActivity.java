package dev.dazai.wol;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.navigation.NavigationView;
import dev.dazai.wol.databinding.ActivityMainBinding;
import dev.dazai.wol.databinding.NoInternetConnectionDailogBinding;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private ActivityMainBinding binding;
    private NetworkConnectionChecker networkConnectionChecker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        NavigationView navigationView = binding.navigationView;
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

        NoInternetConnectionDailogBinding networkBinding = NoInternetConnectionDailogBinding.inflate(getLayoutInflater());
        networkConnectionChecker = new NetworkConnectionChecker(MainActivity.this, networkBinding);

        if(savedInstanceState == null){
            navigationView.setCheckedItem(navigationView.getMenu().getItem(0));
            getSupportFragmentManager()
                    .beginTransaction().
                    replace((R.id.fragmentContainer), new DashboardFragment())
                    .commit();
        }

        binding.menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.drawerLayout.openDrawer(GravityCompat.END);

            }
        });


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        switch(item.getItemId()){
            case R.id.dashboard:
                fragment = new DashboardFragment();
                break;
            case R.id.groups:
                fragment = new MyGroupsFragment();
                break;
            case R.id.help:
                fragment = new HelpFragment();
                break;

        }
        binding.navigationView.setCheckedItem(item);
        getSupportFragmentManager()
                .beginTransaction()
                .replace((R.id.fragmentContainer), fragment)
                .commit();
        binding.drawerLayout.closeDrawer(GravityCompat.END);
        return true;
    }



    @Override
    public void onBackPressed() {
        if(binding.drawerLayout.isDrawerOpen(GravityCompat.END)){
            binding.drawerLayout.closeDrawer(GravityCompat.END);
        }

    }

    @Override
    protected void onDestroy() {
        networkConnectionChecker.unRegister();
        super.onDestroy();
    }
}


