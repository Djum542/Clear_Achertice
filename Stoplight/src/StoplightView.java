
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class StoplightView extends JPanel implements PropertyChangeListener {

    private Stoplight model;
    private int diameter; // of the stoplight
    private int row, col; // x & y coordinates of upper-left corner of bounding box

    public StoplightView(Stoplight model) {
        this.model = model;
        //model.subscribe(this);
        model.addPropertyChangeListener(this);
        diameter = 20;
        row = 100;
        col = 100;
    }

    public void setModel(Stoplight model){
        this.model.removePropertyChangeListener(this);
        this.model = model;
        this.model.initSupport();
        this.model.addPropertyChangeListener(this);
        repaint();
    }

    public void paintComponent(Graphics gc) {
        super.paintComponent(gc);
        Color oldColor = gc.getColor();
        gc.setColor(model.getColor());
        gc.fillOval(row, col, diameter, diameter);
        gc.setColor(oldColor);
    }

    @Override
    public void propertyChange(PropertyChangeEvent arg0) {
        repaint();
    }
}


