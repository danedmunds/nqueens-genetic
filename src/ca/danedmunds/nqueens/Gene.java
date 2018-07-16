package ca.danedmunds.nqueens;

import java.util.Random;

public class Gene {
	
	NQueensGA world;
	
	Random rand;
	public int[] dna;
	private int size;
	private int fitness;
	
	public Gene(NQueensGA world){
		this.world = world;
		this.size = world.getProblemSize();
		
		rand = new Random();
		dna = new int[size];
		
		for(int i=0; i<size; ++i){
			dna[i] = i;
		}
		
		//shuffle
		Random rand = new Random();
        for (int i = 0; i < size; i++) {
            int r = i + rand.nextInt(size - i);
            swapDNACells(i, r);
        }
	}
	
	public Gene(NQueensGA world, int[] dna){
		this.world = world;
		this.dna = dna;
		rand = new Random();
	}
	
	public int[] getDNA(){
		return dna;
	}
	
	public void mutate(){
		swapDNACells(rand.nextInt(dna.length), rand.nextInt(dna.length));
	}
	
	public void swapDNACells(int a, int b){
		int temp = dna[a];
		dna[a] = dna[b];
		dna[b] = temp;
	}
	
	public Gene[] breed(Gene mate){
		int[] child1DNA = new int[dna.length];
		int[] child2DNA = new int[dna.length];
		for(int i=0; i<child1DNA.length; ++i){
			child1DNA[i] = -1;
			child2DNA[i] = -1;
		}
		
		//determine if crossover occurs
		boolean crossover = rand.nextDouble() <= world.getCrossoverRate();
		if(crossover){
			int crossIndex = rand.nextInt(dna.length);
//			System.out.println("Crossover index: "+crossIndex);
			
			int parent1CopyIndex = 0;
			int parent2CopyIndex = 0;
			for(int i=0; i<dna.length; ++i){
				if(i <= crossIndex){
					child1DNA[i] = dna[i];
					child2DNA[i] = mate.dna[i];
				} else {
					while(parent2CopyIndex < dna.length && contains(child1DNA, mate.dna[parent2CopyIndex])){
						++parent2CopyIndex;
					}
					
					child1DNA[i] = mate.dna[parent2CopyIndex];
					
					
					while(parent1CopyIndex < dna.length && contains(child2DNA, dna[parent1CopyIndex])){
						++parent1CopyIndex;
					}
					
					child2DNA[i] = dna[parent1CopyIndex];
				}
			}
		} else {
			for(int i=0; i<dna.length; ++i){
				child1DNA[i] = dna[i];
				child2DNA[i] = mate.dna[i];
			}
		}
		
//		
//		System.out.print("parent1 ");
//		print(dna);
//		System.out.print("parent2 ");
//		print(mate.dna);
//		System.out.print("child1 ");
//		print(child1DNA);
//		System.out.print("child2 ");
//		print(child2DNA);
//		System.out.println();
		
		Gene child1 = new Gene(world, child1DNA);
		Gene child2 = new Gene(world, child2DNA);
		
		//determine if mutation occurs
		boolean mutation = rand.nextDouble() <= world.getMutationRate();
		if(mutation){
			child1.mutate();
		}
		mutation = rand.nextDouble() <= world.getMutationRate();
		if(mutation){
			child2.mutate();
		}
		
		
		//assign fitness to children
		child1.setFitness(FitnessEvaluator.getFitness(child1));
		child2.setFitness(FitnessEvaluator.getFitness(child2));
		
		return new Gene[] {child1, child2} ;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Gene){
			Gene gene = (Gene)obj;
			
			for(int i=0; i<dna.length; ++i){
				if(gene.dna[i] != dna[i]){
					return false;
				}
			}
			
			return true;
		}
		
		return false;
	}
	
	private boolean contains(int[] a, int b){
		for(int i=0; i<a.length; ++i){
			if(a[i] == b){
				return true;
			}
		}
		
		return false;
	}
	
	public void print(int[] b){
		for(int i=0; i<b.length; ++i){
			System.out.print(""+b[i]+",");
		}
		System.out.println();
	}

	public int getFitness() {
		return fitness;
	}

	public void setFitness(int fitness) {
		this.fitness = fitness;
	}
	
	public String toString(){
		StringBuffer buf = new StringBuffer();
		for(int i=0; i<dna.length; ++i){
			buf.append(dna[i]).append(",");
		}
		
		return buf.toString();
	}

}
