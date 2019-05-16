/*
 * ClieneMainFrame.java
 *
 * Created on __DATE__, __TIME__
 */

package com.tanglover.communication.client;

import com.tanglover.communication.datadeal.*;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.*;

/**
 *
 * @author  __USER__
 */
public class ClieneMainFrame extends javax.swing.JFrame {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public static Map<String, Chat> map;

	public static Map<String, String> mapChatLog;

	public static Socket s;
	public static String loginName;
	public DefaultMutableTreeNode root;
	public static Object object;
	private static String friendNichen;
	private static String resivestr;
	public static URL chatUrl;

	JPopupMenu popMenu;
	public static javax.swing.JLabel jLabel3;
	public static javax.swing.JLabel jLabel4;
	public static javax.swing.JLabel jLabel5;

	public static javax.swing.JTree jTree1;

	public JPopupMenu getPopMenu() {
		return popMenu;
	}

	public void setPopMenu(JPopupMenu popMenu) {
		this.popMenu = popMenu;
	}

	public JMenuItem getAddItem() {
		return addItem;
	}

	public void setAddItem(JMenuItem addItem) {
		this.addItem = addItem;
	}

	public JMenuItem getDelItem() {
		return delItem;
	}

	public void setDelItem(JMenuItem delItem) {
		this.delItem = delItem;
	}

	public JMenuItem getEditItem() {
		return editItem;
	}

	public void setEditItem(JMenuItem editItem) {
		this.editItem = editItem;
	}

	public javax.swing.JScrollPane getjScrollPane1() {
		return jScrollPane1;
	}

	public void setjScrollPane1(javax.swing.JScrollPane jScrollPane1) {
		this.jScrollPane1 = jScrollPane1;
	}

	public javax.swing.JTree getjTree1() {
		return jTree1;
	}

	@SuppressWarnings("static-access")
	public void setjTree1(javax.swing.JTree jTree1) {
		this.jTree1 = jTree1;
	}

	JMenuItem addItem;
	JMenuItem delItem;
	JMenuItem editItem;

	/** Creates new form ClieneMainFrame */

