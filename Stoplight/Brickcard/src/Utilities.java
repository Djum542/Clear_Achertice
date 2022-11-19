import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import MVC.Model;

public class Utilities {
    public static JMenu makeMenu(String name, String[] items, ActionListener handler) {

		JMenu result;
		int j = name.indexOf('&');
		if ( j != -1) {
			char c = name.charAt(j - 1);
			String s = name.substring(0, j) + name.substring(j + 1);
			result = new JMenu(s);
			result.setMnemonic(c);
		} else {
			result = new JMenu(name);
		}

		for(int i = 0; i < items.length; i++) {
			if (items[i] == null) {
				result.addSeparator();
			} else {
				j = items[i].indexOf('&');
				JMenuItem item;
				if ( j != -1) {
					char c = items[i].charAt(j + 1);
					String s = items[i].substring(0, j) +
							items[i].substring(j + 1);
					item = new JMenuItem(s, items[i].charAt(j + 1));
					item.setAccelerator(
							KeyStroke.getKeyStroke(c, InputEvent.CTRL_MASK));
				} else { // no accelerator or shortcut key
					item = new JMenuItem(items[i]);
				}
				item.addActionListener(handler);
				result.add(item);
			}
			//result.addMenuListener(this);
		}
		return result;
	}

	public static String askUser(String query) {
		return JOptionPane.showInputDialog(query);
	}
	/*
   public static String getFile(String query) {
   	   return JFileChooser
   }
	 */


	public static boolean confirm(String query) {
		int result = JOptionPane.showConfirmDialog(null,
				query, "choose one", JOptionPane.YES_NO_OPTION);
		return result == 0; // or 1?
	}

	public static void error(String gripe) {
		JOptionPane.showMessageDialog(null,
				gripe,
				"OOPS!",
				JOptionPane.ERROR_MESSAGE);
	}

	public static void error(Exception gripe) {
		gripe.printStackTrace();
		JOptionPane.showMessageDialog(null,
				gripe.toString(),
				"OOPS!",
				JOptionPane.ERROR_MESSAGE);
	}

	public static void informUser(String info) {
		JOptionPane.showMessageDialog(null, info,
				"information", JOptionPane.INFORMATION_MESSAGE);
	}

	public static void saveChanges(Model model) {
		if (model.hasUnsavedChanges() && Utilities.confirm("current model has unsaved changes, continue?"))
			Utilities.save(model, false);
	}

	public static void open(Model model) {
		saveChanges(model);
		String fName = model.getFileName();
		JFileChooser chooser = new JFileChooser();
		if (fName != null) {
				chooser.setCurrentDirectory(new File(fName));
		}
		int returnVal = chooser.showOpenDialog(null);
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			fName = chooser.getSelectedFile().getPath();
		}
		try {
			ObjectInputStream is = new ObjectInputStream(new FileInputStream(fName));
			model = (Model)is.readObject();
			is.close();
		} catch (Exception err) {
			Utilities.error(err.getMessage());
		}
	}

	public static void save(Model model, Boolean saveAs) {
		String fName = model.getFileName();
		if (fName == null || saveAs) {
			JFileChooser chooser = new JFileChooser();
			if (fName != null) {
				chooser.setCurrentDirectory(new File(fName));
			}
			int returnVal = chooser.showOpenDialog(null);
			if(returnVal == JFileChooser.APPROVE_OPTION) {
				fName = chooser.getSelectedFile().getPath();
				model.setFileName(fName);
			}
		}
		try {
			ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(fName));
			os.writeObject(model);
			model.setUnsavedChanges(false);
			os.close();
			model.setUnsavedChanges(false);
		} catch (Exception err) {
			Utilities.error(err.getMessage());
		}
	}
}
