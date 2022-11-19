import java.awt.Color;
import java.util.concurrent.Flow.Publisher;
import java.util.concurrent.Flow.Subscriber;

public class Stoplight implements Publisher {
    private Color cloor = Color.green;
    public Color getColor(){
        return getColor();
    }
    public void change(){
        if (getColor().equals(cloor)) {
            cloor = Color.yellow;
        }else if (getColor().equals(cloor)) {
            cloor = Color.red;
        }
    }
    public void subscribe(StoplightView stoplightView) {
    }
    @Override
    public void subscribe(Subscriber subscriber) {
        // TODO Auto-generated method stub
        
    }
}
