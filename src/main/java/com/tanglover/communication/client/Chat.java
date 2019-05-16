/*
 * Chat.java
 *
 * Created on __DATE__, __TIME__
 */

package com.tanglover.communication.client;

import com.tanglover.communication.datadeal.ChatLogMessage;

import java.awt.Container;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.Date;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * @author __USER__
 */
public class Chat extends javax.swing.JFrame {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    public final String friendName;
    private String loginName;
    private String friendNichen;
    private static String sendStr;

    public javax.swing.JTextArea getjTextArea1() {
        return jTextArea1;
    }

    public void setjTextArea1(javax.swing.JTextArea jTextArea1) {
        this.jTextArea1 = jTextArea1;
    }

    public String getFriendName() {
        return friendName;
    }

    public void setFriendName(String friendName) {
        friendName = this.friendName;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public Socket getS() {
        return s;
    }

    public void setS(Socket s) {
        this.s = s;
    }

    private Socket s;

    /**
     * Creates new form Chat
     */
    public Chat(String friendNichen, Socket s, String loginName,
                String friendName) {
        this.friendName = friendName;
        this.loginName = loginName;
        this.s = s;
        this.friendNichen = friendNichen;

        initComponents();
        URL url = this.getClass().getResource("/mm.jpg");
        ImageIcon img = new ImageIcon(url);//这是背景图片
        JLabel imgLabel = new JLabel(img);//将背景图放在标签里。

        Container cp = this.getContentPane();
        cp.add(imgLabel);
        this.getLayeredPane().add(imgLabel, new Integer(Integer.MIN_VALUE));//注意这里是关键，将背景标签添加到jfram的LayeredPane面板里。
        imgLabel.setBounds(0, 0, img.getIconWidth(), img.getIconHeight());//设置背景标签的位置
        this.setVisible(true);

        ((JPanel) getContentPane()).setOpaque(false); //注意这里，将内容面板设为透明。这样LayeredPane面板中的背景才能显示出来。

        this.setResizable(false);

        setLocation(((int) Toolkit.getDefaultToolkit().getScreenSize()
                .getWidth() - 900), 250);

        Toolkit tool = getToolkit(); //得到一个Toolkit对象
        URL url1 = this.getClass().getResource("/logoo.jpg");
        Image myimage = tool.getImage(url1); //由tool获取图像
        setIconImage(myimage);

        jTextArea1.setEditable(false);

        jTextArea2.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void keyReleased(KeyEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == 10) {
                    String messageContent = send();
                    try {
                        Message message = new Message();
                        message.setReciveUserName(Chat.this.friendName);
                        message.setMessageConten(messageContent);
                        message.setSendUserName(Chat.this.loginName);
                        message.setFlag(false);
                        new ObjectOutputStream(Chat.this.s.getOutputStream())
                                .writeObject(message);

                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                }
            }
        });

