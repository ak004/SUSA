package com.example.susa;

import android.content.ClipData;
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
//        sharedPreferencesData.putuser_id("6454bb2b93c5296f82b71445");
        sharedPreferencesData.putuser_id("644a11ce462c5ae45c83e93f");


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