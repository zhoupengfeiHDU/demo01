package com.zpf.test.sftp;

import cn.hutool.extra.ssh.Sftp;
import com.jcraft.jsch.ChannelSftp;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.List;

@Slf4j
public class SftpConnectExample {
    public static void main(String[] args) {

        String loginName = "root";
        String loginPassword = "Aaa000...";
        String server = "118.89.76.105";
        Integer port = 22;
        // 创建Sftp对象
        Sftp sftp = new Sftp(server, port, loginName, loginPassword);

        // 连接服务器
        String home = sftp.home();
        log.info("home地址:{}", home);
        // 上传文件至服务器此目录
        String localPath = "E:\\sftp\\upload\\video.mp4";
        String downloadLocalPath = "E:\\sftp\\download\\video1.mp4";
        //String remoteFilename = "/home/zpf/sftp/upload/上传至sftp服务器.txt";
        String remoteFilename = "/home/zpf/sftp/upload/上传至sftp服务器1.mp4";
        /*boolean upload = sftp.upload(remoteFilename, new File(localPath));
        log.info("上传状态：{}", upload);*/
        boolean cd = sftp.cd("/home/zpf/sftp/upload/");
        List<String> list = sftp.ls("/home/zpf/sftp/upload/");
        List<ChannelSftp.LsEntry> lsEntries = sftp.lsEntries("/home/zpf/sftp/upload/");
        List<String> strings = sftp.lsFiles("/home/zpf/sftp/upload/");
        List<String> strings1 = sftp.lsDirs("/home/zpf/sftp/upload");
        //Sftp put = sftp.put(localPath, "/home/zpf/sftp/upload");
        log.info("下载开始时间{}", System.currentTimeMillis());
        Sftp sftp1 = sftp.get("/home/zpf/sftp/upload/上传至sftp服务器1.mp4", downloadLocalPath);
        log.info("下载结束时间{}", System.currentTimeMillis());
        log.info("下载开始时间{}", System.currentTimeMillis());
        sftp.download("/home/zpf/sftp/upload/上传至sftp服务器1.mp4", new File(downloadLocalPath));
        log.info("下载结束时间{}", System.currentTimeMillis());
        // 关闭连接
        sftp.close();
    }
}
