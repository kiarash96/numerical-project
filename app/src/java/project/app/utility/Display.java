package project.app.utility;

/**
 * Created by kiarash on 1/13/17.
 */
public class Display {
    public static String numToStr(double x) {
        return "" + x;
    }

    public static String alignArray(double[] arr) {
        StringBuilder out = new StringBuilder();
        for (int i = 0; i < arr.length; i ++)
            out.append((i == 0 ? "" : " & ") + numToStr(arr[i]));
        return out.toString();
    }
    

}
