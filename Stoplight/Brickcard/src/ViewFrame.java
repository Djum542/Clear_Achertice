import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JInternalFrame;
import javax.swing.text.View;

public class ViewFrame extends JInternalFrame{
    private static final long serialVersionUID = 1L;
	public static int openFrameCount = 0;
	public ViewFrame(Container view) {
        super("View #" + (++openFrameCount),
              true, //resizable
              true, //closable
              true, //maximizable
              true);//iconifiable
              setContentPane(view);
        setLocation(30 * openFrameCount, 30 * openFrameCount);
        setMinimumSize(new Dimension(50, 50));
        //pack();
	}
}
