package ca.danedmunds.nqueens;

public class FitnessEvaluator {
	
	public static int getFitness(Gene gene){
		int fitness = 0;
		
		//this shouldn't happen but let's make sure that no queen horizontally threatens another
		int[] dna = gene.getDNA();
		for(int i=0; i<dna.length; ++i){
			for(int j=i+1; j<dna.length; ++j){
				if(dna[i] == dna[j]){
					++fitness;
				}
			}
		}
		
		int temp = 0;
		//now we check for diagonal threatening
		for(int i=0; i<dna.length; ++i){
			
			//check left and up
			int x = i;
			int y = dna[i];
			
			--x;
			--y;
			while(x >= 0 && y >=0){
				if(dna[x] == y){
					temp++;
//					System.out.println("Queen "+i+"-"+dna[i]+" is threatening Queen "+x+"-"+dna[x]);
				}
				
				--x;
				--y;
			}
			
			//check left and down
			x = i;
			y = dna[i];
			
			--x;
			++y;
			while(x >= 0 && y < dna.length){
				if(dna[x] == y){
					temp++;
//					System.out.println("Queen "+i+"-"+dna[i]+" is threatening Queen "+x+"-"+dna[x]);
				}
				
				--x;
				++y;
			}
			
			//check right and up
			x = i;
			y = dna[i];
			
			++x;
			--y;
			while(x < dna.length && y >=0){
				if(dna[x] == y){
					temp++;
//					System.out.println("Queen "+i+"-"+dna[i]+" is threatening Queen "+x+"-"+dna[x]);
				}
				
				++x;
				--y;
			}
			
			//check right and down
			x = i;
			y = dna[i];
			
			++x;
			++y;
			while(x < dna.length && y < dna.length){
				if(dna[x] == y){
					temp++;
//					System.out.println("Queen "+i+"-"+dna[i]+" is threatening Queen "+x+"-"+dna[x]);
				}
				
				++x;
				++y;
			}
		}
		//if one queen threaten another, the other will also threaten it
		//so divide by 2
		temp = temp / 2;
		
		fitness += temp;
		
		return fitness;
	}

}
