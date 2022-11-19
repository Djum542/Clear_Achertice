import MVC.Model;

public class SetHeight extends Command{
    private int newHeight;

	public SetHeight(Brick b, int newHeight) {
		super(b);
		this.newHeight = newHeight;
		undoable = true; // added
	}

	public void execute() throws Exception {
		super.execute();
		// ???
	}
}

class Brick extends Model {

	private int height, width, length;

	public Brick(int h, int w, int l) {
		height = h;
		width = w;
		length = l;
	}

	private class BrickMemento implements Memento {
		// ???
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int h) {
		// change height & notify observers (a little tricky)

	}

	public void accept(Memento m) {
		// restore state & notify observers
	}

	public Memento makeMemento() {
		return null; // for now
	}
}

class HeightView implements Observer {

	public void update(Observable subject, Object msg) {
			if (subject instanceof Brick) {
				System.out.println("height = " + ((Brick)subject).getHeight());
			}
	}

}

public class BrickCAD {
	public static void main(String args[]) {
		try {
			Brick brick = new Brick(10, 20, 30);
			HeightView view = new HeightView();
			brick.addObserver(view);
			Command cmmd = new SetHeight(brick, 15);
			cmmd.execute();
			cmmd.undo();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
