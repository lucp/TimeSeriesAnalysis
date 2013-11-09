package forecasting.model;

/**
 * Klasa modelowa chromosomu
 */
public class Chromosome implements Comparable {

    private double[] genes;
    private double fitness;

    /**
     * Konstruktor
     *
     * @param genes Geny
     */
    public Chromosome(double[] genes){
        setGenes(genes);
    }

    /**
     * Pobierz geny
     *
     * @return Geny
     */
    public double[] getGenes(){
        return genes;
    }

    /**
     * Ustaw geny
     *
     * @param genes Geny
     */
    public void setGenes(double[] genes){
        this.genes = genes;
    }

    /**
     * Pobierz gen na danym indeksie
     *
     * @param index Indeks
     * @return Gen
     */
    public double getGene(int index){
        return genes[index];
    }

    /**
     * Dodaj do danego genu
     *
     * @param index Indeks genu
     * @param val Wartosc jaka ma byc dodana
     */
    public void addToGene(int index, double val){
        genes[index] += val;
    }

    /**
     * Pobierz wartosc fukcji fitness
     *
     * @return Wartosc funkcji fitness
     */
    public double getFitness(){
        return fitness;
    }

    /**
     * Ustaw wartosc funkcji fitness
     *
     * @param fitness Wartosc funkcji fitness
     */
    public void setFitness(double fitness){
        this.fitness = fitness;
    }

    /**
     * Pobierz rozmiar chromosomu
     *
     * @return Rozmiar chromosomu
     */
    public int getSize(){
        return genes.length;
    }

    /**
     * Porownaj obiekt z danym obiektem
     *
     * @param o Obiekt
     * @return Porownanie, metoda naturalna
     */
    @Override
    public int compareTo(Object o) {
        Chromosome chromosome = (Chromosome) o;
        if(this.fitness < chromosome.getFitness()){
            return -1;
        }
        if(this.fitness > chromosome.getFitness()){
            return 1;
        }
        return 0;
    }
}
