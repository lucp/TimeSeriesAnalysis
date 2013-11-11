package forecasting;

import forecasting.model.Chromosome;
import forecasting.model.SlidingTimeWindow;
import org.jfree.data.time.TimeSeries;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

public class TimeSeriesForecast extends AbstractForecast{

    private static final int RANGE_MIN = -1;
    private static final int RANGE_MAX = 1;
    private double percentOfKeptFromSelection = 0.4;
    private double percentOfKeptFromCrossover = 0.4;
    private double percentOfKeptFromMutation = 0.2;

    @Autowired
    private AbstractGeneticAlgorithmOperation selection;

    @Autowired
    private AbstractGeneticAlgorithmOperation crossover;

    @Autowired
    private AbstractGeneticAlgorithmOperation mutation;

    @Autowired
    private AbstractFitnessCalculator fitnessCalculator;

    @Autowired
    private AbstractForecastCalculator forecastCalculator;

    private TimeSeries timeSeries;
    private int populationSize;
    private SlidingTimeWindow slidingTimeWindow;
    private int numOfIterations;
    private int numOfDataPoints;

    private boolean doForecast = false;
    private Chromosome globalBest;

    private List<GAObserver> obs = new ArrayList<>();

    public void initializeForecast(int numOfDataPoints){
        this.numOfDataPoints = numOfDataPoints;
        this.doForecast = true;
    }

    public void initializeGeneticAlgorithm(TimeSeries timeSeries,
                                           int populationSize,
                                           SlidingTimeWindow slidingTimeWindow,
                                           int numOfIterations,
                                           double crossoverProbability,
                                           double mutationProbability,
                                           double percentOfKeptFromSelection ,
                                           double percentOfKeptFromCrossover,
                                           double percentOfKeptFromMutation){

        this.populationSize = populationSize;
        this.slidingTimeWindow = slidingTimeWindow;
        this.numOfIterations = numOfIterations;
        this.percentOfKeptFromSelection = percentOfKeptFromSelection;
        this.percentOfKeptFromCrossover = percentOfKeptFromCrossover;
        this.percentOfKeptFromMutation = percentOfKeptFromMutation;
        crossover.setProbability(crossoverProbability);
        mutation.setProbability(mutationProbability);
        this.timeSeries = timeSeries;

    }

    private Chromosome[] initializePopulation(int populationSize, SlidingTimeWindow stw){

        Chromosome[] population = new Chromosome[populationSize];

        int chromosomeSize = stw.getLength() + 1;

        if(forecastCalculator instanceof ARMAForecastCalculator){
            chromosomeSize += stw.getLength();
        }

        Random random = new Random();

        for(int i = 0; i < population.length; i++){

            double[] genes = new double[chromosomeSize];

            for(int j = 0; j < genes.length; j++){
                genes[j] = RANGE_MIN + (RANGE_MAX - RANGE_MIN) * random.nextDouble();
            }

            population[i] = new Chromosome(genes);
        }
        return population;
    }

    private void calculateFitnessForPopulation(Chromosome[] population,
                                               TimeSeries timeSeries,
                                               SlidingTimeWindow window){

        for(Chromosome chromosome : population){
            double fitness = fitnessCalculator.calculateFitness(timeSeries, window, chromosome);
            chromosome.setFitness(fitness);
        }
    }

    private Chromosome findBestInPopulation(Chromosome[] population){
        Chromosome best = population[0];
        for(int i = 1; i < population.length; i++){
            if(population[i].getFitness() < best.getFitness()){
                best = population[i];
            }
        }
        return best;
    }

    @Override
    protected Chromosome doInBackground() throws Exception {

        globalBest = null;

        Chromosome[] population = initializePopulation(populationSize, slidingTimeWindow);
        calculateFitnessForPopulation(population, timeSeries, slidingTimeWindow);

        for(int i = 0; i < numOfIterations; i++){
            Chromosome[] selectionPopulation = selection.performGeneticOperation(population);
            calculateFitnessForPopulation(selectionPopulation, timeSeries, slidingTimeWindow);
            Arrays.sort(selectionPopulation);

            Chromosome[] crossoverPopulation = crossover.performGeneticOperation(population);
            calculateFitnessForPopulation(crossoverPopulation, timeSeries, slidingTimeWindow);
            Arrays.sort(crossoverPopulation);

            Chromosome[] mutationPopulation = mutation.performGeneticOperation(population);
            calculateFitnessForPopulation(mutationPopulation, timeSeries, slidingTimeWindow);
            Arrays.sort(mutationPopulation);

            int numOfKeptFromSelection = (int) (percentOfKeptFromSelection * populationSize);

            int numOfKeptFromCrossover = (int) (percentOfKeptFromCrossover * populationSize);

            int numOfKeptFromMutation = (int) (percentOfKeptFromMutation * populationSize);

            while(numOfKeptFromSelection +
                    numOfKeptFromCrossover +
                    numOfKeptFromMutation > populationSize){
                numOfKeptFromSelection--;
            }

            while(numOfKeptFromSelection +
                    numOfKeptFromCrossover +
                    numOfKeptFromMutation < populationSize){
                numOfKeptFromSelection++;
            }

            System.arraycopy(selectionPopulation, 0, population, 0, numOfKeptFromSelection);

            System.arraycopy(crossoverPopulation, 0, population, numOfKeptFromSelection, numOfKeptFromCrossover);

            System.arraycopy(mutationPopulation, 0, population, numOfKeptFromSelection + numOfKeptFromCrossover, numOfKeptFromMutation);

            Chromosome best = findBestInPopulation(population);

            if(globalBest == null || globalBest.getFitness() > best.getFitness()){
                globalBest = best;
            }

            for(GAObserver observer : obs){
                observer.update(best.getFitness(), best.getGenes(), i);
            }
        }

        return globalBest;
    }

    @Override
    public TimeSeries doForecast(TimeSeries timeSeries, int numOfDataPoints, double[] genes){

        Chromosome chromosome = new Chromosome(genes);

        for(int i = 0; i < numOfDataPoints; i++){

            double forecast = forecastCalculator.calculateForecast(timeSeries,
                    slidingTimeWindow,
                    chromosome,
                    timeSeries.getItemCount());

            timeSeries.add(timeSeries.getNextTimePeriod(), forecast);
        }
        return timeSeries;
    }

    public void addObserver(GAObserver o){
        obs.add(o);
    }

    @Override
    protected void done() {

        if(doForecast){
            doForecast(timeSeries, numOfDataPoints, globalBest.getGenes());
        }

        for(GAObserver observer : obs){
            observer.done(timeSeries);
            observer.done(globalBest.getGenes());
        }
    }
}
