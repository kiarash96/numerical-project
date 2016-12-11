package project.app;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import matlabcontrol.MatlabInvocationException;
import project.LatexParser;
import project.app.utility.MatlabConnection;
import project.app.utility.MatlabStruct;
import project.controls.AbusedTextField;
import project.controls.LatexLabel;

/**
 * Created by kiarash on 12/7/16.
 */
public class ChapterTwoView {

    LatexParser parser = new LatexParser();

    @FXML AbusedTextField fn1TextField, lowTextField, highTextField, steps1TextField;
    @FXML ComboBox<String> methodChoice;
    @FXML Button calc1Button;
    @FXML Label ans1Label;
    @FXML LatexLabel latexLabel1;

    // second pane
    @FXML TextField fn2TextField, x0TextField, steps2TextField;
    @FXML LatexLabel latexLabel2;
    @FXML Button calcButton2;
    @FXML Label ans2Label;

    // third pane
    @FXML TextField nTextField, step3TextField, p0TextField;
    @FXML TextArea formulaBox;
    @FXML Button calcButton3;
    @FXML LatexLabel latexLabel3;
    @FXML Label ans3Label;

    MatlabConnection connection;

    public ChapterTwoView() {
        super();
    }

    public void init(MatlabConnection connection) {
        this.connection = connection;

        // First screen
        fn1TextField.textProperty().addListener(((observable, oldValue, newValue) -> {
            latexLabel1.setLatex(parser.latex(newValue));
        }));
        fn1TextField.setText("saa");

        methodChoice.getItems().addAll("Bisection", "False Position", "Secant", "Newton-Raphson");

        calc1Button.setOnAction((event -> {
            String method = methodChoice.getValue();
            if (method.equals("Bisection"))
                doType1("bisection");
            else if (method.equals("False Position"))
                doType1("falsePosition");
            else if (method.equals("Secant"))
                doType1("secant");
            else if (methodChoice.getValue().equals("Newton-Raphson"))
                doNewtonRaphson();
        }));


        fn2TextField.textProperty().addListener(((observable, oldValue, newValue) -> {
            latexLabel2.setLatex(parser.latex(fn2TextField.getText()));
        }));
        calcButton2.setOnAction((event) -> doFixedPoint());


        formulaBox.textProperty().addListener(((observable, oldValue, newValue) -> {
            latexLabel3.setLatex(parser.latex(formulaBox.getText().replace("\n", "\\\\")));
        }));
        calcButton3.setOnAction((event) -> {
            try {
                doGNR();
            } catch (MatlabInvocationException e) {
                new Alert(Alert.AlertType.ERROR, e.getLocalizedMessage()).show();
            }
        });
    }

    private void doType1(String method) {
        MatlabStruct args = new MatlabStruct(
                new MatlabStruct.Pair<>("func", fn1TextField.getText()),
                new MatlabStruct.Pair<>("low", Double.parseDouble(lowTextField.getText())),
                new MatlabStruct.Pair<>("high", Double.parseDouble(highTextField.getText())),
                new MatlabStruct.Pair<>("step", Double.parseDouble(steps1TextField.getText())),
                new MatlabStruct.Pair<>("tol", 0.01)
        );

        MatlabStruct res = null;
        try {
            res = connection.feval("chapter-2", method, args,
                    "highs", "lows", "roots", "values", "message", "fail");
        } catch (MatlabInvocationException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

        double[] highs = res.get("highs");
        double[] lows = res.get("lows");
        double[] roots = res.get("roots");
        double[] values = res.get("values");
        String message = res.get("message");
        double[] fail = res.get("fail");

        if (fail[0] == 1)
            ans1Label.setText(message);
        else {
            StringBuilder ans = new StringBuilder("");
            for (int i = 0; i < roots.length; i ++)
                ans.append((i == 0 ? "" : " -> ") + roots[i]);
            ans1Label.setText(ans.toString());
        }
    }

    private void doNewtonRaphson() {
        MatlabStruct args = new MatlabStruct(
                new MatlabStruct.Pair<>("func", fn1TextField.getText()),
                new MatlabStruct.Pair<>("p0", Double.parseDouble(lowTextField.getText())),
                new MatlabStruct.Pair<>("step", Double.parseDouble(steps1TextField.getText())),
                new MatlabStruct.Pair<>("tol", 0.01)
        );

        MatlabStruct res = null;
        try {
            res = connection.feval("chapter-2", "newtonRaphson", args,
                    "roots", "values", "message", "fail");
        } catch (MatlabInvocationException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

        double[] roots = res.get("roots");
        double[] values = res.get("values");
        String message = res.get("message");
        double[] fail = res.get("fail");

        if (fail[0] == 1)
            ans1Label.setText(message);
        else {
            StringBuilder ans = new StringBuilder("");
            for (int i = 0; i < roots.length; i ++)
                ans.append((i == 0 ? "" : " -> ") + roots[i]);
            ans1Label.setText(ans.toString());
        }
    }

    private void doFixedPoint() {
        MatlabStruct args = new MatlabStruct(
                new MatlabStruct.Pair<>("func", fn2TextField.getText()),
                new MatlabStruct.Pair<>("p0", Double.parseDouble(x0TextField.getText())),
                new MatlabStruct.Pair<>("step", Double.parseDouble(steps2TextField.getText()))
        );

        MatlabStruct res = null;
        try {
            res = connection.feval("chapter-2", "fixedPoint", args,
                    "roots", "values", "message", "fail");
        } catch (MatlabInvocationException e) {
            new Alert(Alert.AlertType.ERROR, e.getLocalizedMessage()).show();
        }

        double[] roots = res.get("roots");
        double[] values = res.get("values");
        String message = res.get("message");
        double[] fail = res.get("fail");

        if (fail[0] == 1)
            ans2Label.setText(message);
        else {
            StringBuilder ans = new StringBuilder("");
            for (int i = 0; i < roots.length; i ++)
                ans.append((i == 0 ? "" : " -> ") + roots[i]);
            ans2Label.setText(ans.toString());
        }
    }

    private void doGNR() throws MatlabInvocationException {
        try {
            connection.cd("chapter-2");
            int n = Integer.parseInt(nTextField.getText());
            int j = 1;
            String cmd = "f = {";
            for (String line : formulaBox.getText().split("\n")) {
                StringBuilder vstr = new StringBuilder("@(a0");
                for (int i = 1; i <= n; i ++)
                    vstr.append(",a" + i);
                vstr.append(") ").append(line);
                cmd += "str2func('" + vstr.toString() + "') ";
            }
            cmd += "};";
            System.err.println(cmd);
            connection.eval(cmd);

            Object[] res = connection.proxy.returningEval("GNR(f, " + n + ", "
                    + "[" + p0TextField.getText() + "], "
                    + step3TextField.getText() + ", 0.01);", 4);

            double[] roots = (double[]) res[0];
            double[] values = (double[]) res[1];
            String message = (String) res[2];
            double[] fail = (double[]) res[3];

            if (fail[0] == 1)
                ans3Label.setText(message);
            else {
                StringBuilder ans = new StringBuilder("");
                for (int i = 0; i < roots.length; i ++) {
                    if (i > 0)
                        ans.append(" -> ");
                    ans.append("(" + roots[i ++]);
                    for (j = 1; j <= n - 1; j ++)
                        ans.append(", ").append(roots[i ++]);
                    i --;
                    ans.append(")");
                }
                ans3Label.setText(ans.toString());
            }
        }
        finally {
            connection.cd(connection.rootPath);
        }
    }
}
