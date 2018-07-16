package ca.danedmunds.nqueens;

public class SolverTester {
	
	static int SIZE = 10;
	static int LOOPS = 100;
	
	public static int solveRandom(){
		int iteration = 0;
		
		NQueensGASteadyState world = new NQueensGASteadyState(SIZE, 1, 1, 1);
		Gene gene;
		
		do {
			++iteration;
			gene = new Gene(world);
			//System.out.println(gene+"  fitness: "+FitnessEvaluator.getFitness(gene)+" iteration: "+iteration);
		} while(FitnessEvaluator.getFitness(gene) != 0);
		
		return iteration;
	}
	
	public static int solveGenerationalRoulette(){
		NQueensRoulette world = new NQueensRoulette(SIZE, 50, 1, 0.5);
		world.setGenerational(false);
		world.setNumberOfBreedings(50 / 10);
		int iteration = 0;
		Gene gene;
		
		do {
			++iteration;
			gene = world.step();
		} while(FitnessEvaluator.getFitness(gene) != 0);
		
		return iteration;
	}
	
	public static int solveGeneticAlgorithm(){
		NQueensGASteadyState world = new NQueensGASteadyState(SIZE, 50, 1, 0.5);
		int iteration = 0;
		Gene gene;
		
		do {
			++iteration;
			gene = world.step();
		} while(FitnessEvaluator.getFitness(gene) != 0);
		
		return iteration;
	}
	
	public static int solveTournamentSelection(){
		NQueensTournamentSelection world = new NQueensTournamentSelection(SIZE, 50, 1, 0.5);
		world.setTournamentSize(7);
		world.setBreedPoolSize(7);
		int iteration = 0;
		Gene gene;
		
		do {
			++iteration;
			gene = world.step();
		} while(FitnessEvaluator.getFitness(gene) != 0);
		
		return iteration;
	}
	
	public static void main(String[] args){
		SIZE = 10;
		
		int randomTotal = 0;
		long randomNanos = 0;
		
		int geneticTotal = 0;
		long geneticNanos = 0;
		
		int tournamentTotal = 0;
		long tournamentNanos = 0;
		
		int rouletteTotal = 0;
		long rouletteNanos = 0;
		
		//warm up
		solveGenerationalRoulette();
		solveGeneticAlgorithm();
		solveRandom();
		solveTournamentSelection();
		
		long start;
		long end;
		
		for(int i=0; i<LOOPS; ++i){
			System.out.println(""+(LOOPS - i)+" iterations left");
			
			System.out.println("solving random...");
			start = System.nanoTime();
			randomTotal += solveRandom();
			end = System.nanoTime();
			randomNanos += end - start;
			
			System.out.println("solving genetic...");
			start = System.nanoTime();
			geneticTotal += solveGeneticAlgorithm();
			end = System.nanoTime();
			geneticNanos += end - start;
			
			System.out.println("solving tournament...");
			start = System.nanoTime();
			tournamentTotal += solveTournamentSelection();
			end = System.nanoTime();
			tournamentNanos += end - start;
			
			System.out.println("soling roulette...");
			start = System.nanoTime();
			rouletteTotal += solveGenerationalRoulette();
			end = System.nanoTime();
			rouletteNanos += end - start;
		}
		
		System.out.println("Average Iterations Random: "+( ((long)randomTotal)/((long)LOOPS)));
		System.out.println("Average Time Random: "+((long)randomNanos)/((long)LOOPS));
		System.out.println();
		
		System.out.println("Average Iterations Genetic: "+( ((long)geneticTotal)/((long)LOOPS)));
		System.out.println("Average Time Genetic: "+((long)geneticNanos)/((long)LOOPS));
		System.out.println();
		
		System.out.println("Average Iterations Tournament: "+( ((long)tournamentTotal)/((long)LOOPS)));
		System.out.println("Average Time Tournament: "+((long)tournamentNanos)/((long)LOOPS));
		System.out.println();
		
		System.out.println("Average Iterations Roulette: "+( ((long)rouletteTotal)/((long)LOOPS)));
		System.out.println("Average Time Roulette: "+((long)rouletteNanos)/((long)LOOPS));
	}
	
	

}
