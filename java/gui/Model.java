package gui;

import java.io.Serializable;
import java.util.Observable;
import java.util.Observer;

import org.overture.interpreter.values.Value;

class Model extends Observable implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public Value val;
	
	public Model() {
		
	}
	
	@Override
	public void notifyObservers(Object o){
		this.setChanged();
		super.notifyObservers(o);
	}
}
