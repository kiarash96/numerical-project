package project.controls;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;
import javafx.scene.text.Font;

/**
 * Created by kiarash on 12/7/16.
 */
public class LatexLabel extends Control {

    private StringProperty latex;
    private DoubleProperty fontSize;

    public LatexLabel() {
        latex = new SimpleStringProperty("");
        fontSize = new SimpleDoubleProperty(Font.getDefault().getSize());
    }

    @Override
    public Skin<LatexLabel> createDefaultSkin() {
        return new LatexLabelSkin(this);
    }

    public String getLatex() {
        return latex.get();
    }

    public StringProperty latexProperty() {
        return latex;
    }

    public void setLatex(String latex) {
        this.latex.set(latex);
    }

    public double getFontSize() {
        return fontSize.get();
    }

    public DoubleProperty fontSizeProperty() {
        return fontSize;
    }

    public void setFontSize(double fontSize) {
        this.fontSize.set(fontSize);
    }
}

