package project.app;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import matlabcontrol.MatlabInvocationException;
import matlabcontrol.MatlabProxy;
import project.app.utility.MatlabConnection;
import project.app.utility.MatlabStruct;
import project.controls.AbusedTextArea;
import project.controls.LatexLabel;


/**
 * Created by kiarash on 1/4/17.
 */
public class ChapterSixController {
    private MatlabConnection connection;
    @FXML
    AbusedTextArea aInput, bInput, guessInput;
    @FXML
    TextField stepsInput;
    @FXML
    ChoiceBox methodType;
    @FXML
    Button calcButton;
    @FXML
    LatexLabel answerLatexLabel;
    int methodNumber;
    public void init(MatlabConnection connection) {
        this.connection = connection;
        answerLatexLabel.setFontSize(12);
        methodType.setItems(FXCollections.observableArrayList("Cramer",
                "Gausse Elimination",
                "LU Doolittle",
                "LU Cholesky",
                "LU Crout",
                "Jacobi",
                "Gauss Seidel",
                "Eigen values",
                "Power Method"));
        methodType.getSelectionModel().selectedIndexProperty()
                .addListener(new ChangeListener<Number>() {
                    public void changed(ObservableValue ov, Number value, Number new_value) {
                        methodNumber = new_value.intValue();
                        System.out.println(methodNumber);
                    }
                });
        calcButton.setOnAction((event -> {
            String a = aInput.getText();
            String b = bInput.getText();
            String guess = guessInput.getText();
            String stepsText = stepsInput.getText();
            String aLines [] = a.split("\n");
            String bLines [] = b.split("\n");
            String guessLines [] = guess.split("\n");
            int steps = Integer.parseInt(stepsText);
            try {
                connection.eval("A = []");
                connection.eval("b = []");
                connection.eval("guess = []");

            } catch (MatlabInvocationException e) {
                e.printStackTrace();
            }
            double [] [] anumbers = new double[aLines.length][aLines.length];
            double [] bnumbers = new double[aLines.length];
            double []  gnumbers = new double[aLines.length];
            for (int i = 0; i < aLines.length; i++) {
                String numbers[] = aLines[i].split(" ");
                for (int j = 0; j< aLines.length; j++) {
                    anumbers[i][j] = Double.parseDouble(numbers[j]);
                    try {
                        connection.eval("A(" + (i+1) + "," + (j+1) + ") = " +numbers[j]);
                    } catch (MatlabInvocationException e) {
                        e.printStackTrace();
                    }
                }
            }
            for (int i = 0; i < bLines.length; i++) {
                    try {
                        bnumbers[i] = Double.parseDouble(bLines[i]);
                        String temp = "b(" + (i+1) + ",1) = " + bLines[i];
                        System.out.println(temp);
                        connection.eval("b(" + (i+1) + ",1) = " + bLines[i]);
                    } catch (MatlabInvocationException e) {
                        e.printStackTrace();
                    }
            }
            for (int i = 0; i < guessLines.length; i++) {
                try {
                    gnumbers[i] = Double.parseDouble(guessLines[i]);
                    connection.eval("guess(" + (i+1) + ",1) = " + guessLines[i]);
                } catch (MatlabInvocationException e) {
                    e.printStackTrace();
                }
            }
            Object aMatrix = new Object();
            Object bMatrix = new Object();
            Object gMatrix = new Object();
            try {
                aMatrix = connection.proxy.getVariable("A");
                bMatrix = connection.proxy.getVariable("b");
                gMatrix = connection.proxy.getVariable("guess");
            } catch (MatlabInvocationException e) {
                e.printStackTrace();
            }
            MatlabStruct input = new MatlabStruct(new MatlabStruct.Pair<String, Object>("", anumbers),
                    new MatlabStruct.Pair<String, Object>("", bMatrix),
                    new MatlabStruct.Pair<String, Object>("", gMatrix),
                    new MatlabStruct.Pair<String, Object>("", steps),
                    new MatlabStruct.Pair<String, Object>("", methodNumber+1)
                    );
            MatlabStruct output = new MatlabStruct();
            try {
                System.out.println("method number" + methodNumber+1);
                output = connection.feval("chapter-6", "mainFunc6", input, "fail", "message","history", "final");
            } catch (MatlabInvocationException e) {
                e.printStackTrace();
            }
            double [] temp = output.get("fail");
            int fail = (int) temp[0];
            String message = output.get( "message");
            if (fail == 1){
                answerLatexLabel.setLatex(message);
            }
            else {
                double[] history = output.get("history");
                double[] finalX = output.get("final");
                if (methodNumber == 0) {
                    String text = "";
                    text = "determinants = " + makeTable(history) + " \\\\ " + "X = " + makeTable(finalX);
                    answerLatexLabel.setLatex(text);
                }
                if (methodNumber == 1) {
                    String text = "";
                    int numberOfVars = aLines.length;
                    int numberOfDatas = numberOfVars + 1;
                    int sizeOfTable = (numberOfVars * numberOfDatas);
                    int numberOfTables = history.length / sizeOfTable;
                    double[][][] sorted = new double[numberOfTables][numberOfVars][numberOfDatas];
                    System.out.println("number of tables" + numberOfTables);
                    for (int i = 0; i < history.length; i++) {
                        sorted[i / sizeOfTable][i % numberOfVars][(i % sizeOfTable) / numberOfDatas] = history[i];
                    }
                    for (int i = 0; i < numberOfTables-1; i++) {
                        text = text + "   \\Rightarrow A = " + makeAugmentedTable(sorted[i], numberOfVars);
                        if (i % 2 == 1) {
                            text = text + " \\\\ ";
                        }
                    }
                    text = text + " \\\\ " + "X = " + makeTable(finalX);
                    answerLatexLabel.setLatex(text);
                }
                if (methodNumber<5 && methodNumber > 1) {
                    System.out.println(" in lu");
                    String text = "";
                    int numberOfVars = aLines.length;
                    int numberOfDatas = numberOfVars;
                    int sizeOfTable = (numberOfVars * numberOfDatas);
                    int numberOfTables = history.length / sizeOfTable;
                    double[][][] sorted = new double[numberOfTables][numberOfVars][numberOfDatas];
                    System.out.println("number of tables" + numberOfTables);
                    double [] y = new double[numberOfVars];
                    for (int i = 0; i < history.length; i++) {
                        sorted[i / sizeOfTable][i % numberOfVars][(i % sizeOfTable) / numberOfDatas] = history[i];
                        if (i/sizeOfTable == 2 && i< 2*sizeOfTable+numberOfVars){
                            y[i%sizeOfTable] = history[i];
                        }
                    }
                    text = text + "L = " + makeTable(sorted[0]) + "  ";
                    text = text + "U = " + makeTable(sorted[1]) + " \\\\ ";
                    text = text + "Y = " + makeTable(y) + "  ";
                    text = text + "X = " + makeTable(finalX) + "  ";
                    answerLatexLabel.setLatex(text);
                }
                if (methodNumber>4 && methodNumber<7){
                    String text = "";
                    int numberOfVars = steps;
                    int numberOfDatas = aLines.length;
                    double[][] All = new double[numberOfVars+numberOfDatas+1][numberOfDatas];
                    for (int i = 0; i < history.length; i++) {
                        System.out.println(history[i]);
                        All[i % (numberOfVars+numberOfDatas+1)][i / (numberOfVars+numberOfDatas+1)] = history[i];
                    }
                    double[][] sorted = new double[numberOfVars][numberOfDatas];
                    for (int i = 0; i < numberOfVars; i++) {
                        for (int j = 0; j < numberOfDatas; j++) {
                            sorted[i][j] = All [i][j];
                        }
                    }
                    boolean isZero = true;
                    double[][] createdMatrix = new double[aLines.length][aLines.length+1];
                    for (int i = 0; i < numberOfDatas; i++) {
                        for (int j = 0; j < numberOfDatas + 1; j++) {
                            createdMatrix[i][j] = All[j+numberOfVars][i];
                            if (createdMatrix[i][j]!=0){
                                isZero = false;
                            }
                        }
                    }
                    if (!isZero){
                        text = text + " A = " + makeTable(createdMatrix) + "  \\\\ ";
                    }
                    text = text + " X_{0} = " + makeTable(gnumbers) + "  ";
                    for (int i = 0; i < numberOfVars; i++) {
                        text = text + "X_{"+ (i+1) + "} = " + makeTable(sorted[i]) + "  ";
                        if (i%5 == 4){
                            text = text + " \\\\ ";
                        }
                    }
                    answerLatexLabel.setLatex(text);
                }
                if (methodNumber == 7){
                    String text = "";
                    int numberOfVars = guessLines.length;
                    int numberOfDatas = numberOfVars+1;
                    double[][] sorted = new double[numberOfVars][numberOfDatas];
                    for (int i = 0; i < finalX.length; i++) {
                        sorted[i % numberOfVars][i / numberOfVars] = finalX[i];
                    }
                    for (int i = 0; i < numberOfVars; i++) {
                        text = text + " \\lambda _{"+ (i+1) + "} = " + sorted[i][0] + "  , ";
                        double [] eigenValues = new double[numberOfDatas-1];
                        for (int j = 0; j < numberOfDatas-1; j++) {
                            eigenValues[j] = sorted[i][j+1];
                        }
                        text = text + "EV_{"+ (i+1) + "} = " + makeTable(eigenValues) + "  ";
                        if (i%5 == 4){
                            text = text + " \\\\ ";
                        }
                    }
                    answerLatexLabel.setLatex(text);
                }
                if (methodNumber == 8){
                    String text = "";
                    int numberOfVars = steps;
                    int numberOfDatas = aLines.length;
                    double[][] sorted = new double[numberOfVars][numberOfDatas];
                    for (int i = 0; i < history.length; i++) {
                        System.out.println(history[i]);
                        sorted[i % numberOfVars][i / numberOfVars] = history[i];
                    }
                    for (int i = 0; i < numberOfVars; i++) {
                        text = text + "X_{"+ (i+1) + "} = " + makeTable(sorted[i]) + "  ";
                        if (i%5 == 4){
                            text = text + " \\\\ ";
                        }
                    }
                    text = text + "\\\\ Answer =  " + makeTable(finalX);
                    answerLatexLabel.setLatex(text);
                }
            }
        }));
    }

    public static String makeTable(double [][] input){
        String output = new String("\\begin{bmatrix}\n");
        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < input[0].length; j++) {
                output = output +" " + input[i][j] + " ";
                if (j!= input[0].length-1){
                    output = output +" & ";
                }
            }
            output = output +" \\\\ ";
        }
        output = output + "\\end{bmatrix}";
        return output;
    }
    public static String makeAugmentedTable(double [][] input, int numberOfVal){
        String temp = "";
        for (int i = 0; i < numberOfVal; i++) {
            temp = temp+"c";
        }
        String output = new String("  \\left[ \\begin{array}  {"+temp+"|c} \n");
        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < input[0].length; j++) {
                output = output +" " + input[i][j] + " ";
                if (j!= input[0].length-1){
                    output = output +" & ";
                }
            }
            output = output +" \\\\ ";
        }
        output = output + "\\end{array}\n" +
                "        \\right]";
        return output;
    }
    public static String makeTable(double [] numbers){
        double [][] twoDim = new double[numbers.length][1];
        for (int i = 0; i < numbers.length; i++) {
            twoDim[i][0] = numbers[i];
        }
        return makeTable(twoDim);
    }

}
