package topcodestreamer;

import java.io.IOException;
import java.util.List;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketListener;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeRequest;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeResponse;
import org.eclipse.jetty.websocket.servlet.WebSocketCreator;

import topcodes.TopCode;

public class EventSocket implements WebSocketListener{
	
	public void onWebSocketConnect(Session sess) {
		TopCodeFinder.addSession(sess);
	}

	public void onWebSocketText(String message) {
	}

	public void onWebSocketClose(int statusCode, String reason) {
		
	}

	public void onWebSocketError(Throwable cause) {
		cause.printStackTrace(System.err);
	}


	@Override
	public void onWebSocketBinary(byte[] arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}

}
