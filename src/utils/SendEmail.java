package utils;


import java.util.Date;
import java.util.Properties;
 
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
 
/**
 * 
 * @author ������
 * @time 2016-7-30 ����10:44:58
 * @description
 * 
 */
public class SendEmail {
	public static final String HOST = "smtp.qq.com";
	public static final String PROTOCOL = "smtp";
	public static final int PORT = 25;
	public static final String FROM = "1327293674@qq.com";// �����˵�email
	public static final String PWD = "ysrewnqlxubkhgdc";// ����������
 
	/**
	 * ��ȡSession
	 * 
	 * @return
	 */
	private static Session getSession() {
		Properties props = new Properties();
		props.put("mail.smtp.host", HOST);// ���÷�������ַ
		props.put("mail.store.protocol", PROTOCOL);// ����Э��
		props.put("mail.smtp.port", PORT);// ���ö˿�
		props.put("mail.smtp.auth", true);
 
		Authenticator authenticator = new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(FROM, PWD);
			}
 
		};
		Session session = Session.getDefaultInstance(props, authenticator);
 
		return session;
	}
 
	public static void send(String toEmail, String content, String subject) {
		Session session = getSession();
		try {
			// System.out.println("--send--" + content);
			// Instantiate a message
			Message msg = new MimeMessage(session);
 
			// Set message attributes
			msg.setFrom(new InternetAddress(FROM));
			InternetAddress[] address = { new InternetAddress(toEmail) };
			msg.setRecipients(Message.RecipientType.TO, address);
			msg.setSubject(subject);
			msg.setSentDate(new Date());
			msg.setContent(content, "text/html;charset=utf-8");
 
			// Send the message
			Transport.send(msg);
		} catch (MessagingException mex) {
			mex.printStackTrace();
		}
	}
 
	public static void sendSSLEmail(String emailAddress, String content, String subject) {
                //emailAddress  �ռ�������
                boolean isSSL = true;
		String host = "smtp.qq.com";
		int port = 465;
		String from = "1327293674@qq.com"; // �����˵�email
		boolean isAuth = true;
		final String password = "urldcqzouwfwigch";
 
		Properties props = new Properties();
		props.put("mail.smtp.ssl.enable", isSSL);
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", port);
		props.put("mail.smtp.auth", isAuth);
 
		Session session = Session.getDefaultInstance(props, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(from , password);
			}
		});
 
		try {
			Message message = new MimeMessage(session);
 
			message.setFrom(new InternetAddress(from));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(emailAddress));
			message.setSubject(subject);
			message.setText(content);
 
			Transport.send(message);
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
 
	}
 
	public static void main(String[] args) {
		System.out.println("----����");
		//25�˿�
		SendEmail.send("1327293674@qq.com", "<a href='http://www.baidu.com'>��������</a>", "subject");
		//465�˿�
		SendEmail.sendSSLEmail("1327293674@qq.com", "<a href='http://www.baidu.com'>��������</a>", "subject");
		System.out.println("----���");
	}
}