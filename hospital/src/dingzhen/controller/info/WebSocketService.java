package dingzhen.controller.info;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.springframework.stereotype.Component;

@ServerEndpoint("/websocket/{userid}")
@Component
public class WebSocketService {
	private Session session;
	private static Map<String, Session> sessionPool = new HashMap();
	private static Map<String, String> sessionIds = new HashMap();

	@OnOpen
	public void open(Session session, @PathParam("userid") String userid) {
		this.session = session;
		sessionPool.put(userid, session);
		sessionIds.put(session.getId(), userid);
	}

	@OnMessage
	public void onMessage(String message) {
		//sendMessage((String) sessionIds.get(this.session.getId()) + "<--" + message, "niezhiliang9595");
		//System.out.println("发送人:" + (String) sessionIds.get(this.session.getId()) + "内容:" + message);
	}

	@OnClose
	public void onClose() {
		sessionPool.remove(sessionIds.get(this.session.getId()));
		sessionIds.remove(this.session.getId());
	}

	@OnError
	public void onError(Session session, Throwable error) {
		error.printStackTrace();
	}

	public static void sendMessage(String message, String userId) {
		Session s = (Session) sessionPool.get(userId);
		if (s != null)
			try {
				s.getBasicRemote().sendText(message);
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

	public static int getOnlineNum() {
		return sessionPool.size();
	}

	public static String getOnlineUsers() {
		StringBuffer users = new StringBuffer();
		for (String key : sessionIds.keySet()) {
			users.append((String) sessionIds.get(key) + ",");
		}
		return users.toString();
	}

	public static void sendAll(String msg) {
		for (String key : sessionIds.keySet())
			sendMessage(msg, (String) sessionIds.get(key));
	}

	public static void SendMany(String msg, String[] persons) {
		for (String userid : persons)
			sendMessage(msg, userid);
	}
}