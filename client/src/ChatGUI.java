
import java.awt.Color;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

/**
 * GUI for chat application
 */
public class ChatGUI extends javax.swing.JFrame {

    String username;
    String recipiant;
    final LinkedList<String> connectedUsers = new LinkedList<String>();
    WhozuppClient client;
    final AtomicBoolean chatClosed = new AtomicBoolean(false);

    /**
     * Creates new form ChatGUI
     */
    public ChatGUI() {

        client = new WhozuppClient();

        initComponents();

        client.connect();
        System.out.println("Connected");
        System.out.println("Getting username");

        /* Get username from user */
        JFrame frame = new JFrame("Chat Application");
        frame.setBounds(100, 100, 800, 600);
        username = JOptionPane.showInputDialog(frame, "Enter your username:", "Username", JOptionPane.PLAIN_MESSAGE);
        System.out.println("Username: " + username);
        while (!client.sendNickname(username) && !username.matches("^[a-zA-Z0-9_]+$")) {
            System.out.println("Username invalid");
            username = JOptionPane.showInputDialog(frame, "Username invalid.\nEnter your new username:", "Username", JOptionPane.PLAIN_MESSAGE);
            System.out.println("Username: " + username);
        }

        setVisible(true);
        // TODO NOSHY
        appendMessageToChat(username + " has conncted to WHOZUPP!", null);

        usernameLabel.setText(username);
        System.out.println("Username set");
        
        connectedStatusLabel.setText("Connected");

        // get connected users and update GUI
        // TODO get users
        String[] options = new String[connectedUsers.size() + 1];
        options[0] = "All users";
        for (int i = 1; i < options.length; i++) {
            options[i] = connectedUsers.get(i - 1);
        }
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(options));

        // wait for messages
        new Thread(new Runnable() {
            @SuppressWarnings("empty-statement")
            public void run() {
                WhozuppDataObject msgData;

                if (chatClosed.get()) {
                    return;
                }

                // TODO NOSHYYYYYYYYYYYY MAKE PRETTY (LIKE YOU)
                // IF type = 3 or 4 save the user list it changed.
                while ((msgData = client.recieveMessage()) != null) {
                    if (msgData.type == 1) {
                        appendMessageToChat(msgData.message, msgData.user);
                    }
                    if (msgData.type == 3) {
                        synchronized (connectedUsers) {
                            appendMessageToChat(msgData.user + " has conncted to WHOZUPP!", null);
                            connectedUsers.add(msgData.user);
                            updateComboBox();
                        }
                    }
                    if (msgData.type == 4) {
                        synchronized (connectedUsers) {
                            appendMessageToChat(msgData.user + " has disconncted from WHOZUPP!", null);
                            connectedUsers.remove(msgData.user);
                            updateComboBox();
                        }
                    }
                }
            }
        }).start();

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        chatArea = new javax.swing.JTextPane();
        messageArea = new javax.swing.JTextField();
        sendMessageButton = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        usernameLabel = new javax.swing.JLabel();
        connectedStatusLabel = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                chatClosedAction(evt);
            }
        });

        jLabel1.setText("Whozupp");

        chatArea.setEditable(false);
        jScrollPane1.setViewportView(chatArea);

        sendMessageButton.setText("Send");
        sendMessageButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendMessageButtonActionPerformed(evt);
            }
        });

        jLabel2.setText("Chat To:");

        connectedStatusLabel.setText("Disconnected");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All users" }));
        jComboBox1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox1ItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(connectedStatusLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(usernameLabel)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 76, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(261, 261, 261))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(messageArea)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(sendMessageButton))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 452, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(61, 61, 61))))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(messageArea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sendMessageButton))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 23, Short.MAX_VALUE)
                        .addComponent(usernameLabel))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(connectedStatusLabel)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void sendMessageButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendMessageButtonActionPerformed
        final String message = messageArea.getText();
        final String recipiant = this.recipiant;

        final AtomicBoolean sent = new AtomicBoolean(false);

        // send message to server
        new Thread(new Runnable() {
            @SuppressWarnings("empty-statement")
            public void run() {

                if (chatClosed.get()) {
                    return;
                }

                // TODO use recipiant not 1
                if (client.sendMessage(null, message)) {
                    sent.set(true);
                };

            }
        }).start();

        // update gui
        // TODO at some point add text while message is being sent
        appendMessageToChat(message, username);
    }//GEN-LAST:event_sendMessageButtonActionPerformed

    private void chatClosedAction(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_chatClosedAction
        chatClosed.set(true);
        client.disconect();
    }//GEN-LAST:event_chatClosedAction

    private void jComboBox1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox1ItemStateChanged
       recipiant = (String) jComboBox1.getSelectedItem();
       chatArea.setText("");
    }//GEN-LAST:event_jComboBox1ItemStateChanged

    private void appendMessageToChat(String message, String user) {

        SwingUtilities.invokeLater(
                new Runnable() {
            public void run() {    // append the message to the chat area
                StyledDocument doc = chatArea.getStyledDocument();
                Style style = doc.addStyle("Style", null);
                StyleConstants.setForeground(style, Color.BLACK);
                StyleConstants.setBold(style, true);

                try {

                    if (user != null) {
                        doc.insertString(doc.getLength(), user + ": ", style);
                        doc.insertString(doc.getLength(), message + "\n", null);
                    } else {
                        doc.insertString(doc.getLength(), message + "\n", style);
                    }
                } catch (BadLocationException ex) {
                    // should not occur
                    ex.printStackTrace();
                }
            }
        });

    }

    private void updateComboBox() {

        SwingUtilities.invokeLater(
                new Runnable() {
            public void run() {
                synchronized (connectedUsers) {
                    String[] options = new String[connectedUsers.size() + 1];
                    options[0] = "All users";
                    for (int i = 1; i < options.length; i++) {
                        options[i] = connectedUsers.get(i - 1);
                    }
                    jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(options));
                }
            }

        });
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ChatGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ChatGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ChatGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ChatGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ChatGUI();
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextPane chatArea;
    private javax.swing.JLabel connectedStatusLabel;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField messageArea;
    private javax.swing.JButton sendMessageButton;
    private javax.swing.JLabel usernameLabel;
    // End of variables declaration//GEN-END:variables
}
