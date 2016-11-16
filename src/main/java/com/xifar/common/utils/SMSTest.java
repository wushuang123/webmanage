package com.xifar.common.utils;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;

public class SMSTest {
	
	public static void main(String[] args) throws ApiException{
		TaobaoClient client = new DefaultTaobaoClient("http://gw.api.taobao.com/router/rest", "23523770", "4a461c73cbdad4063a6828f7334f8e2c");
		AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
		req.setExtend("123456");
		req.setSmsType("normal");
		req.setSmsFreeSignName("照相馆");
		req.setSmsParamString("{\"name\":\"大美女，逗你玩，哈哈哈\"}");
		req.setRecNum("18622970066");
		req.setSmsTemplateCode("SMS_25595216");
		AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);
		System.out.println(rsp.getBody());
	}

}
