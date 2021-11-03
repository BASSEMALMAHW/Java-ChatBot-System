package com.bmb.chatbot;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Author Bassem Al Mahow
 */

public class Server_Chat extends javax.swing.JFrame {

    private                 ObjectOutputStream      output;
    private                 ObjectInputStream       input;
    private                 Socket                  connection;
    private                 ServerSocket            server;
    private                 Integer                 totalClients = 100;
    private                 Integer                 port         = 6789;
    /**
     * GUI Parameters
     */
    private                 javax.swing.JLabel      status;
    private                 javax.swing.JTextArea   chatArea;
    private                 javax.swing.JButton     jButton1;
    private                 javax.swing.JLabel      header;
    private                 javax.swing.JLabel      title;
    private                 javax.swing.JPanel      jPanel1;
    private                 javax.swing.JScrollPane jScrollPane1;
    private                 javax.swing.JTextField  messageArea;
    //-----------------------------------------------------------------------------------------------------

    /**
     * chat_server
     */
    public Server_Chat() {
        initComponents();
        this.setTitle("Server");
        this.setVisible(true);
        status.setVisible(true);
    }
    //-----------------------------------------------------------------------------------------------------

    /**
     * startRunning
     */
    public void startRunning() {
        try{
            server = new ServerSocket(port, totalClients);
            while(true){
                try{
                    status.setText(" Waiting for Someone to Connect...");
                    connection = server.accept();
                    status.setText(" Now Connected to "+connection.getInetAddress().getHostName());

                    output = new ObjectOutputStream(connection.getOutputStream());
                    output.flush();
                    input = new ObjectInputStream(connection.getInputStream());

                    whileChatting();
                }catch(EOFException eofException){eofException.printStackTrace();}
            }
        }catch(IOException ioException) {ioException.printStackTrace();}
    }
    //-----------------------------------------------------------------------------------------------------

    /**
     * whileChatting
     * @throws IOException
     */
   private void whileChatting() throws IOException{
        String message="";
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
        jScrollPane1    = new javax.swing.JScrollPane();
        chatArea        = new javax.swing.JTextArea();
        messageArea     = new javax.swing.JTextField();
        jButton1        = new javax.swing.JButton();
        status          = new javax.swing.JLabel();
        title           = new javax.swing.JLabel();
        header          = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(0, 0, 100));
        jPanel1.setForeground(new java.awt.Color(204, 204, 204));
        jPanel1.setLayout(null);

        chatArea.setEditable(false);
        chatArea.setColumns(20);
        chatArea.setRows(5);
        chatArea.setHighlighter(null);
        jScrollPane1.setViewportView(chatArea);

        jPanel1.add(jScrollPane1);
        jScrollPane1.setBounds(10, 90, 480, 250);

        messageArea.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                messageAreaActionPerformed(evt);
            }
        });
        jPanel1.add(messageArea);
        messageArea.setBounds(10, 350, 410, 40);

        jButton1.setBackground(new java.awt.Color(150, 150, 150));
        jButton1.setFont(new java.awt.Font("Arial Black", 1, 12)); // NOI18N
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
        status.setBounds(10, 60, 300, 40);

        title.setFont(new java.awt.Font("Myriad Pro", 1, 48)); // NOI18N
        title.setForeground(new java.awt.Color(255, 255, 255));
        title.setText("Server");
        jPanel1.add(title);
        title.setBounds(80, 10, 190, 60);

        header.setBackground(new java.awt.Color(153, 255, 204));
        jPanel1.add(header);
        header.setBounds(0, 35, 460, 410);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 523, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 460, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        setSize(new java.awt.Dimension(523, 460));
        setLocationRelativeTo(null);
    }
    //-----------------------------------------------------------------------------------------------------

    /**
     * jButton1ActionPerformed
     * @param evt
     */
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {                                         
      sendMessage(messageArea.getText());
        messageArea.setText("");
    }
    //-----------------------------------------------------------------------------------------------------

    /**
     * jTextField1ActionPerformed
     * @param evt
     */
    private void messageAreaActionPerformed(java.awt.event.ActionEvent evt) {
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
            chatArea.append("\nME(Server) : "+message);
            output.writeObject("                                                             Server : " + message);
            output.flush();
        }catch(IOException ioException){chatArea.append("\n Unable to Send Message");}
    }

}
