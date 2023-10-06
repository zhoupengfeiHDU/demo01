package com.zpf.test.file;

import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;

import java.util.concurrent.TimeUnit;

public class FileMonitorTest {

    public static void main(String[] args) throws Exception {
        //Monitor Directory
        String absolateDir = "E:\\sftp\\file";
        //Interval time 5 seconds
        long intervalTime = TimeUnit.SECONDS.toMillis(5);
        //Sample 1:  Create a file observer to process file type
        /*FileAlterationObserver observer1 = new FileAlterationObserver(
                absolateDir,
                FileFilterUtils.and(
                        FileFilterUtils.fileFileFilter(),
                        FileFilterUtils.suffixFileFilter("*.txt")), //filter file type
                null);*/


        //Sample 2:  Create a file Observer to work with file type, eg： Scan the "log" folder under C, and the new file ending with the suffix ".success"
        FileAlterationObserver observer2 = new FileAlterationObserver(absolateDir, FileFilterUtils.and(
                FileFilterUtils.fileFileFilter(), FileFilterUtils.suffixFileFilter(".txt"), FileFilterUtils.prefixFileFilter("aa")));

        FileAlterationObserver observer = new FileAlterationObserver(absolateDir);

        //Set file change listener
        observer2.addListener(new FileListenerExample());
        //Create file change monitor
        FileAlterationMonitor monitor = new FileAlterationMonitor(intervalTime, observer2);
        //start monitor
        monitor.start();

        //Thread.sleep(30000);
        //monitor.stop();

    }
}