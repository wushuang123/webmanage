package com.xifar.common.util.mail;

import java.security.Security;
import java.util.Date;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.net.ssl.internal.ssl.Provider;

public class MailImpl implements IMail {

	private static Logger log = LoggerFactory.getLogger(MailImpl.class);

	public static final String TO = "TO";

	public static final String CC = "CC";

	public static final String BCC = "BCC";

	@Override
	public Message getMessage(String serverHost, String userName, String password, String from, String[] to, String subject, Object content, boolean ssl, boolean debug) {
		return this.getMessage(serverHost, 25, false, userName, password, MailImpl.TO, from, to, subject, content, ssl, debug);
	}

	@Override
	public Message getMessage(String serverHost, int port, String userName, String password, String from, String[] to, String subject, Object content, boolean ssl, boolean debug) {
		return this.getMessage(serverHost, port, false, userName, password, MailImpl.TO, from, to, subject, content, ssl, debug);
	}

	@Override
	public Message getMessage(String serverHost, int port, boolean validate, String userName, String password, String from, String[] to, String subject, Object content, boolean ssl, boolean debug) {
		return this.getMessage(serverHost, port, validate, userName, password, MailImpl.TO, from, to, subject, content, ssl, debug);
	}

	@Override
	public Message getMessage(String serverHost, int port, boolean validate, String userName, String password, String type, String from, String[] to, String subject, Object content, boolean ssl, boolean debug) {
		return this.getMessage(serverHost, port, validate, userName, password, type, from, to, subject, content, "text/plain;charset=utf-8", ssl, debug);
	}

	@Override
	public Message getMessage(String serverHost, int port, boolean validate, String userName, String password, String type, String from, String[] to, String subject, Object content, String contentType, boolean ssl, boolean debug) {
		try {
			MyAuthenticator authenticator = null;
			// 判断是否需要身份认证,如果需要身份认证，则创建一个密码验证器
			if (validate) {
				authenticator = new MyAuthenticator(userName, password);
			}
			Properties properties = new Properties();
			properties.put("mail.smtp.host", serverHost);
			properties.put("mail.smtp.port", port);
			properties.put("mail.smtp.auth", validate ? "true" : "false");
			// 是否支持SSL
			if (ssl) {
				Security.addProvider(new Provider());
				properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
				properties.setProperty("mail.smtp.port", "465");
				properties.setProperty("mail.smtp.socketFactory.port", "465");
			}
			// 根据邮件会话属性和密码验证器构造一个发送邮件的session
			Session mailSession = Session.getDefaultInstance(properties, authenticator);
			mailSession.setDebug(debug);
			// 根据session创建一个邮件消息
			Message mailMessage = new MimeMessage(mailSession);
			// 创建邮件发送者地址
			Address fromAddress = new InternetAddress(from);
			// 设置邮件消息的发送者
			mailMessage.setFrom(fromAddress);
			// 创建邮件的接收者地址，并设置到邮件消息中
			Address[] toAddress = new InternetAddress[to.length];
			for (int i = 0; i < to.length; i++) {
				toAddress[i] = new InternetAddress(to[i]);
			}
			// Message.RecipientType.TO属性表示接收者的类型为TO
			if ("TO".equals(type)) {
				mailMessage.setRecipients(Message.RecipientType.TO, toAddress);
			} else if ("CC".equals(type)) {
				mailMessage.setRecipients(Message.RecipientType.CC, toAddress);
			} else if ("BCC".equals(type)) {
				mailMessage.setRecipients(Message.RecipientType.BCC, toAddress);
			} else {
				mailMessage.setRecipients(Message.RecipientType.TO, toAddress);
			}
			// 设置邮件消息的主题
			mailMessage.setSubject(subject);
			// 设置邮件消息发送的时间
			mailMessage.setSentDate(new Date());
			// MiniMultipart类是一个容器类，包含MimeBodyPart类型的对象
			Multipart mainPart = new MimeMultipart();
			// 创建邮件主体
			BodyPart body = new MimeBodyPart();
			// 设置HTML内容
			body.setContent(content, contentType);
			mainPart.addBodyPart(body);
			// 将MiniMultipart对象设置为邮件内容
			mailMessage.setContent(mainPart);
			log.info("创建邮件消息成功");
			return mailMessage;
		} catch (AddressException ae) {
			log.error("邮件地址异常" + ae.getMessage());
			return null;
		} catch (MessagingException e) {
			log.error("邮件消息异常" + e.getMessage());
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			log.error("生成邮件消息出错" + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	public boolean send(Message msg) {
		try {
			Transport.send(msg);
			return true;
		} catch (MessagingException e) {
			log.error("发送邮件失败,异常：" + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}
}
