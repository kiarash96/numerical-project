package project.app; /**
 * Created by moeen on 12/7/16.
 */

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.util.converter.CharacterStringConverter;
import matlabcontrol.MatlabInvocationException;
import project.LatexParser;
import project.app.utility.MatlabConnection;
import project.app.utility.MatlabStruct;
import project.controls.AbusedTextField;
import project.controls.LatexLabel;

/**
 * Created by kiarash on 12/7/16.
 */
public class ChapterOneView {
    MatlabConnection connection;
    AbusedTextField[] valFields;
    AbusedTextField[] errorFields;
    String postfixFormula;
    LatexParser hashtagLatexParser = new LatexParser();
    LatexParser latexParser = new LatexParser();
    @FXML
    AbusedTextField aVal, bVal, cVal, dVal, eVal, fVal, aError, bError, cError, dError, eError, fError, percesion, formula;
    @FXML
    Button evaluateButton;
    @FXML
    LatexLabel answer, latexLabelFormula;
    @FXML
    ChoiceBox roundType;
    double roundCoefficient;
    public void init(MatlabConnection connection) {
        roundType.setItems(FXCollections.observableArrayList("round of", "chop of"));
        roundType.getSelectionModel().selectedIndexProperty()
                .addListener(new ChangeListener<Number>() {
                    public void changed(ObservableValue ov, Number value, Number new_value) {
                        if (new_value.intValue() == 1){
                            roundCoefficient = 1;
                        }
                        else {
                            roundCoefficient = 0.5;
                        }
                    }
                });
        valFields = new AbusedTextField[]{aVal, bVal, cVal, dVal, eVal, fVal};
        errorFields = new AbusedTextField[]{aError, bError, cError, dError, eError, fError};
        this.connection = connection;
        formula.textProperty().addListener((obs, oldText, newText) -> {
            String latexed = latexParser.latex(newText);
            System.out.println(latexed);
            latexLabelFormula.setLatex(latexed);
        });
        evaluateButton.setOnAction((event -> {
            boolean numerical = true;
            for (AbusedTextField temp :
                    valFields) {
                if (temp.getText().equals("")){
                    numerical = false;
                }
            }
            if (numerical){
                try {
                    for (int i = 0; i < 6; i++) {
                        if (errorFields[i].getText().equals("")){
                            errorFields[i].setText(getPrecesion(valFields[i].getText()));
                        }
                    }
                    MatlabStruct matlabStruct = new MatlabStruct();
                    hashtagLatexParser.setHashtag(true);
                    postfixFormula = hashtagLatexParser.postfix(formula.getText());
                    matlabStruct.add("a", postfixFormula);
                    double [] vals = new  double[6];
                    double [] errors = new  double[6];
                    for (int i = 0; i < 6; i++) {
                        vals[i] = Double.parseDouble(valFields[i].getText());
                        matlabStruct.add("a", vals[i]);
                    }
                    for (int i = 0; i < 6; i++) {
                        errors[i] = Double.parseDouble(errorFields[i].getText());
                        matlabStruct.add("a", errors[i]);
                    }
                    MatlabStruct outPut = connection.feval("chapter-1", "errorPropagationWithValue", matlabStruct, "output");
                    String latexedOutPut = latexParser.latex(outPut.get("output"));
                    answer.setLatex(latexedOutPut);
                }
                catch (Exception a){
                    a.printStackTrace();
                }
            }
            else {
                try {
                    connection.eval("syms a b c d e f");
                    connection.eval("syms  Ea Eb Ec Ed Ee Ef");
                    connection.eval("syms  g h i j k l");
                    MatlabStruct matlabStruct = new MatlabStruct();
                    hashtagLatexParser.setHashtag(true);
                    postfixFormula = hashtagLatexParser.postfix(formula.getText());
                    matlabStruct.add("a", postfixFormula);
                    char [] vals = new  char[6];
                    String [] errors = new  String[6];
                    for (int i = 0; i < 6; i++) {
                        vals[i] = (char) (97 + i);
                        matlabStruct.add("", Character.toString(vals[i]));
                        valFields[i].setText(Character.toString(vals[i]));
                    }
                    for (int i = 0; i < 6; i++) {
                        errors[i] = Character.toString( (char)('g' + i) );
                        matlabStruct.add("", errors[i]);
                        errorFields[i].setText(errors[i]);
                    }
                    MatlabStruct outPut = connection.feval("chapter-1", "errorPropagationWithValue", matlabStruct, "output");
                    String temp = outPut.get("output");
                    System.out.println(temp);
                    String latexedOutPut = latexParser.latex(outPut.get("output"));
                    answer.setLatex(latexedOutPut);
                }
                catch (MatlabInvocationException e) {
                    e.printStackTrace();
                }
            }
        }));


    }

    public String getPrecesion(String number){
        double err;
        if (!number.contains(".")){
            err = 1;
        }
        else {
            err = Math.pow(0.1, (number.length() - number.indexOf(".") - 1));
        }
        return Double.toString(err*roundCoefficient);

    }

}

