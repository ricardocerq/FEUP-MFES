package gui;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;

import javax.swing.JPanel;

class StrategoPanel extends JPanel implements Observer, MouseListener, MouseMotionListener {

	private static final long serialVersionUID = 1L;

	Model model;

	transient static BufferedImage piece;
	
	StrategoPanel(Model model) throws IOException {
		super();
		this.model = model;
		this.setBackground(Color.WHITE);
		
		//piece = ImageIO.read(new File(this.getClass().getResource("/res/piece.png").getFile()));
		
		// Add event listeners
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		
		this.requestFocus();
	}

	public void update(Observable obs, Object arg) {
	}

	public void paintComponent(java.awt.Graphics g) {
		//g.drawImage(piece, 0, 0, 50, 50, null);
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}
}
