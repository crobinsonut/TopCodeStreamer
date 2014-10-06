package topcodestreamer;

import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

public class EventServlet extends WebSocketServlet {
	
	
	@Override
	public void configure(WebSocketServletFactory factory) {
		factory.setCreator(new SocketCreator(CameraDaemon.getDaemon(null)));
	}
}
