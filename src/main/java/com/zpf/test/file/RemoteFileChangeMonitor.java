package com.zpf.test.file;

import com.jcraft.jsch.*;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class RemoteFileChangeMonitor {
    public static void main(String[] args) {

        //        String loginName = "root";
        //        String loginPassword = "Aaa000...";
        //        String server = "118.89.76.105";
        //        Integer port = 22;
        String username = "root";
        String password = "Aaa000...";
        String host = "118.89.76.105";
        int port = 22;
        String folderPath = "/home/zpf/sftp";

        try {
            JSch jsch = new JSch();
            Session session = jsch.getSession(username, host, port);
            session.setPassword(password);
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();

            Channel channel = session.openChannel("exec");
            ((ChannelExec) channel).setCommand("tail -f " + folderPath);
            channel.setInputStream(null);
            ((ChannelExec) channel).setErrStream(System.err);

            InputStream in = channel.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            channel.connect();

            String line;
            while ((line = reader.readLine()) != null) {
                // 处理文件变化事件
                System.out.println(line);
            }

            reader.close();
            channel.disconnect();
            session.disconnect();
        } catch (JSchException | java.io.IOException e) {
            e.printStackTrace();
        }
    }
}
