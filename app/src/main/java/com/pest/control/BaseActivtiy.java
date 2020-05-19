package com.pest.control;

import android.app.FragmentManager;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class BaseActivtiy extends AppCompatActivity {

    protected FragmentManager fragmentManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentManager = getSupportFragmentManager();
    }
}
