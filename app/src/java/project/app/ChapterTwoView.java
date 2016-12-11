package project.app;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import project.app.utility.MatlabConnection;
import project.controls.AbusedTextField;
import project.controls.LatexLabel;

/**
 * Created by kiarash on 12/7/16.
 */
public class ChapterTwoView {

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
        latexLabel1.latexProperty().bind(fn1TextField.textProperty());

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
        /*Object[] res = connection.feval(method, 6,
                fn1TextField.getText(),
                Double.parseDouble(lowTextField.getText()),
                Double.parseDouble(highTextField.getText()),
                Double.parseDouble(steps1TextField.getText()),
                0.01);
        double[] highs = (double[]) res[0];
        double[] lows = (double[]) res[1];
        double[] roots = (double[]) res[2];
        double[] values = (double[]) res[3];
        String message = (String) res[4];
        double[] fail = (double[]) res[5];

        if (fail[0] == 1)
            ans1Label.setText(message);
        else {
            StringBuilder ans = new StringBuilder("");
            for (int i = 0; i < roots.length; i ++)
                ans.append((i == 0 ? "" : " -> ") + roots[i]);
            ans1Label.setText(ans.toString());
        }*/
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
