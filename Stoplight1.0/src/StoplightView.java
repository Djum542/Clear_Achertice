import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;
import javax.swing.JPanel;

public class StoplightView extends JPanel implements Subscriber {

    @Override
    public void onSubscribe(Subscription subscription) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onNext(Object item) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onError(Throwable throwable) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onComplete() {
        // TODO Auto-generated method stub
        
    }
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

   
    public void update() {
        repaint();
    }
}
