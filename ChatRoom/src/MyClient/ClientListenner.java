/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MyClient;

import static MyClient.ChatClient.insertString;
import java.io.InputStream;
import java.net.Socket;
import javax.swing.JLabel;

/**
 *
 * @author ngocanh
 */
public class ClientListenner implements Runnable {

    private Socket socket;
    private InputStream input;
    private JLabel jListMess;
    private int PORT;

    public ClientListenner(Socket Socket, JLabel jListMess, int PORT) {
        this.socket = Socket;
        this.jListMess = jListMess;
        this.PORT = PORT;
        try {
            input = socket.getInputStream();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            byte[] buffer = new byte[1024];
            int byteRead;
            while ((byteRead = input.read(buffer)) != -1) {
                String message = new String(buffer, 0, byteRead);
                System.out.println(message);
                int index_port = message.indexOf("_");
                String svPort = message.substring(index_port + 1);
                int sendPort = Integer.parseInt(svPort);
                System.out.println(sendPort+" - " + PORT);
                if (sendPort == PORT) {
                    String listMessage = jListMess.getText();
                    int index = listMessage.indexOf("</html>");
                    String finalString = insertString(listMessage, "<br>" + message.substring(0,index_port), index);
                    jListMess.setText(finalString);
                }

            }

        } catch (Exception e) {
        }
    }

}
