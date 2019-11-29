package by.whiskarek.calculator;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.singularsys.jep.EmptyOperatorTable;
import com.singularsys.jep.Jep;
import com.singularsys.jep.JepException;
import com.singularsys.jep.Operator;
import com.singularsys.jep.OperatorTable2;
import com.singularsys.jep.misc.functions.Factorial;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnLongClickListener {

    public enum MyOperators implements EmptyOperatorTable.OperatorKey {FACT}

    private EditText screen;
    private Jep jep = new Jep();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        onCreateFlavor();
        screen = findViewById(R.id.screen);

        // Build the operator
        Operator factop = new Operator("FACT", "!",
                new Factorial(),
                Operator.SUFFIX + Operator.UNARY + Operator.LEFT);
        ((OperatorTable2) jep.getOperatorTable()).addOperator(new EmptyOperatorTable.OperatorKey() {
        }, factop);
        jep.reinitializeComponents();
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

    public void onKeyPress(@NotNull View view) {
        switch (view.getId()) {
            case R.id.btn_eq:
                onEquals();
                break;
            case R.id.btn_del:
                onDelete();
                break;
            case R.id.btn_cos:
            case R.id.btn_ln:
            case R.id.btn_log:
            case R.id.btn_sin:
            case R.id.btn_e:
            case R.id.btn_sqrt:
                screen.append(((Button) view).getText() + "(");
                break;
            default:
                screen.setText(screen.getText().append(((Button) view).getText()));
                break;
        }
    }

    private void onDelete() {
        int text_size = screen.getText().length();
        if (text_size > 0)
            screen.setText(screen.getText().delete(text_size - 1, text_size));
    }

    private void onEquals() {
        try {
            jep.parse(screen.getText().toString());
            Object result = jep.evaluate();

            screen.setText(result.toString());
        } catch (JepException ignored) {
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if (v.getId() == R.id.btn_del)
            screen.setText("");
        return true;
    }
}
