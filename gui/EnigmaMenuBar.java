/**
* Enigma Machine GUI
* Coded by Amir El Bawab
* Date: 25 January 2015
* License: MIT License ~ Please read License.txt for more information about the usage of this software
* */

package gui;

import gui.listener.EnigmaMenuListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.ButtonGroup;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileFilter;

public class EnigmaMenuBar {

	private JMenuBar menuBar;
	private File lastFile;
	private Scanner fileInputReader;
	private PrintWriter fileOutputWriter;
	private JFileChooser fileChooser;
	private String FILE_EXTENSION = "txt";
	private EnigmaMenuListener emlistener;
	
	public EnigmaMenuBar(final JFrame frame) {
		
		// Create menu bar
		menuBar = new JMenuBar();
		
		// FileChooser
		fileChooser = new JFileChooser();
		fileChooser.addChoosableFileFilter(new FileFilter() {
			
			// File extension to be displayed on the file chooser
			public String getDescription() {
				return FILE_EXTENSION;
			}
			
			// The files that should appear on the file chooser
			public boolean accept(File file) {
				String[] extentions = {FILE_EXTENSION};
				if (file.isDirectory()) {
					return true;
			    } else {
			      String path = file.getAbsolutePath().toLowerCase();
			      for (int i = 0, n = extentions.length; i < n; i++) {
			        String extension = extentions[i];
			        if ((path.endsWith(extension) && (path.charAt(path.length() - extension.length() - 1)) == '.')) 
			          return true;
			      }
			    }
			    return false;
			}
		});
		
		// File
		JMenu file = new JMenu("File");
		JMenuItem importTxt = new JMenuItem("Import text");
		JMenuItem exportTxt = new JMenuItem("Export text");
		JMenuItem restart = new JMenuItem("Restart");
		JMenuItem exit = new JMenuItem("Exit");
		file.add(importTxt);
		file.add(exportTxt);
		file.add(restart);
		file.add(exit);
		
		// View
		JMenu display = new JMenu("Display");
		ButtonGroup displayGroup = new ButtonGroup();
		JRadioButtonMenuItem textBox = new JRadioButtonMenuItem("Text box");
		JRadioButtonMenuItem keyboard = new JRadioButtonMenuItem("Keyboard");
		JRadioButtonMenuItem wiresConnection = new JRadioButtonMenuItem("Wires connection");
		textBox.setSelected(true);
		displayGroup.add(textBox);
		displayGroup.add(keyboard);
		displayGroup.add(wiresConnection);
		display.add(textBox);
		display.add(keyboard);
		display.add(wiresConnection);
		
		// Add Menu to MenuBar
		menuBar.add(file);
		menuBar.add(display);

		// Display> keyboard textBox
		keyboard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				emlistener.keyboardDisplay();
			}
		});
		
		textBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				emlistener.textBoxDisplay();
			}
		});
		
		wiresConnection.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				emlistener.wiresConnectionDisplay();
			}
		});
		
		restart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				emlistener.restart();
			}
		});
		
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(1);
			}
		});
		
		// File>Import TXT
		importTxt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I,ActionEvent.CTRL_MASK));
		importTxt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				// Set the path
				if(lastFile != null)
					fileChooser.setCurrentDirectory(lastFile);
				
				// Open window, if hit cancel then exit
				if(fileChooser.showOpenDialog(frame) == JFileChooser.CANCEL_OPTION)
					return;
				
				// Save file as last file opened
				lastFile = fileChooser.getSelectedFile();
				
				// Verify that file ext is correct
				if(lastFile != null){
					if(getExtension(lastFile).equalsIgnoreCase(FILE_EXTENSION)){
						try {
							fileInputReader = new Scanner(lastFile);
							
							String text = "";
							while(fileInputReader.hasNextLine())
								text += fileInputReader.nextLine()+"\n";
							emlistener.importFile(text);
							
						} catch (FileNotFoundException e) {
							JOptionPane.showMessageDialog(frame, "An error occurred while loading the file.");
						} finally{
							if(fileInputReader != null)
								fileInputReader.close();
						}
						
					}else{
						JOptionPane.showMessageDialog(frame, "You selected an unsupported file input.");
					}
				}
			}
		});
		
		//File > Export
		exportTxt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E,ActionEvent.CTRL_MASK));
		exportTxt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				// Check if empty output
				String text = emlistener.exportFile();
				if(text.isEmpty()){
					JOptionPane.showMessageDialog(frame, "You can't export an empty output");
					return;
				
				// Wires mode
				} else if(text.equals("-1")){
					JOptionPane.showMessageDialog(frame, "You can't export a text in this display");
					return;
				}
				
				// Set the path
				if(lastFile != null)
					fileChooser.setCurrentDirectory(lastFile);
				
				// Save window, if hit cancel then exit
				if(fileChooser.showSaveDialog(frame) == JFileChooser.CANCEL_OPTION)
					return;
				
				// Save file as last file opened
				lastFile = fileChooser.getSelectedFile();
				
				// Verify that file ext is correct
				if(lastFile != null){
					
					// If file exist, ask the user if replace file
					if(lastFile.exists() && JOptionPane.showConfirmDialog(frame, "Would you like to overwrite the existing file ?", "Overwite existing file", JOptionPane.YES_NO_OPTION) == JOptionPane.CANCEL_OPTION)
						return;
					
					try {
						if(getExtension(lastFile).equalsIgnoreCase(FILE_EXTENSION))
							fileOutputWriter = new PrintWriter(lastFile.getAbsolutePath());
						else
							fileOutputWriter = new PrintWriter(lastFile.getAbsolutePath()+ "." + FILE_EXTENSION);
						fileOutputWriter.print(emlistener.exportFile());
						JOptionPane.showMessageDialog(frame, "File output exported successfully");
					} catch (FileNotFoundException e) {
						JOptionPane.showMessageDialog(frame, "An error occured while exporting the file");
					} finally{
						if(fileOutputWriter != null)
							fileOutputWriter.close();
					}
				}
			}
		});
		
	}
	
	/**
	 * Get Menu Bar
	 * @return Menu Bar
	 * */
	public JMenuBar getMenuBar(){
		return menuBar;
	}
	
	/**
	 * Get extension of a file
	 * @param f File to be evaluated
	 * */
	private String getExtension(File f) {
        String ext = "";
        String s = f.getName();
        int i = s.lastIndexOf('.');
        if (i > 0 &&  i < s.length() - 1) {
            ext = s.substring(i+1).toLowerCase();
        }
        return ext;
    }
	
	/**
	 * Set Enigma menu listener
	 * @param emlistener
	 */
	public void setEmlistener(EnigmaMenuListener emlistener) {
		this.emlistener = emlistener;
	}
}
