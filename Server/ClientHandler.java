import java.net.*;
import java.io.*;
import java.util.*;

public class ClientHandler extends Thread
{
    public ServerCell serverCell;
    public ServerGUI serverGUI;
    private Socket socketBackToClient;

    public File file;
    public String placeHolder, serverAction, input, fileContent = "";

    public ClientHandler(Socket socket)
    {
        socketBackToClient = socket;
        start();
    }

    public void run()
    {

        serverGUI.infoArea.append("\nClientHandler starting");

        try
        {

            InputStream is = socketBackToClient.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            DataOutputStream dos = new DataOutputStream(socketBackToClient.getOutputStream());
            DataInputStream dis = new DataInputStream(is);
            PrintStream os = new PrintStream(socketBackToClient.getOutputStream(), true);

            while(true)
            {
                serverAction = br.readLine();

                if(serverAction.equals("OUT"))
                {
                    serverGUI.infoArea.append("\nOutput is: " + file.getName());

                    String fileName = file.getName();
                    dos.writeUTF(fileName);

                    Scanner sc = new Scanner(file);
                    while(sc.hasNext())
                    {
                        String input = sc.next();
                        os.println(input);
                    }
                    os.println("EXIT_READER");

                    sc.close();
                    serverAction = "";
                }
                /**
                if(serverAction.equals("TEST_LINE")) {
                    String textContent = dis.readUTF();
                    System.out.print(textContent);
                    serverCell.save(textContent);
                    serverAction = "";
                }

                if(serverAction.equals("CLIENT_REQUESTING_DATA")) {
                    String sh = serverCell.networkText;
                    System.out.println(sh);
                    os.println(sh);
                    serverAction = "";
                }
                **/
                if(serverAction.equals("IN"))
                {
                    input = dis.readUTF();
                    serverGUI.infoArea.append("\nInput is: " + input);
                    file = new File("files\\" + input);
                    file.getParentFile().mkdirs();
                    file.createNewFile();

                    PrintWriter pw = new PrintWriter(file, "UTF-8");

                    while(true)
                    {
                        placeHolder = "";
                        placeHolder = br.readLine();
                        if(placeHolder.equals("EXIT_READER"))
                        {
                            break;
                        }
                        else
                        {
                            fileContent += " " + placeHolder;
                        }
                    }
                    pw.print(fileContent);
                    pw.close();
                }

                serverAction = "";
            }
        }
        catch(Exception e)
        {
            serverGUI.infoArea.append("\nClient Handler Error:\n" + e.toString());
        }
    }

    public void saveCellAndGUI(ServerCell cell, ServerGUI gui)
    {
        serverCell = cell;
        serverGUI = gui;
    }
}
