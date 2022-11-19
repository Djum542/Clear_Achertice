package customization1;

import framework.*;


public class StopLight extends Model {
	private static final long serialVersionUID = 1L;

	public enum State {GO, SLOW, STOP}
	private State state = State.STOP;
	public void changeState() throws AppException {
		if (state.equals(State.GO)) {
			state = State.SLOW;
		} else if (state.equals(State.SLOW)){
			state = State.STOP;
		} else if (state.equals(State.STOP)) {
			state = State.GO;
		} else {
			throw new AppException("Invalid state");
		}
		setChanged();
	}
	
	public State getState() { return state; }
	
	private class StopLightMemento implements Memento {
		private State savedState;
		public StopLightMemento() {
			savedState = state;
		}
	}
	
	public Memento makeMemento() {
		return new StopLightMemento();
	}
	
	public void restore(Memento m) {
		state = ((StopLightMemento)m).savedState; //getState();
		setChanged();
	}
}
