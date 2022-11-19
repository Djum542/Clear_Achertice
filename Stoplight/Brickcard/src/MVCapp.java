import javax.swing.JDesktopPane;
import javax.swing.JPanel;

import MVC.Model;


public class MVCapp extends JPanel implements ActionListenerr{
    private static final long serialVersionUID = 1L;
	private JDesktopPane desktop;
	private AppFactory factory;
	private Model model;
	private CommandProcessor commandProcessor;

	public MVCApp(AppFactory factory) {

		this.factory = factory;
		this.model = factory.makeModel();
		this.commandProcessor = CommandProcessor.makeCommandProcessor();

		setDefaultLookAndFeelDecorated(true);
		desktop = new JDesktopPane(); //a specialized layered pane

		int inset = 50;
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds(inset, inset,screenSize.width  - inset * 2,screenSize.height - inset * 2);

		setContentPane(desktop);
		setJMenuBar(createMenuBar());

		//Make dragging a little faster but perhaps uglier.
		desktop.setDragMode(JDesktopPane.OUTLINE_DRAG_MODE);

		//create first "window"
		showView(null);
	}

	protected JMenuBar createMenuBar() {
	   // create a menu bar containing File, Edit, View, and Help menus
		JMenuBar result = new JMenuBar();
		result.add(Utilities.makeMenu("F&ile", new String[] {"New", "Open", "Save", "SaveAs", "Quit"}, this));
		// create Edit, View, and Help menus
		return result;
	}

	public void actionPerformed(ActionEvent e){
    	String cmmd = e.getActionCommand();
    	if (cmmd == "Save") {
    		Utilities.save(model, false);
    	} else if (cmmd == "SaveAs") {
    		Utilities.save(model, true);
    	} else if (cmmd == "Open") {
    		Utilities.open(model);
    		for(JInternalFrame frame: desktop.getAllFrames()) {
    			frame.setVisible(false);
    		}
    		showView(null);
    	} else if (cmmd == "New") {
    		// save changes, close all views, create a new model, and open a first view
    	} else if (cmmd == "Quit") {
    		Utilities.saveChanges(model);
    		System.exit(1);
    	} else if (cmmd == "Help") {
    		// informer user of available commands and views
    	} else if (cmmd == "About") {
    		// inform user of application name, version, and company
    	} else {
    		Utilities.error("Unrecognized command: " + cmmd);
    	}
    }

	private void showView(String type) {
		if (type == null) type = factory.getViews().get(0);
		View view = factory.makeView(type);
		view.setModel(model);
		ViewFrame vf = new ViewFrame(view);
		vf.setSize(200, 100);
		vf.setTitle(type);
		/*
		 * I added a boolean packable field to View with default value false.
		 * I set it to true for views that contain controls (like text fields)
		 * rather than graphics. A bit of a hack, but avoids a major redesign.
		 */
		if (view.getPackable()) {
			vf.pack();
		}
		vf.setVisible(true);
		desktop.add(vf);
		try {
			vf.setSelected(true);
		} catch(Exception e) {
			Utilities.error(e);
		}
	}

	class ViewHandler implements ActionListener {
		public void actionPerformed(ActionEvent e){
			String cmmd = e.getActionCommand();
			showView(cmmd);
		}
	}

    class EditHandler implements ActionListener {
    	public void actionPerformed(ActionEvent e){
    		String cmmd = e.getActionCommand();
    		 if (cmmd == "Undo") {
    	    		commandProcessor.undo();
    	     } else if (cmmd == "Redo") {
    	    		commandProcessor.redo();
    	    } else {
    	    	// create a command, set its model, then execute it
    	    }
    	}
    }

    public static void run(AppFactory factory) {
    	try {
    		MVCApp app = new MVCApp(factory);
    		app.setSize(800,600);
			app.setTitle(factory.getTitle());
			app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			app.setVisible(true);
    	} catch(Exception e) {
    		Utilities.error("" + e);
    	}

    }

}
