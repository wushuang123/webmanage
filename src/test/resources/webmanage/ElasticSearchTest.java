package webmanage;

import com.xifar.common.util.json.JsonUtil;
import com.xifar.common.util.net.HttpUtil;
import com.xifar.elasticsearch.model.Response;

public class ElasticSearchTest {

	private String get() {
		String url = "http://192.168.177.129:9200/mytest/contacts/_search?pretty";
		String result = HttpUtil.sendGet(url, "UTF-8", null);
		return result;
	}

	private void parse(String data) {
		Response response = JsonUtil.fromJson(data, Response.class);
		System.out.println(JsonUtil.toJson(response.getHits().getHits().get(0).get_source()));
	}

	public static void main(String[] args) {
		long begin = System.currentTimeMillis();
		ElasticSearchTest es = new ElasticSearchTest();
		String data = es.get();
		System.out.println(data);
		es.parse(data);
		long end = System.currentTimeMillis();
		System.out.println(end - begin);
	}

}
