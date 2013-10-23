package forecasting.model;

public class Chromosome implements Comparable {

    private double[] genes;
    private double fitness;

    public Chromosome(double[] genes){
        setGenes(genes);
    }

    public double[] getGenes(){
        return genes;
    }

    public void setGenes(double[] genes){
        this.genes = genes;
    }

    public double getGene(int index){
        return genes[index];
    }

    public void addToGene(int index, double val){
        genes[index] += val;
    }

    public double getFitness(){
        return fitness;
    }

    public void setFitness(double fitness){
        this.fitness = fitness;
    }

    public int getSize(){
        return genes.length;
    }

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
