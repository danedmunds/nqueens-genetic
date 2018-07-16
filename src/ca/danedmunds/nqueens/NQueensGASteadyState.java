package ca.danedmunds.nqueens;

import java.util.ArrayList;
import java.util.Random;

public class NQueensGASteadyState extends NQueensGA{
	
	public NQueensGASteadyState(int problemSize, int population, double crossoverRate, double mutationRate) {
		super(problemSize, population, crossoverRate, mutationRate);
	}


	private Random random = new Random();

	
	@Override
	public Gene step() {
		ArrayList<Gene> bestGenes = getBestGenes();
		
		//randomly get two "best" genes
		int p1 = random.nextInt(bestGenes.size());
		int p2 = random.nextInt(bestGenes.size());
		while(p1 == p2){
			p2 = random.nextInt(bestGenes.size());
		}
		

		
		Gene parent1 = bestGenes.get(p1);
		Gene parent2 = bestGenes.get(p2);

		
		Gene[] children = parent1.breed(parent2);
		
		
//		System.out.println("----------------------------");
//		System.out.println("number of best genes: "+bestGenes.size());
//		System.out.println("Parent1: "+parent1);
//		System.out.println("Parent2: "+parent2);
//		System.out.println("Child1: "+children[0]);
//		System.out.println("Child2: "+children[1]);
//		System.out.println("----------------------------");
		
		
		addGene(children[0]);
		addGene(children[1]);
		
		sortPopulationByFitness();
		removeEnoughToBringPopulationToNormal();
	
		//return the best Gene
		return getGeneAt(0);
	}
}
