package gui.lib;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import enigma.Rotor;

public class JRotor extends JPanel{
	private Dimension dim;
	private GridBagConstraints gc;
	private int thickOutLine = -1;
	private int thickInLine = -1;
	
	public JRotor() {
		// Create fixed dimension
		dim = new Dimension(210,445);
		
		// Set layout
		setLayout(new GridBagLayout());
		
		// Default grid configuration
		gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.NONE;
		gc.weightx = 1;
		gc.weighty = 1;
		
		// Set size
		setPreferredSize(dim);
	}
	
	/**
	 * Redraw the lines
	 * @param connection
	 */
	public void redraw(Rotor rotor){
		// Remove old drawing if exist
		removeAll();
		
		for(int i=0; i<26; i++){
			
			// Left column
			gc.gridx = 0;
			gc.gridy = i;
			JLabel letter = new JLabel(""+(char)((rotor.getRingHead()-'A'+i)%26 + 'A')+"");
			letter.setFont(new Font(letter.getFont().getName(), Font.PLAIN, 11));
			add(letter, gc);
			
			// Right column
			gc.gridx = 2;
			char current = (char)((rotor.getRotorHead()-'A'+i)%26 + 'A');
			String pre = rotor.getNotch() == current ? "[":"";
			String post = rotor.getNotch() == current ? "]":"";
			letter = new JLabel(pre + current + post);
			letter.setFont(new Font(letter.getFont().getName(), Font.PLAIN, 11));
			add(letter, gc);
		}
		
		// Generate and add Left Rotor
		int[][] connection = new int[26][2];
		for(int i=0; i<26; i++){
			connection[i][1] = i;
			connection[i][0] = rotor.getAnOutWire(i);
		}
		
		// Generate a new one
		gc.gridheight = 26;
		gc.gridy = 0;
		gc.gridx = 1;
		add(new DrawingLine(connection), gc);
		gc.gridheight = 1;
	}
	
	/**
	 * Put border around the rotor head
	 */
	public void paintComponent(Graphics g) {
		g.setColor(Color.GRAY);
    	g.drawRect(1, 0, dim.width-6, 17);
    }
	
	/**
	 * Set Out thick line
	 * @param line
	 */
	public void setThickOutLine(int line){
		this.thickOutLine = line;
	}
	
	/**
	 * Set In thick line
	 * @param line
	 */
	public void setThickInLine(int line){
		this.thickInLine = line;
	}
	
	/**
	 * Remove thick lines
	 */
	public void removeThickLines(){
		setThickInLine(-1);
		setThickOutLine(-1);
	}
	
	
	
	private class DrawingLine extends JPanel {
		private int[][] connection;
		private int width;
		Color colors[] = {Color.BLUE, Color.DARK_GRAY, Color.MAGENTA, Color.ORANGE.darker(), Color.RED, Color.RED.darker(), Color.YELLOW.darker(), Color.PINK.darker()};
		
		/**
		 * Draw lines
		 * @param connection
		 * @param rotor
		 */
		public DrawingLine(int[][] connection) {
			this.connection = connection;
			width = dim.width-50;
		}
		
		/**
		 * Paint lines: Colors, XY etc..
		 */
		public void paintComponent(Graphics g) {
	        for(int i=0; i<connection.length; i++){
	        	Graphics2D g2 = (Graphics2D) g;
	        	if(thickInLine == i || thickOutLine == i){
	        		g2.setStroke(new BasicStroke(3));
	        		g2.setColor(Color.BLACK);
	        	}else{
	        		g2.setStroke(new BasicStroke(1));
	        		g2.setColor(colors[i% colors.length]);
		       }
				g2.drawLine(0, connection[i][0]*17+10, width, connection[i][1]*17+10);
	        }
	    }

		/**
		 * Dimension of the generated image
		 */
		public Dimension getPreferredSize() {
	    	setOpaque(false);
	        return new Dimension(width+1, dim.height);
	    }
	}
}
