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
        int n = Integer.parseInt(intHTextField.getText());
        MatlabStruct args = new MatlabStruct(
                new MatlabStruct.Pair<>("f", intFunctionTextField.getText()),
                new MatlabStruct.Pair<>("start", Double.parseDouble(intStartTextField.getText())),
                new MatlabStruct.Pair<>("end", Double.parseDouble(intEndTextField.getText())),
                new MatlabStruct.Pair<>("h", Double.parseDouble(intHTextField.getText())),
                new MatlabStruct.Pair<>("h", n),
                new MatlabStruct.Pair<>("method", intMethodType.getSelectionModel().getSelectedIndex()),
                new MatlabStruct.Pair<>("plotPath", getClass().getResource("/matlab").getPath() + "/plot.jpg")
        );
        MatlabStruct res = null;
        try {
            res = connection.feval("chapter-4", "integrate", args, "result", "error");
        } catch (MatlabInvocationException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

        double[] error = res.get("error");

        if (error[0] == 0)
            intPlotView.setImage(new Image(this.getClass().getResourceAsStream("/matlab/plot.jpg")));

        if (intMethodType.getValue().equals("Romberg")) {
            double[] result = res.get("result");
            double[][] table = new double[n][n + 1];

            for (int i = 0; i < result.length; i ++)
                table[i % n][i / n] = result[i];
            intAnswerLatexLabel.setLatex(ChapterSixController.makeTable(table));
        } else {
            String result = res.get("result");
            intAnswerLatexLabel.setLatex(result);
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
        diffAnswerLatexLabel.setLatex(String.valueOf(ans[0]));
    }
}
