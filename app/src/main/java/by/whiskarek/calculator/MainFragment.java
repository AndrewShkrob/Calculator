package by.whiskarek.calculator;

import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import java.util.stream.IntStream;

public abstract class MainFragment extends Fragment {

    protected <T extends ViewGroup> void initOnClickListeners(T root) {
        IntStream
                .range(0, root.getChildCount())
                .mapToObj(root::getChildAt)
                .flatMap(view -> view.getTouchables().stream())
                .filter(view -> view instanceof Button)
                .forEach(view -> view.setOnClickListener((CalculatorActivity) getActivity()));
    }
}
