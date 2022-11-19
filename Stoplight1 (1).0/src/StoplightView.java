
import tools.Subscriber;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;

public class StoplightView extends JPanel implements Subscriber {

    private Stoplight model;
    private int diameter; // of the stoplight
    private int row, col; // x & y coordinates of upper-left corner of bounding box

    public StoplightView(Stoplight model) {
        this.model = model;
        model.subscribe(this);
        diameter = 20;
        row = 100;
        col = 100;
    }

    public void paintComponent(Graphics gc) {
        super.paintComponent(gc);
        Color oldColor = gc.getColor();
        gc.setColor(model.getColor());
        gc.fillOval(row, col, diameter, diameter);
        gc.setColor(oldColor);
    }

    @Override
    public void update() {
        repaint();
    }
}

