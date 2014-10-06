package topcodestreamer;

import org.eclipse.jetty.websocket.servlet.ServletUpgradeRequest;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeResponse;
import org.eclipse.jetty.websocket.servlet.WebSocketCreator;

public class SocketCreator implements WebSocketCreator{
	private CameraDaemon daemon;
	
	public SocketCreator(CameraDaemon daemon){
		this.daemon = daemon;
	}
	
	@Override
	public Object createWebSocket(ServletUpgradeRequest arg0,
			ServletUpgradeResponse arg1) {
		// TODO Auto-generated method stub
		TopCodeHandler socket = new TopCodeHandler();
		this.daemon.addHandler(socket);
		
		return socket;
	}

}
