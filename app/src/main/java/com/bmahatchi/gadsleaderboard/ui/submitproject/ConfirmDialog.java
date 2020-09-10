package com.bmahatchi.gadsleaderboard.ui.submitproject;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.bmahatchi.gadsleaderboard.R;

public class ConfirmDialog extends DialogFragment {
    private final String TAG = getClass().getSimpleName();
    private OnResult onResult;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, R.style.DialogTheme_transparent);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_confirm, container, false);
        view.findViewById(R.id.cancel).setOnClickListener(v->{
            dismiss();
            onResult.onResult(false);
        });
        view.findViewById(R.id.confirm).setOnClickListener(v->{
            dismiss();
            onResult.onResult(true);
        });
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            onResult = (OnResult) getActivity();
        } catch (ClassCastException e) {
            Log.e(TAG, "onAttach: " + e.getMessage());
        }
    }

    public interface OnResult {
        void onResult(boolean shouldSubmit);
    }
}
