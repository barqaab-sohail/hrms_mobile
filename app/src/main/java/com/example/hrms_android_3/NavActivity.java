package com.example.hrms_android_3;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hrms_android_3.classes.PreferenceHelper;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.AppBarConfiguration.Builder;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.hrms_android_3.databinding.ActivityNavBinding;
import com.squareup.picasso.Picasso;


public class NavActivity extends BaseActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityNavBinding binding;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityNavBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarNav.toolbar);
        binding.appBarNav.fab.hide();
//        binding.appBarNav.fab.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;



        View header = navigationView.getHeaderView(0);
        PreferenceHelper preferenceHelper = new PreferenceHelper(this);
        TextView userEmail = header.findViewById(R.id.userEmail);
        TextView userName = header.findViewById(R.id.userName);
        ImageView userPicture = header.findViewById(R.id.userPicture);

        userEmail.setText(preferenceHelper.getEmail());
        userName.setText(preferenceHelper.getName().toUpperCase());
       Picasso.get().load(preferenceHelper.getPictureUrl()).resize(150, 150).into(userPicture);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new Builder(
                R.id.nav_dashboard, R.id.nav_add_asset, R.id.nav_search_asset, R.id.nav_employee_list)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_nav);

        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        //hide nav_menu item
        Menu nav_Menu = navigationView.getMenu();
        //hideMenuEmployeeList(nav_Menu);

    }



    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_nav);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void hideMenuEmployeeList(Menu menu){
        menu.findItem(R.id.nav_employee_list).setVisible(false);
    }


}