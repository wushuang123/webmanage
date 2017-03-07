package com.xifar.zookeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

public class Connector {
	
	public static void main(String[] args){
		Watcher watcher = new ZooKeeperWatcher();
		ZooKeeper zookeeper = null;
		try {
			zookeeper = new ZooKeeper("192.168.177.128:2181", 5000, watcher);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		System.out.println(zookeeper.getState());
	}
	

}
