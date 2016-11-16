package com.xifar.common.utils.mail;

import java.util.ArrayList;
import java.util.List;

import javax.mail.Message;

import com.xifar.common.utils.ProxyHandler;

public class MailUtil {

	static ProxyHandler proxy = new ProxyHandler();

	public static final String TO = "TO";

	public static final String CC = "CC";

	public static final String BCC = "BCC";

	public static boolean send(String serverHost, String userName, String password, String from, String[] to, String subject, Object content, boolean ssl, boolean debug) {
		IMail iMail = (IMail) proxy.bind(new MailImpl());
		Message message = iMail.getMessage(serverHost, userName, password, from, to, subject, content, ssl, debug);
		return iMail.send(message);
	}

	public static boolean send(String serverHost, int port, String userName, String password, String from, String[] to, String subject, Object content, boolean ssl, boolean debug) {
		IMail iMail = (IMail) proxy.bind(new MailImpl());
		Message message = iMail.getMessage(serverHost, port, userName, password, from, to, subject, content, ssl, debug);
		return iMail.send(message);
	}

	public static boolean send(String serverHost, int port, boolean validate, String userName, String password, String from, String[] to, String subject, Object content, boolean ssl, boolean debug) {
		IMail iMail = (IMail) proxy.bind(new MailImpl());
		Message message = iMail.getMessage(serverHost, port, validate, userName, password, from, to, subject, content, ssl, debug);
		return iMail.send(message);
	}

	public static boolean send(String serverHost, int port, boolean validate, String userName, String password, String type, String from, String[] to, String subject, Object content, boolean ssl, boolean debug) {
		IMail iMail = (IMail) proxy.bind(new MailImpl());
		Message message = iMail.getMessage(serverHost, port, validate, userName, password, from, to, subject, content, ssl, debug);
		return iMail.send(message);
	}

	public static boolean send(String serverHost, int port, boolean validate, String userName, String password, String type, String from, String[] to, String subject, Object content, String contentType, boolean ssl, boolean debug) {
		IMail iMail = (IMail) proxy.bind(new MailImpl());
		Message message = iMail.getMessage(serverHost, port, validate, userName, password, type, from, to, subject, content, contentType, ssl, debug);
		return iMail.send(message);
	}

	public static void main(String[] args) {
		List<String> list = new ArrayList<String>();
		list.add("zxj100@foxmail.com");
		list.add("ninimailer@163.com");
		String[] array = (String[]) list.toArray();
		boolean s = MailUtil.send("smtp.qq.com", 25, true, "1316452047@qq.com", "efkozckqacofjahf", "1316452047@qq.com", "1316452047@qq.com", array, "您好", "您中大奖了，赶紧领取<h1>非常好的</h1>", "text/html;charset=utf-8", true, true);
		System.out.println(s);
	}
}
