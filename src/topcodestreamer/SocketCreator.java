package topcodestreamer;

import org.eclipse.jetty.websocket.servlet.ServletUpgradeRequest;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeResponse;
import org.eclipse.jetty.websocket.servlet.WebSocketCreator;

public class SocketCreator implements WebSocketCreator{
	private TopCodeDaemon daemon;
	
	public SocketCreator(TopCodeDaemon daemon){
		this.daemon = daemon;
	}
	
	@Override
	public Object createWebSocket(ServletUpgradeRequest arg0,
			ServletUpgradeResponse arg1) {
		// TODO Auto-generated method stub
		//ArrayBlockingQueue<BufferedImage> imageQueue = new ArrayBlockingQueue<BufferedImage>(10);
		//this.daemon.register(imageQueue);
				
		return new EventSocket(daemon);
	}

}
