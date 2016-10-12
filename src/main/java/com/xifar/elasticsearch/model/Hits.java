package com.xifar.elasticsearch.model;

import java.io.Serializable;
import java.util.List;

public class Hits implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1391206413073402123L;
	
	/**总数**/
	private long total;
	/**评分**/
	private double max_score;
	/**数据**/
	private List<Detail> hits;
	
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	public double getMax_score() {
		return max_score;
	}
	public void setMax_score(double max_score) {
		this.max_score = max_score;
	}
	public List<Detail> getHits() {
		return hits;
	}
	public void setHits(List<Detail> hits) {
		this.hits = hits;
	}

}
