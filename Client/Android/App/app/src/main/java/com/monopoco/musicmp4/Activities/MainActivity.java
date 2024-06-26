package com.monopoco.musicmp4.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.appcompat.widget.SearchView;

import com.google.android.material.navigation.NavigationView;
import com.monopoco.musicmp4.Fragments.HomeFragment;
import com.monopoco.musicmp4.R;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private FrameLayout frameLayout;

    private DrawerLayout drawerLayout;

    private ImageView closeNavIcon;

    private NavigationView navigationView;


    private SearchView searchView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e("monopoco", "Return2");
        super.onCreate(savedInstanceState);
        SharedPreferences sp= getSharedPreferences("Login", Context.MODE_PRIVATE);
        Boolean isLogin = Boolean.valueOf(sp.getString("isLogin", null));
        if (!isLogin) {
            Intent intent = new Intent(this, SignInActivity.class);
            startActivity(intent);
        }
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar); //Ignore red line errors
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        drawerLayout = findViewById(R.id.drawer_layout);


        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        closeNavIcon = navigationView.getHeaderView(0).findViewById(R.id.close_nav);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav,
                R.string.close_nav);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        frameLayout = findViewById(R.id.main_frame_layout);
        if (savedInstanceState == null) {
            setFragment(new HomeFragment());
            navigationView.setCheckedItem(R.id.nav_home);
        }

        closeNavIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeNavMenu();
            }
        });
    }

    public void closeNavMenu() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(frameLayout.getId(), fragment);
        fragmentTransaction.commit();
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        Fragment toFragment = null;
        if (id == R.id.nav_home) {
            toFragment = new HomeFragment();
        } else if (id == R.id.nav_logout) {
            SharedPreferences sp= getSharedPreferences("Login", Context.MODE_PRIVATE);
            SharedPreferences.Editor Ed=sp.edit();
            Ed.putString("isLogin", "false" );
            Ed.remove("userId");
            Ed.commit();
            Intent intent = new Intent(this, SignInActivity.class);
            startActivity(intent);
            return true;
        }
        if (toFragment != null) {
            setFragment(toFragment);
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        } else {
            return false;
        }
    }
}