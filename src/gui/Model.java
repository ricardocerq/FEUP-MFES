package gui;

import java.io.Serializable;
import java.util.Observable;

import org.overture.interpreter.values.Value;

class Model extends Observable implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public Value val;
	
	public Model() {
		
	}
}
