/**
* Enigma Machine GUI
* Coded by Amir El Bawab
* Date: 25 January 2015
* License: MIT License ~ Please read License.txt for more information about the usage of this software
* */

package gui.panels;

import gui.listener.KeyboardListener;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class KeyboardPanel extends JPanel{
	
	private JButton alphaButtons[] = new JButton[26];
	private GridBagConstraints gc;
	private JPanel boardPanel, textPanel;
	private JTextField inputField;
	private KeyboardListener keyboardListener;
	
	public KeyboardPanel() {
		
		// Create panels
		boardPanel = new JPanel();
		textPanel = new JPanel();
		
		// Set Layout
		setLayout(new BorderLayout());
		boardPanel.setLayout(new GridBagLayout());
		
		// Create output field
		inputField = new JTextField(95);
		inputField.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		inputField.setEditable(false);
		
		// Create buttons
		for(int i=0; i<26; i++){
			alphaButtons[i] = new JButton(""+(char)('A'+i), new ImageIcon(getClass().getResource("/gui/images/button.png")));
			alphaButtons[i].setBorderPainted(false); 
	        alphaButtons[i].setContentAreaFilled(false); 
	        alphaButtons[i].setFocusPainted(false); 
	        alphaButtons[i].setOpaque(false);
			alphaButtons[i].setHorizontalTextPosition(SwingConstants.CENTER);
			alphaButtons[i].setForeground(Color.white);
			alphaButtons[i].setPreferredSize(new Dimension(50, 50));
			alphaButtons[i].setPressedIcon(new ImageIcon(getClass().getResource("/gui/images/button_clicked.png")));
			
			final int currentID = i;
			alphaButtons[i].addMouseListener(new MouseListener() {
				int lastLetter;
				
				public void mouseReleased(MouseEvent e) {
					keyboardListener.releaseAction(this.lastLetter);
				}

				public void mousePressed(MouseEvent e) {
					
					// Add letter to input text and pass the character only
					inputField.setText(inputField.getText() + (char)('A'+currentID));
					this.lastLetter = keyboardListener.pressAction((char)('A'+currentID));
				}
				
				public void mouseExited(MouseEvent e) {}
				public void mouseEntered(MouseEvent e) {}
				public void mouseClicked(MouseEvent e) {}
			});
		}
		
		// Adding border
		setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		
		// Add empty border
		textPanel.setBorder(BorderFactory.createEmptyBorder(0,0,15,0));
		
		// Default grid configuration
		gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.NONE;
		gc.weightx = 1;
		gc.weighty = 0.15;
		
		// Add Keyboard Row 1
		String row1 = "QWERTZUIO";
		for(int i=0; i<9; i++){
			gc.gridx = i*2;
			gc.gridy = 0;
			boardPanel.add(alphaButtons[row1.charAt(i)-'A'], gc);
		}
		
		// Add Keyboard Row 2
		String row2 = "ASDFGHJK";
		for(int i=0; i<8; i++){
			gc.gridx = i*2+1;
			gc.gridy = 1;
			boardPanel.add(alphaButtons[row2.charAt(i)-'A'], gc);
		}
		
		// Add Keyboard Row 3
		String row3 = "PYXCVBNML";
		for(int i=0; i<9; i++){
			gc.gridx = i*2;
			gc.gridy = 2;
			boardPanel.add(alphaButtons[row3.charAt(i)-'A'], gc);
		}
		
		// Add output field
		textPanel.add(inputField);
		
		// Change background to dark
		setBackground(Color.DARK_GRAY);
		boardPanel.setOpaque(false);
		textPanel.setOpaque(false);
		inputField.setBackground(null);
		inputField.setOpaque(false);
		inputField.setForeground(Color.WHITE);
		
		// Add panels
		add(boardPanel, BorderLayout.CENTER);
		add(textPanel, BorderLayout.NORTH);
	}
	
	/**
	 * Set KeyboardListener
	 * @param keyboardListener
	 */
	public void setKeyboardListener(KeyboardListener keyboardListener){
		this.keyboardListener = keyboardListener;
	}
	
	/**
	 * Get input field
	 * @return input field
	 */
	public JTextField getInputField(){
		return inputField;
	}
}
