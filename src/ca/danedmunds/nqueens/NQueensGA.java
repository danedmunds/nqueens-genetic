package ca.danedmunds.nqueens;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public abstract class NQueensGA {
	
	private int problemSize;
	private int populationSize;
	private double crossoverRate;
	private double mutationRate;
	
	protected ArrayList<Gene> population;
	
	public NQueensGA(int problemSize, int population, double crossoverRate, double mutationRate){
		this.problemSize = problemSize;
		this.populationSize = population;
		this.crossoverRate = crossoverRate;
		this.mutationRate = mutationRate;
		
		initializePopulation();
	}
	
	protected void initializePopulation(){
		population = new ArrayList<Gene>();
		while(population.size() < populationSize){
			Gene toAdd = new Gene(this);
			toAdd.setFitness(FitnessEvaluator.getFitness(toAdd));
			addGene(toAdd);
		}
//		for(int i=0; i<populationSize; ++i){
//			Gene toAdd = new Gene(this);
//			toAdd.setFitness(FitnessEvaluator.getFitness(toAdd));
//			population.add(toAdd);
//		}
	}
	
	protected void sortPopulationByFitness(){
		Collections.sort(population, new Comparator<Gene>(){
			
			public int compare(Gene o1, Gene o2) {
				return o1.getFitness() - o2.getFitness();
			}
		});
	}
	
	protected Gene getGeneAt(int index){
		return population.get(index);
	}
	
	protected ArrayList<Gene> getBestGenes(){
		sortPopulationByFitness();
		
		ArrayList<Gene> result = new ArrayList<Gene>();
		
		Gene best = getGeneAt(0);
		int bestFitness = best.getFitness();
		
		for(Gene gene : population){
			if(gene.getFitness() != bestFitness){
				break;
			} else {
				result.add(gene);
			}
		}
		
		//need at least two best genes
		if(result.size() == 1){
			result.add(getGeneAt(1));
		}
		
		return result;
	}
	
	protected ArrayList<Gene> getPopulation(){
		return population;
	}
	
	protected void killPopulation(){
		population.clear();
	}
	
	protected Gene removeGeneAt(int index){
		return population.remove(index);
	}
	
	protected void removeEnoughToBringPopulationToNormal(){
		while(population.size() > populationSize){
			removeGeneAt(population.size() - 1);
		}
	}
	
	protected void addGene(Gene gene){
		if(!population.contains(gene)){
			population.add(gene);
		}
	}
	
	public abstract Gene step();

	public int getProblemSize() {
		return problemSize;
	}

	public int getPopulationSize() {
		return populationSize;
	}

	public double getCrossoverRate() {
		return crossoverRate;
	}

	public double getMutationRate() {
		return mutationRate;
	}

}
