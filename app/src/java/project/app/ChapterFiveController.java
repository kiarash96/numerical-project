package project.app;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import matlabcontrol.MatlabInvocationException;
import project.LatexParser;
import project.app.utility.MatlabConnection;
import project.app.utility.MatlabStruct;
import project.controls.LatexLabel;

/**
 * Created by kiarash on 1/4/17.
 */
public class ChapterFiveController {
    MatlabConnection connection;
    LatexParser parser;

    @FXML TextField fTextField, gTextField, hTextField, nTextField,
            x0TextField, y0TextField, z0TextField, stepTextField;
    @FXML ChoiceBox<String> methodType;
    @FXML Button calcButton;
    @FXML LatexLabel functionLatexLabel, gLatexLabel, answerLatexLabel;

    public void init(MatlabConnection connection) {
        this.connection = connection;
        parser = new LatexParser();

        fTextField.textProperty().addListener(((observable, oldValue, newValue) -> {
            functionLatexLabel.setLatex("\\frac{dy}{dx} = " + parser.latex(newValue));
        }));

        gTextField.textProperty().addListener(((observable, oldValue, newValue) -> {
            gLatexLabel.setLatex("\\frac{dz}{dx} = " + parser.latex(newValue));
        }));

        methodType.getItems().addAll("Taylor", "Euler", "Midpoint Runge-Kutta",
                "Third Order Runge-Kutta", "Fourth Order Runge-Kutta",
                "Second Order D.E. Euler", "Second Order D.E. Runge-Kutta");

        calcButton.setOnAction(event -> {
            doFirstOrder();
        });
    }

    public void doFirstOrder() {
        MatlabStruct args = new MatlabStruct(
                new MatlabStruct.Pair<>("equation1", fTextField.getText()),
                new MatlabStruct.Pair<>("equation2", gTextField.getText()),
                new MatlabStruct.Pair<>("h", Double.parseDouble(hTextField.getText())),
                new MatlabStruct.Pair<>("x0", Double.parseDouble(x0TextField.getText())),
                new MatlabStruct.Pair<>("y0", Double.parseDouble(y0TextField.getText())),
                new MatlabStruct.Pair<>("z0", Double.parseDouble(z0TextField.getText())),
                new MatlabStruct.Pair<>("method", methodType.getSelectionModel().getSelectedIndex() + 1),
                new MatlabStruct.Pair<>("step", Double.parseDouble(stepTextField.getText()))
        );

        MatlabStruct res = null;
        try {
            res = connection.feval("chapter-5", "chapter5", args, "xs", "ys");
        } catch (MatlabInvocationException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

        double[] xs = res.get("xs"), ys = res.get("ys");
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < xs.length; i ++)
            s.append((i == 0 ? "" : ", " + xs[i]));
        s.append("\\\\");
        for (int i = 0; i < ys.length; i ++)
            s.append((i == 0 ? "" : ", ") + ys[i]);

        answerLatexLabel.setLatex(s.toString());
    }
}
