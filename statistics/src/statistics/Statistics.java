/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statistics;

import java.util.Arrays;
import org.apache.commons.math3.stat.StatUtils;
import org.apache.commons.math3.stat.correlation.*;
import org.jfree.data.time.TimeSeries;

/**
 *
 * @author synu
 *
 */
public class Statistics {

    public Statistics() {
    }

    /**
     * Wyznacz wspolczynniki wzajemnej korelacji dwo³ch szeregow czasowych tej
     * samej dlugosci
     *
     * @param s1 Tablica n - pierwszy szereg czasowy
     * @param s2 Tablica n - drugi szereg czasowy
     * @return tablica 2*n-1 elementowa wspolczynnikow korelacji
     */
    public double[] getCorrelationCoefficients(TimeSeries s1, TimeSeries s2) {

        double[] d1 = timeSeriesToDoubles(s1);
        double[] d2 = timeSeriesToDoubles(s2);

        SpearmansCorrelation pc = new SpearmansCorrelation();
        int l;
        double[] cc = new double[2 * d1.length - 1];

        for (int i = 0; i < d1.length; i++) {
            cc[i + d1.length] = pc.correlation(Arrays.copyOfRange(d1, i, d1.length - 1),
                    Arrays.copyOfRange(d2, 0, d1.length - i - 1));
        }

        for (int i = d1.length; i > 0; i--) {
            cc[d1.length - i] = pc.correlation(Arrays.copyOfRange(d1, 0, d1.length - i - 1),
                    Arrays.copyOfRange(d2, i, d1.length - 1));
        }

        return cc;
    }

    /**
     * Wyznacz srednia arytmetyczna (estymator wartosci oczekiwanej)
     *
     * @param data Tablica wartosci szeregu czasowego
     * @return
     */
    public double getMean(double[] data) {
        return StatUtils.mean(data);
    }


    /**
     * 
     * @param s szereg czasowy wejsciowy
     * @return tablica doubli wartosci 
     */
    private double[] timeSeriesToDoubles(TimeSeries s) {
        int length = s.getItemCount();
        double[] d = new double[length];
        for (int i = 0; i < length; i++) {
            d[i] = (double)s.getValue(i);
        }

        return d;
    }

}
