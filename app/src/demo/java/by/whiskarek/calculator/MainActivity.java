package by.whiskarek.calculator;

import android.os.Bundle;

import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends CalculatorActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.mode_basic, new BasicModeFragment()).commit();
    }
}
