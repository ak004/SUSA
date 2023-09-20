package com.example.susa;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class MainActivity extends AppCompatActivity {

    private ChipNavigationBar chipNavigationBar;
    private Fragment fragment = null;
    SharedPreferencesData sharedPreferencesData;

    private int homee = R.id.home2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferencesData = SharedPreferencesData.getInstance(this);
        //this from computer
//        sharedPreferencesData.putuser_id("64ddb0116127693946021a59");
        // this from the laptop

        if(sharedPreferencesData.getUSER_id().isEmpty()) {
            Intent intent = new Intent(MainActivity.this,LoginActivity.class);
            startActivity(intent);
        }
        chipNavigationBar = findViewById(R.id.chipNavigation);

        chipNavigationBar.setItemSelected(R.id.home2, true);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new HomeFragment()).commit();

        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                Log.d("TJHSELSEF", "The selected item is: " + i);
                if (i == R.id.home2) {
                    fragment = new HomeFragment();
                } else if (i == R.id.learn) {
                    fragment = new LearnFragment();
                } else if (i == R.id.bookmarks) {
                    fragment = new BookmarkFragment();
                } else if (i == R.id.settingg) {
                    fragment = new SettingFragment();
                }

                if (fragment != null) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
                }
            }
        });
    }
}