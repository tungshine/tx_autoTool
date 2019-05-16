package com.tanglover.communication.datadeal;

import com.tanglover.communication.client.*;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

public class ReadThread implements Runnable {

    private Socket s;

    private String partName;

    private String onlineUserName;

    private String onlineUserIp;

    private String allUserName;

    private String allUserNichen;

    private String allUserPart;

    private String allUserTouxiang;

    private DefaultMutableTreeNode root;

    private JTree jTree1;


    private String loginName;


    public ReadThread(Socket s, DefaultMutableTreeNode root, JTree jTree1, String loginName) {
        super();
        this.s = s;
        this.root = root;
        this.jTree1 = jTree1;
        this.loginName = loginName;
    }

    public Object object;

    private ServerToClient o;
    public Message message;
    public static String friendName;
    public static String content;
    public static boolean flag;

    @Override
    public void run() {
        // TODO Auto-generated method stub
        while (true) {
            ObjectInputStream ois;
            try {
                ois = new ObjectInputStream(s.getInputStream());
            } catch (IOException e2) {
                System.out.println("服务器已经关闭...");
                JOptionPane.showMessageDialog(null, "与服务器连接已断开！");
                System.exit(0);
//					e2.printStackTrace();
                break;
            }

            try {
                object = ois.readObject();
                ClieneMainFrame.object = object;
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                System.out.println("读文件的异常..");
                e1.printStackTrace();
                break;
            } catch (ClassNotFoundException e1) {
                System.out.println("找不到文件异常...");
                // TODO Auto-generated catch block
                e1.printStackTrace();
                break;
            }


            try {
                if (object instanceof ServerToClient) {

                    // 找到在线好友的名字和IP
                    o = (ServerToClient) this.object;
                    OnlineUserArrayList online = o.getOnlineList();
                    ArrayList<OnlineUserBean> onlineList = online
                            .getOnlineUserList();

                    // 找到所有人的信息
                    AllUserArrayList allUser = o.getAllUserList();
                    ArrayList<UserInfoBean> allUserList = allUser
                            .getAllUserList();


                    // 找到所有部门信息
                    ArrayList<PartInfoBean> allPart = o.getAllPartList();

                    root.removeAllChildren();

                    Iterator<PartInfoBean> partIter = allPart.iterator();
                    while (partIter.hasNext()) {
                        partName = partIter.next().getPartName();
                        DefaultMutableTreeNode root1 = new DefaultMutableTreeNode(
                                partName);
                        root.add(root1);

                        Iterator<OnlineUserBean> onlineIter = onlineList
                                .iterator();
                        while (onlineIter.hasNext()) {
                            OnlineUserBean onlineUser = onlineIter.next();
                            onlineUserName = onlineUser.getLoginName();
                            onlineUserIp = onlineUser.getLoginIP();

                            Iterator<UserInfoBean> allIter = allUserList.iterator();
                            while (allIter.hasNext()) {
                                UserInfoBean allUserInfo = allIter.next();
                                allUserName = allUserInfo.getName();
                                allUserNichen = allUserInfo.getNichen();
                                allUserPart = allUserInfo.getPart();
                                allUserTouxiang = allUserInfo.getTouXiang();

                                // 在线好友在列表显示

                                if (onlineUserName.equals(allUserName)) {
                                    Head h = new Head();
                                    h.setImageIcon(new ImageIcon(this
                                            .getClass().getResource(
                                                    allUserTouxiang)));
                                    h.setName("<html><font color = '121460' size='3' style='font-weight:bold'> " + allUserNichen + "&nbsp;&nbsp;</font><font color = '5f0b0b' size='3'>(" + allUserName
                                            + ")</font>" + "<br><font color = '41413d'>" + onlineUserIp + "</font></html>");

                                    if (allUserPart.equals(partName)) {
                                        DefaultMutableTreeNode node1 = new DefaultMutableTreeNode(
                                                h);
                                        root1.add(node1);
                                    }
                                }
                                // 用户自己的头像名字显示
                                if (loginName.equals(allUserName)) {
                                    URL url = this.getClass().getResource("/" + allUserTouxiang);
                                    ImageIcon image = new ImageIcon(url);
                                    ClieneMainFrame.jLabel3.setIcon(image);
                                    ClieneMainFrame.jLabel4
                                            .setText(allUserNichen);
                                    ClieneMainFrame.jLabel5
                                            .setText(onlineUserIp);

                                }
                            }
                        }

                    }
                    jTree1.updateUI();

                } else if (object instanceof Message) {
                    message = (Message) object;
                    friendName = message.getSendUserName();
                    content = message.getMessageConten();
                    flag = message.isFlag();
                    ClieneMainFrame.findChat();

                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
    }

}
