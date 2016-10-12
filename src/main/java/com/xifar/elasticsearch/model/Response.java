package com.xifar.elasticsearch.model;

import java.io.Serializable;

public class Response implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6809676053260681807L;

	/** 毫秒数 **/
	private long took;
	/** 是否超时 **/
	private Boolean timed_out;
	/** 分片信息 **/
	private Shards _shards;
	/** 数据信息 **/
	private Hits hits;
	
	public long getTook() {
		return took;
	}
	public void setTook(long took) {
		this.took = took;
	}
	public Boolean getTimed_out() {
		return timed_out;
	}
	public void setTimed_out(Boolean timed_out) {
		this.timed_out = timed_out;
	}
	public Shards get_shards() {
		return _shards;
	}
	public void set_shards(Shards _shards) {
		this._shards = _shards;
	}
	public Hits getHits() {
		return hits;
	}
	public void setHits(Hits hits) {
		this.hits = hits;
	}
}
