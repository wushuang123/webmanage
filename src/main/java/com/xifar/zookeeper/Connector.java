package com.xifar.zookeeper;

import java.io.IOException;

import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

public class Connector {
	
	public static void main(String[] args){
		Watcher watcher = new ZooKeeperWatcher();
		ZooKeeper zookeeper = null;
		try {
			zookeeper = new ZooKeeper("192.168.177.133:2181", 5000, watcher);
			System.out.println(zookeeper.getState());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e);
		}
	}
	

}
