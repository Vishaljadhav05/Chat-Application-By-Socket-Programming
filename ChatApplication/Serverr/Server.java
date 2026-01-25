import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.ServerSocket;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class Server 
{
    private JFrame serverFrame;
    private JTextArea ta;
    private JScrollPane scrollpane;
    private JTextField tf;

    private ServerSocket serverSocket;
    private InetAddress inet_address;

    Server()
    {
        
        serverFrame = new JFrame("Server");
        serverFrame.setSize(500,500);
        serverFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ta = new JTextArea();
        ta.setEditable(false);

        scrollpane = new JScrollPane(ta);
        serverFrame.add(scrollpane);

        tf = new JTextField();

        //Adding action listener 
        tf.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                showMessage(tf.getText());
            }
        });

        serverFrame.add(tf, BorderLayout.SOUTH);

        serverFrame.setVisible(true);

    }

    public void waitForCleint(){
        try 
        {
            String ipaddress = getIpAddress();

            serverSocket = new ServerSocket(1111);
            ta.setText("To Connect with server, please provide IP Address :::" + ipaddress);
            serverSocket.accept();
            ta.setText("Client connected.. \n");
            ta.append("------------------------------------------------------------------------ \n");


        } 
        catch (Exception e) 
        {
            System.out.println(e);
        }
        
    }

    //Method to connect ip address
    public String getIpAddress()
    {

        String ip_address = "";
        try 
        {
            inet_address = InetAddress.getLocalHost();
            ip_address = inet_address.getHostAddress();
            
        } 
        catch (Exception e) 
        {
            System.out.println(e);
        }
        return ip_address;
    }


    public void showMessage(String message)
    {
        //Appending messaget to text feild
        ta.append(message + "\n");

        //empty to entering field
        //ta.setText("");
    }

}
