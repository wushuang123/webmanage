package com.xifar.elasticsearch.model;

import java.io.Serializable;
/**
 * @author wushuang
 * 
 * */
public class Shards implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1433489476183243224L;
	
	/**总分片数**/
	private long total;
	/**查找成功数量**/
	private long successful;
	/**失败数量**/
	private long failed;
	
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	public long getSuccessful() {
		return successful;
	}
	public void setSuccessful(long successful) {
		this.successful = successful;
	}
	public long getFailed() {
		return failed;
	}
	public void setFailed(long failed) {
		this.failed = failed;
	}

}
