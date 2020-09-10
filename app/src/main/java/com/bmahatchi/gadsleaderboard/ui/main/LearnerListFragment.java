package com.bmahatchi.gadsleaderboard.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bmahatchi.gadsleaderboard.GadsApiService;
import com.bmahatchi.gadsleaderboard.Learner;
import com.bmahatchi.gadsleaderboard.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A placeholder fragment containing a simple view.
 */
public class LearnerListFragment extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";
    public int index;
    private List<Learner> learnerList = new ArrayList<>();
    public RecyclerAdapter recyclerAdapter;
    public RecyclerView recyclerView;

    public static LearnerListFragment newInstance(int index) {
        LearnerListFragment fragment = new LearnerListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        index = 0;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = root.findViewById(R.id.recyclerView);
        recyclerAdapter = new RecyclerAdapter(learnerList, index==0);
        recyclerView.setAdapter(recyclerAdapter);
        setup();
        return root;
    }

    private void setup() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://gadsapi.herokuapp.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GadsApiService service = retrofit.create(GadsApiService.class);
        final Call<List<Learner>> call;
        if (index==0) call = service.getLeadingLearners();
        else call = service.getLeadingSkillIq();

        call.enqueue(new Callback<List<Learner>>() {
            @Override
            public void onResponse(@NonNull Call<List<Learner>> call, @NonNull Response<List<Learner>> response) {
                learnerList = response.body();
                recyclerView.setAdapter(new RecyclerAdapter(learnerList, index==0));
            }

            @Override
            public void onFailure(@NonNull Call<List<Learner>> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), "Error Occurred", Toast.LENGTH_SHORT).show();
            }
        });
    }

}