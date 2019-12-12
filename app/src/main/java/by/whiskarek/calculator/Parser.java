package by.whiskarek.calculator;

import com.singularsys.jep.EmptyOperatorTable;
import com.singularsys.jep.Jep;
import com.singularsys.jep.JepException;
import com.singularsys.jep.Operator;
import com.singularsys.jep.OperatorTable2;
import com.singularsys.jep.misc.functions.Factorial;

import java.util.Stack;

public class Parser {
    private Jep jep = new Jep();
    private String expression = "";
    private Stack<CharSequence> operands = new Stack<>();

    public Parser() {
        Operator factop = new Operator("FACT", "!",
                new Factorial(),
                Operator.SUFFIX + Operator.UNARY + Operator.LEFT);
        ((OperatorTable2) jep.getOperatorTable()).addOperator(new EmptyOperatorTable.OperatorKey() {
        }, factop);
        jep.reinitializeComponents();
    }

    public void append(CharSequence text) {
        expression += text;
        operands.push(text);
    }

    public CharSequence getString() {
        return expression;
    }

    public String evaluate() throws JepException {
        if (expression.isEmpty())
            return "";
        jep.parse(expression);
        Object result = jep.evaluate();
        expression = result.toString();
        return expression;
    }

    public void clear() {
        expression = "";
    }

    public void removeLast() {
        if (!expression.isEmpty() && !operands.isEmpty()) {
            expression = expression.substring(0, expression.length() - operands.peek().length());
            operands.pop();
        }
    }
}
