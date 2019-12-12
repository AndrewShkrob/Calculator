package by.whiskarek.calculator;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends CalculatorActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int orientation = this.getResources().getConfiguration().orientation;
        List<Fragment> calculatorModes = new ArrayList<>();
        calculatorModes.add(new BasicModeFragment());
        calculatorModes.add(new ScientificModeFragment());
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            ViewPager pager = findViewById(R.id.mode_frame);
            pager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), calculatorModes));
        } else {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.mode_basic, calculatorModes.get(0)).add(R.id.mode_scientific, calculatorModes.get(1)).commit();
        }
    }
}
