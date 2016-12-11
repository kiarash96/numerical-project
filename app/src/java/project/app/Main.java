package project.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.TitledPane;
import javafx.stage.Stage;
import project.app.utility.MatlabConnection;

/**
 * Created by kiarash on 12/9/16.
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        /*MatlabConnection connection = new MatlabConnection();
        connection.open();
        connection.setRootPath("/home/kiarash");

        MatlabStruct args = new MatlabStruct(
                new MatlabStruct.Pair<>("x", 3),
                new MatlabStruct.Pair<>("y", 10),
                new MatlabStruct.Pair<>("z", "kiaraash"));

        MatlabStruct ret = connection.feval("", "kia", args, "a", "b", "c");
        double[] a = ret.get("a");
        String b = ret.get("b");
        double c = ret.get("c");

        System.err.println("a = [" + a[0] + " " + a[1] + "]");
        System.err.println("b = " + b);
        System.err.println("c = " + c);

        TextField tf = new TextField("\\frac{1}{2 + \\frac{3}{5}}");
        LatexLabel l = new LatexLabel();
        l.setFontSize(32);
        //l.latexProperty().bind(tf.textProperty());
        LatexParser parser = new LatexParser();
        Label label = new Label();
        tf.textProperty().addListener((observable, oldValue, newValue) -> {
            l.setLatex(parser.latex(newValue));
            parser.setHashtag(true);
            label.setText(parser.postfix(newValue));
            parser.setHashtag(false);
        });

        VBox box = new VBox(tf, l, label);
        primaryStage.setOnCloseRequest((event) -> connection.close());
        primaryStage.setScene(new Scene(box));
        primaryStage.show();*/

        MatlabConnection connection = new MatlabConnection();
        connection.open();
        connection.setRootPath(getClass().getResource("/matlab").getPath());
        primaryStage.setOnCloseRequest((event) -> connection.close());

        /*FXMLLoader loader1 = new FXMLLoader(getClass().getResource("/chapter-1.fxml"));
        ChapterOneView ch1Controller = new ChapterOneView();
        loader1.setController(ch1Controller);
        TitledPane root1 = loader1.load();
        ch1Controller.init(connection);*/

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/chapter-2.fxml"));
        ChapterTwoView ch2controller = new ChapterTwoView();
        loader.setController(ch2controller);
        TitledPane root2 = loader.load();
        ch2controller.init(connection);

        /*FXMLLoader loader3 = new FXMLLoader(getClass().getResource("/chapter-3.fxml"));
        ChapterThreeView ch3Controller = new ChapterThreeView();
        loader3.setController(ch3Controller);
        TitledPane root3 = loader3.load();
        ch3Controller.init(connection);*/

        Accordion accordion = new Accordion();
        accordion.getPanes().addAll(root2);
        accordion.setExpandedPane(root2);

        primaryStage.setTitle("Numerical Methods Project");
        primaryStage.setScene(new Scene(accordion));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
