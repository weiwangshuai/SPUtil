package com.bhj.sputil;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.bhj.sp.UserStorage;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        UserStorage.putUsername("456");
        Log.d("MainActivity--",UserStorage.getUsername("0"));
    }

}
