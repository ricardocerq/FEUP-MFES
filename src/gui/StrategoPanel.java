package gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
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
	
	transient BufferedImage[] pieces_red;
	transient BufferedImage[] pieces_blu;
	transient BufferedImage background;
	
	ValueFactory valueFactory = new ValueFactory();
	
	int size = Math.min(this.getWidth(), this.getHeight());
	int startX = (getWidth() - size)/2;
	int startY = (getHeight() - size)/2;
	int squareSize = size / 10;
	
	RecordValue sel1;
	RecordValue sel2;
	
	StrategoPanel(Model model, StrategoControl strategoCtrl) throws IOException {
		super();
		this.model = model;
		this.strategoCtrl = strategoCtrl;
		
		this.setBackground(Color.WHITE);
		
		pieces_red = new BufferedImage[12];
		for(int i = 0; i <= 11; i++)
			pieces_red[i] = ImageIO.read(this.getClass().getResourceAsStream("/res/red_" + i + ".png"));
		
		pieces_blu = new BufferedImage[12];
		for(int i = 0; i <= 11; i++)
			pieces_blu[i] = ImageIO.read(this.getClass().getResourceAsStream("/res/blue_" + i + ".png"));
		
		background  = ImageIO.read(this.getClass().getResourceAsStream("/res/background.png"));
		
		// Add event listeners
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		
		this.requestFocus();
		
		this.setBackground(Color.WHITE);
	}

	public void update(Observable obs, Object arg) {
		if(arg != null && arg instanceof Value)
			model.val = (Value) arg;
		sel1 = null;
		sel2 = null;
		this.repaint();
	} 
	
	public void centerString(java.awt.Graphics g, String s, 
	        Font font, int width, int height, int x, int y) {
	    FontRenderContext frc = 
	            new FontRenderContext(null, true, true);

	    Rectangle2D r2D = font.getStringBounds(s, frc);
	    int rWidth = (int) Math.round(r2D.getWidth());
	    int rHeight = (int) Math.round(r2D.getHeight());
	    int rX = (int) Math.round(r2D.getX());
	    int rY = (int) Math.round(r2D.getY());

	    int a = (width / 2) - (rWidth / 2) - rX;
	    int b = (height / 2) - (rHeight / 2) - rY;

	    g.setFont(font);
	    g.drawString(s, x + a, y + b);
	}
	
	public void drawMatrixString(java.awt.Graphics2D g, Color c, String s, int x, int y) {
		g.setColor(c);
		centerString(g, s,  new Font("Arial", Font.PLAIN, size/15), squareSize, squareSize, startX + x*squareSize, startY + y*squareSize);
	}
	
	public void fillMatrixRect(java.awt.Graphics2D g, Color c, int x, int y) {
		g.setColor(c);
		g.fillRect(startX + x*squareSize, startY + y*squareSize, squareSize, squareSize);
	}
	
	public void drawMatrixRect(java.awt.Graphics2D g, Color c, int x, int y, int width) {
		g.setColor(c);
		g.setStroke(new BasicStroke(width));
		g.drawRect(startX + x*squareSize, startY + y*squareSize, squareSize, squareSize);
	}
	
	public void drawMatrixRectRounded(java.awt.Graphics2D g, Color c, int x, int y, double radius, int width) {
		g.setColor(c);
		g.setStroke(new BasicStroke(width));
		g.drawRoundRect(startX + x*squareSize, startY + y*squareSize, squareSize, squareSize, (int)(squareSize*radius), (int)(squareSize*radius));
	}
	
	public void fillMatrixCircle(java.awt.Graphics2D g, Color c, int x, int y, double rad) {
		g.setColor(c);
		g.fillOval(startX + x*squareSize + (int)(squareSize*(1-rad)/2), startY + y*squareSize+ (int)(squareSize*(1-rad)/2), (int)(squareSize*rad), (int)(squareSize*rad));
	}
	
	public void drawMatrixCircle(java.awt.Graphics2D g, Color c, int x, int y, double rad, int width) {
		g.setColor(c);
		g.setStroke(new BasicStroke(width));
		g.drawOval(startX + x*squareSize + (int)(squareSize*(1-rad)/2), startY + y*squareSize+ (int)(squareSize*(1-rad)/2), (int)(squareSize*rad), (int)(squareSize*rad));
	}
	
	public void paintComponent(java.awt.Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		try {
			size = Math.min(this.getWidth(), this.getHeight());
			startX = (getWidth() - size)/2;
			startY = (getHeight() - size)/2;
			squareSize = size / 10;
			
			Value val = model.val;
			
			//System.out.println(model);
			if(val != null){
				ValueList list = val.seqValue(null);
				
				RecordValue instance = list.get(0).recordValue(null);
				
				ValueMap board = instance.fieldmap.get("board").mapValue(null);
				ValueList strengths = instance.fieldmap.get("ruleSet").recordValue(null).fieldmap.get("characterStrengths").seqValue(null);
				
				g.drawImage(background, startX, startY, 10 * squareSize, 10 * squareSize, null);
				
				for(int y = 0; y < 10; y++){
					for(int x = 0; x < 10; x++){
						if( (x+1 >= 3 && x+1 <= 4 && y+1 >= 5 && y+1 <= 6) ||(x+1 >= 7 && x+1 <= 8 && y+1 >= 5 && y+1 <= 6)){
							//System.out.print("~~  ");
							//fillMatrixRect(g, Color.BLUE, x, y);
							//drawMatrixString(g, Color.WHITE, "~", x, y);
							//drawMatrixRect(g, Color.BLACK, x, y);
							continue;
						}
						
						RecordValue p = valueFactory.createRecord("Stratego`Point", new NaturalValue(x+1), new NaturalValue(y+1));
						Value piece = board.get(p);
						
						if(sel1 == null){
							if(isIn("src", x+1, y+1) != null){
								drawMatrixRectRounded(g2, Color.GREEN, x, y, .1, 10);
							}
						} else {
							if(sel1.fieldmap.get("x").nat1Value(null) == x+1 && sel1.fieldmap.get("y").nat1Value(null) == y+1){
								drawMatrixRectRounded(g2, Color.GREEN, x, y, .1, 10);
							} else if(isDst(x+1, y+1) != null){
								if(sel2 == null || (sel2.fieldmap.get("x").nat1Value(null) == x+1 && sel2.fieldmap.get("y").nat1Value(null) == y+1)){
									Color c;
									if(piece != null)
										c = Color.RED;
									else c = Color.YELLOW;
										drawMatrixRectRounded(g2, c, x, y, .1, 10);
								}
							}
						}
						
						
						
						drawMatrixRect(g2, Color.BLACK, x, y, 1);
						if(piece == null){
							//System.out.print("--  ");
							//drawMatrixRect(g, Color.GRAY, x, y);
							//drawMatrixString(g, Color.BLACK, "x", x, y);
						} else {
							RecordValue pieceRecord = piece.recordValue(null);
							
							String str = Integer.toString(strengths.indexOf(pieceRecord.fieldmap.get("character")));
							int num = Integer.parseInt(str);

							if(str.equals("0"))
								str = "F";
							else if(str.equals("11"))
								str = "B";
							else str = str.substring(str.length()-1, str.length());
							
							
							String team = pieceRecord.fieldmap.get("team").toString().substring(1, 2).toLowerCase();
							//System.out.print(str + "" + team + "  ");
							Color c;
							if(team.equals("r"))
								c = Color.RED;
							else c = Color.BLUE;
							
							BufferedImage img = team.equals("r") ? pieces_red[num] : pieces_blu[num];
							
							g.drawImage(img, startX + x * squareSize, startY + y * squareSize, squareSize, squareSize, null);
							
							//fillMatrixCircle(g, c, x, y, .75);
							//drawMatrixString(g, Color.WHITE, str, x, y);
						}
					}
					//System.out.println("\n");
				}
				//System.out.println("\n\n");
				//System.out.println(board);
			} 
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
		//System.out.println(model.val);
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		mouseClicked(arg0);
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}
	
	public RecordValue isIn(String srcDst, int x, int y) throws ValueException{
		for (Value move : model.val.seqValue(null).get(1).setValue(null)){
			RecordValue point = move.recordValue(null).fieldmap.get(srcDst).recordValue(null);
			if(point.fieldmap.get("x").nat1Value(null) == x && point.fieldmap.get("y").nat1Value(null) == y)
				return point;
		}
		return null;
	}
	
	public RecordValue isDst(int x2, int y2) throws ValueException{
		int x1 = (int)sel1.fieldmap.get("x").nat1Value(null);
		int y1 = (int)sel1.fieldmap.get("y").nat1Value(null);
		for (Value move : model.val.seqValue(null).get(1).setValue(null)){
			RecordValue pointSrc = move.recordValue(null).fieldmap.get("src").recordValue(null);
			RecordValue pointDst = move.recordValue(null).fieldmap.get("dst").recordValue(null);
			if(pointSrc.fieldmap.get("x").nat1Value(null) == x1 && pointSrc.fieldmap.get("y").nat1Value(null) == y1 &&
					pointDst.fieldmap.get("x").nat1Value(null) == x2 && pointDst.fieldmap.get("y").nat1Value(null) == y2)
				return pointDst;
		}
		return null;
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		this.repaint();
		try {
			if(model.val != null){
				if(arg0.getX() >= startX/2 && arg0.getX() < getWidth() - startX /2 && arg0.getY() >= startY/2 && arg0.getY() < getHeight() - startY /2){
					int x = (arg0.getX() - startX)/squareSize+1;
					int y = (arg0.getY() - startY)/squareSize+1;
					//System.out.println("clicked " + x + ", " + y);
					if(sel2 != null){
						//System.out.println("Done");
					} else if (sel1 != null){
						RecordValue v = isDst(x,y);
						if(v != null){
							sel2 = v;
							String str = "gr.sendData(StrategoOperations`getGameData(Stratego`executeMove(" + model.val.seqValue(null).get(0) + ")(" + 
									valueFactory.createRecord("Stratego`Move", sel1, sel2)
									+ ")))";
							System.out.println("return " + this.strategoCtrl.execute(str));
							//this.strategoCtrl.execute("gr.board(\"hello\")");
							//this.strategoCtrl.execute("gr.sendData([mk_Point(1,1)])");
						} else {
							v = isIn("src", x, y);
							sel1 = v;
						}
					} else {
						RecordValue v = isIn("src", x, y);
						sel1 = v;
					}
				}
			}
		} catch (Exception e) {
			
		}
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
