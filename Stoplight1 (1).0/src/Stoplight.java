
import tools.Publisher;
import java.awt.Color;


public class Stoplight extends   Publisher {

    private Color color = Color.GREEN;

    public Color getColor() {
        return color;
    }

    public void change() {
        if (color.equals(Color.GREEN)) color = Color.YELLOW;
        else if (color.equals(Color.YELLOW)) color = Color.RED;
        else if (color.equals(Color.RED)) color = Color.GREEN;
        notifySubscribers();
    }

}
