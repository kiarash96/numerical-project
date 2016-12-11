package project.app; /**
 * Created by moeen on 12/7/16.
 */

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import project.app.utility.MatlabConnection;
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
    MatlabConnection connection;
    @FXML
    ChoiceBox interpolationType;
    int intepolationState;
    public ChapterThreeView() {
        super();

    }

}

