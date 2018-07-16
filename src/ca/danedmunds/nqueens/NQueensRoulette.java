package ca.danedmunds.nqueens;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class NQueensRoulette extends NQueensGA {
	
	private Random random;
	private int breedPoolSize;
	private int numberOfBreedings;
	private boolean generational;
	
	public NQueensRoulette(int problemSize, int population, double crossoverRate, double mutationRate) {
		super(problemSize, population, crossoverRate, mutationRate);
		random = new Random();
		breedPoolSize = population / 5;
		numberOfBreedings = population / 2;
		generational = true;
	}

	@Override
	public Gene step() {
		ArrayList<Gene> breeders = new ArrayList<Gene>();
		ArrayList<Gene> shuffledPopulation = new ArrayList<Gene>(getPopulation());
		Collections.shuffle(shuffledPopulation);
		
		int totalFitness = 0;
		for(Gene gene : shuffledPopulation){
			totalFitness += gene.getFitness();
		}
		
		while(breeders.size() < breedPoolSize && !shuffledPopulation.isEmpty()){
			int rouletteSpin = random.nextInt(totalFitness + 1);
			int runningTotal = 0;
			//make sure we traverse in order
			for(int i=0; i<shuffledPopulation.size(); ++i){
				Gene current = shuffledPopulation.get(i);
				runningTotal += current.getFitness();
				
				if(runningTotal > rouletteSpin){
					breeders.add(current);
					shuffledPopulation.remove(i);
					totalFitness -= current.getFitness();
					break;
				}
			}
		}
		
		if(generational){
			//generate enough children to replace the current generation
			numberOfBreedings = getPopulationSize() / 2;
			killPopulation();
		}
		
		for(int i=0; i<numberOfBreedings; ++i){
			Gene p1 = breeders.remove(random.nextInt(breeders.size()));
			Gene p2 = breeders.remove(random.nextInt(breeders.size()));
			
			Gene[] children = p1.breed(p2);
			addGene(children[0]);
			addGene(children[1]);
			
			breeders.add(p1);
			breeders.add(p2);
		}
		
		sortPopulationByFitness();
		removeEnoughToBringPopulationToNormal();
		
		return getGeneAt(0);
	}

	public int getBreedPoolSize() {
		return breedPoolSize;
	}

	public void setBreedPoolSize(int breedPoolSize) {
		this.breedPoolSize = breedPoolSize;
	}

	public int getNumberOfBreedings() {
		return numberOfBreedings;
	}

	public void setNumberOfBreedings(int numberOfBreedings) {
		this.numberOfBreedings = numberOfBreedings;
	}

	public boolean isGenerational() {
		return generational;
	}

	public void setGenerational(boolean generational) {
		this.generational = generational;
	}

}
