package gui;

import org.overture.interpreter.debug.RemoteInterpreter;
import org.overture.interpreter.values.Value;

public class StrategoControl {
	
	private RemoteInterpreter interpreter;

	public RemoteInterpreter getInterpreter() {
		return interpreter;
	}

	public StrategoControl(RemoteInterpreter intrprtr) {
		interpreter = intrprtr;
		StrategoWindow.strategoCtrl = this;
	}

	public void init() {
		try {
			/*execute("create inst := let ini = StrategoState`defaultStartingInstance() in (StrategoState`setBoard(ini, StrategoState`fillBoardRandom(ini.ruleSet)))");
			execute("create gui := new StrategoGUI()");
			execute("gui.setInstance(inst)");
			execute("gui.run()");
			execute("gui.sendInstance()");*/
			execute("create gr := new gui_Graphics()");
			execute("gr.init()");
			//execute("gr.sendData([1,2,3])");
			execute("let inst = StrategoOperations`newRandomInstance() in gr.sendData(StrategoOperations`getGameData(inst))");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Value execute(String arguments) throws Exception {
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
