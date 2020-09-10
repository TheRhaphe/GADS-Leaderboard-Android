package com.bmahatchi.gadsleaderboard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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

        EditText email = findViewById(R.id.email);
        EditText firstName = findViewById(R.id.firstName);
        EditText lastName = findViewById(R.id.lastName);
        EditText link = findViewById(R.id.link);

        findViewById(R.id.buttonSubmit).setOnClickListener(v->submit(
                email.getText().toString(), firstName.getText().toString(), lastName.getText().toString(),
                link.getText().toString()
        ));
    }

    private void submit(String email, String firstName, String lastName, String link) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://docs.google.com/forms/d/e/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GoogleFormsService service = retrofit.create(GoogleFormsService.class);
        service.submitForm(email, firstName, lastName, link)
                .enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {

            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {

            }
        });
    }
}
