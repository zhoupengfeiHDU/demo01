package com.zpf.test.file;
 
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.springframework.stereotype.Component;
 
import java.io.File;
 
@Component
public class FileListenerExample extends FileAlterationListenerAdaptor {
   @Override
   public void onStart(FileAlterationObserver observer) {
      System.out.println("观察文件变化任务开始");
   }
 
   @Override
   public void onDirectoryCreate(File directory) {
      System.out.println("创建文件夹" + directory);
   }
 
   @Override
   public void onDirectoryChange(File directory) {
      System.out.println("修改文件夹" + directory);
   }
 
   @Override
   public void onDirectoryDelete(File directory) {
      System.out.println("删除文件夹" + directory);
   }
 
   @Override
   public void onFileCreate(File file) {
      System.out.println("创建文件" + file);
   }
 
   @Override
   public void onFileChange(File file) {
      System.out.println("修改文件" + file);
   }
 
   @Override
   public void onFileDelete(File file) {
      System.out.println("删除文件" + file);
   }
 
   @Override
   public void onStop(FileAlterationObserver observer) {
      System.out.println("观察文件变化任务结束");
   }
}