package com.mak.apps;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import okhttp3.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class DataHandler extends JFrame {
    private JList<String> data_list;
    private JLabel label = new JLabel("Selected image");
    private DefaultListModel<String> l1;

    private OkHttpClient client;
    DataHandler(){
        client = new OkHttpClient();
        l1 = new DefaultListModel<>();
        refreshData();
        data_list = new JList<>(l1);
        data_list.setBounds(0,0, 250,400);
        add(data_list);
        label.setBounds(270,0,100,50);
        JButton upload_file = new JButton("upload");
        upload_file.setBounds(270,60,100,50);
        JButton refresh_list = new JButton("refresh");
        refresh_list.setBounds(270,120,100,50);
        JButton delete = new JButton("delete");
        delete.setBounds(270,180,100,50);
        add(upload_file);
        add(refresh_list);
        add(delete);
        refresh_list.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshData();
            }
        });

        upload_file.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser file = new JFileChooser();
                file.setCurrentDirectory(new File(System.getProperty("user.home")));
                //filter the files
                FileNameExtensionFilter filter = new FileNameExtensionFilter("*.Images", "jpg","gif","png");
                file.addChoosableFileFilter(filter);
                int result = file.showSaveDialog(null);
                //if the user click on save in Jfilechooser
                if(result == JFileChooser.APPROVE_OPTION){
                    File selectedFile = file.getSelectedFile();
                    String path = selectedFile.getAbsolutePath();
                    label.setText(path);
                    File f = new File(path);
                    String server_url = "http://localhost/kms/upload.php";
                    if (uploadFile(server_url,f)){
                        System.out.println("Done");
                        refreshData();
                    } else {
                        JOptionPane.showMessageDialog(null, "ERROR");
                        System.out.println("Error");
                    }
                }
                //if the user click on save in Jfilechooser
                else if(result == JFileChooser.CANCEL_OPTION){
                    System.out.println("No File Select");

                    JOptionPane.showMessageDialog(null, "No file selected");
                }
            }
        });

        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selected = data_list.getSelectedValue();
                RequestBody requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("filename", selected)
                        .build();

                Request request = new Request.Builder()
                        .url("http://localhost/kms/delete.php")
                        .post(requestBody)
                        .build();
                try {
                    Response response =client.newCall(request).execute();
                    System.out.println(response.body().string());
                    refreshData();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            }
        });
        setSize(400,400);
        setResizable(false);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void refreshData() {
        l1.removeAllElements();
        Request request = new Request.Builder()
                .url("http://localhost/kms/file_list.php")
                .build();
        try {
            Response response = client.newCall(request).execute();
            Gson gson = new Gson();
            String body = response.body().string();
            java.lang.reflect.Type listType = new TypeToken<List<String>>(){}.getType();
            List<String> posts = gson.fromJson(body, listType);
            for (String s:posts){
                l1.addElement(s);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public Boolean uploadFile(String serverURL, File file) {
        try {

            RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                    .addFormDataPart("file", file.getName(),
                            RequestBody.create(MediaType.parse("image/*"), file))
                    .addFormDataPart("Submit", "somevalue")
                    .build();

            Request request = new Request.Builder()
                    .url(serverURL)
                    .post(requestBody)
                    .build();

            client.newCall(request).enqueue(new Callback() {

                @Override
                public void onFailure(final Call call, final IOException e) {
                    JOptionPane.showMessageDialog(null, e.getMessage());
                }

                @Override
                public void onResponse(final Call call, final Response response) throws IOException {
                    if (!response.isSuccessful()) {
                        JOptionPane.showMessageDialog(null, "Respone unsuccessful");
                    }
                    System.out.println(response.body().string());
                }
            });

            return true;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return false;
    }
}
