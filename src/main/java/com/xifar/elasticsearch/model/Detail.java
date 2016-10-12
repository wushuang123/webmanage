package com.xifar.elasticsearch.model;

import java.io.Serializable;

/***
 * @author wushuang
 * 
 * @note This Class is detail information of Data
 * 
 */

public class Detail implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5421858571309035174L;

	/** index:对应关系型数据库中的库 **/
	private String _index;
	/** type:对应关系型数据库中的表 **/
	private String _type;
	/** id:对应关系型数据库中的主键 **/
	private String _id;
	/** 相关性评分 **/
	private double _score;
	/** 数据 **/
	private Object _source;
	
	public String get_index() {
		return _index;
	}
	public void set_index(String _index) {
		this._index = _index;
	}
	public String get_type() {
		return _type;
	}
	public void set_type(String _type) {
		this._type = _type;
	}
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	public double get_score() {
		return _score;
	}
	public void set_score(double _score) {
		this._score = _score;
	}
	public Object get_source() {
		return _source;
	}
	public void set_source(Object _source) {
		this._source = _source;
	}

}
