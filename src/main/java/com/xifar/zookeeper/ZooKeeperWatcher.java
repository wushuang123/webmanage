package com.xifar.zookeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.AsyncCallback.StringCallback;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

public class ZooKeeperWatcher implements Watcher {

	private static CountDownLatch connectedSemaphore = new CountDownLatch(1);

	@Override
	public void process(WatchedEvent event) {
		// TODO Auto-generated method stub
		System.out.println(event);
		if (KeeperState.SyncConnected == event.getState()) {
			// connectedSemaphore.countDown();
		}

	}

	public static void main(String[] args) {
		Watcher watcher = new ZooKeeperWatcher();
		ZooKeeper zookeeper = null;
		try {
			zookeeper = new ZooKeeper("192.168.177.133:2181", 5000, watcher);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e);
		}
		System.out.println(zookeeper.getState());
		long sessionid = zookeeper.getSessionId();
		byte[] password = zookeeper.getSessionPasswd();
		System.out.println("sessionid:" + sessionid);
		System.out.println("password:" + password);

		System.out.println("zookeeper session established");

		try {
			String path1 = zookeeper.create("/test", "123".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
			String path2 = zookeeper.create("/junk", "231".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
			System.out.println("create node path1:" + path1);
			System.out.println("create node path2:" + path2);
			zookeeper.create("/dock", "ooooo".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT, new IAsynCallBack(), "I am OK");
		} catch (KeeperException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			connectedSemaphore.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}

class IAsynCallBack implements AsyncCallback.StringCallback {

	public void processResult(int rc, String path, Object ctx, String name) {
		System.out.println("asyn path:" + path);
		System.out.println("ctx:" + ctx);
	}
}
