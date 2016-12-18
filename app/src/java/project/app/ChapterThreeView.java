package project.app; /**
 * Created by moeen on 12/7/16.
 */

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import matlabcontrol.MatlabInvocationException;
import project.app.utility.MatlabConnection;
import project.app.utility.MatlabStruct;
import project.controls.AbusedTextArea;
import project.controls.AbusedTextField;
import project.controls.CalculatedTitledPane;
import project.controls.Calculator;
import project.controls.LatexLabel;

/**
 * Created by kiarash on 12/7/16.
 */
public class ChapterThreeView {

    @FXML
    CalculatedTitledPane interpolationPanel, curveFittingPanel;
    @FXML
    AbusedTextField InterpolationsNumberOfpointsEntry, curveFittingNumberOfpointsEntry, functionType, interpolationsFunctionArgument, curveFittingFunctionArgument;
    @FXML
    AbusedTextArea InterpolationsPointsEntry, curveFittingPointsEntry;
    @FXML
    Label interpolationsAnswerToArgument, curveFittingAnswerToArgument;
    @FXML
    LatexLabel curveFittingFunctionLatex, interpolationsFunctionLatex;
    @FXML
    Button interpolationsValueCalculateButton, curveFittingValueCalculateButton, interpolationsFunctionCalculateButton, curveFittingFunctionCalculateButton;
    @FXML
    Calculator interpolationsCalc, curveFittingCalc;
    @FXML
    ChoiceBox interpolationType;
    int interpolationState;
    MatlabConnection connection;

    public ChapterThreeView() {
    }

    public void init(MatlabConnection connection) {
        this.connection = connection;
        interpolationType.setItems(FXCollections.observableArrayList("Lagrange", "Newton Divided Differences", "newtonForwardBackward"));
        interpolationType.getSelectionModel().selectedIndexProperty()
                .addListener(new ChangeListener<Number>() {
                    public void changed(ObservableValue ov, Number value, Number new_value) {
                        interpolationState = new_value.intValue() + 1;
                        System.out.println(interpolationState);
                    }
                });
        interpolationsValueCalculateButton.setOnAction((event -> {
            int number = Integer.parseInt(InterpolationsNumberOfpointsEntry.getText());
            double [] X = new double[number];
            double [] Y = new double[number];
            double x = Double.parseDouble(interpolationsFunctionArgument.getText());
            String wholeText = InterpolationsPointsEntry.getText();
            String [] points = wholeText.split("\n");
            try {
                connection.eval("Xs = 1:" + number);
                connection.eval("Ys = 1:" + number);

            } catch (MatlabInvocationException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < number; i++) {
                String temp = points[i];
                String [] xy = temp.split(",");
                X[i] = Double.parseDouble(xy[0]);
                Y[i] = Double.parseDouble(xy[1]);
                try {
                    connection.eval("Xs( " + (i+1) + " ) = " + X[i]);
                    connection.eval("Ys( " + (i+1) + " ) = " + Y[i]);
                } catch (MatlabInvocationException e) {
                    e.printStackTrace();
                }
            }
            MatlabStruct matlabStruct = new MatlabStruct(new MatlabStruct.Pair<String, Object>("", "Xs"),
                    new MatlabStruct.Pair<String, Object>("", "Ys"),
                    new MatlabStruct.Pair<String, Object>("", x),
                    new MatlabStruct.Pair<String, Object>("", "10"));
            try {
                MatlabStruct outPutStruct = new MatlabStruct();
                if(interpolationState == 1){
                    outPutStruct = connection.feval("chapter-3", "Lagrange", matlabStruct, "answer");
                }
                if(interpolationState == 2){
                    outPutStruct = connection.feval("chapter-3", "newtonDividedDifference", matlabStruct, "answer");
                }
                if(interpolationState == 3){
                    outPutStruct = connection.feval("chapter-3", "newtonForwardBackward", matlabStruct, "answer");
                }
                double  answer = outPutStruct.get("answer");
                interpolationsAnswerToArgument.setText(Double.toString(answer));
            } catch (MatlabInvocationException e) {
                e.printStackTrace();
            }

        }
        ));
    }

}

