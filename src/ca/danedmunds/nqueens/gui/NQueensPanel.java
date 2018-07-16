package ca.danedmunds.nqueens.gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

import ca.danedmunds.nqueens.Gene;
import ca.danedmunds.nqueens.NQueensRoulette;


@SuppressWarnings("serial")
public class NQueensPanel extends JPanel{
	
	private JFrame parent;
	private ChessBoard board;
	StatsPanel statsPanel;
	
	private JTextField problemSize;
	private JTextField popSize;
	private JTextField mutationRate;
	private JTextField crossoverRate;

	private Timer timer;
	JButton goButton;
	JButton stopButton;
	AbstractAction stopAction;
	
	public NQueensPanel(JFrame parent){
		this.parent = parent;
		board = new ChessBoard(10);
		buildGUI();
	}
	
	private void buildGUI(){
		setLayout(null);
		board.setSize(350, 350);
		board.setLocation(0, 0);
		add(board);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(0, 2));
	
			JLabel label = new JLabel("Problem Size:");
			buttonPanel.add(label);
			problemSize = new JTextField("10");
			buttonPanel.add(problemSize);
			
			label = new JLabel("Population Size:");
			buttonPanel.add(label);
			popSize = new JTextField("50");
			buttonPanel.add(popSize);
			
			label = new JLabel("Mutation Rate:");
			buttonPanel.add(label);
			mutationRate = new JTextField("0.5");
			buttonPanel.add(mutationRate);
			
			label = new JLabel("Crossover Rate:");
			buttonPanel.add(label);
			crossoverRate = new JTextField("1");
			buttonPanel.add(crossoverRate);
		
			buttonPanel.setSize(210, 100);
			buttonPanel.setLocation(370, 30);
			
			add(buttonPanel);
			
		goButton = new JButton(new AbstractAction("Go!"){
			
			public void actionPerformed(ActionEvent e) {
				if(validateInputs()){
					final int n = Integer.parseInt(problemSize.getText());
					final int pop = Integer.parseInt(popSize.getText());
					final double mutation = Double.parseDouble(mutationRate.getText());
					final double crossover = Double.parseDouble(crossoverRate.getText());
					
					board.setSize(n);
					board.setGene(null);
					parent.repaint();
					
					//TODO here
//					final NQueensGA world = new NQueensGASteadyState(n, pop, crossover, mutation);
//					final NQueensTournamentSelection world = new NQueensTournamentSelection(n, pop, crossover, mutation);
					final NQueensRoulette world = new NQueensRoulette(n, pop, crossover, mutation);
					world.setGenerational(false);
					world.setNumberOfBreedings(pop / 10);
					
					timer = new Timer(10, new ActionListener(){
						
						private int iteration = 0;
						
						public void actionPerformed(ActionEvent e) {
							Gene best = world.step();
							
							board.setGene(best);
							statsPanel.setStats(best, iteration, best.getFitness());
							
							parent.repaint();
							
							if(best.getFitness() == 0){
								stopAction.actionPerformed(null);
							}

							++iteration;
						}
					});
					timer.start();
					goButton.setEnabled(false);
					problemSize.setEnabled(false);
					popSize.setEnabled(false);
					mutationRate.setEnabled(false);
					crossoverRate.setEnabled(false);
					stopButton.setEnabled(true);
				}
			}
		});
		goButton.setSize(70, 30);
		goButton.setLocation(430, 160);
		add(goButton);
		
		stopAction = new AbstractAction("Stop"){
			
			public void actionPerformed(ActionEvent e) {
				timer.stop();
				
				goButton.setEnabled(true);
				stopButton.setEnabled(false);
				problemSize.setEnabled(true);
				popSize.setEnabled(true);
				mutationRate.setEnabled(true);
				crossoverRate.setEnabled(true);
			}
		};
		
		stopButton = new JButton(stopAction);
		stopButton.setEnabled(false);
		stopButton.setSize(70, 30);
		stopButton.setLocation(430, 200);
		add(stopButton);
		
		statsPanel = new StatsPanel();
		statsPanel.setSize(500, 100);
		statsPanel.setLocation(370, 260);
		add(statsPanel);
	}
	
	private boolean validateInputs(){
		//check problem size
		try{
			int prob = Integer.parseInt(problemSize.getText());
			if(prob < 1){
				showError("Problem size must be larger than zero.");
				return false;
			}
		} catch (NumberFormatException e){
			showError("You must provide a number for problem size");
			return false;
		}
		
		//check population size
		try{
			int pop = Integer.parseInt(popSize.getText());
			if(pop < 1 || pop % 2 != 0){
				showError("Population size must be larger than zero and an even number");
				return false;
			}
		} catch (NumberFormatException e){
			showError("You must provide a number for population size");
			return false;
		}
		
		//check mutation rate
		try{
			double mut = Double.parseDouble(mutationRate.getText());
			if(mut < 0 || mut > 1){
				showError("You must provide a double between 0 and 1 for mutation rate");
				return false;
			}
			
		} catch (NumberFormatException e){
			showError("You must provide a double between 0 and 1 for mutation rate");
			return false;
		}
		
		//check crossover rate
		try{
			double cross = Double.parseDouble(crossoverRate.getText());
			if(cross < 0 || cross > 1){
				showError("You must provide a double between 0 and 1 for crossover rate");
				return false;
			}
			
		} catch (NumberFormatException e){
			showError("You must provide a double between 0 and 1 for crossover rate");
			return false;
		}
		
		
		return true;
	}
	
	private void showError(String error){
		JOptionPane.showMessageDialog(this, error, "NQueens", JOptionPane.ERROR_MESSAGE, null);
	}

}
