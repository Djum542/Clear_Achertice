package framework;

public class Command {
	protected Model theModel;
	protected Memento someMemento;
	private boolean undoable;
	
	public Command(Model m, boolean canUndo) {
		theModel = m;
		undoable = canUndo;
	}
	
	public Command(Model m) {
		this(m, true);
	}
	
	public boolean isUndoable() {
		return undoable;
	}

	public void execute() throws AppException {
		if (undoable) {
			someMemento = theModel.makeMemento();
		}
	}
	
	public void undo() {
		if (undoable && someMemento != null) {
			theModel.restore(someMemento);
		}
	}
}
