package project.app; /**
 * Created by moeen on 12/7/16.
 */

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import matlabcontrol.MatlabInvocationException;
import project.app.utility.MatlabConnection;
import project.app.utility.MatlabStruct;
import project.controls.AbusedTextArea;
import project.controls.AbusedTextField;
import project.controls.CalculatedTitledPane;
import project.controls.Calculator;
import project.controls.LatexLabel;

import java.util.ArrayList;

/**
 * Created by kiarash on 12/7/16.
 */
public class ChapterThreeView {
    @FXML
    CalculatedTitledPane interpolationPanel, curveFittingPanel;
    @FXML
    AbusedTextField InterpolationsNumberOfpointsEntry, interpolationsFunctionArgument, curveFittingFunctionArgument;
    @FXML
    AbusedTextArea InterpolationsPointsEntry, curveFittingPointsEntry;
    @FXML
    Label interpolationsAnswerToArgument, curveFittingAnswerToArgument;
    @FXML
    LatexLabel interpolationsFunctionLatex;

    @FXML Label cfAnswer;
    @FXML
    Button interpolationsValueCalculateButton, curveFittingValueCalculateButton, interpolationsFunctionCalculateButton, curveFittingFunctionCalculateButton;
    @FXML
    Calculator interpolationsCalc, curveFittingCalc;
    MatlabConnection connection;
    @FXML
    ChoiceBox interpolationType, cfFuncType;
    int intepolationState;

    @FXML ImageView cfPlotView;
    @FXML StackPane cfPlotPane;

    public ChapterThreeView() {
        super();

    }

    public void init(MatlabConnection connection) {
        this.connection = connection;

        cfPlotView.fitHeightProperty().bind(curveFittingPanel.heightProperty().divide(2));

        cfFuncType.getItems().addAll("a * e^(b * x)", "a * log(x) + b", "a * 1/x + b", "1 / (ax + b)", "ax + b");

        curveFittingFunctionCalculateButton.setOnAction((event) -> {
            if (cfFuncType.getValue().equals("a * e^(b * x)"))
                doCurveFitting(1);
            else if (cfFuncType.getValue().equals("a * log(x) + b"))
                doCurveFitting(2);
            else if (cfFuncType.getValue().equals("a * 1/x + b"))
                doCurveFitting(3);
            else if (cfFuncType.getValue().equals("1 / (ax + b)"))
                doCurveFitting(4);
            else if (cfFuncType.getValue().equals("ax + b"))
                doCurveFitting(5);
        });
    }

    private void doCurveFitting(int type) {
        ArrayList<Double> Xs = new ArrayList<>(),
                Ys = new ArrayList<>();

        for (String line : curveFittingPointsEntry.getText().split("\n")) {
            String[] xy = line.split(" ");
            Xs.add(Double.parseDouble(xy[0]));
            Ys.add(Double.parseDouble(xy[1]));
        }

        double[] xs = new double[Xs.size()];
        for (int i = 0; i < xs.length; i ++)
            xs[i] = Xs.get(i);
        double[] ys = new double[Ys.size()];
        for (int i = 0; i < ys.length; i ++)
            ys[i] = Ys.get(i);

        String result = "";
        if (type <= 4) {
            MatlabStruct args = new MatlabStruct(
                    new MatlabStruct.Pair<>("X1", xs),
                    new MatlabStruct.Pair<>("Y1", ys),
                    new MatlabStruct.Pair<>("m", 10),
                    new MatlabStruct.Pair<>("type", type)
            );

            MatlabStruct res = null;
            try {
                res = connection.feval("chapter-3", "CurveFitting", args, "flag", "result");
            } catch (MatlabInvocationException e) {
                e.printStackTrace();
            }

            result = res.get("result");
        } else {
            MatlabStruct args = new MatlabStruct(
                    new MatlabStruct.Pair<>("X1", xs),
                    new MatlabStruct.Pair<>("Y1", ys),
                    new MatlabStruct.Pair<>("m", 10)
            );

            try {
                MatlabStruct res = connection.feval("chapter-3", "linearCurveFitting", args, "a", "b");
                double[] a = res.get("a"), b = res.get("b");
                result = a[0] + " * x + " + b[0];
            } catch (MatlabInvocationException e) {
                e.printStackTrace();
            }
        }

        cfAnswer.setText(result);
        getPlot(xs, ys, result);
    }

    private void getPlot(double[] xs, double[] ys, String result) {
        MatlabStruct args = new MatlabStruct(
                new MatlabStruct.Pair<>("X", xs),
                new MatlabStruct.Pair<>("Y", ys),
                new MatlabStruct.Pair<>("func", result),
                new MatlabStruct.Pair<>("addr", getClass().getResource("/matlab").getPath() + "/plot.jpg")
        );

        try {
            connection.feval("chapter-3", "CurveFittingPlot", args);
        } catch (MatlabInvocationException e) {
            e.printStackTrace();
        }

        cfPlotView.setImage(new Image(this.getClass().getResourceAsStream("/matlab/plot.jpg")));
    }
}

