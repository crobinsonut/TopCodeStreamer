package topcodestreamer;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketListener;
import topcodes.Scanner;
import topcodes.TopCode;

public class EventSocket implements WebSocketListener{
	private TopCodeDaemon daemon;
	private BlockingQueue<BufferedImage> imageQueue;
	private boolean open;
	private Scanner scanner;
	
	public void submit(Session session){
		while(this.open){
			BufferedImage image = this.imageQueue.poll();
			
			if(image != null){
				
				List<TopCode> codes = scanner.scan(image);
				
				if (codes != null && !codes.isEmpty()){
					ArrayList<String> json = new ArrayList<String>();
					for(TopCode code: codes){
						int id = code.getCode();
						double x = code.getCenterX();
						double y = code.getCenterY();
						double o = code.getOrientation();
						double d = code.getDiameter();
						
						json.add(String.format("\"%d\": {\"x\":%f,\"y\":%f,\"o\":%f,\"d\":%f}", id, x, y ,o, d));
					}
					
					try {
						String str = String.format("{%s}",String.join(",", json));
						session.getRemote().sendString(str);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		this.daemon.unregister(this.imageQueue);
	}
	
	public EventSocket(TopCodeDaemon daemon){
		this.daemon = daemon;
		this.open = false;
	}
	
	public void onWebSocketConnect(Session sess) {
		this.open = true;
		this.scanner = new Scanner();
		this.imageQueue = new ArrayBlockingQueue<BufferedImage>(10);
		this.daemon.register(imageQueue);
		this.submit(sess);
	}

	public void onWebSocketText(String message) {
	}

	public void onWebSocketClose(int statusCode, String reason) {
		this.open = false;
	}

	public void onWebSocketError(Throwable cause) {
		cause.printStackTrace(System.err);
	}


	@Override
	public void onWebSocketBinary(byte[] arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}

}
