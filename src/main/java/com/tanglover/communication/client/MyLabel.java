package com.tanglover.communication.client;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeCellRenderer;





public class MyLabel extends JPanel implements TreeCellRenderer{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public MyLabel(){
		setOpaque(true);
	}
	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value,
			boolean selected, boolean expanded, boolean leaf, int row,
			boolean hasFocus) {
		this.setLayout(new FlowLayout());
		JLabel imageLabel = new JLabel();
		JLabel textLabel = new JLabel();
		if(value != null){
			this.removeAll();
			DefaultMutableTreeNode node = (DefaultMutableTreeNode)value;
			Object o = node.getUserObject();
			if(o instanceof Head){
				Head head = (Head)o;
				imageLabel.setIcon(head.getImageIcon());
				textLabel.setText(head.getName());
				textLabel.setFont(new java.awt.Font("΢���ź�", 0, 12));
				this.add(imageLabel);
				this.add(textLabel);
			}
			if(o instanceof String){
				textLabel.setText((String)o);
				this.add(textLabel);
			}
		}
		if(selected){
			this.setBackground(new Color(173, 209, 239));
		}else{
			this.setBackground(new Color(229, 241, 251));
		}
		this.setFont(new java.awt.Font("΢���ź�", 0, 12));
		return this;
	}

}
