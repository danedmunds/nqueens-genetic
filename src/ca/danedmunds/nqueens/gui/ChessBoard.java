package ca.danedmunds.nqueens.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import ca.danedmunds.nqueens.Gene;


@SuppressWarnings("serial")
public class ChessBoard extends JPanel{
	
	private Gene gene;
	private int size;
	private int squareSize;
	private int boardSize;
	
	public ChessBoard(int size){
		this.size = size;
	}
	
	public void setSize(int size){
		this.size = size;
	}
	
	public void setGene(Gene gene){
		this.gene = gene;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		paintBoard(g);
		paintQueens(g);
	}
	
	private void paintBoard(Graphics pen){
		Dimension d = getSize();
		boardSize = Math.min(d.height, d.width);
		squareSize = boardSize / size;
		
		boolean black = true;
		boolean startBlack = false;
		for(int j=0; j<size; ++j){
			startBlack = !startBlack;
			black = startBlack;
			for(int i=0; i<size; ++i){
				if(black){
					pen.setColor(Color.BLACK);
				} else {
					pen.setColor(Color.WHITE);
				}
				
				pen.fillRect(i * squareSize, j * squareSize, squareSize, squareSize);
				
				black = !black;
			}
		}
		
		pen.setColor(Color.BLACK);
		pen.drawRect(0, 0, boardSize+1, boardSize+1);
	}
	
	private void paintQueens(Graphics pen){
		if(gene == null){
			return;
		}
		
		int[] dna = gene.getDNA();
		if(dna.length != size){
			System.out.println("Error dna length and board size don't match up");
		}
		
		pen.setColor(Color.RED);
		for(int i=0; i<dna.length && i<size; ++i){
			pen.fillOval(i*squareSize, dna[i] * squareSize, squareSize, squareSize);
		}
		
	}
	
	

}
