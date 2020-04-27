package com.mak.apps;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        try
        {
            Login frame=new Login();
            frame.setBounds(200,200,300,100);
            frame.setVisible(true);
            frame.setResizable(false);
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, e.getMessage());}
    }
}
