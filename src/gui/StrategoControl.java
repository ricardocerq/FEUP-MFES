package gui;

import org.overture.interpreter.debug.RemoteInterpreter;
import org.overture.interpreter.values.Value;

public class StrategoControl {
	private RemoteInterpreter interpreter;

	public StrategoControl(RemoteInterpreter intrprtr) {
		interpreter = intrprtr;
		StrategoWindow.strategoCtrl = this;
	}

	public void init() {
		try {
			execute("create inst := StrategoState`defaultStartingInstance()");
			execute("inst := StrategoState`setBoard(inst, StrategoState`fillBoardRandom(inst.ruleSet))");
			execute("create gui := new StrategoGUI()");
			execute("gui.setInstance(inst)");
			execute("gui.run()");
			execute("gui.sendBoard()");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Value execute(String arguments) throws Exception {
		String cmd = arguments;
		if (cmd.toLowerCase().startsWith("create")) {
			cmd = cmd.substring(cmd.indexOf(" "));
			cmd = cmd.trim();
			String name = cmd.substring(0, cmd.indexOf(" "));
			String exp = cmd.substring(cmd.indexOf(":=") + 2);
			System.out.println("CREATE:  var: " + name + " exp: " + exp);
			interpreter.create(name, exp);
			return null;
		} else if (cmd.toLowerCase().startsWith("debug") || cmd.toLowerCase().startsWith("print")) {
			cmd = /* "p" + */cmd.substring(cmd.indexOf(" "));

			cmd = cmd.trim();
		}

		try {
			System.out.println("Calling VDMJ with: " + cmd);
			Value result = interpreter.valueExecute(cmd);
			return result;
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * Notifies Overture that the remote interface is being disposed and that
	 * interpretation is finished. After this Overture is allowed to terminate
	 * the current process.
	 */
	public void finish() {
		this.interpreter.finish();
	}
}
