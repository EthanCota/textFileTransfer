import java.awt.*;
import java.awt.Toolkit.*;
import java.awt.event.*;
import java.awt.datatransfer.StringSelection;
import javax.swing.*;
import java.util.*;

public class ServerGUI extends JFrame
{
    private ArrayList < String > strList = new ArrayList < String >(Arrays.asList("trash", "pause", "play", "exit"));
    private ArrayList < ImageIcon > icoList = new ArrayList < ImageIcon >();
    public ServerDaemon daemon;
    public JTextArea infoArea;
    private JButton pauseB, playB, exitB, trashB;
    private ServerGUI currentGUI;
    private Image img, newImg;

    public ServerGUI()
    {
        //sets GUI details
        int scrW =(int) Math.round(Toolkit.getDefaultToolkit().getScreenSize().getWidth()) - 400;
        this.setSize(400, 200);
        this.setLocation(scrW, 0);
        this.setUndecorated(true);
        this.getRootPane().setWindowDecorationStyle(JRootPane.NONE);

        //creates panel
        JPanel panel = new JPanel();
        panel.setBackground(Color.white);

        //creates title label
        JLabel title = new JLabel("          Server Interface  ");
        panel.setBorder(BorderFactory.createLineBorder(Color.black));
        panel.add(title);

        //loop creates icons
        for(int i = 0; i < 4; i++)
        {
            icoList.add(new ImageIcon("icons\\" + strList.get(i) + ".png"));
            img = icoList.get(i).getImage();
            newImg = img.getScaledInstance(15, 15, java.awt.Image.SCALE_SMOOTH);
            icoList.get(i).setImage(newImg);
        }

        //defines buttons
        trashB = new JButton(icoList.get(0));
        pauseB = new JButton(icoList.get(1));
        playB = new JButton(icoList.get(2));
        exitB = new JButton(icoList.get(3));

        //loop adds buttons
        ArrayList < JButton > bList = new ArrayList < JButton >(Arrays.asList(pauseB, playB, trashB, exitB));
        for(JButton x :
                bList)
        {
            x.setBorder(BorderFactory.createEmptyBorder());
            x.setContentAreaFilled(false);
            x.addActionListener(new ListenForButton());
            panel.add(x);
        }

        infoArea = new JTextArea("", 10, 33);
        infoArea.setLineWrap(true);
        infoArea.setWrapStyleWord(true);
        infoArea.setEditable(false);
        panel.add(infoArea);
        JScrollPane infoAreaScrollbar = new JScrollPane(infoArea, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        panel.add(infoAreaScrollbar);

        //creates GUI
        this.add(panel);
        this.setVisible(true);
        currentGUI = this;
    }

    public static void main(String args[])
    {
        new ServerGUI();
    }
    private class ListenForButton extends JFrame implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            if(e.getSource() == playB)
            {
                daemon = new ServerDaemon();
                daemon.saveGUI(currentGUI);
            }
            else if(e.getSource() == pauseB)
            {
                try
                {
                    daemon.socketOnWhichToListenForClients.close();
                }
                catch(Exception ex)
                {
                    System.out.println("Error: " + ex.toString());
                }
            }
            else if(e.getSource() == exitB)
            {
                try
                {
                    daemon.socketOnWhichToListenForClients.close();
                    System.exit(0);
                }
                catch(Exception ex)
                {
                    System.out.println("Error: " + ex.toString());
                }
            }
            else if(e.getSource() == trashB)
            {
                infoArea.setText("");
            }
        }
    }
}
