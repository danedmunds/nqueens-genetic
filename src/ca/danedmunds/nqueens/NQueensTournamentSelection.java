package ca.danedmunds.nqueens;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class NQueensTournamentSelection extends NQueensGA {
	
	private Random random;
	private int tournamentSize;
	private int breedPoolSize;
	private int numberOfBreedings;

	public NQueensTournamentSelection(int problemSize, int population, double crossoverRate, double mutationRate) {
		super(problemSize, population, crossoverRate, mutationRate);
		tournamentSize = population / 10;
		breedPoolSize = population / 10;
		numberOfBreedings = population / 12;
		random = new Random();
	}

	
	public Gene step() {
		ArrayList<Gene> breeders = getBreeders();
		
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
	
	public ArrayList<Gene> getBreeders(){
		ArrayList<Gene> breeders = new ArrayList<Gene>();
		
		//we'll produce 1/4 of the population size of offspring
		ArrayList<Gene> population;
		ArrayList<Gene> tournament;
		
		for(int i = 0; i<breedPoolSize; ++i){
			population = new ArrayList<Gene>(getPopulation());
			tournament = new ArrayList<Gene>();
			
			//choose Genes at random to be in the tournament
			for(int x=0; x<tournamentSize && !population.isEmpty(); ++x){
				tournament.add(population.remove(random.nextInt(population.size())));
			}
			
			//sort the list by fitness so we can get the tournament winner
			Collections.sort(tournament, new Comparator<Gene>(){
				
				public int compare(Gene o1, Gene o2) {
					return o1.getFitness() - o2.getFitness();
				}
			});
			
			//get a winner we don't already have as a breeder
			Gene winner;
			do {
				winner = tournament.remove(0);
			} while (!tournament.isEmpty() && breeders.contains(winner));
			
			if(winner != null){
				breeders.add(winner);
			}
		}
		
		return breeders;
	}
	
	public static void main(String[] args){
		NQueensTournamentSelection nqueen = new NQueensTournamentSelection(10, 50, 1, 0.5);
		nqueen.setTournamentSize(7);
		nqueen.setBreedPoolSize(7);
		nqueen.step();
	}

	public int getTournamentSize() {
		return tournamentSize;
	}

	public void setTournamentSize(int tournamentSize) {
		this.tournamentSize = tournamentSize;
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

}
