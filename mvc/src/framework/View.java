package framework;

import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;

public class View extends JPanel implements Observer {
	
	private static final long serialVersionUID = 1L;
	protected String title = "App Framework";
	protected Model theModel;
	protected JMenuBar menuBar;
	protected JMenu editMenu;
	protected CommandProcessor theCommandProcessor;
	protected int windowHeight = 0, windowWidth = 0;
	protected EditController ec;
	
	public View(Model m) {
		theModel = m;
		theModel.addObserver(this);
		menuBar = new JMenuBar();
		theCommandProcessor = new CommandProcessor();
		FileController fc = new FileController();
		JMenu fileMenu = GUIUtils.makeMenu("&File", new String[] {"&New", "&Open", "&Save", "Save&As", "&Exit"}, fc);
		menuBar.add(fileMenu);
		ec = new EditController();
		editMenu = GUIUtils.makeMenu("&Edit", new String[] {"&Undo", "&Redo"}, ec);
		menuBar.add(editMenu);
	}
	
	/**
	 * The following three methods should be overridden in the subclass
	 */

	public void update(Observable arg0, Object arg1) {
		
	}
	
	public Model makeModel() {
		return new Model();
	}
	
	public Command makeCommand(String cmmd) {
		return new Command(theModel);
	}

	/**
	 * Utility for displaying this panel
	 */
	public void display() {
		 JFrame frame = new JFrame();
		 frame.setTitle(title);
		 frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 frame.add(this);
		 frame.setJMenuBar(menuBar);
		 if (windowHeight <= 0 || windowWidth <= 0) {
			 frame.pack();
		 } else {
			 frame.setSize(windowWidth, windowHeight);
		 }
		 frame.setVisible(true);
	}

	
/**
 * 
 * Menu listeners
 *
 */
	public class EditController implements ActionListener {
		public void actionPerformed(ActionEvent ae) {
			try {
				String ac = ae.getActionCommand();
				System.out.println("COMMAND = " + ac);
				if (ac.equals("Undo")) {
					theCommandProcessor.undo();
				} else if (ac.equals("Redo")) {
					theCommandProcessor.redo();
				} else {
					Command cmmd = makeCommand(ac);
					theCommandProcessor.execute(cmmd);
				}
					
			} catch(AppException ax) {
				GUIUtils.error(ax.getMessage());
			}
			
		}
	}
	
	
	public class FileController implements ActionListener {

		/**
		 * memory for the last directory opened
		 */
		private File durrentDir;

		public FileController() {
			File f = theModel.getFile();
			if (f != null) {
				durrentDir = f.getParentFile();
			}
		}

		/**
		 * Asks user if model should be saved
		 * @return true if yes
		 */
		private boolean saveChanges() {
			if (!theModel.isChanged()) return false;
			int choice = JOptionPane.showConfirmDialog(View.this, "save changes?");
			return choice == 0;
		}

		/**
		 * Prompts user for file to be saved or open
		 * @return the selected file
		 */
		private File getFile() {
			 JFileChooser chooser = new JFileChooser();
			 if (durrentDir != null) {
				 chooser.setCurrentDirectory(durrentDir);
			 }
			 chooser.showOpenDialog(View.this);
			 File result = chooser.getSelectedFile();
			 durrentDir = result.getParentFile();
			 return result;
		}

		/**
		 * Replaces existing model with model read from selected file.
		 */
		private void open() {
			File f = getFile();
			try {
				ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
				theModel.deleteObserver(View.this);
				theModel = (Model)ois.readObject();
				theModel.addObserver(View.this);
				theCommandProcessor.reset();
				theModel.setChanged(true);
				theModel.setChanged();
				theModel.setChanged(false);
			} catch(Exception e) {
				GUIUtils.error(e.getMessage());
			}
		}
		
		private void newModel() {
			try {
				theModel.deleteObserver(View.this);
				theModel = makeModel();
				theModel.addObserver(View.this);
				theCommandProcessor.reset();
				theModel.setChanged(true);
				theModel.setChanged();
				theModel.setChanged(false);
			} catch(Exception e) {
				GUIUtils.error(e.getMessage());
			}
		}

		/**
		 * Saves model to selected file.
		 * @param saveAs prompt user for new file if true
		 */
		private void save(boolean saveAs) {
			if (!saveAs && !theModel.isChanged()) return; // nothing to do
			File f = theModel.getFile();
			if (f == null || saveAs) {
				f = getFile();
				theModel.setFile(f);
			}
			try {
				ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));
				oos.writeObject(theModel);
				theModel.setChanged(false);
			} catch(Exception e) {
				GUIUtils.error(e.getMessage());
			}
		}

		public void actionPerformed(ActionEvent e) {
			String cmmd = e.getActionCommand();
			if (cmmd.equals("Exit")) {
				if (saveChanges()) save(false);
				System.exit(0);
			} else if (cmmd.equals("Save")) {
				save(false);

			} else if (cmmd.equals("SaveAs")) {
				save(true);

			} else if (cmmd.equals("New")) {
				if (saveChanges()) save(false);
				newModel();
			} else if (cmmd.equals("Open")) {
				if (saveChanges()) save(false);
				open();

			} else {
				GUIUtils.error("unrecognized command: " + cmmd);
			}
		}

	}
}
