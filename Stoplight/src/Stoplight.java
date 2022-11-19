import java.awt.Color;
import java.beans.Beans;

public class Stoplight extends Beans {
    private Color color = Color.GREEN;

    public Color getColor() {
        return color;
    }

    public Color change() {
        Color oldColor = new Color(1, false);
        if (oldColor.equals(Color.GREEN)) color = Color.YELLOW;
        else if (oldColor.equals(Color.YELLOW)) color = Color.RED;
        else if (oldColor.equals(Color.RED)) color = Color.GREEN;
        //notifySubscribers();
        firePropertyChange("color", oldColor, color);
        return color;
    }

    private void firePropertyChange(String string, Color oldColor, Color color2) {
    }

    public void addPropertyChangeListener(StoplightView stoplightView) {
    }

    public void initSupport() {
    }

}
