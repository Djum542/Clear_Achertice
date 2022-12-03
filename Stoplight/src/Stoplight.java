

import java.awt.Color;

public class Stoplight extends Bean {
    private Color color = Color.GREEN;

    public Color getColor() {
        return color;
    }

    public void change() {
        Color oldColor = color;
        if (color.equals(Color.GREEN)) color = Color.YELLOW;
        else if (color.equals(Color.YELLOW)) color = Color.RED;
        else if (color.equals(Color.RED)) color = Color.GREEN;
        //notifySubscribers();
        firePropertyChange("color", oldColor, color);
    }

}

