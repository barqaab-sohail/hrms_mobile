package com.example.hrms_android_3;


import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.hrms_android_3.classes.PreferenceHelper;
import com.example.hrms_android_3.classes.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class BaseActivity extends AppCompatActivity {

    private PreferenceHelper preferenceHelper;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        preferenceHelper = new PreferenceHelper(this);
        String data = preferenceHelper.getName();
        MenuItem item = menu.getItem(0);
        item.setTitle(data);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuLogout:
                logOut();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void logOut(){

        preferenceHelper = new PreferenceHelper(this);
        String userToken = "Bearer "+ preferenceHelper.getToken();
        String email = preferenceHelper.getEmail();
        Call<Object> call = RetrofitClient.getInstance().getApi().userLogout(userToken,email);

        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                redirectToLogin();
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                String data = t.toString();
                Toast.makeText(getApplicationContext(), data, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void redirectToLogin() {
        preferenceHelper = new PreferenceHelper(this);
        preferenceHelper.putName("");
        preferenceHelper.putToken("");
        preferenceHelper.putTokenType("");
        Toast.makeText(getApplicationContext(),"You have sucessfully logout",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
