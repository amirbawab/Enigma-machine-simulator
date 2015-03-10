package gui.panels;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import enigma.Enigma;
import gui.lib.JStatic;
import gui.lib.JReflector;
import gui.lib.JRotor;
import gui.listener.TypeListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class WiresPanel extends JPanel{
	
	private JRotor rotorLeft, rotorRight, rotorCenter;
	private JReflector reflector;
	private JStatic rotorStatic;
	private Enigma enigma;
	private JTextField input;
	private JTextField previous;
	private JLabel output;
	private TypeListener typeListener;
	private JPanel wiresPanel;
	private JPanel textPanel;
	private GridBagConstraints gc;
	
	public WiresPanel() {
		
		// Set layout
		setLayout(new GridBagLayout());
		
		// Create components
		wiresPanel = new JPanel();
		textPanel = new JPanel();
		rotorStatic = new JStatic();
		rotorLeft = new JRotor();
		rotorCenter = new JRotor();
		rotorRight = new JRotor();
		reflector = new JReflector();
		input = new JTextField(1);
		previous = new JTextField(10);
		output = new JLabel("-");
		
		// Filter input to upper case only
		( (AbstractDocument) input.getDocument() ).setDocumentFilter(new DocumentFilter(){
			public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
				
				if(text.length() != 1)
					return;
				
				// If not a letter
				if( (text.charAt(0) < 'a' || text.charAt(0) > 'z') && (text.charAt(0) < 'A' || text.charAt(0) > 'Z'))
					return;
				
				// Convert to upper case and add text
				text = text.toUpperCase();
				previous.setText(previous.getText() + text);
			}
		});
		
		// Add listener on document change
		previous.getDocument().addDocumentListener(new DocumentListener() {
			public void removeUpdate(DocumentEvent e) {
				insertUpdate(e); // Perform same actions
			}
			
			public void insertUpdate(DocumentEvent e) {
				enigma.resetRotation();
				enigma.type(previous.getText());
				typeListener.typeAction(); // refresh the states
				
				// Remove/Add thick lines
				if(previous.getText().length() == 0){
					rotorStatic.removeThickLines();
					rotorRight.removeThickLines();
					rotorCenter.removeThickLines();
					rotorLeft.removeThickLines();
					reflector.removeThickLines();
					
					// Update output
					output.setText("-");
				
				}else{
					int result = previous.getText().charAt(previous.getText().length()-1) - 'A';
					
					// Static rotor
					rotorStatic.setThickOutLine(result);
					
					// Plug board
					if(enigma.getPlugboardOf(result) != -1)
						result = enigma.getPlugboardOf(result);
					
					// Left to right
					rotorRight.setThickOutLine(result);
					
					result = enigma.getRightRotor().getAnOutWire(result);
					rotorCenter.setThickOutLine(result);
					
					result = enigma.getCenterRotor().getAnOutWire(result);
					rotorLeft.setThickOutLine(result);
					
					// Reflector
					result = enigma.getLeftRotor().getAnOutWire(result);
					reflector.setThickOutLine(result);
					
					// Right to left
					result = enigma.getLeftRotor().getAnInWire(enigma.getReflector().getAnOutWire(result));
					rotorLeft.setThickInLine(result);
					
					result = enigma.getCenterRotor().getAnInWire(result);
					rotorCenter.setThickInLine(result);
					
					result = enigma.getRightRotor().getAnInWire(result);
					rotorRight.setThickInLine(result);
					
					// Plug board
					if(enigma.getPlugboardOf(result) != -1)
						result = enigma.getPlugboardOf(result);
					
					// Static rotor
					rotorStatic.setThickInLine(result);
					
					// Update output
					output.setText((char)(result+'A')+"");
				}
				
				redraw();
			}
			public void changedUpdate(DocumentEvent e) {}
		});
		
		// Set wires pane layout
		wiresPanel.setLayout(new FlowLayout(FlowLayout.CENTER,0,0));
		
		// Set text panel layout
		textPanel.setLayout(new GridBagLayout());
		
		// Border for text panel
		textPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Input text"), BorderFactory.createEmptyBorder(2, 2, 2, 2)));
		
		// Ouput size
		output.setFont(new Font(output.getFont().getName(), Font.BOLD, 20));
		
		// Disable previous input
		previous.setEditable(false);
		
		// Default grid configuration
		gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.NONE;
		gc.weightx = 1;
		gc.insets = new Insets(5, 2, 5, 2);
		
		// Row 1
		gc.gridx = 0;
		gc.gridy = 0;
		gc.gridwidth = 2;
		textPanel.add(output, gc);
		
		// Row 2
		gc.gridx = 0;
		gc.gridy = 1;
		gc.gridwidth = 1;
		textPanel.add(previous, gc);
		
		gc.gridx = 1;
		gc.gridy = 1;
		textPanel.add(input, gc);
		
		gc.gridx = 0;
		gc.gridy = 2;
		gc.gridwidth = 2;
		JButton reset = new JButton("Clear");
		reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				previous.setText("");
			}
		});
		textPanel.add(reset, gc);
		
		// Add components
		wiresPanel.add(reflector);
		wiresPanel.add(rotorLeft);
		wiresPanel.add(rotorCenter);
		wiresPanel.add(rotorRight);
		wiresPanel.add(rotorStatic);
		
		add(wiresPanel);
		add(textPanel);
	}
	
	/**
	 * Redraw rotos and reflector
	 */
	public void redraw(){
		
		// Generate and add Static Rotor
		int[][] connectionStatic = new int[26][2];
		for(int i=0; i<26; i++){
			if(enigma.getPlugboardOf(i) == -1)
				connectionStatic[i][0] = connectionStatic[i][1] = i;
			else{
				connectionStatic[i][1] = i;
				connectionStatic[i][0] = enigma.getPlugboardOf(i);
			}
				
		}
		rotorStatic.redraw(connectionStatic);
		reflector.redraw(enigma.getReflector());
		rotorLeft.redraw(enigma.getLeftRotor());
		rotorCenter.redraw(enigma.getCenterRotor());
		rotorRight.redraw(enigma.getRightRotor());
		
		// Repaint
		repaint();
		revalidate();
	}
	
	/**
	 * Clear and reset wires 
	 */
	public void clear(){
		enigma.resetRotation();
		typeListener.typeAction(); // Refresh state
		previous.setText("");
		redraw();
	}
	
	/**
	 * Set wire listener
	 * @param wiresListener
	 */
	public void setTypeListener(TypeListener typeListener){
		this.typeListener = typeListener;
	}
	
	/**
	 * Set enigma
	 * @param enigma
	 */
	public void setEnigma(Enigma enigma){
		this.enigma = enigma;
	}

	/**
	 * Update grapihcs
	 */
	public void updateWires() {
		previous.setText("");
		redraw();
	}
}
