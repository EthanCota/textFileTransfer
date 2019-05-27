import java.net.*;
import java.io.*;
import java.util.*;
import javax.swing.*;

public class ServerDaemon extends Thread {
    private Socket socketBackToClient;
    private ServerCell serverCell;
    public ServerGUI serverGUI;
    public ServerSocket socketOnWhichToListenForClients;
    
    public ServerDaemon(){
        start();
    }

    public void run() {
        try{
            serverGUI.infoArea.append("\nServer daemon starting");
            socketOnWhichToListenForClients = new ServerSocket(10101);
            serverCell = new ServerCell();
            serverCell.saveGUI(serverGUI);
            
            // Listen indefinitely for client requests
            while(true){
                Socket socketBackToClient = socketOnWhichToListenForClients.accept();
                
                new ClientHandler(socketBackToClient).saveCellAndGUI(serverCell, serverGUI);
            }
        }catch (Exception e){
            serverGUI.infoArea.append("\nServer Daemon Error: " + e.toString());
        }
        serverGUI.infoArea.append("\nServer daemon is closed");
    }
    
    public void saveGUI(ServerGUI gui) {
        serverGUI = gui;
    }
}