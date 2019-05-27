import java.awt.*;
import java.awt.Toolkit.*;
import java.awt.event.*;
import java.awt.datatransfer.*;
import javax.swing.*;
import java.util.*;

public class TextGUI extends JFrame
{
    public static String text;
    public static boolean saved, refreshed;
    private JButton exitB, refreshB, trashB, saveB, bookmarkB;
    public JTextArea editor;
    private ArrayList<ImageIcon> icoList = new ArrayList<ImageIcon>();
    private ArrayList<String> strList = new ArrayList<String>(Arrays.asList("refresh","trash","save","bookmark","exit"));
    private Image img, newImg;
    public JFrame currentGUI;

    public TextGUI()
    {
        
        //GUI details
        this.setSize(135,150);       
        this.setLocation(0, 49);
        this.setUndecorated(true);
        this.getRootPane().setWindowDecorationStyle(JRootPane.NONE);

        //creates panel
        JPanel panel = new JPanel();
        panel.setBackground(Color.white);
        panel.setBorder(BorderFactory.createLineBorder(Color.black));

        //creates title label
        JLabel title = new JLabel("         Text Transfer         ");
        panel.add(title);

        //loop creates icons
        for(int i = 0; i < 5; i++)
        {
            icoList.add(new ImageIcon("icons\\" + strList.get(i) + ".png"));
            img = icoList.get(i).getImage();
            newImg = img.getScaledInstance(15, 15, java.awt.Image.SCALE_SMOOTH);
            icoList.get(i).setImage(newImg);
        }

        //defines buttons
        refreshB = new JButton(icoList.get(0));
        trashB = new JButton(icoList.get(1));
        saveB = new JButton(icoList.get(2));
        bookmarkB = new JButton(icoList.get(3));
        exitB = new JButton(icoList.get(4));

        //loop adds buttons
        ArrayList<JButton> bList = new ArrayList<JButton>(Arrays.asList(refreshB,trashB,saveB,exitB));
        for(JButton x : bList)
        {
            x.setBorder(BorderFactory.createEmptyBorder());
            x.setContentAreaFilled(false);
            x.addActionListener(new ListenForButton());
            panel.add(x);
        }

        //creates text area with scrollbar
        editor = new JTextArea(5, 10);
        editor.setLineWrap(true);
        editor.setWrapStyleWord(true);
        panel.add(editor);
        JScrollPane editorScrollbar = new JScrollPane(editor, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        panel.add(editorScrollbar);
        text = editor.getText();

        //creates GUI
        this.add(panel);
        this.setVisible(false);
        currentGUI=this;
    }
    
    private class ListenForButton implements ActionListener
    {
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == refreshB)
            {
                refreshed = true;
            } 
            else if(e.getSource() == exitB)
            {
                currentGUI.setVisible(false);
            } 
            else if(e.getSource() == trashB)
            {
                editor.setText(null);
            } 
            else if(e.getSource() == saveB)
            {
                saved = true;
                text = editor.getText();
            }
            else if(e.getSource() == bookmarkB) 
            {
                String str = editor.getText();
                StringSelection strSelect = new StringSelection(str);
                Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();
                cb.setContents(strSelect, null);
            }
        }
    }
}