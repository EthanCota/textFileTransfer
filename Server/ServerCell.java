import java.io.File;

public class ServerCell {
    ServerGUI serverGUI;
    public String networkText = "";
    File networkFile;

    public ServerCell(){
    }

    public void save(String s) {
        if(!s.equals(networkText)) {
            networkText = s;
            if(networkText.length() > 20) {
                serverGUI.infoArea.append("\nText changed to: " + networkText.substring(0, 20) + "...");
            } else serverGUI.infoArea.append("\nText changed to: " + networkText);
        }
    }

    public void file(File f) {
        networkFile = f;
        serverGUI.infoArea.append("/nFile added: " + networkFile.getName());
    }

    public void saveGUI(ServerGUI gui) {
        serverGUI = gui;
    }

    public String getText() {
        return networkText;
    }
}