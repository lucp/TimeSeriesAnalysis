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

    private double[] d1, d2;
    private double[][] confInt;

    public Statistics(TimeSeries s1, TimeSeries s2) {
        d1 = timeSeriesToDoubles(s1);
        d2 = timeSeriesToDoubles(s2);
    }

    /**
     * Wyznacz wspolczynniki wzajemnej korelacji dwoch szeregow czasowych dl n,
     * n>3
     *
     * @return tablica 2*n-7 elementowa wspolczynnikow korelacji
     */
    public double[] getCorrelationCoefficients() {

        double[] cc = new double[2 * d1.length - 7];
        confInt = new double[cc.length][2];
        PearsonsCorrelation pc = new PearsonsCorrelation();

        // korelacja dla opoznien dodatnich
        for (int i = 0; i < d1.length - 3; i++) {
            double[] p1 = Arrays.copyOfRange(d1, i, d1.length);
            double[] p2 = Arrays.copyOfRange(d2, 0, d1.length - i);
            cc[i + d1.length - 4] = pc.correlation(p1, p2);
            confInt[i + d1.length - 4] = findConfidenceIntervals(cc[i + d1.length - 4], d1.length);
        }

        //korelacja dla opoznien ujemnych
        for (int i = 1; i < d1.length - 3; i++) {
            double[] p1 = Arrays.copyOfRange(d1, 0, d1.length - i);
            double[] p2 = Arrays.copyOfRange(d2, i, d1.length);
            cc[d1.length - i - 4] = pc.correlation(p1, p2);
            confInt[d1.length - i - 4] = findConfidenceIntervals(cc[d1.length - i - 4], d1.length);
        }
        return cc;
    }

    /**
     * Wyznaczenie limitow wartosci wsp korelacji w 95% przedziale ufnosci
     *
     * @param r wspolczynnik korelacji
     * @param size wielkosc probki
     * @return dolna i gorna granica przedzialu ufnosci
     */
    private double[] findConfidenceIntervals(double r, int size) {
        double n3;
        n3 = Math.sqrt(1.0 / (size - 3));
        double z = 0.5 * Math.log((1 + r) / (1 - r));

        double lowR = z - 1.96 * n3;
        lowR = (Math.exp(2 * lowR) - 1) / (Math.exp(2 * lowR) + 1);

        double upR = z + 1.96 * n3;
        upR = (Math.exp(2 * upR) - 1) / (Math.exp(2 * upR) + 1);

        return new double[]{lowR, upR};
    }

    /**
     * zwraca limity wspolczynnikow korelacji dla 95% przedzialu ufnosci
     * @return talbica zawierajaca dolne i gorne limity dla kazdego wsp korelacji wzajemnej
     */
    public double[][] getConfidenceIntervals() {
        return confInt;
    }

    /**
     *
     * @return srednia pierwszego szeregu
     */
    public double getMeanOfSeries1() {
        return StatUtils.mean(d1);
    }

    /**
     *
     * @return srednia drugiego szeregu
     */
    public double getMeanOfSeries2() {
        return StatUtils.mean(d2);
    }

    /**
     *
     * @return wariancja pierwszego szeregu
     */
    public double getVarOfSeries1() {
        return StatUtils.variance(d1);
    }

    /**
     *
     * @return wariancja drugiego szeregu
     */
    public double getVarOfSeries2() {
        return StatUtils.variance(d2);
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
