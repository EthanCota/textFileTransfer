import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.awt.*; 
import java.io.*;
import java.nio.file.*;

public class FileGUI extends JFrame {

    //Creating icons for buttons
    private ArrayList<ImageIcon> icoList = new ArrayList<ImageIcon>();
    private ArrayList<String> strList;
    private Image img, newImg;

    //Creating JFrame stuff
    private JRadioButton f1;
    private JButton exB, upB, dwB;
    private JFrame currentGUI;
    private JTextArea editor;

    //Creating File stuff
    public File file, file1;
    public Path file1Path;
    public boolean anyReady, downloadClicked;
    private JFileChooser fc;

    public FileGUI() {

        //GUI details
        this.setSize(120,199);
        this.setLocation(134,0);
        this.setUndecorated(true);
        this.getRootPane().setWindowDecorationStyle(JRootPane.NONE);

        //Creates JPanel
        JPanel panel = new JPanel();
        panel.setBackground(Color.white);
        panel.setBorder(BorderFactory.createLineBorder(Color.black));

        //Creates JLabel
        JLabel title = new JLabel("         File Transfer         ");
        panel.add(title);

        //Loop to create Images
        strList = new ArrayList<String>(Arrays.asList("upload","download","exit"));
        for(int i = 0; i < 3; i++) {
            icoList.add(new ImageIcon("icons\\" + strList.get(i) + ".png"));
            img = icoList.get(i).getImage();
            newImg = img.getScaledInstance(15, 15, java.awt.Image.SCALE_SMOOTH);
            icoList.get(i).setImage(newImg);
        }

        //Initializes JButtons
        upB = new JButton(icoList.get(0));
        dwB = new JButton(icoList.get(1));
        exB = new JButton(icoList.get(2));

        //Loop adds JButtons
        ArrayList<JButton> bList = new ArrayList<JButton>(Arrays.asList(upB,dwB,exB));
        for(JButton x : bList) {
            x.setBorder(BorderFactory.createEmptyBorder());
            x.setContentAreaFilled(false);
            x.addActionListener(new ListenForButton());
            panel.add(x);
        }

        //Initializes JRadioButtons and ButtonGroup
        f1 = new JRadioButton(" File 1");
        ButtonGroup fileGroup = new ButtonGroup();

        //Adds JRadioButtons and ButtonGroup for file selection
        ArrayList<JRadioButton> rList = new ArrayList<JRadioButton>(Arrays.asList(f1));
        for(JRadioButton y : rList) {
            y.setBorder(BorderFactory.createEmptyBorder());
            y.setContentAreaFilled(false);
            fileGroup.add(y);
            panel.add(y);
        }

        //Starts up GUI
        this.add(panel);
        this.setVisible(false);
        currentGUI=this;
    }

    private class ListenForButton implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == exB) {
                currentGUI.setVisible(false);
            } 

            if(e.getSource() == upB) {
                fc = new JFileChooser();
                int returnVal = fc.showOpenDialog(null);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    file = fc.getSelectedFile();
                }

                String fileName = file.getName();

                if(f1.isSelected()) { 
                    file1 = new File("files\\" + fileName);
                    try {
                        file1 = Files.copy(file.toPath(), file1.toPath(), StandardCopyOption.REPLACE_EXISTING).toFile();
                    } catch (Exception ex) {
                        System.out.println("Error: " + ex.toString());
                    }
                    anyReady = true;
                } 
            }

            if(e.getSource() == dwB) {
                downloadClicked = true;
            }
        }
    }
}