/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statistics;

import org.apache.commons.math3.stat.StatUtils;
import org.apache.commons.math3.stat.correlation.*;

/**
 *
 * @author synu
 *
 */
public class Statistics {

    public Statistics() {
    }

    /**
     * Wyznacz wspólczynnik korelacji Pearsona dwóch szeregów czasowych
     * @param d1 Tablica wartosci pierwszego szeregu 
     * @param d2 Tablica wartosci drugiego szeregu
     * @return 
     */
    public double getCorrelationCoefficient(double[] d1, double[] d2) {
        PearsonsCorrelation pc = new PearsonsCorrelation();
        return pc.correlation(d1, d2);
    }

    /**
     * Wyznacz srednia arytmetyczna (estymator wartosci oczekiwanej)
     * @param data Tablica wartosci szeregu czasowego
     * @return 
     */
    public double getMean(double[] data) {
        return StatUtils.mean(data);
    }

    /**
     * Wyznacz odchylenie standardowe
     * @param data Tablica wartosci szeregu czasowego
     * @return 
     */
    public double getVariance(double[] data) {
        return StatUtils.variance(data);
    }

}
