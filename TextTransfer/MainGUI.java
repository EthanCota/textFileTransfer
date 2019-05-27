import java.awt.*;
import java.awt.Toolkit.*;
import java.awt.event.*;
import java.awt.datatransfer.StringSelection;
import javax.swing.*;
import java.util.*;

public class MainGUI extends JFrame {
    private ArrayList < String > strList = new ArrayList < String >(Arrays.asList("sort","edit","exit"));
    private ArrayList < ImageIcon > icoList = new ArrayList < ImageIcon >();
    public FileGUI fileGUI = new FileGUI();
    public TextGUI textGUI = new TextGUI();
    private JButton fileB, textB, exitB;
    private int int1, int2, int3;
    private JFrame currentGUI;
    private Image img, newImg;

    public MainGUI() {
        //sets GUI details
        this.setSize(135, 50);
        this.setLocation(0, 0);
        this.setUndecorated(true);
        this.getRootPane().setWindowDecorationStyle(JRootPane.NONE);

        //creates panel
        JPanel panel = new JPanel();
        panel.setBackground(Color.white);

        //creates title label
        JLabel title = new JLabel("Text File Transfer     ");
        panel.setBorder(BorderFactory.createLineBorder(Color.black));
        panel.add(title);

        //loop creates icons
        for(int i = 0; i < 3; i++) {
            icoList.add(new ImageIcon("icons\\" + strList.get(i) + ".png"));
            img = icoList.get(i).getImage();
            newImg = img.getScaledInstance(15, 15, java.awt.Image.SCALE_SMOOTH);
            icoList.get(i).setImage(newImg);
        }

        //defines buttons
        fileB = new JButton(icoList.get(0));
        textB = new JButton(icoList.get(1));
        exitB = new JButton(icoList.get(2));

        //loop adds buttons
        ArrayList < JButton > bList = new ArrayList < JButton >(Arrays.asList(fileB, textB, exitB));
        for(JButton x : bList) {
            x.setBorder(BorderFactory.createEmptyBorder());
            x.setContentAreaFilled(false);
            x.addActionListener(new ListenForButton());
            panel.add(x);
        }

        //creates GUI
        this.add(panel);
        this.setVisible(true);
        currentGUI = this;
    }
    
    private class ListenForButton extends JFrame implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == fileB) {
                if(!fileGUI.isVisible()) {
                    fileGUI.setVisible(true);
                } else fileGUI.setVisible(false);
            } else if(e.getSource() == textB) {
                if(!textGUI.isVisible()) {
                    textGUI.setVisible(true);
                } else textGUI.setVisible(false);
            } else if(e.getSource() == exitB) {
                System.exit(0);
            }
        }
    }
}