        jButton1.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                exit();

            }
        });

        jButton2.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                String messageContent = send();
                if (messageContent != null && !messageContent.equals("")) {

                    try {

                        Message message = new Message();
                        message.setReciveUserName(Chat.this.friendName);
                        message.setMessageConten(messageContent);
                        message.setSendUserName(Chat.this.loginName);
                        message.setFlag(false);
                        new ObjectOutputStream(Chat.this.s.getOutputStream())
                                .writeObject(message);

                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                }
            }
        });

        jButton3.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                shows();
            }
        });

        this.addWindowListener(new WindowListener() {

            @Override
            public void windowOpened(WindowEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void windowIconified(WindowEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void windowDeiconified(WindowEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void windowDeactivated(WindowEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void windowClosing(WindowEvent e) {
                // TODO Auto-generated method stub
                ClieneMainFrame.map.remove(Chat.this.friendName);
            }

            @Override
            public void windowClosed(WindowEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void windowActivated(WindowEvent e) {
                // TODO Auto-generated method stub

            }
        });

        jButton4.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub

                douDong();

                String str = "给你发送了一个窗口抖动！";

                Message message = new Message();
                message.setReciveUserName(Chat.this.friendName);
                message.setSendUserName(Chat.this.loginName);
                message.setMessageConten(str);
                message.setFlag(true);
                try {
                    new ObjectOutputStream(Chat.this.s.getOutputStream())
                            .writeObject(message);
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        });

        jTextArea1.setLineWrap(true);
        jTextArea2.setLineWrap(true);

    }

    /**
     * This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    //GEN-BEGIN:initComponents
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("\u804a\u5929");

        jLabel1.setFont(new java.awt.Font("黑体", 0, 14));
        jLabel1.setText("与" + friendNichen + "聊天中");

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jLabel2.setFont(new java.awt.Font("黑体", 0, 14));
        jLabel2.setText("     ");

        jLabel3.setFont(new java.awt.Font("黑体", 0, 14));
        jLabel3.setText("     ");

        jTextArea2.setColumns(20);
        jTextArea2.setRows(5);
        jScrollPane2.setViewportView(jTextArea2);

        jButton1.setText("\u5173\u95ed");

        jButton2.setText("\u53d1\u9001");

        jButton3.setText("\u804a\u5929\u8bb0\u5f55");

        jButton4.setText("抖动");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
                getContentPane());
        getContentPane().setLayout(layout);
        layout
                .setHorizontalGroup(layout
                        .createParallelGroup(
                                javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(
                                layout
                                        .createSequentialGroup()
                                        .addGroup(
                                                layout
                                                        .createParallelGroup(
                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(
                                                                layout
                                                                        .createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                false)
                                                                        .addComponent(
                                                                                jLabel1,
                                                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                240,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                                                layout
                                                                                        .createSequentialGroup()
                                                                                        .addContainerGap()
                                                                                        .addComponent(
                                                                                                jLabel2)
                                                                                        .addGap(
                                                                                                18,
                                                                                                18,
                                                                                                18)
                                                                                        .addComponent(
                                                                                                jLabel3))
                                                                        .addComponent(
                                                                                jScrollPane2,
                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(
                                                                                jScrollPane1,
                                                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                378,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(
                                                                layout
                                                                        .createSequentialGroup()
                                                                        .addContainerGap()
                                                                        .addComponent(
                                                                                jButton3)
                                                                        .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                        .addComponent(
                                                                                jButton4)
                                                                        .addGap(
                                                                                27,
                                                                                27,
                                                                                27)
                                                                        .addComponent(
                                                                                jButton1)
                                                                        .addGap(
                                                                                18,
                                                                                18,
                                                                                18)
                                                                        .addComponent(
                                                                                jButton2)))
                                        .addContainerGap(136, Short.MAX_VALUE)));
        layout
                .setVerticalGroup(layout
                        .createParallelGroup(
                                javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(
                                layout
                                        .createSequentialGroup()
                                        .addComponent(
                                                jLabel1,
                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                28,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(
                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(
                                                jScrollPane1,
                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                225,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(
                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(
                                                layout
                                                        .createParallelGroup(
                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jLabel2)
                                                        .addComponent(jLabel3))
                                        .addPreferredGap(
                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(
                                                jScrollPane2,
                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                82,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(
                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                Short.MAX_VALUE)
                                        .addGroup(
                                                layout
                                                        .createParallelGroup(
                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jButton3)
                                                        .addComponent(jButton2)
                                                        .addComponent(jButton1)
                                                        .addComponent(jButton4))
                                        .addContainerGap()));

        pack();
    }// </editor-fold>
    //GEN-END:initComponents

    public void exit() {
        ClieneMainFrame.map.remove(friendName);
        this.dispose();
    }

    @SuppressWarnings("deprecation")
    //发送消息
    public String send() {
        String str2 = "";

        Date date = new Date();
        int year = date.getYear();
        int month = date.getMonth() + 1;
        int day = date.getDate();
        int hours = date.getHours();
        int minites = date.getMinutes();
        int seconds = date.getSeconds();

        String str1 = jTextArea2.getText();
        str2 += str1;
        String myNameDate = " " + ClieneMainFrame.myNichen() + "    "
                + (year - 100) + "年" + month + "月" + day + "日  " + hours + ":"
                + minites + ":" + seconds;

        System.out.println(str1);
        if (str1 == null || str1.equals("")) {
            JOptionPane.showMessageDialog(null, "发送内容不能为空！");


        } else {
            jTextArea1.setText(jTextArea1.getText() + myNameDate + "\r\n"
                    + "   " + str2 + "\r\n" + "\r\n");

            jTextArea2.setText("");

            sendStr = myNameDate + "\r\n" + str2 + "\r\n" + "\r\n";

            sendChat();

            return str2;

        }
        return null;

    }

    //将发送的消息保存到mapChatLog
    public void sendChat() {

        if (ClieneMainFrame.mapChatLog.containsKey(friendName)) {
            String str = ClieneMainFrame.mapChatLog.get(friendName);
            str = str + sendStr;
            ClieneMainFrame.mapChatLog.put(friendName, str);
            ClieneMainFrame.chatLogs();
        } else {
            ClieneMainFrame.mapChatLog.put(friendName, sendStr);
            ClieneMainFrame.chatLogs();
        }
        sendStr = "";
    }

    //把聊天记录从本地文件读出来并显示到窗口中
    public void shows() {
        ChatLogFrm chatLog = new ChatLogFrm(friendName);
        String str = null;
        ObjectInputStream ois = null;
        File file = null;
        file = new File("src\\" + loginName + ".txt");
        try {
            ois = new ObjectInputStream(new FileInputStream(file));
            ChatLogMessage chatLogMessage = (ChatLogMessage) ois.readObject();
            Map<String, String> map = chatLogMessage.getMap();
            if (map.containsKey(friendName)) {
                str = map.get(friendName);
                chatLog.getjTextArea1().setText(str);
            } else {
                chatLog.getjTextArea1().setText("");
            }

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                ois.close();

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    //给别人发送抖动
    @SuppressWarnings("deprecation")
    public void douDong() {
        Date date = new Date();
        int year = date.getYear();
        int month = date.getMonth() + 1;
        int day = date.getDate();
        int hours = date.getHours();
        int minites = date.getMinutes();
        int seconds = date.getSeconds();

        String str1 = "你向" + friendNichen + "发送了一个窗口抖动！";

        String myNameDate = " " + ClieneMainFrame.myNichen() + "    "
                + (year - 100) + "年" + month + "月" + day + "日  " + hours + ":"
                + minites + ":" + seconds;

        jTextArea1.setText(jTextArea1.getText() + myNameDate + "\r\n"
                + "   " + str1 + "\r\n" + "\r\n");

        sendStr = myNameDate + "\r\n" + str1 + "\r\n" + "\r\n";

        sendChat();
    }


    /**
     * @param args the command line arguments
     */
    //	public static void main(String args[]) {
    //		java.awt.EventQueue.invokeLater(new Runnable() {
    //			public void run() {
    //				new Chat("nig").setVisible(true);
    //			}
    //		});
    //	}

    //GEN-BEGIN:variables
    // Variables declaration - do not modify
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    // End of variables declaration//GEN-END:variables

}