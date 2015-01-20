/**
* Enigma Machine GUI
* Coded by Amir El Bawab
* Date: 20 January 2015
* License: MIT License ~ Please read License.txt for more information about the usage of this software
* */

package gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

import enigma.Enigma;
import gui.listener.RotorListener;

public class RotorsPanel extends JPanel{
	
	private GridBagConstraints gc;
	private JComboBox reflectorBox;
	private JComboBox leftRotorBox;
	private JComboBox centerRotorBox;
	private JComboBox rightRotorBox;
	
	private JComboBox leftRotorStartBox;
	private JComboBox centerRotorStartBox;
	private JComboBox rightRotorStartBox;
	
	private JComboBox leftRotorRingBox;
	private JComboBox centerRotorRingBox;
	private JComboBox rightRotorRingBox;
	
	private JButton applyBtn;
	
	private JTextField leftState;
	private JTextField centerState;
	private JTextField rightState;
	
	private JPanel reflectorPanel;
	private JPanel leftRotorPanel;
	private JPanel centerRotorPanel;
	private JPanel rightRotorPanel;
	private JPanel stateRotorPanel;
	
	private String[][] ENIGMA_ROTORS = {Enigma.I, Enigma.II, Enigma.III, Enigma.IV, Enigma.V}; 
	private String[] ENIGMA_REFLECTORS = {Enigma.A, Enigma.B, Enigma.C}; 
	
	private RotorListener rotorListener;
	
	private Enigma enigma;
	
