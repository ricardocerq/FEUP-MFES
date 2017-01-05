package gui;

import java.io.Serializable;

import org.overture.interpreter.runtime.ValueException;
import org.overture.interpreter.values.Value;
import org.overture.interpreter.values.ValueList;
import org.overture.interpreter.values.VoidValue;

public class Graphics implements Serializable {

	private static final long serialVersionUID = 1L;

	transient StrategoWindow ctrl;
	Model model;

	public Value init() {
		ctrl = new StrategoWindow();
		model = ctrl.getModel();
		return new VoidValue();
	}
	
	public Value sendData(Value str) throws ValueException {
		System.out.println("Received :" + str);
		
		model.notifyObservers(str);
		
		return new VoidValue();
	}
}