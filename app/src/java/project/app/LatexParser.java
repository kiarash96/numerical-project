package project;

import java.util.*;

public class LatexParser {

    private enum Operator
    {
        ADD(1), SUBTRACT(2), MULTIPLY(3), DIVIDE(4), POWER(5);
        final int precedence;
        Operator(int p) { precedence = p; }
    }

    private class Operand {
        boolean complex;
        String expr;

        Operand() {
            complex = false;
            expr = "";
        }

        Operand(boolean complex, String expr) {
            this.complex = complex;
            this.expr = expr;
        }

        @Override
        public String toString() {
            return expr;
        }
    }

    private static Map<String, Operator> ops = new HashMap<String, Operator>() {{
        put("+", Operator.ADD);
        put("-", Operator.SUBTRACT);
        put("*", Operator.MULTIPLY);
        put("/", Operator.DIVIDE);
        put("^", Operator.POWER);
    }};

    private static boolean isHigerPrec(String op, String sub)
    {
        return (ops.containsKey(sub) && ops.get(sub).precedence >= ops.get(op).precedence);
    }

    public static String postfix(String infix)
    {
        StringBuilder output = new StringBuilder();
        Deque<String> stack  = new LinkedList<>();

        for (String token : infix.split(" ")) {
            token = token.trim();
            if (token.equals(""))
                continue;

            // operator
            if (ops.containsKey(token)) {
                while (!stack.isEmpty() && isHigerPrec(token, stack.peek()))
                    output.append(stack.pop()).append(' ');
                stack.push(token);

                // left parenthesis
            } else if (token.equals("(")) {
                stack.push(token);

                // right parenthesis
            } else if (token.equals(")")) {
                while ( ! stack.peek().equals("("))
                    output.append(stack.pop()).append(' ');
                stack.pop();

                // digit
            } else {
                output.append(token).append(' ');
            }
        }

        while ( ! stack.isEmpty())
            output.append(stack.pop()).append(' ');

        return output.toString();
    }

    public String latex(String infix) {
        String pf = postfix(infix);

        Stack<Operand> stack = new Stack<>();
        for (String token : pf.split(" ")) {
            if (ops.containsKey(token)) {
                Operand v2 = (stack.isEmpty() ? new Operand(): stack.pop()),
                        v1 = (stack.isEmpty() ? new Operand() : stack.pop());
                if (v1.equals("")) {
                    v1 = v2;
                    v2 = new Operand();
                }

                String expr = "";
                if (token.equals("^")) {
                    if (v1.complex)
                        expr = "\\left(" + v1 + "\\right)";
                    else
                        expr = v1.toString();
                    expr += "^" + "{" + v2 + "}";
                } else if (token.equals("+") || token.equals("-"))
                    expr = v1 + " " + token + " " + v2;
                else if (token.equals("*"))
                    expr = v1 + " \\times " + v2;
                else if (token.equals("/"))
                    expr = "\\frac{" + v1 + "}{" + v2 + "}";
                stack.push(new Operand(true, expr));
            } else
                stack.push(new Operand(false, token));
        }

        StringBuilder out = new StringBuilder("");
        while (!stack.isEmpty())
            out.insert(0, stack.pop().expr + " ");

        return out.toString();
    }

}
