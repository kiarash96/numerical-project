package project.app; /**
 * Created by moeen on 12/7/16.
 */

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import project.app.utility.MatlabConnection;
import project.controls.AbusedTextField;
import project.controls.LatexLabel;

/**
 * Created by kiarash on 12/7/16.
 */
public class ChapterOneView {

    @FXML
    AbusedTextField aVal, bVal, cVal, dVal, eVal, fVal, aError, bError, cError, dError, eError, fError, percesion, formula;
    @FXML
    Button evaluateButton;
    @FXML
    LatexLabel answer, latexLabelFormula;

    MatlabConnection connection;

    public ChapterOneView(MatlabConnection connection) {
        super();
        this.connection = connection;
    }

}

