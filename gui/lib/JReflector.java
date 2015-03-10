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

import enigma.Reflector;

public class JReflector extends JPanel{
	Dimension dim;
	private GridBagConstraints gc;
	private int thickOutLine = -1;
	
	public JReflector() {
		// Create fixed dimension
		dim = new Dimension(150,445);
		
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
	public void redraw(Reflector reflector){
		// Remove old drawing if exist
		removeAll();
		
		for(int i=0; i<26; i++){
			
			// Right column
			gc.gridx = 1;
			gc.gridy = i;
			gc.anchor = GridBagConstraints.CENTER;
			JLabel letter = new JLabel(""+(char)('A'+i)+"");
			letter.setFont(new Font(letter.getFont().getName(), Font.PLAIN, 11));
			add(letter, gc);
		}
		
		// Generate and add Left Rotor
		int[][] connection = new int[26][2];
		for(int i=0; i<26; i++){
			connection[i][1] = i;
			connection[i][0] = reflector.getAnOutWire(i);
		}
		
		// Generate a new one
		gc.gridheight = 26;
		gc.gridy = 0;
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.LINE_END;
		add(new DrawingRectangle(connection), gc);
		gc.gridheight = 1;
	}
	
	/**
	 * Set Out thick line
	 * @param line
	 */
	public void setThickOutLine(int line){
		this.thickOutLine = line;
	}
	

	/**
	 * Remove thick lines
	 */
	public void removeThickLines(){
		setThickOutLine(-1);
	}
	
	
	
	private class DrawingRectangle extends JPanel {
		private int[][] connection;
		private int width;
		Color colors[] = {Color.BLUE, Color.DARK_GRAY, Color.MAGENTA, Color.ORANGE.darker(), Color.RED, Color.RED.darker(), Color.YELLOW.darker(), Color.PINK.darker()};
		
		/**
		 * Draw rectangle
		 * @param connection
		 * @param rotor
		 */
		public DrawingRectangle(int[][] connection) {
			this.connection = connection;
			width = dim.width-50;
		}
		
		/**
		 * Paint rectangle: Colors, XY etc..
		 */
		public void paintComponent(Graphics g) {
			
			// Draw all lines
	        for(int i=0; i<connection.length; i++){

	        	// Config the design and thickness
	        	Graphics2D g2 = (Graphics2D) g;
	        	if(thickOutLine == i || thickOutLine == connection[i][0]){
	        		g2.setStroke(new BasicStroke(3));
	        		g2.setColor(Color.BLACK);
	        	}else{
	        		g2.setStroke(new BasicStroke(1));
	        		g2.setColor(colors[i% colors.length]);
		       }
	        	
				// From to
				int from = connection[i][0]*17+10;
				int height = connection[i][1]*17+10 - from;
				if(height > 0)
					g.drawRect(1+i*3, from, width, height);
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
