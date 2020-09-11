package com.bmahatchi.gadsleaderboard.ui.submitproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.bmahatchi.gadsleaderboard.R;
import com.bmahatchi.gadsleaderboard.network.GoogleFormsService;
import com.bmahatchi.gadsleaderboard.ui.submitproject.ConfirmDialog;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProjectSubmissionActivity extends AppCompatActivity implements ConfirmDialog.OnResult {
    private EditText email;
    private EditText firstName;
    private EditText lastName;
    private EditText link;
    private Button submit;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_submission);
        if (getSupportActionBar()!=null) {
            getSupportActionBar().setLogo(R.drawable.gads_small);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayUseLogoEnabled(true);
        }

        email = findViewById(R.id.email);
        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        link = findViewById(R.id.link);
        progressBar = findViewById(R.id.progressBar);

        submit = findViewById(R.id.buttonSubmit);
        submit.setOnClickListener(v->{
            hideViews();
            new ConfirmDialog().show(getSupportFragmentManager(), null);
        });
    }

    @Override
    public void onResult(boolean shouldSubmit) {
        if (shouldSubmit) submit(email.getText().toString(), firstName.getText().toString(), lastName.getText().toString(),
                link.getText().toString());
        else showViews();
    }

    private void submit(String email, String firstName, String lastName, String link) {
        progressBar.setVisibility(View.VISIBLE);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://docs.google.com/forms/d/e/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GoogleFormsService service = retrofit.create(GoogleFormsService.class);
        service.submitForm(email, firstName, lastName, link)
                .enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) showSuccessDialog();
                else showFailureDialog();
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                progressBar.setVisibility(View.GONE);
                showFailureDialog();
            }
        });
    }

    private void showSuccessDialog() {
        new MaterialAlertDialogBuilder(this, R.style.MaterialAlertDialog_rounded)
                .setView(R.layout.dialog_success)
                .setOnDismissListener(dialog -> finish())
                .show();
    }

    private void showFailureDialog() {
        new MaterialAlertDialogBuilder(this, R.style.MaterialAlertDialog_rounded)
                .setView(R.layout.dialog_failure)
                .setOnDismissListener(dialog -> showViews())
                .show();
    }

    private void hideViews() {
        email.setVisibility(View.INVISIBLE);
        firstName.setVisibility(View.INVISIBLE);
        lastName.setVisibility(View.INVISIBLE);
        link.setVisibility(View.INVISIBLE);
        submit.setVisibility(View.INVISIBLE);
    }

    private void showViews() {
        email.setVisibility(View.VISIBLE);
        firstName.setVisibility(View.VISIBLE);
        lastName.setVisibility(View.VISIBLE);
        link.setVisibility(View.VISIBLE);
        submit.setVisibility(View.VISIBLE);
    }
}
