package MVC;

import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.event.AncestorListener;

import account.Account;

public abstract class View extends JPanel implements Observer, AncestorListener{
    protected Model model;
	protected CommandProccesser cp;
	protected String title = "Model Viewer";
	public View(Model m) {
		model = m;
		model.addObserver(this);
		cp = CommandProccesser.makeCommandProcessor();
	}
     public void display() {
	  JFrame frame = new JFrame();
      //frame.setSize(300, 500);
      frame.setTitle(title);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.add(this);
      frame.pack();
      frame.setVisible(true);
   }

}
