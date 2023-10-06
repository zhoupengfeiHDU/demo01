package com.zpf.test.file;

import cn.hutool.core.io.watch.SimpleWatcher;
import cn.hutool.core.io.watch.WatchMonitor;
import cn.hutool.core.io.watch.watchers.DelayWatcher;
import cn.hutool.extra.ssh.JschUtil;
import cn.hutool.extra.ssh.Sftp;
import com.jcraft.jsch.JSchException;

import java.nio.file.Path;
import java.nio.file.WatchEvent;

public class FileMonitorTest2 {
    public static void main(String[] args) throws JSchException {
        String host = "your_remote_host";
        int port = 22;
        String username = "your_username";
        String password = "your_password";
        String remoteFolderPath = "/path/to/remote_folder";

        Sftp sftp = JschUtil.createSftp(host, port, username, password);

        WatchMonitor.createAll(remoteFolderPath, new DelayWatcher(new SimpleWatcher() {
            @Override
            public void onCreate(WatchEvent<?> event, Path currentPath) {
                // 处理文件创建事件逻辑
                System.out.println("新文件被创建：" + currentPath);
            }

            // 处理其他事件逻辑（如修改、删除等）

            // ...
        })).start();
    }
}
