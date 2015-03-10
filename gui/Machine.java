/**
* Enigma Machine GUI
* Coded by Amir El Bawab
* Date: 25 January 2015
* License: MIT License ~ Please read License.txt for more information about the usage of this software
* */

package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import enigma.Enigma;
import gui.listener.EnigmaMenuListener;
import gui.listener.RotorListener;
import gui.listener.TypeListener;
import gui.panels.WiresPanel;
import gui.panels.EncodePanel;
import gui.panels.PlugboardPanel;
import gui.panels.RotorsPanel;
import gui.panels.TypewriterPanel;

public class Machine extends JFrame{
	
	private EncodePanel epanel;
	private RotorsPanel rpanel;
	private PlugboardPanel pbpanel;
	private Enigma enigma;
	private TypewriterPanel twpanel;
	private WiresPanel dpanel;
	private EnigmaMenuBar menuBar;
	
	private final int TEXT_MODE = 1;
	private final int KB_MODE = 2;
	private final int WIRES_MODE = 3;
	private int mode = TEXT_MODE;
	
	public Machine() {
		super("Enigma machine v3.0 - by Amir El Bawab");
		
		// Create the enigma
		enigma = new Enigma(Enigma.I, Enigma.II, Enigma.III, Enigma.A);
		
		// Set frame layout
		setLayout(new BorderLayout());

		// Create Menu bar
		menuBar = new EnigmaMenuBar(this);
		
		// Set JMenu
		setJMenuBar(menuBar.getMenuBar());
		
		// Create panels
		epanel = new EncodePanel();
		rpanel = new RotorsPanel();
		pbpanel = new PlugboardPanel();
		twpanel = new TypewriterPanel();
		dpanel = new WiresPanel();
		
		// Pass the enigma to the panel
		epanel.setEnigma(enigma);
		rpanel.setEnigma(enigma);
		twpanel.setEnigma(enigma);
		dpanel.setEnigma(enigma);
		
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
				scan.close();
				
				// Redraw enigma wires
				if(mode == WIRES_MODE)
					dpanel.updateWires();
				
				// Refresh encode panel
				if(mode == TEXT_MODE)
					epanel.refresh();
			}
		});
		
		// Add type listener
		epanel.setTypeListener(new TypeListener() {
			public void typeAction() {
				rpanel.setStates(enigma.getLeftRotor().getRotorHead(), enigma.getCenterRotor().getRotorHead(), enigma.getRightRotor().getRotorHead());
			}
		});
		
		twpanel.setTypeListener(new TypeListener() {
			public void typeAction() {
				rpanel.setStates(enigma.getLeftRotor().getRotorHead(), enigma.getCenterRotor().getRotorHead(), enigma.getRightRotor().getRotorHead());
			}
		});
		
		dpanel.setTypeListener(new TypeListener() {
			public void typeAction() {
				rpanel.setStates(enigma.getLeftRotor().getRotorHead(), enigma.getCenterRotor().getRotorHead(), enigma.getRightRotor().getRotorHead());
			}
		});
		
		// Set default text
		epanel.setDefaultText("THE NUMBER OF DIFFERENT CONFIGURATIONS FOR THE ENIGMA MACHINE IS ONE HUNDRED FIFTY EIGHT QUINTILLION NINE HUNDRED SIXTY TWO QUADRILLION\nFIVE HUNDRED FIFTY FIVE TRILLION TWO HUNDRED SEVENTEEN BILLION EIGHT HUNDRED TWENTY SIX MILLION THREE HUNDRED SIXTY THOUSAND");

		menuBar.setEmlistener(new EnigmaMenuListener() {
			public void textBoxDisplay() {
				mode = TEXT_MODE;
				epanel.setVisible(true);
				twpanel.setVisible(false);
				dpanel.setVisible(false);
				add(epanel,BorderLayout.CENTER);
				revalidate();
				
				// Force state change
				epanel.setDefaultText("A");
				epanel.setDefaultText("");
				
				// Light theme
				pbpanel.lightTheme();
				rpanel.lightTheme();
			}
			
			public void keyboardDisplay() {
				mode = KB_MODE;
				twpanel.setVisible(true);
				epanel.setVisible(false);
				dpanel.setVisible(false);
				add(twpanel,BorderLayout.CENTER);
				revalidate();
				twpanel.clear();
				
				// Dark theme
				pbpanel.darkTheme();
				rpanel.darkTheme();
			}
			
			public void wiresConnectionDisplay() {
				mode = WIRES_MODE;
				dpanel.setVisible(true);
				epanel.setVisible(false);
				twpanel.setVisible(false);
				add(dpanel,BorderLayout.CENTER);
				revalidate();
				dpanel.clear();
				
				// Light theme
				pbpanel.lightTheme();
				rpanel.lightTheme();
			}
			
			public void importFile(String text) {
				if(mode != TEXT_MODE){
					JOptionPane.showMessageDialog(Machine.this, "You cannot import a file in this display");
					return;
				}
				epanel.setDefaultText(text);
			}
			
			public String exportFile() {
				if(mode == TEXT_MODE)
					return epanel.getOutput();
				else if(mode == KB_MODE)
					return twpanel.getOutput();
				return "-1";
			}

			public void restart() {
				Machine.this.dispose();
				epanel = null;
				rpanel = null;
				pbpanel = null;
				enigma = null;
				twpanel = null;
				dpanel = null;
				menuBar = null;
				System.gc();
				new Machine();
			}
		});
		
		// Frame preference
		setVisible(true);
		Dimension dim = new Dimension(1150,700);
		setPreferredSize(dim);
		setMinimumSize(dim);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		pack();
	}
}
