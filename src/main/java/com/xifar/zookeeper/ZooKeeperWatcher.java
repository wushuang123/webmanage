package com.xifar.zookeeper;

import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;

public class ZooKeeperWatcher implements Watcher{

	private static CountDownLatch connectedSemaphore = new CountDownLatch(1);
	@Override
	public void process(WatchedEvent event) {
		// TODO Auto-generated method stub
		System.out.println(event.getPath());
		try {
			connectedSemaphore.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(KeeperState.SyncConnected == event.getState()){
			connectedSemaphore.countDown();
		}
	}

}
