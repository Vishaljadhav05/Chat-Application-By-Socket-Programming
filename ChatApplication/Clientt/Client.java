package ChatApplication.Clientt;

import java.awt.BorderLayout;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Client {
    

    private JFrame clientFrame;
    private JTextArea ta;
    private JScrollPane scrollpane;
    private JTextField tf;

    private Socket socket;

    String ipaddress;

    Client()
    {

        ipaddress = JOptionPane.showInputDialog("Enter IP Address: ");
        System.out.println(ipaddress);

        if (ipaddress != null) 
        {
            if (!ipaddress.equals("")) 
            {

                //Calling connet methof
                connectToServer();

                clientFrame = new JFrame("Client");
                clientFrame.setSize(500,500);
                clientFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                ta = new JTextArea();
                ta.setEditable(false);

                scrollpane = new JScrollPane(ta);
                clientFrame.add(scrollpane);

                tf = new JTextField();
                clientFrame.add(tf, BorderLayout.SOUTH);

                clientFrame.setVisible(true);
            }
        
        }

        
    }

    void connectToServer()
    {

        try 
        {
            socket = new Socket(ipaddress, 1111);
        } 
        catch (Exception e)
        {
            System.out.println(e);
        }
        
    }
}
