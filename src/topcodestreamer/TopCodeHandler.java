package topcodestreamer;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketListener;

import topcodes.TopCode;

public class TopCodeHandler implements WebSocketListener{
	private Session session;
	
	public TopCodeHandler(){
		
	}
	
	public void onTopCodes(List<TopCode> codes){
		String str = "{}";
		
		if((codes != null) && (!codes.isEmpty())){
		
			LinkedList<String> strs = new LinkedList<String>();
			for(TopCode code: codes){
				strs.add(String.format("%d:{x:%f,y:%f}", code.getCode(), code.getCenterX(), code.getCenterY()));
			}
			str = String.format("{%s}", String.join(",", strs));
		}
		
		
		try {
			session.getRemote().sendString(str);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void onWebSocketBinary(byte[] arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onWebSocketClose(int arg0, String arg1) {
		// TODO Auto-generated method stub
		this.session.close();
		CameraDaemon.removeHandler(this);
	}

	@Override
	public void onWebSocketConnect(Session arg0) {
		// TODO Auto-generated method stub
		this.session = arg0;
		CameraDaemon.addHandler(this);
	}

	@Override
	public void onWebSocketError(Throwable arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onWebSocketText(String arg0) {
		// TODO Auto-generated method stub
		
	}

}
