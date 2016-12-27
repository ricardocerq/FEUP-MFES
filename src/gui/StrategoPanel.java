package gui;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import org.overture.interpreter.runtime.ValueException;
import org.overture.interpreter.values.RecordValue;
import org.overture.interpreter.values.Value;

class StrategoPanel extends JPanel implements Observer, MouseListener, MouseMotionListener {

	private static final long serialVersionUID = 1L;

	Model model;
	
	transient static BufferedImage piece;
	
	StrategoPanel(Model model) throws IOException {
		super();
		this.model = model;
		model.addObserver(this);
		System.out.println("added observer");
		this.setBackground(Color.WHITE);
		
		//piece = ImageIO.read(new File(this.getClass().getResource("/res/piece.png").getFile()));
		
		// Add event listeners
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		
		this.requestFocus();
		
		
	}

	public void update(Observable obs, Object arg) {
		System.out.println("------------update");
		if(arg instanceof Value)
			model.val = (Value) arg;
		this.repaint();
	}

	public void paintComponent(java.awt.Graphics g) {
		try {
			Value val = model.val;
			System.out.println(model);
			if(val != null){
				RecordValue instance = val.recordValue(null);
				Value board = instance.fieldmap.get("board");
				System.out.println(board);
			} else System.out.println("is null");
		} catch (ValueException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		System.out.println("click");
		this.repaint();
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
