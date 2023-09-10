package com.zpf.test.utils;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.*;

/**
 * @author: void
 * @date: 2021-10-15 16:08
 * @description: sftp服务器文件操作
 * @version: 1.0
 */
@Slf4j
public class SftpUtil {

    /*private String loginName = "root";
    private String loginPassword = "Aaa000...";
    private String server = "118.89.76.105";
    private Integer port = 22;*/

    private final String loginName;
    private final String loginPassword;
    private final String server;
    private final Integer port;

    public SftpUtil(String loginName, String loginPassword, String server, Integer port) {
        this.loginName = loginName;
        this.loginPassword = loginPassword;
        this.server = server;
        this.port = port;
    }

    public static void main(String[] args) {
        String loginName = "root";
        String loginPassword = "Aaa000...";
        String server = "118.89.76.105";
        Integer port = 22;
        SftpUtil sftpUtil = new SftpUtil(loginName, loginPassword, server, port);
        //上传文件
        sftpUtil.uploadFile();
        //下载文件
        sftpUtil.downloadFile();
        //写文件
        sftpUtil.writeFile();
        //读文件
        sftpUtil.readFile();
        //删除文件
        sftpUtil.deleteFile();


    }


    /**
     * 连接登陆远程服务器
     *
     * @return
     */
    public ChannelSftp connect() {
        JSch jSch = new JSch();
        Session session = null;
        ChannelSftp sftp = null;
        try {
            session = jSch.getSession(loginName, server, port);
            session.setPassword(loginPassword);
            session.setConfig(this.getSshConfig());
            session.connect();

            sftp = (ChannelSftp) session.openChannel("sftp");
            sftp.connect();

            log.error("结果：" + session.equals(sftp.getSession()));
            log.info("登录成功:" + sftp.getServerVersion());

        } catch (Exception e) {
            log.error("SSH方式连接FTP服务器时有JSchException异常!", e);
            return null;
        }
        return sftp;
    }

    /**
     * 获取服务配置
     *
     * @return
     */
    private Properties getSshConfig() {
        Properties sshConfig = new Properties();
        sshConfig.put("StrictHostKeyChecking", "no");
        return sshConfig;
    }


    /**
     * 关闭连接
     *
     * @param sftp
     */
    public void disconnect(ChannelSftp sftp) {
        try {
            if (sftp != null) {
                if (sftp.getSession().isConnected()) {
                    sftp.getSession().disconnect();
                }
            }
        } catch (Exception e) {
            log.error("关闭与sftp服务器会话连接异常", e);
        }
    }


