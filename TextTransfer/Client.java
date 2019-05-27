import java.net.*;
import java.io.*;
import java.util.Scanner;
import java.nio.file.*;

public class Client
{
    public static void main(String[] args)
    {
        File file;
        String placeHolder, fileContent, input2, textData, serverInput;
        MainGUI main = new MainGUI();
        try
        {
            Socket socket = new Socket("AlexHasGay", 10101);

            fileContent = "";

            InputStream is = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            DataInputStream dis = new DataInputStream(is);
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

            PrintStream os = new PrintStream(socket.getOutputStream(), true);

            while(true)
            {
                if(main.fileGUI.downloadClicked)
                {
                    os.println("OUT");

                    input2 = dis.readUTF();
                    file = new File("files\\" + input2);
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
                    main.fileGUI.downloadClicked = false;
                }

                if(main.fileGUI.anyReady)
                {

                    os.println("IN");
                    file = main.fileGUI.file1;
                    String fileName = file.getName();
                    dos.writeUTF(fileName);

                    Scanner sc = new Scanner(file);
                    while(sc.hasNext())
                    {
                        String input = sc.next();
                        os.println(input);
                    }
                    os.println("EXIT_READER");

                    main.fileGUI.anyReady = false;

                    sc.close();
                }
                /**
                if(main.textGUI.saved)
                {
                    os.println("TEST_LINE");

                    textData = main.textGUI.editor.getText();
                    System.out.println(textData);
                    dos.writeUTF(textData);

                    main.textGUI.saved = false;
                }

                if(main.textGUI.refreshed)
                {
                    os.println("CLIENT_REQUESTING_DATA");
                    System.out.println(br.readLine());
                    //main.textGUI.editor.setText(serverInput);
                    System.out.println("I got here");

                    main.textGUI.refreshed = false;
                }
                **/
            }
        }
        catch(Exception e)
        {
            System.out.println("Error:\n" + e.toString());
        }
    }
}