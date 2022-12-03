
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.swing.*;

public class AppPanel extends JPanel implements ActionListener {
//khai báo model,view
    private Stoplight model;
    private StoplightView view;
    //private StopLightController controller;
    private JFrame frame;
    public static int FRAME_WIDTH = 500;
    public static int FRAME_HEIGHT = 300;

    public AppPanel() {
// tao ra phương thức stoplight, tao ra phương thức JPanel, stoplightview.
        model = new Stoplight();
        JPanel controlPanel = new JPanel();
        view = new StoplightView(model);
        //controller = new StopLightController(model);
        // tao ra bang chứa có 1 hàng và hai cột.
        setLayout((new GridLayout(1, 2)));
        // dùng Jpanel tao ra bộ đệm kép và bố cục luồng.
        add(controlPanel);
        // gán lóp hiển thi vào.
        add(view);
        //thiết đặt nền cho controlPanel và view
        controlPanel.setBackground(Color.PINK);
        view.setBackground(Color.GRAY);
        // tao ra giao diện button chứa tên của chức năng cần thiết đặt cho ứng dụng.
        JButton changeButton = new JButton("Change");
        //changeButton.addActionListener(controller);
        changeButton.addActionListener(this);
        controlPanel.add(changeButton);
        // khoi tao JFrame
        frame = new JFrame();
        // hien thi ra giao diện do JFrame cung cấp.
        Container cp = frame.getContentPane();
        cp.add(this);
        // Truyen menu vào
        frame.setJMenuBar(createMenuBar());
        // cai dat viecj dóng giao diện
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // thiết lập tiêu đề
        frame.setTitle("Stoplight Simulator 1.1");
        // thiết đặt kích co cho cửa sổ
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
    }
    // tao ra menu cho trình gia lập.
    protected JMenuBar createMenuBar() {
        JMenuBar result = new JMenuBar();
        // add file, edit, and help menus
        JMenu fileMenu =
                Utilities.makeMenu("File", new String[] {"New",  "Save", "SaveAs", "Open", "Quit"}, this);
        result.add(fileMenu);

        JMenu editMenu =
                Utilities.makeMenu("Edit", new String[] {"Change"}, this);
        result.add(editMenu);

        JMenu helpMenu =
                Utilities.makeMenu("Help", new String[] {"About", "Help"}, this);
        result.add(helpMenu);

        return result;
    }

    public void display() { frame.setVisible(true); }
    // tao ra phương thức để lắng nghe các sự kiện tư người dùng
    public void actionPerformed(ActionEvent ae) {
        String cmmd = ae.getActionCommand();
        if (cmmd == "Save") {
            try {
                //String fName = Utilities.ask("File Name?");
                String fName = Utilities.getFileName(null, false);
                ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(fName));
                os.writeObject(model);
                os.close();
            } catch (Exception err) {
                Utilities.error(err);
            }
        } else if (cmmd == "SaveAs") {
            //Utilities.save(model, true);
        } else if (cmmd == "Open") {
            try {
                String fName = Utilities.getFileName(null, true);
                ObjectInputStream is = new ObjectInputStream(new FileInputStream(fName));
                //model.removePropertyChangeListener(this);
                model = (Stoplight)is.readObject();
                //this.model.initSupport();
                //model.addPropertyChangeListener(this);
                view.setModel(model);
                is.close();
            } catch (Exception err) {
                Utilities.error(err.getMessage());
            }
        } else if (cmmd == "New") {
            model = new Stoplight();
            view.setModel(model);
        } else if (cmmd == "Quit") {
            //Utilities.saveChanges(model);
            System.exit(1);
        } else if (cmmd == "About") {
            Utilities.inform("Cyberdellic Designs Stoplight Simulator 1.1, 2020. All rights reserved.");
        } else if (cmmd == "Help") {
            Utilities.inform("Click or select change to change the light");
        } else if (cmmd == "Change") {
            model.change();
        } else  {
            Utilities.error("Unrecognized command: " + cmmd);
        }
    }
    public static void main(String[] args) {
    AppPanel app = new AppPanel();
    app.display();
}
}