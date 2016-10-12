
/**
 * @author wushuang
 *
 *
 * @note 摘取fastJson部分代码,封装JSON转换和解析方法,实现底层包可以随意替换(暂时支持fastJson 和 Gson)
 *
 */
package com.xifar.common.utils.json;

/**
 * JsonHelper 使用方法
 * 
 * fastJson
 * 
 * 序列化: JsonHelper.serialize(Object);
 * 
 * 对象反序列化: JsonHelper.deserialize(JSON, obj.Class);
 * 
 * 泛型反序列化: JsonHelper.deserialize(JSON, new TypeReferences<List<String>>() {}.getType()););
 * 
 * GSON
 * 
 * 序列化: JsonHelper.toJson(Object);
 * 
 * 对象反序列化: JsonHelper.fromJson(JSON, obj.Class);
 * 
 * 泛型反序列化: JsonHelper.fromJson(JSON, new TypeReferences<List<String>>() {}.getType()););
 * 
 * 
 *
 */