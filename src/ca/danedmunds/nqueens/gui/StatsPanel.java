package ca.danedmunds.nqueens.gui;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import ca.danedmunds.nqueens.Gene;

@SuppressWarnings("serial")
public class StatsPanel extends JPanel {
	
	private Gene bestGene;
	private int iteration;
	private int bestFitness;
	
	public void setStats(Gene bestGene, int iteration, int bestFitness){
		this.bestGene = bestGene;
		this.iteration = iteration;
		this.bestFitness = bestFitness;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.setColor(Color.BLACK);
		g.drawString("Iteration: "+iteration, 0, 15);
		if(bestGene != null){
			g.drawString("Best Fitness: "+bestFitness, 0, 30);
			g.drawString("Best Gene: "+bestGene.toString(), 0, 45);
		}
	}

}
