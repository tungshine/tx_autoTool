/*
 * AboutFrm.java
 *
 * Created on __DATE__, __TIME__
 */

package com.tanglover.communication.client;

import java.awt.Container;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author  __USER__
 */
public class AboutFrm extends javax.swing.JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** Creates new form AboutFrm */
	public AboutFrm() {
		initComponents();
		URL url = this.getClass().getResource("/about2.jpg");
		ImageIcon img = new ImageIcon(url);//���Ǳ���ͼƬ    
		JLabel imgLabel = new JLabel(img);//������ͼ���ڱ�ǩ�  

		Container cp = this.getContentPane();
		cp.add(imgLabel);
		this.getLayeredPane().add(imgLabel, new Integer(Integer.MIN_VALUE));//ע�������ǹؼ�����������ǩ��ӵ�jfram��LayeredPane����  
		imgLabel.setBounds(0, 0, img.getIconWidth(), img.getIconHeight());//���ñ�����ǩ��λ��  
		// this.setSize(800, 500);
		this.setVisible(true);

		((JPanel) getContentPane()).setOpaque(false); //ע����������������Ϊ͸��������LayeredPane����еı���������ʾ������ 

		this.setResizable(false);
		
		Toolkit tool = getToolkit(); //�õ�һ��Toolkit���� 
		URL url1 = this.getClass().getResource("/logoo.jpg");
		Image myimage = tool.getImage(url1); //��tool��ȡͼ��  
	    setIconImage(myimage);
	    
	    setLocation(((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth()-850), 300);

	}

	//GEN-BEGIN:initComponents
	// <editor-fold defaultstate="collapsed" desc="Generated Code">
	private void initComponents() {
		URL url2 = this.getClass().getResource("/gy.jpg");
		ImageIcon image = new ImageIcon(url2);
		URL url3 = this.getClass().getResource("/xiangxi.jpg");
		ImageIcon image1 = new ImageIcon(url3);
		jlabel1 = new JLabel(image);
		jlabel2 = new JLabel(image1);
		jTabbedPane1 = new javax.swing.JTabbedPane();
		jTabbedPane2 = new javax.swing.JTabbedPane();
		jTabbedPane5 = new javax.swing.JTabbedPane();
		
		

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("\u5173\u4e8e");


		jTabbedPane1.addTab("����", jTabbedPane2);
//		jTabbedPane5.setBackground(new java.awt.Color(249, 255, 249));
//		jTabbedPane5.setName("\u5173\u4e8e");
		jTabbedPane1.addTab("��ϸ��Ϣ", jTabbedPane5);	
		

		
		jTabbedPane2.add(jlabel1);
		jTabbedPane5.add(jlabel2);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addComponent(
				jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 391,
				Short.MAX_VALUE));
		layout.setVerticalGroup(layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addComponent(
				jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 305,
				Short.MAX_VALUE));

		pack();
	}// </editor-fold>
	//GEN-END:initComponents

	/**
	 * @param args the command line arguments
	 */
	public static void main(String args[]) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new AboutFrm().setVisible(true);
			}
		});
	}

	//GEN-BEGIN:variables
	// Variables declaration - do not modify
	private JLabel jlabel1;
	private JLabel jlabel2;
	private javax.swing.JTabbedPane jTabbedPane1;
	private javax.swing.JTabbedPane jTabbedPane2;
	private javax.swing.JTabbedPane jTabbedPane5;
	// End of variables declaration//GEN-END:variables

}