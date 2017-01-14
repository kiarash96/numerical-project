package project.app;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import matlabcontrol.MatlabInvocationException;
import project.LatexParser;
import project.app.utility.Display;
import project.app.utility.MatlabConnection;
import project.app.utility.MatlabStruct;
import project.controls.LatexLabel;

/**
 * Created by kiarash on 1/4/17.
 */
public class ChapterFourController {

    MatlabConnection connection;
    LatexParser parser;

    // Integral
    @FXML Label intHLabel;
    @FXML TextField intStartTextField, intEndTextField, intHTextField, intFunctionTextField;
    @FXML ChoiceBox<String> intMethodType;
    @FXML LatexLabel intFunctionLatexLabel, intAnswerLatexLabel;
    @FXML Button intCalcButton;
    @FXML ImageView intPlotView;

    // Diff
    @FXML TextField diffXTextField, diffDegreeTextField, diffOrderTextField, diffHTextField, diffFTextField;
    @FXML LatexLabel diffFunctionLatexLabel, diffAnswerLatexLabel;
    @FXML Button diffCalcButton;
    @FXML CheckBox richardsonCheckBox;

    public void init(MatlabConnection connection) {
        this.connection = connection;
        parser = new LatexParser();

        intMethodType.getItems().addAll("Trapezoidal", "Simpson 1/3", "Simpson 3/8",
                "Romberg", "Gauss Legendre", "Customized Simpson");
        intMethodType.valueProperty().addListener(((observable, oldValue, newValue) -> {
            if (newValue.equals("Romberg") || newValue.equals("Gauss Legendre"))
                intHLabel.setText("n = ");
            else
                intHLabel.setText("h = ");
        }));
        intFunctionTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            intFunctionLatexLabel.setLatex(parser.latex(newValue));
        });
        intCalcButton.setOnAction(event -> callIntegral());

        diffFTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            diffFunctionLatexLabel.setLatex(parser.latex(newValue));
        });
        diffCalcButton.setOnAction((event) -> {
            callDiff();
        });
    }

    public void callIntegral() {
        double nn = Double.parseDouble(intHTextField.getText());
        int n = (int) nn;
        double a = Double.parseDouble(intStartTextField.getText()),
                b = Double.parseDouble(intEndTextField.getText());
        MatlabStruct args = new MatlabStruct(
                new MatlabStruct.Pair<>("f", intFunctionTextField.getText()),
                new MatlabStruct.Pair<>("start", a),
                new MatlabStruct.Pair<>("end", b),
                new MatlabStruct.Pair<>("h", Double.parseDouble(intHTextField.getText())),
                new MatlabStruct.Pair<>("h", n),
                new MatlabStruct.Pair<>("method", intMethodType.getSelectionModel().getSelectedIndex()),
                new MatlabStruct.Pair<>("plotPath", getClass().getResource("/matlab").getPath() + "/plot.jpg")
        );
        MatlabStruct res = null;
        try {
            res = connection.feval("chapter-4", "integrate", args,
                    "xs", "ws", "ys", "result", "error");
        } catch (MatlabInvocationException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

        double[] error = res.get("error");

        if (error[0] == 1)
            intAnswerLatexLabel.setLatex(res.get("result"));
        else {
            intPlotView.setImage(new Image(this.getClass().getResourceAsStream("/matlab/plot.jpg")));

            if (intMethodType.getValue().equals("Romberg")) {
                double[] result = res.get("result");
                double[][] table = new double[n][n + 1];

                for (int i = 0; i < result.length; i++)
                    table[i % n][i / n] = result[i];
                intAnswerLatexLabel.setLatex(ChapterSixController.makeTable(table));
            } else {
                double[] ans = res.get("result");
                double[] xs = res.get("xs"),
                        ys = res.get("ys"),
                        ws = res.get("ws");

                StringBuilder result = new StringBuilder();
                result.append("\\begin{array}{c|*{" + xs.length + "}{c}}");
                result.append("x_i & " + Display.alignArray(xs) + "\\\\");
                result.append("f(x_i) & " + Display.alignArray(ys) + "\\\\");
                if (intMethodType.getValue().equals("Gauss Legendre"))
                    result.append("w_i & " + Display.alignArray(ws));
                result.append("\\end{array}\\\\");
                result.append("\\int_{" + Display.numToStr(a) + "}^{" + Display.numToStr(b)
                        + "} " + intFunctionTextField.getText() + " \\, dx "
                        + " = " + Display.numToStr(ans[0]));

                intAnswerLatexLabel.setLatex(result.toString());
            }
        }
    }

    public void callDiff() {
        MatlabStruct args = new MatlabStruct(
                new MatlabStruct.Pair<>("func", diffFTextField.getText()),
                new MatlabStruct.Pair<>("degree", Double.parseDouble(diffDegreeTextField.getText())),
                new MatlabStruct.Pair<>("x", Double.parseDouble(diffXTextField.getText())),
                new MatlabStruct.Pair<>("order", Double.parseDouble(diffOrderTextField.getText())),
                new MatlabStruct.Pair<>("h", Double.parseDouble(diffHTextField.getText()))
        );

        MatlabStruct res = null;
        try {
            res = connection.feval("chapter-4/diff/", "Derivation", args, "y");
        } catch (MatlabInvocationException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

        double[] ans = res.get("y");
        diffAnswerLatexLabel.setLatex(Display.numToStr(ans[0]));
    }
}
