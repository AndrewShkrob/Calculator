package by.whiskarek.calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.singularsys.jep.JepException;

import org.jetbrains.annotations.NotNull;

public abstract class CalculatorActivity extends AppCompatActivity implements View.OnLongClickListener, View.OnClickListener {

    private EditText screen;
    private static Parser expressionParser = new Parser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        screen = findViewById(R.id.screen);
    }

    private void onDelete() {
        expressionParser.removeLast();
        updateScreen();
    }

    private void onEquals() {
        try {
            String result = expressionParser.evaluate();
            screen.setText(result);
        } catch (JepException ex) {
            expressionParser.clear();
            screen.setText(getResources().getText(R.string.error));
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if (v.getId() == R.id.btn_del) {
            expressionParser.clear();
            screen.setText(expressionParser.getString());
            return true;
        }
        return false;
    }

    @Override
    public void onClick(@NotNull View view) {
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
                expressionParser.append(((Button) view).getText() + "(");
                updateScreen();
                break;
            default:
                expressionParser.append(((Button) view).getText());
                updateScreen();
                break;
        }
    }

    protected void updateScreen() {
        screen.setText(expressionParser.getString());
    }
}
