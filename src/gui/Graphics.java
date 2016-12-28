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

	public Value testFunc(Value number) throws ValueException {
		final Value temp = number;
		System.out.println(temp.intValue(null));
		return new VoidValue();
	}
	
	public Value testFunc2(Value inst) throws ValueException {
		System.out.println(inst.stringValue(null));
		return new VoidValue();
	}
	
	public Value board(Value str) throws ValueException {
		String res = "";
		ValueList lst = str.seqValue(null);
		System.out.println("Banana ");
		for(int i = 0; i < lst.size(); i++) {
			res += lst.get(i).charValue(null);
		}
		
		System.out.println("Banana:\n" + res);
		
		return new VoidValue();
	}
	
	public Value inst(Value str) throws ValueException {
		System.out.println(str);
		System.out.println("notifying ((");
		
		model.notifyObservers(str);
		System.out.println("++++++++");
		
		return new VoidValue();
	}
	
	public Value send(Value str) throws ValueException {
		System.out.println(str);
		
		model.notifyObservers(str);
		
		return new VoidValue();
	}
}