    /**
     * 上传文件至sftp服务器
     *
     * @return
     */
    public void uploadFile() {
        FileInputStream fis = null;
        ChannelSftp sftp = null;
        // 上传文件至服务器此目录
        String localPath = "E:\\sftp\\upload\\video.mp4";
        //String remoteFilename = "/home/zpf/sftp/upload/上传至sftp服务器.txt";
        String remoteFilename = "/home/zpf/sftp/upload/上传至sftp服务器.mp4";
        try {
            sftp = connect();
            if (sftp == null) {
                return;
            }

            File localFile = new File(localPath);
            fis = new FileInputStream(localFile);
            //发送文件
            sftp.put(fis, remoteFilename);
            log.info("成功上传文件");
        } catch (Exception e) {
            log.error("上传文件时异常!", e);
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
                // 关闭连接
                disconnect(sftp);
            } catch (Exception e) {
                log.error("关闭文件时出错!", e);
            }
        }
    }


    /**
     * 下载远程sftp服务器文件
     *
     * @return
     */
    public void downloadFile() {
        FileOutputStream output = null;
        ChannelSftp sftp = null;
        try {
            sftp = connect();
            if (sftp == null) {
                return;
            }
            //sftp服务器上文件路径
            String remoteFilename = "/home/zpf/sftp/download/remoteFile.txt";
            //下载至本地路径
            File localFile = new File("E:\\sftp\\download\\从sftp服务器上下载.txt");
            output = new FileOutputStream(localFile);

            sftp.get(remoteFilename, output);
            System.out.println("成功接收文件,本地路径：" + localFile.getAbsolutePath());
        } catch (Exception e) {
            log.error("接收文件异常!", e);
        } finally {
            try {
                if (null != output) {
                    output.flush();
                    output.close();
                }
                // 关闭连接
                disconnect(sftp);
            } catch (IOException e) {
                log.error("关闭文件时出错!", e);
            }
        }
    }


    /**
     * 读取远程sftp服务器文件
     *
     * @return
     */
    public void readFile() {
        InputStream in = null;
        ArrayList<String> strings = new ArrayList<>();
        ChannelSftp sftp = null;
        try {
            sftp = connect();
            if (sftp == null) {
                return;
            }
            String remotePath = "/home/zpf/sftp/read/";
            String remoteFilename = "readFile.txt";
            sftp.cd(remotePath);
            if (!listFiles(remotePath).contains(remoteFilename)) {
                log.error("no such file");
                return;
            }
            in = sftp.get(remoteFilename);
            if (in != null) {
                BufferedReader br = new BufferedReader(new InputStreamReader(in, "utf-8"));
                String str = null;
                while ((str = br.readLine()) != null) {
                    System.out.println(str);
                }
            } else {
                log.error("in为空，不能读取。");
            }
        } catch (Exception e) {
            log.error("接收文件时异常!", e);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                // 关闭连接
                disconnect(sftp);
            } catch (Exception e) {
                log.error("关闭文件流时出现异常!", e);
            }
        }
    }


    /**
     * 写文件至远程sftp服务器
     *
     * @return
     */
    public void writeFile() {
        ChannelSftp sftp = null;
        ByteArrayInputStream input = null;
        try {
            sftp = connect();
            if (sftp == null) {
                return;
            }
            // 更改服务器目录
            String remotePath = "/home/zpf/sftp/write/";
            sftp.cd(remotePath);
            // 发送文件
            String remoteFilename = "writeFile.txt";
            String content = "测试内容";
            input = new ByteArrayInputStream(content.getBytes());
            sftp.put(input, remoteFilename);
        } catch (Exception e) {
            log.error("发送文件时有异常!", e);
        } finally {
            try {
                if (null != input) {
                    input.close();
                }
                // 关闭连接
                disconnect(sftp);
            } catch (Exception e) {
                log.error("关闭文件时出错!", e);
            }
        }
    }


    /**
     * 遍历远程文件
     *
     * @param remotePath
     * @return
     * @throws Exception
     */
    public List<String> listFiles(String remotePath) {
        List<String> ftpFileNameList = new ArrayList<String>();
        ChannelSftp.LsEntry isEntity = null;
        String fileName = null;
        ChannelSftp sftp = null;
        try {
            sftp = connect();
            if (sftp == null) {
                return null;
            }
            Vector<ChannelSftp.LsEntry> sftpFile = sftp.ls(remotePath);
            Iterator<ChannelSftp.LsEntry> sftpFileNames = sftpFile.iterator();
            while (sftpFileNames.hasNext()) {
                isEntity = (ChannelSftp.LsEntry) sftpFileNames.next();
                fileName = isEntity.getFilename();
                ftpFileNameList.add(fileName);
            }
            return ftpFileNameList;
        } catch (Exception e) {
            log.error("遍历查询sftp服务器上文件异常", e);
            return null;
        } finally {
            disconnect(sftp);
        }

    }


    /**
     * 删除远程文件
     *
     * @return
     */
    public void deleteFile() {
        boolean success = false;
        ChannelSftp sftp = null;
        try {
            sftp = connect();
            if (sftp == null) {
                return;
            }
            String remotePath = "//home/zpf/sftp/delete/";
            String remoteFilename = "delete.txt";
            // 更改服务器目录
            sftp.cd(remotePath);
            //判断文件是否存在
            if (listFiles(remotePath).contains(remoteFilename)) {
                // 删除文件
                sftp.rm(remoteFilename);
                log.info("删除远程文件" + remoteFilename + "成功!");
            }

        } catch (Exception e) {
            log.error("删除文件时有异常!", e);
        } finally {
            // 关闭连接
            disconnect(sftp);
        }
    }
}