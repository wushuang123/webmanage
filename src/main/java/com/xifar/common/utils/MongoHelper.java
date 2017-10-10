package com.xifar.common.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bson.BsonValue;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.result.UpdateResult;

public class MongoHelper {

	private static final Logger log = LoggerFactory.getLogger(MongoHelper.class);

	private final static ThreadLocal<MongoClient> mongoClient = new ThreadLocal<MongoClient>();

	/**
	 * @param map
	 *            插入mongo的键值对
	 * 
	 * @param databaseName
	 *            mongo的库名
	 * 
	 * @param collectionName
	 *            mongo中的集合名
	 * 
	 **/
	public static boolean insert(String host, int port, String databaseName, String collectionName,
			Map<String, Object> map) {
		boolean result = false;
		MongoClient tempMongoClient = new MongoClient(host, port);
		try {
			mongoClient.set(tempMongoClient);
			MongoDatabase database = tempMongoClient.getDatabase(databaseName);
			MongoCollection<Document> collection = database.getCollection(collectionName);

			Document queryDocument = new Document();
			queryDocument.put("_id", UUID.randomUUID());
			Document insertDocument = new Document();

			insertDocument.put("$set", map);

			// 设置此参数如果没有这个条记录则插入
			UpdateOptions options = new UpdateOptions();
			options.upsert(true);

			UpdateResult updateResult = collection.updateOne(queryDocument, insertDocument, options);
			long modifiedCount = updateResult.getModifiedCount();
			BsonValue upsertedId = updateResult.getUpsertedId();
			if (modifiedCount == 1 || upsertedId != null) {
				log.info("插入mongodb一条数据");
				result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("插入mongodb报错,异常为" + e.getMessage());
		} finally {
			tempMongoClient.close();
		}
		return result;
	}

	/**
	 * @param parameter
	 *            查询的参数
	 * 
	 * @param databaseName
	 *            数据库名
	 * 
	 * @param collectionName
	 *            集合名
	 * 
	 * @param sortCriteria
	 *            key为排序字段名 value为1，正序;value为-1,倒序
	 **/
	public static List<Map<String, Object>> query(String host, int port, Map<String, Object> parameter,
			String databaseName, String collectionName, Map<String, Integer> sortCriteria) {

		List<Map<String, Object>> list = null;
		MongoClient tempMongoClient = new MongoClient(host, port);
		try {
			mongoClient.set(tempMongoClient);
			MongoDatabase database = tempMongoClient.getDatabase(databaseName);
			MongoCollection<Document> collection = database.getCollection(collectionName);
			list = new ArrayList<Map<String, Object>>();
			FindIterable<Document> resultIterable = null;
			if (null != sortCriteria && sortCriteria.size() != 0) {
				BasicDBObject dbObject = new BasicDBObject();
				for (Map.Entry<String, Integer> entry : sortCriteria.entrySet()) {
					entry.getValue();
					dbObject.put(entry.getKey(), entry.getValue());
				}
				if (null == parameter || parameter.size() == 0) {
					resultIterable = collection.find().sort(dbObject);
				} else {
					resultIterable = collection.find(new Document(parameter)).sort(dbObject);
				}
			} else {
				if (null == parameter || parameter.size() == 0) {
					resultIterable = collection.find();
				} else {
					resultIterable = collection.find(new Document(parameter));
				}
			}
			MongoCursor<Document> resultCursor = resultIterable.iterator();
			while (resultCursor.hasNext()) {
				Document document = (Document) resultCursor.next();
				list.add(document);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			tempMongoClient.close();
		}
		return list;
	}

}
