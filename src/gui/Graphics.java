package gui;

import java.io.Serializable;

import org.overture.interpreter.runtime.ValueException;
import org.overture.interpreter.values.Value;
import org.overture.interpreter.values.VoidValue;

public class Graphics implements Serializable {

	private static final long serialVersionUID = 1L;

	transient Controller ctrl;
	Model model;

	public Value init() {
		ctrl = new Controller();
		model = ctrl.getModel();
		return new VoidValue();
	}

	public Value testFunc(Value number) throws ValueException {
		final Value temp = number;
		System.out.println(temp.intValue(null));
		return new VoidValue();
	}
	
	public Value testFunc2(Value inst) throws ValueException {
		System.out.println(inst.stringValue(null));
		return new VoidValue();
	}
}