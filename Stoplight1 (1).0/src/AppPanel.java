
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class AppPanel extends JPanel implements ActionListener {

    private Stoplight model;
    private StoplightView view;
    private JFrame frame;
    public static int FRAME_WIDTH = 500;
    public static int FRAME_HEIGHT = 300;

    public AppPanel() {

        model = new Stoplight();
        JPanel controlPanel = new JPanel();
        view = new StoplightView(model);

        setLayout((new GridLayout(1, 2)));
        add(controlPanel);
        add(view);

        controlPanel.setBackground(Color.PINK);
        view.setBackground(Color.GRAY);

        JButton changeButton = new JButton("Change");
        changeButton.addActionListener(this);
        controlPanel.add(changeButton);

        frame = new JFrame();
        Container cp = frame.getContentPane();
        cp.add(this);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Stoplight Simulator");
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
    }

    public void display() { frame.setVisible(true); }

    public void actionPerformed(ActionEvent ae) {
        String cmmd = ae.getActionCommand();
        if (cmmd == "Change") {
            if (model != null) {
                model.change();
            } else {
                System.out.println("no model?");
            }
        }
    }

    public static void main(String[] args) {
        AppPanel app = new AppPanel();
        app.display();
    }

}



