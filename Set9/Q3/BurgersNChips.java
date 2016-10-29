package BandC;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Created by shiv on 25/06/15.
 */
public class BurgersNChips {

    public static void main(String []args){
        BufferedReader bufferReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            int t = Integer.parseInt(bufferReader.readLine());
            for (int tc=1; tc <= t; tc++) {
                String[] input = bufferReader.readLine().split("\\s+");

                int r=Integer.parseInt(input[0]);
                int b=Integer.parseInt(input[1]);
                //double radiance=Math.toRadians(180 / b);
                double rad=Math.PI/b;
                if(b==1){
                    System.out.println("Case #"+tc+": "+(double)r);

                }
                else {
                    double radiusInner=(double)(r+r)/(2*Math.sin(Math.PI/b));
                    double radiusOuter=radiusInner+r;
                    NumberFormat num=new DecimalFormat("########.##########");
                    System.out.println("Case #"+tc+": "+num.format(radiusOuter));
                    //System.out.printf("Case # %d : %f \n",tc,radiusOuter);
                }


            }


            } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
