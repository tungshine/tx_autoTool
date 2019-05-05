package com.tanglover.barrage;

import javax.swing.*;

/**
 * @author TangXu
 * @create 2019-05-04 19:48
 * @description:
 */
public class Login {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Login");
        frame.setContentPane(new Login().loginPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private JPanel loginPanel;
    private JLabel accountLabel;
    private JTextField account;
    private JPasswordField password;
    private JLabel passwordLabel;
}