	@SuppressWarnings("static-access")
	public ClieneMainFrame(Socket s, String loginName, Object object) {
		this.addWindowListener(new WindowAdapter() {
			public void windowClosed(WindowEvent e) {
				System.exit(0);
			}
		});
		this.s = s;
		this.loginName = loginName;
		this.object = object;

		this.setTitle("Leafdrop及时通");
		initComponents();

		URL url = this.getClass().getResource("/main.jpg");
		ImageIcon img = new ImageIcon(url);//这是背景图片  //这是背景图片
		JLabel imgLabel = new JLabel(img);//将背景图放在标签里。

		Container cp = this.getContentPane();
		cp.add(imgLabel);
		this.getLayeredPane().add(imgLabel, new Integer(Integer.MIN_VALUE));//注意这里是关键，将背景标签添加到jfram的LayeredPane面板里。
		imgLabel.setBounds(0, 0, img.getIconWidth(), img.getIconHeight());//设置背景标签的位置
		this.setVisible(true);

		((JPanel) getContentPane()).setOpaque(false); //注意这里，将内容面板设为透明。这样LayeredPane面板中的背景才能显示出来。
		(jScrollPane1).setOpaque(false);

		Toolkit tool = getToolkit(); //得到一个Toolkit对象
		URL url1 = this.getClass().getResource("/logoo.jpg");
		Image myimage = tool.getImage(url1); //由tool获取图像
		setIconImage(myimage);

		setLocation(((int) Toolkit.getDefaultToolkit().getScreenSize()
				.getWidth() - 400), 100);//根据电脑分辨率设置位置
		setResizable(false);

		chatMap();

		chatUrl = this.getClass().getResource("/chatlog.txt");

		root = new DefaultMutableTreeNode("公司");

		find();

		ReadThread read = new ReadThread(s, root, jTree1, loginName);
		Thread readTh = new Thread(read);
		readTh.start();
		try {
			Thread.currentThread().sleep(100);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		//初始化右键菜单
		popMenu = new JPopupMenu();
		addItem = new JMenuItem("聊天");
		addItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				DefaultMutableTreeNode treenode = (DefaultMutableTreeNode) jTree1
						.getLastSelectedPathComponent();
				Head head = (Head) treenode.getUserObject();
				String name = head.getName();
				String[] name1 = name.split("\\(");
				String name2 = name1[0];
				String name4 = name1[1];
				String[] name5 = name4.split("\\)");
				String friendName = name5[0];
				String[] name3 = name2.split("\\>");
				String name6 = name3[2];
				String[] name7 = name6.split("&");
				String friendNichen = name7[0];
				Chat chat = new Chat(friendNichen, ClieneMainFrame.this.s,
						ClieneMainFrame.this.loginName, friendName);

				map.put(friendName, chat);

			}
		});
		editItem = new JMenuItem("好友资料");
		editItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new FriendMessage(friendInfo());
			}
		});

		popMenu.add(addItem);
		popMenu.add(editItem);
		popMenu.setBackground(new Color(229, 241, 251));
		(getAddItem()).setOpaque(false);
		(getEditItem()).setOpaque(false);

		MyLabel myLabel = new MyLabel();
		jTree1.setCellRenderer(myLabel);
		jTree1.setBackground(new Color(229, 241, 251));

		map = new HashMap<String, Chat>();

		jTree1.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				TreePath path = jTree1.getPathForLocation(e.getX(), e.getY());
				if (path == null) {
					return;
				}
				jTree1.setSelectionPath(path);

				if (e.getButton() == 3) {
					popMenu.show(jTree1, e.getX(), e.getY());
				}
			}

			@Override
			public void mousePressed(MouseEvent e) {

				DefaultMutableTreeNode treenode = (DefaultMutableTreeNode) jTree1
						.getLastSelectedPathComponent();

				int selRow = jTree1.getRowForLocation(e.getX(), e.getY());
				try {
					if (selRow != -1) {
						if (e.getClickCount() == 2 && (treenode.isLeaf())) {
							Head head = (Head) treenode.getUserObject();
							String name = head.getName();
							String[] name1 = name.split("\\(");
							String name2 = name1[0];
							String name4 = name1[1];
							String[] name5 = name4.split("\\)");
							String friendName = name5[0];
							String[] name3 = name2.split("\\>");
							String name6 = name3[2];
							String[] name7 = name6.split("&");
							String friendNichen = name7[0];
							Chat chat = new Chat(friendNichen,
									ClieneMainFrame.this.s,
									ClieneMainFrame.this.loginName, friendName);

							map.put(friendName, chat);
						}
					}
				} catch (Exception e1) {
					System.out.println("本部门为空...");
					//					e1.printStackTrace();
				}

			}

			@Override
			public void mouseExited(MouseEvent e) {
				//				System.out.println("--------mouseExited----------");
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				//				System.out.println("--------mouseEntered----------");
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				//				System.out.println("--------mouseClicked----------");
			}
		});
		jScrollPane1.setViewportView(jTree1);

	}

	/** This method is called from within the constructor to
	 * initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
	 */
	//GEN-BEGIN:initComponents
	// <editor-fold defaultstate="collapsed" desc="Generated Code">
	private void initComponents() {

		jPanel1 = new javax.swing.JPanel();
		jScrollPane1 = new javax.swing.JScrollPane();
		jTree1 = new javax.swing.JTree();
		jLabel3 = new javax.swing.JLabel();
		jSeparator2 = new javax.swing.JSeparator();
		jMenuBar1 = new javax.swing.JMenuBar();
		jMenu1 = new javax.swing.JMenu();
		jMenu2 = new javax.swing.JMenu();
		jLabel4 = new javax.swing.JLabel();
		jLabel5 = new javax.swing.JLabel();
		jMenuBar2 = new javax.swing.JMenuBar();
		jMenu3 = new javax.swing.JMenu();
		jMenuItem1 = new javax.swing.JMenuItem();
		jMenuItem4 = new javax.swing.JMenuItem();
		jMenuItem2 = new javax.swing.JMenuItem();
		jMenu4 = new javax.swing.JMenu();
		jMenuItem3 = new javax.swing.JMenuItem();

		javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(
				jPanel1);
		jPanel1.setLayout(jPanel1Layout);
		jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 100,
				Short.MAX_VALUE));
		jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 100,
				Short.MAX_VALUE));

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		jTree1.setFont(new java.awt.Font("微软雅黑", 0, 14));
		jTree1.setForeground(new java.awt.Color(51, 0, 51));
		jScrollPane1.setViewportView(jTree1);

		jMenu1.setText("File");
		jMenuBar1.add(jMenu1);

		jMenu2.setText("Edit");
		jMenuBar1.add(jMenu2);

		jLabel4.setFont(new java.awt.Font("微软雅黑", 1, 12));
		jLabel4.setForeground(new java.awt.Color(153, 51, 0));
		jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
		jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource(
				"/com/etc/programe/person.png"))); // NOI18N
		jLabel4.setText("waiwai");
		jLabel4.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1,
				1));

		jLabel5.setFont(new java.awt.Font("微软雅黑", 1, 12));
		jLabel5.setForeground(new java.awt.Color(51, 51, 255));
		jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
		jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource(
				"/com/sun/java/swing/plaf/windows/icons/Computer.gif"))); // NOI18N
		jLabel5.setText("172.18.22.6");

		jMenuBar2.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1,
				1, 1));
		jMenuBar2.setOpaque(false);

		jMenu3.setForeground(new java.awt.Color(102, 51, 0));
		jMenu3.setText("\u7528\u6237");

		jMenuItem1.setFont(new java.awt.Font("微软雅黑", 0, 12));
		jMenuItem1.setForeground(new java.awt.Color(102, 102, 102));
		jMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource(
				"/com/etc/programe/point.gif"))); // NOI18N
		jMenuItem1.setText("\u7528\u6237\u4fe1\u606f");
		jMenuItem1.setActionCommand("yhxx");
		jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				menuEvent(evt);
			}
		});
		jMenu3.add(jMenuItem1);

		jMenuItem4.setFont(new java.awt.Font("微软雅黑", 0, 12));
		jMenuItem4.setForeground(new java.awt.Color(102, 102, 102));
		jMenuItem4.setIcon(new javax.swing.ImageIcon(getClass().getResource(
				"/com/etc/programe/point.gif"))); // NOI18N
		jMenuItem4.setText("\u91cd\u65b0\u767b\u5f55");
		jMenuItem4.setActionCommand("cxdl");
		jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				menuEvent(evt);
			}
		});
		jMenu3.add(jMenuItem4);

		jMenuItem2.setFont(new java.awt.Font("微软雅黑", 0, 12));
		jMenuItem2.setForeground(new java.awt.Color(102, 102, 102));
		jMenuItem2.setIcon(new javax.swing.ImageIcon(getClass().getResource(
				"/com/etc/programe/point.gif"))); // NOI18N
		jMenuItem2.setText("\u9000\u51fa\u7cfb\u7edf");
		jMenuItem2.setActionCommand("tcxt");
		jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				menuEvent(evt);
			}
		});
		jMenu3.add(jMenuItem2);

		jMenuBar2.add(jMenu3);

		jMenu4.setForeground(new java.awt.Color(102, 51, 0));
		jMenu4.setText("\u5173\u4e8e");

		jMenuItem3.setFont(new java.awt.Font("微软雅黑", 0, 12));
		jMenuItem3.setForeground(new java.awt.Color(102, 102, 102));
		jMenuItem3.setIcon(new javax.swing.ImageIcon(getClass().getResource(
				"/com/etc/programe/point.gif"))); // NOI18N
		jMenuItem3.setText("\u5173\u4e8e\u8f6f\u4ef6");
		jMenuItem3.setActionCommand("gyrj");
		jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				menuEvent(evt);
			}
		});
		jMenu4.add(jMenuItem3);

		jMenuBar2.add(jMenu4);

		setJMenuBar(jMenuBar2);

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
										.addContainerGap()
										.addComponent(
												jLabel3,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												70,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																jLabel4,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																149,
																Short.MAX_VALUE)
														.addComponent(
																jLabel5,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																149,
																Short.MAX_VALUE)))
						.addComponent(jSeparator2,
								javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addComponent(jScrollPane1,
								javax.swing.GroupLayout.DEFAULT_SIZE, 235,
								Short.MAX_VALUE));
		layout
				.setVerticalGroup(layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								layout.createSequentialGroup().addContainerGap(
										584, Short.MAX_VALUE).addComponent(
										jSeparator2,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										8,
										javax.swing.GroupLayout.PREFERRED_SIZE))
						.addGroup(
								layout
										.createSequentialGroup()
										.addGroup(
												layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(
																layout
																		.createSequentialGroup()
																		.addContainerGap()
																		.addComponent(
																				jLabel4)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
																		.addComponent(
																				jLabel5,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				19,
																				javax.swing.GroupLayout.PREFERRED_SIZE))
														.addComponent(jLabel3))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												jScrollPane1,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												529, Short.MAX_VALUE)));

		pack();
	}// </editor-fold>
	//GEN-END:initComponents

	private void menuEvent(java.awt.event.ActionEvent evt) {
		if (evt.getActionCommand().equals("yhxx")) {
			new PersonnerMessage(myInfo(), s);
		} else if (evt.getActionCommand().equals("cxdl")) {
			new Login();
			//			this.dispose();
		} else if (evt.getActionCommand().equals("tcxt")) {
			System.exit(0);
		} else if (evt.getActionCommand().equals("gyrj")) {
			new AboutFrm();
		}
	}

	String onlineUserName;
	String onlineUserIp;

	String allUserName;
	String allUserPwd;
	String allUserNichen;
	String allUserSex;
	String allUserPart;
	String allUserPhoneNum;
	String allUserEmail;
	String allUserJianjie;
	String allUserTouxiang;
	String touxiang;

	String partName;

	static ServerToClient o;

	@SuppressWarnings("static-access")
	public void find() {

		//找到在线好友的名字和IP
		o = (ServerToClient) this.object;
		OnlineUserArrayList online = o.getOnlineList();
		ArrayList<OnlineUserBean> onlineList = online.getOnlineUserList();

		//找到所有人的信息
		AllUserArrayList allUser = o.getAllUserList();
		ArrayList<UserInfoBean> allUserList = allUser.getAllUserList();

		//找到所有部门信息
		ArrayList<PartInfoBean> allPart = o.getAllPartList();

		Iterator<PartInfoBean> partIter = allPart.iterator();
		while (partIter.hasNext()) {
			partName = partIter.next().getPartName();
			DefaultMutableTreeNode root1 = new DefaultMutableTreeNode(partName);
			root.add(root1);

			Iterator<OnlineUserBean> onlineIter = onlineList.iterator();
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

					//在线好友在列表显示

					if (onlineUserName.equals(allUserName)) {
						Head h = new Head();
						h.setImageIcon(new ImageIcon(this.getClass()
								.getResource(allUserTouxiang)));
						h
								.setName("<html><font color = '121460' size='3' style='font-weight:bold'> "
										+ allUserNichen
										+ "&nbsp;&nbsp;</font><font color = '5f0b0b' size='3'>("
										+ allUserName
										+ ")</font>"
										+ "<br><font color = '41413d'>"
										+ onlineUserIp + "</font></html>");

						if (allUserPart.equals(partName)) {
							DefaultMutableTreeNode node1 = new DefaultMutableTreeNode(
									h);
							root1.add(node1);
						}
					}

					//用户自己的头像名字显示
					if (loginName.equals(allUserName)) {
						URL url = this.getClass().getResource("/"+ allUserTouxiang);
						ImageIcon image = new ImageIcon(url);
						jLabel3.setIcon(image);
						jLabel4.setText(allUserNichen);
						jLabel5.setText(onlineUserIp);
					}
				}
				DefaultTreeModel model = new DefaultTreeModel(root);
				jTree1 = new JTree(model);
			}
		}
		//展开树
		initJTree();
	}

	//把自己的资料封装成一个对象
	@SuppressWarnings("static-access")
	public UserInfoBean myInfo() {
		//找到所有人的信息
		o = (ServerToClient) this.object;
		AllUserArrayList allUser = o.getAllUserList();
		ArrayList<UserInfoBean> allUserList = allUser.getAllUserList();
		Iterator<UserInfoBean> allIter = allUserList.iterator();
		while (allIter.hasNext()) {
			UserInfoBean allUserInfo = allIter.next();
			String mName = allUserInfo.getName();
			if (loginName.equals(mName)) {
				return allUserInfo;
			}
		}
		return null;
	}

	//把好友的资料封装成一个对象
	@SuppressWarnings("static-access")
	public UserInfoBean friendInfo() {

		//得到选择的好友的真实姓名
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) jTree1
				.getLastSelectedPathComponent();
		Head head = (Head) node.getUserObject();
		String name = head.getName();
		String[] name1 = name.split("\\(");
		String name2 = name1[1];
		String[] name3 = name2.split("\\)");
		String friendName = name3[0];

		//找到所有人的信息
		o = (ServerToClient) this.object;
		AllUserArrayList allUser = o.getAllUserList();
		ArrayList<UserInfoBean> allUserList = allUser.getAllUserList();
		Iterator<UserInfoBean> allIter = allUserList.iterator();
		while (allIter.hasNext()) {
			UserInfoBean allUserInfo = allIter.next();
			String mName = allUserInfo.getName();
			if (friendName.equals(mName)) {
				return allUserInfo;
			}
		}
		return null;

	}

	//创建一个mapChatLog，将好友真实姓名保存为key，消息内容
	public void chatMap() {
		mapChatLog = new HashMap<String, String>();
	}

	//找到消息需要发送的聊天窗口并发送消息
	@SuppressWarnings("deprecation")
	public static void findChat() {
		String friendName = ReadThread.friendName;
		if (map.containsKey(friendName)) {

			Chat chat = map.get(friendName);

			Date date = new Date();
			int year = date.getYear();
			int month = date.getMonth() + 1;
			int day = date.getDate();
			int hours = date.getHours();
			int minites = date.getMinutes();
			int seconds = date.getSeconds();

			String friendNameDate = " " + headText() + "    " + (year - 100)
					+ "年" + month + "月" + day + "日  " + hours + ":" + minites
					+ ":" + seconds;

			if(ReadThread.flag == false) {

				chat.getjTextArea1().setText(
						chat.getjTextArea1().getText() + friendNameDate
								+ "\r\n" + "   " + ReadThread.content + "\r\n"
								+ "\r\n");
				resivestr = friendNameDate + "\r\n" + ReadThread.content
						+ "\r\n" + "\r\n";

			} else {
				for (int i = 0; i < 100; i++) {
					if (i % 2 == 0) {
						chat.setLocation(400, 250);
					} else {
						chat.setLocation(410, 260);
					}
				}

				chat.getjTextArea1().setText(
						chat.getjTextArea1().getText() + friendNameDate
								+ "\r\n" + "   " + ReadThread.content + "\r\n"
								+ "\r\n");
				resivestr = friendNameDate + "\r\n" + ReadThread.content
						+ "\r\n" + "\r\n";
			}

		} else {
			Chat chat = new Chat(headText(), s, loginName, friendName);

			//new出的窗口抖动
			for (int i = 0; i < 100; i++) {
				if (i % 2 == 0) {
					chat.setLocation(400, 250);
				} else {
					chat.setLocation(410, 260);
				}
			}

			Date date = new Date();
			int year = date.getYear();
			int month = date.getMonth() + 1;
			int day = date.getDate();
			int hours = date.getHours();
			int minites = date.getMinutes();
			int seconds = date.getSeconds();

			String friendNameDate = " " + headText() + "    " + (year - 100)
					+ "年" + month + "月" + day + "日  " + hours + ":" + minites
					+ ":" + seconds;
			chat.getjTextArea1().setText(
					friendNameDate + "\r\n" + "   " + ReadThread.content
							+ "\r\n" + "\r\n");

			map.put(friendName, chat);
			resivestr = friendNameDate + "\r\n" + ReadThread.content + "\r\n"
					+ "\r\n";
		}

		resiveChat();
	}

	//把接收的聊天消息保存到mapChatLog中
	public static void resiveChat() {
		if (mapChatLog.containsKey(ReadThread.friendName)) {
			String str = mapChatLog.get(ReadThread.friendName);
			str = str + resivestr;
			mapChatLog.put(ReadThread.friendName, str);
			chatLogs();
		} else {
			mapChatLog.put(ReadThread.friendName, resivestr);
			chatLogs();
		}
		resivestr = "";
	}

	//将发送的消息保存到本地txt
	public static void chatLogs() {
		File file = null;
		ObjectOutputStream oos = null;
		try {

			file = new File("src\\"+loginName+".txt");
			oos = new ObjectOutputStream(new FileOutputStream(file));

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("没有找到文件");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("IO异常");
			e.printStackTrace();
		}
		try {
			ChatLogMessage chatLogMessage = new ChatLogMessage();
			chatLogMessage.setMap(ClieneMainFrame.mapChatLog);
			oos.writeObject(chatLogMessage);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	//拿到好友的昵称
	public static String headText() {
		String friendName = ReadThread.friendName;

		AllUserArrayList allUser = o.getAllUserList();
		ArrayList<UserInfoBean> allUserList = allUser.getAllUserList();
		Iterator<UserInfoBean> allIter = allUserList.iterator();
		while (allIter.hasNext()) {
			UserInfoBean allUserInfo = allIter.next();
			String mName = allUserInfo.getName();
			if (friendName.equals(mName)) {
				friendNichen = allUserInfo.getNichen();
				return friendNichen;
			}
		}
		return null;
	}

	//展开树
	@SuppressWarnings("unchecked")
	public void initJTree() {
		//   得到根结点
		DefaultMutableTreeNode root = (DefaultMutableTreeNode) jTree1
				.getModel().getRoot();
		//   深度遍历整个树
		Enumeration df = root.depthFirstEnumeration();
		DefaultMutableTreeNode temp = null;
		while (df.hasMoreElements()) {
			temp = (DefaultMutableTreeNode) df.nextElement();
			//   如果不是叶子节点，则展开此结点
			if (!temp.isLeaf()) {
				jTree1.expandPath(new TreePath(temp.getPath()));
			}
		}
	}

	//拿到自己的昵称
	public static String myNichen() {
		String myName = loginName;

		AllUserArrayList allUser = o.getAllUserList();
		ArrayList<UserInfoBean> allUserList = allUser.getAllUserList();
		Iterator<UserInfoBean> allIter = allUserList.iterator();
		while (allIter.hasNext()) {
			UserInfoBean allUserInfo = allIter.next();
			String name = allUserInfo.getName();
			if (myName.equals(name)) {
				String myNi = allUserInfo.getNichen();
				return myNi;
			}
		}
		return null;
	}

	/**
	 * @param args the command line arguments
	 */
	//	public static void main(String args[]) {
	//		java.awt.EventQueue.invokeLater(new Runnable() {
	//			public void run() {
	//				new ClieneMainFrame().setVisible(true);
	//				//				new ClieneMainFrame(s,object).setVisible(true);
	//			}
	//		});
	//	}

	//GEN-BEGIN:variables
	// Variables declaration - do not modify
	private javax.swing.JMenu jMenu1;
	private javax.swing.JMenu jMenu2;
	private javax.swing.JMenu jMenu3;
	private javax.swing.JMenu jMenu4;
	private javax.swing.JMenuBar jMenuBar1;
	private javax.swing.JMenuBar jMenuBar2;
	private javax.swing.JMenuItem jMenuItem1;
	private javax.swing.JMenuItem jMenuItem2;
	private javax.swing.JMenuItem jMenuItem3;
	private javax.swing.JMenuItem jMenuItem4;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JSeparator jSeparator2;
	// End of variables declaration//GEN-END:variables
}