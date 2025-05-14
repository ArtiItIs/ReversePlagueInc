import javax.swing.*;
import java.awt.*;

public class MyFrame extends JFrame {

    public MyFrame(JPanel panel){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(new Dimension(500, 500));
        this.setLocationRelativeTo(null);
        this.add(panel);
        this.setTitle("Reverse Plague Inc.");
        this.setVisible(true);
    }
}
