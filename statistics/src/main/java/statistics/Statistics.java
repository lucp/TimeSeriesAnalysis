/**
 * Copyright (c) 2013, Tomasz Choma, Olgierd Grodzki, £ukasz Potêpa, Monika
 * Rakoczy, Pawe³ Synowiec, £ukasz Szarkowicz
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package statistics;

import java.util.Arrays;
import java.util.Comparator;
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
    private double[][] confidenceIntervals;
    private double[] cc;

    public Statistics() {
    }

    /**
     * wczytuje szeregi czasowe, ktore maja byc porownywane
     *
     * @param s1 szereg czasowy do porownania
     * @param s2 szereg czasowy do porownania
     */
    public void loadTimeSeries(TimeSeries s1, TimeSeries s2) {
        d1 = timeSeriesToDoubles(s1);
        d2 = timeSeriesToDoubles(s2);
        if (d1.length < d2.length) {
            d1 = zeroPadding(d1, d2.length);
        } else if (d2.length < d1.length) {
            d2 = zeroPadding(d2, d1.length);
        }
    }

    /**
     * Wyznacza nalepszy wspolczynnik korelacji, tj. o najwiekszej wartosci
     * r/rho, r - wsp korelacji, rho - szerokosc przedzialu ufnosci
     *
     * @return najlepszy wspolczynnik korelacji i jego przedzial 95% ufnosci
     */
    public double[] findBestCoefficient() {

        cc = new double[2 * d1.length - 7];
        confidenceIntervals = new double[cc.length][2];
        PearsonsCorrelation pc = new PearsonsCorrelation();

        // korelacja dla opoznien dodatnich
        for (int i = 0; i < d1.length - 3; i++) {
            double[] p1 = Arrays.copyOfRange(d1, i, d1.length);
            double[] p2 = Arrays.copyOfRange(d2, 0, d1.length - i);
            cc[i + d1.length - 4] = pc.correlation(p1, p2);
            confidenceIntervals[i + d1.length - 4] = findConfidenceIntervals(cc[i + d1.length - 4], d1.length);
        }

        //korelacja dla opoznien ujemnych
        for (int i = 1; i < d1.length - 3; i++) {
            double[] p1 = Arrays.copyOfRange(d1, 0, d1.length - i);
            double[] p2 = Arrays.copyOfRange(d2, i, d1.length);
            cc[d1.length - i - 4] = pc.correlation(p1, p2);
            confidenceIntervals[d1.length - i - 4] = findConfidenceIntervals(cc[d1.length - i - 4], d1.length);
        }

        double[][] bestResults = new double[confidenceIntervals.length][2];
        for (int i = 0; i < confidenceIntervals.length; i++) {
            bestResults[i][0] = i;
            bestResults[i][1] = Math.abs(cc[i]) / (confidenceIntervals[i][1] - confidenceIntervals[i][1]);
        }

        Arrays.sort(bestResults, new Comparator<double[]>() {

            @Override
            public int compare(double[] t, double[] t1) {
                return Double.compare(t[1], t1[1]);
            }
        });

        int i = (int) bestResults[0][0];

        return new double[]{cc[i], confidenceIntervals[i][0], confidenceIntervals[i][1]};
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
     *
     * @return tablica zawierajaca dolne i gorne limity dla kazdego wsp
     * korelacji wzajemnej
     */
    public double[][] getConfidenceIntervals() {
        return confidenceIntervals;
    }

    /**
     * zwraca wyznaczone wspolczynniki korelacji wzajemnej szeregow
     *
     * @return tablica wsp. korelacji
     */
    public double[] getCorrelationCoefficients() {
        return cc;
    }

    /**
     * zwraca srednia pierwszego szeregu
     *
     * @return srednia pierwszego szeregu
     */
    public double getMeanOfSeries1() {
        return StatUtils.mean(d1);
    }

    /**
     * zwraca srednia drugiego szeregu
     *
     * @return srednia drugiego szeregu
     */
    public double getMeanOfSeries2() {
        return StatUtils.mean(d2);
    }

    /**
     * zwraca wariancje pierwszego szeregu
     *
     * @return wariancja pierwszego szeregu
     */
    public double getVarOfSeries1() {
        return StatUtils.variance(d1);
    }

    /**
     * zwraca wariancje drugiego szeregu
     *
     * @return wariancja drugiego szeregu
     */
    public double getVarOfSeries2() {
        return StatUtils.variance(d2);
    }

    /**
     * zwraca tablice doubli zawierajaca wartosci przekazanego szeregu czasowego
     * TimeSeries
     *
     * @param s szereg czasowy TimeSeries
     * @return tablica doubli wartosci szeregu s
     */
    private double[] timeSeriesToDoubles(TimeSeries s) {
        int length = s.getItemCount();
        double[] d = new double[length];
        for (int i = 0; i < length; i++) {
            d[i] = (double) s.getValue(i).doubleValue();
        }

        return d;
    }

    /**
     * Uzupelnienie zerami krotszej tablicy do dlugosci drugiej, dluzszej
     * tablicy
     *
     * @param shorter mniejsza tablica, ktora ma byc uzupelniona zerami
     * @param size rozmiar, do jakiego ma byc uzupelniona tablica
     * @return
     */
    private double[] zeroPadding(double[] shorter, int size) {
        double[] t = new double[size];
        System.arraycopy(shorter, 0, t, 0, shorter.length);
        Arrays.fill(t, shorter.length, t.length, 0);
        return t;
    }

}
