package ca.danedmunds.nqueens.gui;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class NQueensFrame extends JFrame {

	public NQueensFrame(){
		super("NQueens Genetic Algorithms Solver");
		setContentPane(new NQueensPanel(this));
	}
	
	public static void main(String[] args){
		NQueensFrame frame = new NQueensFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600, 400);
		frame.setVisible(true);
	}

}
