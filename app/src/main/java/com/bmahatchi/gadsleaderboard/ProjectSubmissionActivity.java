package com.bmahatchi.gadsleaderboard;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class ProjectSubmissionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_submission);
        if (getSupportActionBar()!=null) {
            getSupportActionBar().setLogo(R.drawable.gads_small);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayUseLogoEnabled(true);
        }
    }
}