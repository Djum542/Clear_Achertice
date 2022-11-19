package framework;

import java.util.*;

public class CommandProcessor {
	private Stack<Command> undo = new Stack<Command>();
	private Stack<Command> redo = new Stack<Command>();
	
	public void execute(Command cmmd) throws AppException {
		cmmd.execute();
		if (cmmd.isUndoable()) {
			undo.push(cmmd);
			redo.clear();
		}
	}
	
	public void undo() {
		if (!undo.empty()) {
			Command cmmd = undo.pop();
			cmmd.undo();
			redo.push(cmmd);
		}
	}
	
	public void redo() throws AppException {
		if (!redo.empty()) {
			Command cmmd = redo.pop();
			cmmd.execute();
			undo.push(cmmd);
		}
	}
	
	public void reset() {
		undo.clear();
		redo.clear();
	}

}
