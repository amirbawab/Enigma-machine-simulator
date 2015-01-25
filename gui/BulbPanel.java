/**
* Enigma Machine GUI
* Coded by Amir El Bawab
* Date: 25 January 2015
* License: MIT License ~ Please read License.txt for more information about the usage of this software
* */

package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class BulbPanel extends JPanel{
	
	private JLabel alphaBulbs[] = new JLabel[26];
	private GridBagConstraints gc;
	private JPanel boardPanel, textPanel;
	private JTextField outputField;
	
	public BulbPanel() {
		
		// Create panels
		boardPanel = new JPanel();
		textPanel = new JPanel();
			
		// Set Layout
		setLayout(new BorderLayout());
		boardPanel.setLayout(new GridBagLayout());
		
		// Create output field
		outputField = new JTextField(95);
		outputField.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		outputField.setEditable(false);
		
		// Adding border
		setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		
		// Add empty border
		textPanel.setBorder(BorderFactory.createEmptyBorder(0,0,15,0));
		
		// Create bulbs
		for(int i=0; i<26; i++){
			alphaBulbs[i] = new JLabel("<html><b>"+(char)('A'+i)+"</b></html>", new ImageIcon(getClass().getResource("images/button_bulb.png")),SwingConstants.CENTER);
	        alphaBulbs[i].setOpaque(false);
			alphaBulbs[i].setHorizontalTextPosition(SwingConstants.CENTER);
			alphaBulbs[i].setForeground(Color.GRAY);
			alphaBulbs[i].setPreferredSize(new Dimension(50, 50));
			alphaBulbs[i].setFont(new Font(getFont().getFontName(), Font.PLAIN, getFont().getSize()+2));
		}
		
		// Default grid configuration
		gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.NONE;
		gc.weightx = 1;
		gc.weighty = 0.15;
		
		// Add Bulb Row 1
		String row1 = "QWERTZUIO";
		for(int i=0; i<9; i++){
			gc.gridx = i*2;
			gc.gridy = 0;
			boardPanel.add(alphaBulbs[row1.charAt(i)-'A'], gc);
		}
		
		// Add Bulb Row 2
		String row2 = "ASDFGHJK";
		for(int i=0; i<8; i++){
			gc.gridx = i*2+1;
			gc.gridy = 1;
			boardPanel.add(alphaBulbs[row2.charAt(i)-'A'], gc);
		}
		
		// Add Bulb Row 3
		String row3 = "PYXCVBNML";
		for(int i=0; i<9; i++){
			gc.gridx = i*2;
			gc.gridy = 2;
			boardPanel.add(alphaBulbs[row3.charAt(i)-'A'], gc);
		}
		
		// Add output field
		textPanel.add(outputField);
		
		// Change background to dark
		setBackground(Color.DARK_GRAY);
		boardPanel.setOpaque(false);
		textPanel.setOpaque(false);
		outputField.setBackground(null);
		outputField.setForeground(Color.WHITE);
		outputField.setOpaque(false);
		
		// Add panels
		add(boardPanel, BorderLayout.CENTER);
		add(textPanel, BorderLayout.NORTH);
	}
	
	/**
	 * Bulb on
	 * @param c
	 */
	public void bulbOn(int c){
		alphaBulbs[c].setForeground(Color.WHITE);
	}
	
	/**
	 * Bulb off
	 * @param c
	 */
	public void bulbOff(int c){
		alphaBulbs[c].setForeground(Color.GRAY);
	}
	
	/**
	 * Get output field
	 * @return output field
	 */
	public JTextField getOutputField(){
		return outputField;
	}
}
