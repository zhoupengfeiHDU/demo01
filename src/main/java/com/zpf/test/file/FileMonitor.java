package com.zpf.test.file;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.watch.WatchMonitor;
import cn.hutool.core.io.watch.Watcher;
import cn.hutool.core.lang.Console;
import cn.hutool.core.util.ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.WatchEvent;

/**
 * @Author: zongshaofeng
 * @Description: 文件目录监控
 * @Date:Create：in 2021/10/31 14:58
 * @Modified By：
 */
@Slf4j
@Component
public class FileMonitor {
	
	@PostConstruct
	public static void fileCreateMonitor() {
		// 指定监听目录，实际应用需要由外部配置文件进行配置
		String dirPath = "E:\\sftp\\file";
		File file = FileUtil.file(dirPath);
		if (ObjectUtil.isNull(file) && !file.exists()) {
			log.info("监听目录：{}，不存在，请检查并修改配置文件后重启程序。", dirPath);
			return;
		}
		// 只监听目录的创建事件
		WatchMonitor watchMonitor = WatchMonitor.create(file, WatchMonitor.EVENTS_ALL);
		watchMonitor.setWatcher(new Watcher() {
			@Override
			public void onCreate(WatchEvent<?> watchEvent, Path path) {
				// 在监听创建事件回调中进行业务处理
				// TODO 拿到文件进行IO操作，具体业务具体分析
				// 此处简单打印一下目录路径和文件名称
				Object context = watchEvent.context();
				log.info("创建：{}-->{}", path, context);
			}
			
			@Override
			public void onModify(WatchEvent<?> watchEvent, Path path) {
				// 在监听创建事件回调中进行业务处理
				// TODO 拿到文件进行IO操作，具体业务具体分析
				// 此处简单打印一下目录路径和文件名称
				Object context = watchEvent.context();
				log.info("修改：{}-->{}", path, context);
			}
			
			@Override
			public void onDelete(WatchEvent<?> watchEvent, Path path) {
				// 在监听创建事件回调中进行业务处理
				// TODO 拿到文件进行IO操作，具体业务具体分析
				// 此处简单打印一下目录路径和文件名称
				Object context = watchEvent.context();
				log.info("删除：{}-->{}", path, context);
			}
			
			@Override
			public void onOverflow(WatchEvent<?> watchEvent, Path path) {
				// 在监听创建事件回调中进行业务处理
				// TODO 拿到文件进行IO操作，具体业务具体分析
				// 此处简单打印一下目录路径和文件名称
				Object context = watchEvent.context();
				log.info("事件丢失：{}-->{}", path, context);
			}
		});
		// 设置监听目录的最大深度，目录层级大于指定层级的变更将不再监听，不设置默认只监听当前目录
		watchMonitor.setMaxDepth(3);
		// 启动监听
		watchMonitor.start();
	}

	public static void main(String[] args) {
		fileCreateMonitor();
	}
}

