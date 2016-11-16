package com.xifar.common.utils.mail;

import javax.mail.Message;

public interface IMail {

	Message getMessage(String serverHost, String userName, String password, String from, String[] to, String subject, Object content, boolean ssl, boolean debug);

	Message getMessage(String serverHost, int port, String userName, String password, String from, String[] to, String subject, Object content, boolean ssl, boolean debug);

	Message getMessage(String serverHost, int port, boolean validate, String userName, String password, String from, String[] to, String subject, Object content, boolean ssl, boolean debug);

	Message getMessage(String serverHost, int port, boolean validate, String userName, String password, String type, String from, String[] to, String subject, Object content, boolean ssl, boolean debug);

	Message getMessage(String serverHost, int port, boolean validate, String userName, String password, String type, String from, String[] to, String subject, Object content, String contentType, boolean ssl, boolean debug);

	boolean send(Message message);

}
