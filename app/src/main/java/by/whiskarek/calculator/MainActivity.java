package by.whiskarek.calculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.content.res.Configuration;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        onCreateFlavor();
    }

    private void onCreateFlavor() {
        switch (BuildConfig.FLAVOR) {
            case "full":
                onCreateFull();
                break;
            case "demo":
                onCreateDemo();
                break;
        }
    }

    private void onCreateDemo() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.mode_basic, new BasicModeFragment()).commit();
    }

    private void onCreateFull() {
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
