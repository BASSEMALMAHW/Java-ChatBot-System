package com.bmb.chatbot;


import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.io.IOException;
import java.net.InetAddress;
import javax.swing.JOptionPane;

/**
 * Author Bassem Al Mahow
 */

public class Client_Chat extends javax.swing.JFrame {

    /**
     * Parameters
     */
    private               ObjectOutputStream      output;
    private               ObjectInputStream       input;
    private               String                  message    ="";
    private               String                  serverIP;
    private               Socket                  connection;
    private               Integer                 port       = 6789;
    /**
     * GUI Parameters
     */
    private               javax.swing.JTextArea   chatArea;
    private               javax.swing.JButton     jButton1;
    private               javax.swing.JLabel      header;
    private               javax.swing.JLabel      title;
    private               javax.swing.JPanel      jPanel1;
    private               javax.swing.JScrollPane jScrollPane1;
    private               javax.swing.JTextField  messageArea;
    private               javax.swing.JLabel      status;
    //-----------------------------------------------------------------------------------------------------

    /**
     * chat_Client
     * @param serIP
     */
    public Client_Chat(String serIP) {
        initComponents();
        this.setTitle("Client");
        this.setVisible(true);
        status.setVisible(true);
        serverIP = serIP;
    }
    //-----------------------------------------------------------------------------------------------------
    /**
     * startRunning
     */
    public void startRunning(){
        try{
            status.setText("Attempting Connection ...");
            try {
                connection = new Socket(InetAddress.getByName(serverIP),port);}
            catch(IOException ioEception){
                JOptionPane.showMessageDialog(null,"Server Might Be Down!","Warning",JOptionPane.WARNING_MESSAGE);}

            status.setText("Connected to: " + connection.getInetAddress().getHostName());
            output = new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input = new ObjectInputStream(connection.getInputStream());
            whileChatting();
        }catch(IOException ioException){ ioException.printStackTrace();}
    }
    //-----------------------------------------------------------------------------------------------------

    /**
     * whileChatting
     * @throws IOException
     */
    private void whileChatting() throws IOException{
        messageArea.setEditable(true);
        do{
            try{
                message = (String) input.readObject();
                chatArea.append("\n"+message);
            }catch(ClassNotFoundException classNotFoundException){classNotFoundException.printStackTrace();}
        }while(!message.equals("Client - END"));
    }
    //-----------------------------------------------------------------------------------------------------

    /** GUI **/

    /**
     * initComponents
     */
    private void initComponents() {
        jPanel1         = new javax.swing.JPanel();
        messageArea     = new javax.swing.JTextField();
        jButton1        = new javax.swing.JButton();
        jScrollPane1    = new javax.swing.JScrollPane();
        chatArea        = new javax.swing.JTextArea();
        title           = new javax.swing.JLabel();
        status          = new javax.swing.JLabel();
        header          = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(70,130,180));
        jPanel1.setForeground(new java.awt.Color(204, 204, 204));
        jPanel1.setLayout(null);

        chatArea.setEditable(false);
        chatArea.setColumns(20);
        chatArea.setRows(5);
        chatArea.setHighlighter(null);
        jScrollPane1.setViewportView(chatArea);

        jPanel1.add(jScrollPane1);
        jScrollPane1.setBounds(10, 90, 480, 250);

        messageArea.setToolTipText("Write your message here...");
        messageArea.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                messageAreaActionPerformed(evt);
            }
        });
        jPanel1.add(messageArea);

        messageArea.setBounds(10, 350, 400, 40);

        jButton1.setBackground(new java.awt.Color(150, 150, 150));
        jButton1.setFont(new java.awt.Font("Arial Black", 1, 12));
        jButton1.setText("Send");
        jButton1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.add(jButton1);
        jButton1.setBounds(410, 350, 80, 40);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        status.setForeground(new java.awt.Color(255, 255, 255));
        status.setText("...");
        jPanel1.add(status);
        status.setBounds(10, 50, 300, 40);
        jPanel1.add(header);
        header.setBounds(0, 0, 400, 400);

        title.setFont(new java.awt.Font("Myriad Pro", 1, 48)); // NOI18N
        title.setForeground(new java.awt.Color(255, 255, 255));
        title.setText("Client");
        jPanel1.add(title);
        title.setBounds(140, 20, 180, 40);


        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 525, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 425, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        setSize(new java.awt.Dimension(525, 460));
        setLocationRelativeTo(null);
    }
    //-----------------------------------------------------------------------------------------------------

    /**
     *jTextField1ActionPerformed
     * @param evt
     */
    private void messageAreaActionPerformed(java.awt.event.ActionEvent evt) {
      sendMessage(messageArea.getText());
        messageArea.setText("");
    }
    //-----------------------------------------------------------------------------------------------------

    /**
     *jButton1ActionPerformed
     * @param evt
     */
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {                                         
      sendMessage(messageArea.getText());
        messageArea.setText("");
    }
    //-----------------------------------------------------------------------------------------------------

    /**
     * sendMessage
     * @param message
     */
    private void sendMessage(String message){
        try{
            chatArea.append("\nME(Client) : "+message);
            output.writeObject("                                                             Client : " + message);
            output.flush();
        }catch(IOException ioException){chatArea.append("\n Unable to Send Message");}
    }
}
