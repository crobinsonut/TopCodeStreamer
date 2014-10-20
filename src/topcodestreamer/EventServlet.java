package topcodestreamer;

import java.awt.image.BufferedImage;
import java.util.List;

import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

import topcodes.TopCode;
import base.Distributor;

public class EventServlet extends WebSocketServlet {
	private TopCodeDaemon daemon;
	
	public EventServlet(TopCodeDaemon daemon){
		this.daemon = daemon;
	}
	
	@Override
	public void configure(WebSocketServletFactory factory) {
		factory.setCreator(new SocketCreator(this.daemon));
	}
}
