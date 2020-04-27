package com.mak.apps;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class Login extends JFrame implements ActionListener
{
    private final JTextField  text1,text2;
    Login()
    {
        JLabel label1 = new JLabel();
        label1.setText("Username:");
        text1 = new JTextField(15);

        JLabel label2 = new JLabel();
        label2.setText("Password:");
        text2 = new JPasswordField(15);

        JButton SUBMIT = new JButton("SUBMIT");

        JPanel panel = new JPanel(new GridLayout(3, 1));
        panel.add(label1);
        panel.add(text1);
        panel.add(label2);
        panel.add(text2);
        panel.add(SUBMIT);
        add(panel,BorderLayout.CENTER);
        SUBMIT.addActionListener(this);
        setTitle("LOGIN FORM");
    }
    public void actionPerformed(ActionEvent ae)
    {
        String value1=text1.getText();
        String value2=text2.getText();
        if (value1.equals("vivek") && value2.equals("vivek")) {
            this.dispose();
            DataHandler dataHandler = new DataHandler();
            dataHandler.setVisible(true);
        }
        else{
            System.out.println("enter the valid username and password");
            JOptionPane.showMessageDialog(this,"Incorrect login or password",
                    "Error",JOptionPane.ERROR_MESSAGE);
        }
    }
}
