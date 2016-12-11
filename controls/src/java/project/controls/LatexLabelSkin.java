package project.controls;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.SkinBase;
import javafx.scene.image.ImageView;
import org.scilab.forge.jlatexmath.TeXConstants;
import org.scilab.forge.jlatexmath.TeXFormula;
import org.scilab.forge.jlatexmath.TeXIcon;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * Created by kiarash on 12/10/16.
 */

// TODO: Separate behavior from skin
public class LatexLabelSkin extends SkinBase<LatexLabel> {

    private ImageView iv;

    /**
     * Constructor for all SkinBase instances.
     *
     * @param control The control for which this Skin should attach to.
     */
    protected LatexLabelSkin(LatexLabel control) {
        super(control);

        iv = new ImageView();
        this.getChildren().add(iv);

        getSkinnable().latexProperty().addListener((observable) -> render());
        getSkinnable().fontSizeProperty().addListener((observable) -> render());
        getSkinnable().widthProperty().addListener((observable) -> render());
        getSkinnable().heightProperty().addListener((observable) -> render());

        render();
    }

    private void render() {
        TeXFormula formula = new TeXFormula(getSkinnable().getLatex());
        TeXIcon icon = formula.new TeXIconBuilder().setStyle(TeXConstants.STYLE_DISPLAY)
                .setSize((float) getSkinnable().getFontSize())
                .setWidth(TeXConstants.UNIT_PIXEL, (float)getSkinnable().getWidth(), TeXConstants.ALIGN_CENTER)
                .setIsMaxWidth(true).setInterLineSpacing(TeXConstants.UNIT_PIXEL, 20f)
                .build();
        BufferedImage bufferedImage = new BufferedImage(icon.getIconWidth(),
                icon.getIconHeight(),
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = bufferedImage.createGraphics();
        icon.paintIcon(null, g, 0, 0);
        g.dispose();

        iv.setFitHeight(bufferedImage.getHeight());
        iv.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
    }

    @Override
    protected double computeMinWidth(double height,
                                 double topInset,
                                 double rightInset,
                                 double bottomInset,
                                 double leftInset) {
        /* TODO: A better implementation would be to calculate
            the maximum word length */
        /* TODO: getSkinnable().getMinWidth() is still -1
        System.err.println(getSkinnable().getMinWidth()); */
        return 1;
    }

}
