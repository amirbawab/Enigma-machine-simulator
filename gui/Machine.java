/**
* Enigma Machine GUI
* Coded by Amir El Bawab
* Date: 20 January 2015
* License: MIT License ~ Please read License.txt for more information about the usage of this software
* */

package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Scanner;

import javax.swing.JFrame;

import enigma.Enigma;
import gui.listener.RotorListener;
import gui.listener.TypeListener;

public class Machine extends JFrame{
	
	private EncodePanel epanel;
	private RotorsPanel rpanel;
	private PlugboardPanel pbpanel;
	private Enigma enigma;
	
	public Machine() {
		super("Enigma machine v1.0 - by Amir El Bawab");
		setLayout(new BorderLayout());

		// Create panels
		epanel = new EncodePanel();
		rpanel = new RotorsPanel();
		pbpanel = new PlugboardPanel();
		
		// Create the enigma
		enigma = new Enigma(Enigma.I, Enigma.II, Enigma.III, Enigma.A);
		
		// Pass the enigma to the panel
		epanel.setEnigma(enigma);
		rpanel.setEnigma(enigma);
		
		// Add panels
		add(epanel,BorderLayout.CENTER);
		add(rpanel,BorderLayout.NORTH);
		add(pbpanel,BorderLayout.SOUTH);
		
		// Add rotor listener
		rpanel.setRotorListener(new RotorListener() {
			public void configure(String[] leftRotor, String[] centerRotor,
					String[] rightRotor, char leftStart, char centerStart,
					char rightStart, char leftRing, char centerRing, char rightRing,
					String reflector) {
				
				// Set type
				enigma.getLeftRotor().setRotor(leftRotor);
				enigma.getCenterRotor().setRotor(centerRotor);
				enigma.getRightRotor().setRotor(rightRotor);
				
				// Set rotor head
				enigma.getLeftRotor().setRotorHead(leftStart);
				enigma.getCenterRotor().setRotorHead(centerStart);
				enigma.getRightRotor().setRotorHead(rightStart);
				
				// Set rotor ring
				enigma.getLeftRotor().setRingHead(leftRing);
				enigma.getCenterRotor().setRingHead(centerRing);
				enigma.getRightRotor().setRingHead(rightRing);
				
				// Set reflector
				enigma.getReflector().setReflector(reflector);
				
				// Reset & Insert plugs
				enigma.resetPlugboard();
				Scanner scan = new Scanner(pbpanel.getPlugboard());
				while(scan.hasNext()){
					String wire = scan.next();
					if(wire.length() == 2){
						char from = wire.charAt(0);
						char to = wire.charAt(1);
						if(!enigma.isPlugged(from) && !enigma.isPlugged(to) && from != to)
							enigma.insertPlugboardWire(from, to);
					}
				}
				
				// Refresh encode panel
				epanel.refresh();
			}
		});
		
		// Add type listener
		epanel.setTypeListener(new TypeListener() {
			public void typeAction() {
				rpanel.setStates(enigma.getLeftRotor().getRotorHead(), enigma.getCenterRotor().getRotorHead(), enigma.getRightRotor().getRotorHead());
			}
		});
		
		// Set default text
		epanel.setDefaultText("THE NUMBER OF DIFFERENT CONFIGURATIONS FOR THE ENIGMA MACHINE IS ONE HUNDRED FIFTY EIGHT QUINTILLION NINE HUNDRED SIXTY TWO QUADRILLION\nFIVE HUNDRED FIFTY FIVE TRILLION TWO HUNDRED SEVENTEEN BILLION EIGHT HUNDRED TWENTY SIX MILLION THREE HUNDRED SIXTY THOUSAND");

		// Frame preference
		setVisible(true);
		setMinimumSize(new Dimension(1150,700));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}
}
