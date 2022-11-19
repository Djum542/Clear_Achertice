import java.util.Observer;

import javax.swing.JFrame;

import MVC.Model;

public class View extends JFrame implements Observer{
    protected Model model;
	
	private Boolean packable;

	public View(Model model) {
		super();
		this.model = model;
		model.addObserver(this);
		this.update(model, null);
		packable = false;
	}
	
	public Boolean getPackable() {
		return packable;
	}

	public void setPackable(Boolean packable) {
		this.packable = packable;
	}

	public View() {
		super();
		packable = false;
	}

	public Model getModel() {
		return model;
	}
	
	public void setModel(Model model) {
		this.model = model;
		model.addObserver(this);
		this.update(model, null);
	}
}
