/**
* Enigma Machine GUI
* Coded by Amir El Bawab
* Date: 20 January 2015
* License: MIT License ~ Please read License.txt for more information about the usage of this software
* */

package gui;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;

import enigma.Enigma;
import gui.listener.TypeListener;

public class EncodePanel extends JPanel{
	
	private JTextArea inputText;
	private JTextArea outputText;
	private JSplitPane splitter;
	private JScrollPane spInput;
	private JScrollPane spOutput;
	private Enigma enigma;
	
	private TypeListener typeListener;
	
	public EncodePanel() {
		
		// Set layout
		setLayout(new GridLayout());
		
		// Adding borders
		Border outerBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
		setBorder(outerBorder);

		// JTextArea
		inputText = new JTextArea();
		outputText = new JTextArea();
		spInput = new JScrollPane(inputText);
		spOutput = new JScrollPane(outputText);
		
		// Set Border
		spInput.setBorder(BorderFactory.createCompoundBorder(outerBorder, BorderFactory.createTitledBorder("Input")));
		spInput.setBackground(Color.white);
		spOutput.setBorder(BorderFactory.createCompoundBorder(outerBorder, BorderFactory.createTitledBorder("Output")));
		spOutput.setBackground(Color.white);
		
		// Configure output
		outputText.setEditable(false);
		
		// Splitter
		splitter = new JSplitPane(JSplitPane.VERTICAL_SPLIT,spOutput,spInput);
		splitter.setResizeWeight(0.50);
		splitter.setDividerSize(10);
		splitter.setContinuousLayout(true);

		// Set Border
		inputText.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		outputText.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		
		// Allow Undo & Redo
		final UndoManager undoManager = new UndoManager();
		Document doc = inputText.getDocument();
		doc.addUndoableEditListener(new UndoableEditListener() {
		    public void undoableEditHappened(UndoableEditEvent e) {
		        undoManager.addEdit(e.getEdit());
		    }
		});

		InputMap im = inputText.getInputMap(JComponent.WHEN_FOCUSED);
		ActionMap am = inputText.getActionMap();
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_Z, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()), "Undo");
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_Y, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()), "Redo");
		am.put("Undo", new AbstractAction() {
		    public void actionPerformed(ActionEvent e) {
		        try {
		            if (undoManager.canUndo())
		                undoManager.undo();
		        } catch (CannotUndoException exp) {}
		    }
		});
		
		am.put("Redo", new AbstractAction() {
		    public void actionPerformed(ActionEvent e) {
		        try {
		            if (undoManager.canRedo()) 
		                undoManager.redo();
		        } catch (CannotUndoException exp) {}
		    }
		});
		
		
		// Add component
		add(splitter);
		
		// Filter input to upper case only
		( (AbstractDocument) inputText.getDocument() ).setDocumentFilter(new DocumentFilter(){
			public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
				for (int i = 0; i < text.length(); i++) 
					// If not a letter, white space or new line, then return
					if( (text.charAt(i) < 'a' || text.charAt(i) > 'z') && (text.charAt(i) < 'A' || text.charAt(i) > 'Z') && text.charAt(i) != ' ' && text.charAt(i) != '\n')
						return;
				
				// Convert to upper case and add text
				text = text.toUpperCase();
				super.replace(fb, offset, length, text, attrs);
			}
		});
		
		// Add listener on document change
		inputText.getDocument().addDocumentListener(new DocumentListener() {
			public void removeUpdate(DocumentEvent e) {
				enigma.resetRotation();
				outputText.setText(enigma.type(inputText.getText()));
				typeListener.typeAction(); // refresh the states
			}
			
			public void insertUpdate(DocumentEvent e) {
				enigma.resetRotation();
				outputText.setText(enigma.type(inputText.getText()));
				typeListener.typeAction(); // refresh the states
			}
			public void changedUpdate(DocumentEvent e) {}
		});
	}
	
	/**
	 * Set the enigma machine
	 * @param enigma
	 */
	public void setEnigma(Enigma enigma){
		this.enigma = enigma;
	}
	
	/**
	 * Refresh by reinserting the input
	 */
	public void refresh(){
		inputText.setText(inputText.getText());
	}
	
	/**
	 * Set type listener
	 * @param typeListener
	 */
	public void setTypeListener(TypeListener typeListener){
		this.typeListener = typeListener;
	}
	
	/**
	 * Set input text
	 * @param text
	 */
	public void setDefaultText(String text){
		inputText.setText(text);
	}
}
