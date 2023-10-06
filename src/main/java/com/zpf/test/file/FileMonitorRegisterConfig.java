package com.zpf.test.file;
 
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.springframework.stereotype.Component;
 
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.File;
 
@Component
public class FileMonitorRegisterConfig {
   @Resource
   private FileListenerMonitorUtils fileListenerMonitorUtils ;
 
   @PostConstruct
   private void register() {
      File directory = new File("E:\\sftp\\file");
      if (!directory.exists()) {
         throw new NullPointerException("目录不存在启动文件观察者失败:" + directory);
      }
      Long intervalSeconds = 5L;
      String prefix = "test_";
      String suffix = ".json";
      try {
         // 为指定文件夹下面的指定文件注册文件观察者
         FileAlterationMonitor monitor = fileListenerMonitorUtils.getMonitor(directory, intervalSeconds, prefix, suffix);
         // 启动观察者
         monitor.start();
      } catch (Exception e) {
         e.printStackTrace();
      }
   }
}