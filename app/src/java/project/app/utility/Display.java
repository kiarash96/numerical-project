package project.app.utility;

import java.text.DecimalFormat;

/**
 * Created by kiarash on 1/13/17.
 */
public class Display {
    public static DecimalFormat format = new DecimalFormat("#.####");

    public static String numToStr(double x) {
        return "" + format.format(x);
    }

    public static String alignArray(double[] arr) {
        StringBuilder out = new StringBuilder();
        for (int i = 0; i < arr.length; i ++)
            out.append((i == 0 ? "" : " & ") + numToStr(arr[i]));
        return out.toString();
    }

    public static void setDigits(int n) {
        String str = "#.";
        for (int i = 0; i < n; i ++)
            str += "#";
        format = new DecimalFormat(str);
    }

}