	public RotorsPanel() {
		
		// Grid layout
		setLayout(new GridBagLayout());
		
		// Create panels
		reflectorPanel = new JPanel();
		reflectorPanel.setLayout(new GridBagLayout());
		reflectorPanel.setPreferredSize(new Dimension(100,100));
		
		leftRotorPanel = new JPanel();
		leftRotorPanel.setLayout(new GridBagLayout());
		leftRotorPanel.setPreferredSize(new Dimension(220,100));
		
		centerRotorPanel = new JPanel();
		centerRotorPanel.setLayout(new GridBagLayout());
		centerRotorPanel.setPreferredSize(new Dimension(220,100));
		
		rightRotorPanel = new JPanel();
		rightRotorPanel.setLayout(new GridBagLayout());
		rightRotorPanel.setPreferredSize(new Dimension(220,100));
		
		stateRotorPanel = new JPanel();
		stateRotorPanel.setLayout(new GridBagLayout());
		stateRotorPanel.setPreferredSize(new Dimension(130,100));
		
		// Common components
		String[] rotorBoxOption = {"I" ,"II", "III", "IV", "V"};
		String[] rotorBoxLetterOption = new String[26];
		for(int i=0; i < 26; i++) rotorBoxLetterOption[i] = (char) ('A' + i)+"";
		
		// Create labels
		leftState = new JTextField("A", 1);
		leftState.setEditable(false);
		centerState = new JTextField("A", 1);
		centerState.setEditable(false);
		rightState = new JTextField("A", 1);
		rightState.setEditable(false);
		
		// Create reflector
		String[] reflectorBoxOption = {"A" ,"B", "C"};
		reflectorBox = new JComboBox(reflectorBoxOption);
		reflectorBox.setSelectedIndex(0);
		
		// Create left rotor
		leftRotorBox = new JComboBox(rotorBoxOption);
		leftRotorBox.setSelectedIndex(0);
		
		// Create center rotor
		centerRotorBox = new JComboBox(rotorBoxOption);
		centerRotorBox.setSelectedIndex(1);
		
		// Create right rotor
		rightRotorBox = new JComboBox(rotorBoxOption);
		rightRotorBox.setSelectedIndex(2);
		
		// Create left rotor start option
		leftRotorStartBox = new JComboBox(rotorBoxLetterOption);
		leftRotorStartBox.setSelectedIndex(0);
	
		// Create center rotor start option
		centerRotorStartBox = new JComboBox(rotorBoxLetterOption);
		centerRotorStartBox.setSelectedIndex(0);
		
		// Create right rotor start option
		rightRotorStartBox = new JComboBox(rotorBoxLetterOption);
		rightRotorStartBox.setSelectedIndex(0);

		// Create left rotor ring option
		leftRotorRingBox = new JComboBox(rotorBoxLetterOption);
		leftRotorRingBox.setSelectedIndex(0);
	
		// Create center rotor ring option
		centerRotorRingBox = new JComboBox(rotorBoxLetterOption);
		centerRotorRingBox.setSelectedIndex(0);
		
		// Create right rotor ring option
		rightRotorRingBox = new JComboBox(rotorBoxLetterOption);
		rightRotorRingBox.setSelectedIndex(0);
		
		// Create apply button
		applyBtn = new JButton("Plug & Apply!");
		
		// Adding borders to panels
		Border outerBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
		Border innerBorder = BorderFactory.createTitledBorder("Reflector");
		reflectorPanel.setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));
		
		innerBorder = BorderFactory.createTitledBorder("Left Rotor");
		leftRotorPanel.setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));
		
		innerBorder = BorderFactory.createTitledBorder("Center Rotor");
		centerRotorPanel.setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));
		
		innerBorder = BorderFactory.createTitledBorder("Right Rotor");
		rightRotorPanel.setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));
		
		innerBorder = BorderFactory.createTitledBorder("Sate");
		stateRotorPanel.setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));
		
		// Default grid configuration
		gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.NONE;
		gc.insets = new Insets(0, 10, 0, 10);
		gc.anchor = GridBagConstraints.NORTHWEST;
		int y = 0;
		gc.weightx = 1;
		gc.weighty = 0.15;
		
		// Add ReflectorBox
		gridPosition(0, y);
		reflectorPanel.add(new JLabel("Type: "),gc);
		gridPosition(0, y+1);
		reflectorPanel.add(reflectorBox, gc);
		
		// Add LeftRotorBox
		gridPosition(0, y);
		leftRotorPanel.add(new JLabel("Type: "),gc);
		gridPosition(0, y+1);
		leftRotorPanel.add(leftRotorBox, gc);
		gridPosition(1, y);
		leftRotorPanel.add(new JLabel("Start: "),gc);
		gridPosition(1, y+1);
		leftRotorPanel.add(leftRotorStartBox, gc);
		gridPosition(2, y);
		leftRotorPanel.add(new JLabel("Ring: "),gc);
		gridPosition(2, y+1);
		leftRotorPanel.add(leftRotorRingBox, gc);
		
		// Add CenterRotorBox
		gridPosition(0, y);
		centerRotorPanel.add(new JLabel("Type: "),gc);
		gridPosition(0, y+1);
		centerRotorPanel.add(centerRotorBox, gc);
		gridPosition(1, y);
		centerRotorPanel.add(new JLabel("Start: "),gc);
		gridPosition(1, y+1);
		centerRotorPanel.add(centerRotorStartBox, gc);
		gridPosition(2, y);
		centerRotorPanel.add(new JLabel("Ring: "),gc);
		gridPosition(2, y+1);
		centerRotorPanel.add(centerRotorRingBox, gc);
		
		// Add CenterRotorBox
		gridPosition(0, y);
		rightRotorPanel.add(new JLabel("Type: "),gc);
		gridPosition(0, y+1);
		rightRotorPanel.add(rightRotorBox, gc);
		gridPosition(1, y);
		rightRotorPanel.add(new JLabel("Start: "),gc);
		gridPosition(1, y+1);
		rightRotorPanel.add(rightRotorStartBox, gc);
		gridPosition(2, y);
		rightRotorPanel.add(new JLabel("Ring: "),gc);
		gridPosition(2, y+1);
		rightRotorPanel.add(rightRotorRingBox, gc);
		
		// Add CenterRotorBox
		gridPosition(0, y);
		stateRotorPanel.add(new JLabel("L"),gc);
		gridPosition(0, y+1);
		stateRotorPanel.add(leftState, gc);
		gridPosition(1, y);
		stateRotorPanel.add(new JLabel("C"),gc);
		gridPosition(1, y+1);
		stateRotorPanel.add(centerState, gc);
		gridPosition(2, y);
		stateRotorPanel.add(new JLabel("R"),gc);
		gridPosition(2, y+1);
		stateRotorPanel.add(rightState, gc);
		
		// Add panels
		add(reflectorPanel);
		add(leftRotorPanel);
		add(centerRotorPanel);
		add(rightRotorPanel);
		add(stateRotorPanel);
		add(applyBtn);
		
		// Add button listener
		applyBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rotorListener.configure(
						ENIGMA_ROTORS[leftRotorBox.getSelectedIndex()], ENIGMA_ROTORS[centerRotorBox.getSelectedIndex()], ENIGMA_ROTORS[rightRotorBox.getSelectedIndex()],
						(char) ('A' + leftRotorStartBox.getSelectedIndex()), (char) ('A' + centerRotorStartBox.getSelectedIndex()), (char) ('A' + rightRotorStartBox.getSelectedIndex()), 
						(char) ('A' + leftRotorRingBox.getSelectedIndex()), (char) ('A' + centerRotorRingBox.getSelectedIndex()), (char) ('A' + rightRotorRingBox.getSelectedIndex()), 
						ENIGMA_REFLECTORS[reflectorBox.getSelectedIndex()]);
				
				// Update states
				leftState.setText( enigma.getLeftRotor().getRotorHead() + "");
				centerState.setText( enigma.getCenterRotor().getRotorHead() + "");
				rightState.setText( enigma.getRightRotor().getRotorHead() + "");
			}
		});
		
		// Set size
		setPreferredSize(new Dimension(100,100));
	}
	
	/**
	 * Set rotor listener
	 * @param rotorListener
	 */
	public void setRotorListener(RotorListener rotorListener){
		this.rotorListener = rotorListener; 
	}
	
	/**
	 * Set states
	 * @param l
	 * @param c
	 * @param r
	 */
	public void setStates(char l, char c, char r){
		leftState.setText(l+"");
		centerState.setText(c+"");
		rightState.setText(r+"");
	}
	
	/**
	 * Set enigma
	 * @param enigma
	 */
	public void setEnigma(Enigma enigma){
		this.enigma = enigma;
	}
	
	/**
	* Set grid position
	* @param x X-Position
	* @param y Y-Position
	* */
	private void gridPosition(int x, int y) {
		gc.gridx = x;
		gc.gridy = y;
	}
}