package by.whiskarek.calculator;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class BasicModeFragment extends MainFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.basic, container, false);
        initOnClickListeners((ViewGroup) view);
        view.findViewById(R.id.btn_del).setOnLongClickListener((CalculatorActivity) getActivity());
        return view;
    }
}
