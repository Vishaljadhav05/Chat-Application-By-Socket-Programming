package ChatApplication.Clientt;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.net.Socket;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
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

    private DataInputStream dis;
    private DataOutputStream dos;

    private Socket socket;

    String ipaddress;


    
    //---------------------------------------Thread Created---------------------------------
    Thread thread = new Thread(){
        public void run(){
            while ( true) {
                readMessage();
            }
        }
    };
    //-----------------------------------------Thread creation end-----------------------------------------------


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
                clientFrame.setSize(600,600);
                clientFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                ta = new JTextArea();
                ta.setEditable(false);

                Font font = new Font("Arial", 1, 16);
                ta.setFont(font);

                scrollpane = new JScrollPane(ta);
                clientFrame.add(scrollpane);

                tf = new JTextField();
                tf.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e){
                        sendMessage(tf.getText());
                        ta.append(tf.getText() + "\n");
                        tf.setText("");
                    }
                });
                clientFrame.add(tf, BorderLayout.SOUTH);

                clientFrame.setVisible(true);
            }
        
        }

        
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
            showMessage("Server: " +message);
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
