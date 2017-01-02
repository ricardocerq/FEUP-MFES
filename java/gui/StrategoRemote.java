package gui;

import org.overture.interpreter.debug.RemoteControl;
import org.overture.interpreter.debug.RemoteInterpreter;

public class StrategoRemote implements RemoteControl {

	RemoteInterpreter interpreter;

	@Override
	public void run(RemoteInterpreter intrprtr) throws Exception {
		interpreter = intrprtr;
		StrategoControl ctrl = new StrategoControl(interpreter);

		ctrl.init();
	}
}
