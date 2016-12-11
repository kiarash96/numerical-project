package project.app;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
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
        /*Object[] res = connection.feval("newtonRaphson", 4,
                fn1TextField.getText(),
                Double.parseDouble(lowTextField.getText()),
                Double.parseDouble(steps1TextField.getText()),
                0.01);

        double[] roots = (double[]) res[0];
        double[] values = (double[]) res[1];
        String message = (String) res[2];
        double[] fail = (double[]) res[3];

        if (fail[0] == 1)
            ans1Label.setText(message);
        else {
            StringBuilder ans = new StringBuilder("");
            for (int i = 0; i < roots.length; i ++)
                ans.append((i == 0 ? "" : " -> ") + roots[i]);
            ans1Label.setText(ans.toString());
        }*/
    }
}
