package customization1;

import java.awt.*;
import java.awt.event.*;
import java.util.Observable;

import javax.swing.*;
import framework.*;

public class StopLightView extends View {
	
	public StopLightView(StopLight model) {
		super(model);
		title = "Stop Light Simulation";
		windowHeight = 300;
		windowWidth = 300;
		JMenuItem changeItem = new JMenuItem("Change");
		changeItem.addActionListener(ec);
		editMenu.add(changeItem);
	}
	
	public void paintComponent(Graphics gc) {
		StopLight model = (StopLight)theModel;
		StopLight.State state = model.getState();
		
		if (state.equals(StopLight.State.STOP)) {
			gc.setColor(Color.RED);
		} else {
			gc.setColor(Color.BLACK);
		}
		gc.fillOval(10, 10, 10, 10);
		if (state.equals(StopLight.State.SLOW)) {
			gc.setColor(Color.YELLOW);
		} else {
			gc.setColor(Color.BLACK);
		}
		gc.fillOval(10, 30, 10, 10);
		if (state.equals(StopLight.State.GO)) {
			gc.setColor(Color.GREEN);
		} else {
			gc.setColor(Color.BLACK);
		}
		gc.fillOval(10, 50, 10, 10);
	}
	
	// three oveerrides:
	public Model makeModel() {
		return new StopLight();
	}
	
	public Command makeCommand(String cmmd) {
		System.out.println("COMMAND = " + cmmd);
		if (cmmd.equals("Change")) {
			return new ChangeCommand((StopLight)theModel);
		} else {
			return null;
		}
	}
	
	public void update(Observable arg0, Object arg1) {
		repaint();
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		StopLightView gui = new StopLightView(new StopLight());
		gui.display();

	}
}
