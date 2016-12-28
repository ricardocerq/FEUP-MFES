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

import org.overture.ast.analysis.AnalysisException;
import org.overture.interpreter.runtime.ValueException;
import org.overture.interpreter.values.NaturalValue;
import org.overture.interpreter.values.RecordValue;
import org.overture.interpreter.values.Value;
import org.overture.interpreter.values.ValueList;
import org.overture.interpreter.values.ValueMap;

class StrategoPanel extends JPanel implements Observer, MouseListener, MouseMotionListener {

	private static final long serialVersionUID = 1L;

	Model model;

	StrategoControl strategoCtrl;
	
	transient static BufferedImage piece;
	
	StrategoPanel(Model model, StrategoControl strategoCtrl) throws IOException {
		super();
		this.model = model;
		this.strategoCtrl = strategoCtrl;
		
		this.setBackground(Color.WHITE);
		
		//piece = ImageIO.read(new File(this.getClass().getResource("/res/piece.png").getFile()));
		
		// Add event listeners
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		
		this.requestFocus();
		
		
	}

	public void update(Observable obs, Object arg) {
		if(arg != null && arg instanceof Value)
			model.val = (Value) arg;
		this.repaint();
	}
	

	public void paintComponent(java.awt.Graphics g) {
		try {
			Value val = model.val;
			ValueFactory v = new ValueFactory();
			//System.out.println(model);
			if(val != null){
				RecordValue instance = val.recordValue(null);
				ValueMap board = instance.fieldmap.get("board").mapValue(null);
				ValueList strengths = instance.fieldmap.get("ruleSet").recordValue(null).fieldmap.get("characterStrengths").seqValue(null);
				
				for(int y = 0; y < 10; y++){
					for(int x = 0; x < 10; x++){
						RecordValue p = v.createRecord("Stratego`Point", new NaturalValue(x+1), new NaturalValue(y+1));
						Value piece = board.get(p);
						if(piece == null)
							System.out.print("--  ");
						else {
							RecordValue pieceRecord = piece.recordValue(null);
							
							String str = Integer.toString(strengths.indexOf(pieceRecord.fieldmap.get("character")) - 1);
							if(str.equals("0"))
								str = "F";
							else if(str.equals("10"))
								str = "B";
							else str = str.substring(str.length()-1, str.length());
							
							String team = pieceRecord.fieldmap.get("team").toString().substring(1, 2).toLowerCase();
							System.out.print(str + "" + team + "  ");
						}
					}
					System.out.println("\n");
				}
				System.out.println("\n\n");
				//System.out.println(board);
			} else System.out.println("is null");
		} catch (ValueException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AnalysisException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
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
