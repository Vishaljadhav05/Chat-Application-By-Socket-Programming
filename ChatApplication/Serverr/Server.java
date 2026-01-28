import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
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

    private DataInputStream dis;
    private DataOutputStream dos;

    private Socket socket;

    //---------------------------------------Thread Created---------------------------------
    Thread thread = new Thread(){
        public void run(){
            while ( true) {
                readMessage();
            }
        }
    };
    //-----------------------------------------Thread creation end-----------------------------------------------

    Server()
    {
        
        serverFrame = new JFrame("Server");
        serverFrame.setSize(600,600);
        serverFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ta = new JTextArea();
        ta.setEditable(false);

        Font font = new Font("Arial", 1, 16);
        ta.setFont(font);

        scrollpane = new JScrollPane(ta);
        serverFrame.add(scrollpane);

        tf = new JTextField();

        //Adding action listener 
        tf.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                sendMessage(tf.getText());
                ta.append(tf.getText() + "\n");
                tf.setText("");
            }
        });

        tf.setEditable(false);

        serverFrame.add(tf, BorderLayout.SOUTH);

        serverFrame.setVisible(true);

    }

    public void waitForCleint(){
        try 
        {
            String ipaddress = getIpAddress();

            serverSocket = new ServerSocket(1111);
            ta.setText("To Connect with server, please provide IP Address ::: " + ipaddress + "\n");
            socket = serverSocket.accept();
            ta.setText("Client connected.. \n");
            ta.append("------------------------------------------------------------------------ \n");
            tf.setEditable(true);

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

    //Working on Input and output stream
    void setIOStream()
    {
         try 
         {
            dis = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());

         } 
         catch (Exception e) 
         {
            System.out.println(e);
         }
         thread.start();
    }

    //method for message sending
    public void sendMessage(String message)
    {

        try 
        {
            dos.writeUTF(message);
            dos.flush();
        } 
        catch (Exception e) 
        {
            System.out.println(e);
        }
    }

    //Mesage for reading the data
    public void readMessage()
    {
        try 
        {
            String message = dis.readUTF();
            showMessage( "Client: "+ message);
            //chatSoundRecieve();
            
        } 
        catch (Exception e) 
        {
            System.out.println(e);
        }
    }

    public void showMessage(String message)
    {
        //Appending messaget to text feild
        ta.append(message + "\n");
        chatSoundSend();
    }


    public void chatSoundSend()
    {
        try 
        {
            String soundName = "F:\\SP Core Java\\Code\\Chat_Application_Project_Socket_Prog\\ChatApplication\\Sound\\Send.wav";    
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName));
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } 
        catch (Exception e) 
        {
           System.out.println(e);
        }
    }
// cONTTRADICTING SO , COMMENTED
    // public void chatSoundRecieve()
    // {
    //     try 
    //     {
    //         String soundName = "F:\\SP Core Java\\Code\\Chat_Application_Project_Socket_Prog\\ChatApplication\\Sound\\Recieve.wav";    
    //         AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName));
    //         Clip clip = AudioSystem.getClip();
    //         clip.open(audioInputStream);
    //         clip.start();
    //     } 
    //     catch (Exception e) 
    //     {
    //        System.out.println(e);
    //     }
    // }
}
