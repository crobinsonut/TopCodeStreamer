package topcodestreamer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jetty.websocket.api.Session;

import topcodes.Scanner;
import topcodes.TopCode;

public class TopCodeFinder {
	Camera camera;
	static ArrayList<Session> listeners;
	Scanner scanner;
	boolean isStopped;
	
	public TopCodeFinder(Camera camera){
		this.camera = camera;
		this.isStopped = false;
		this.scanner = new Scanner();
		TopCodeFinder.listeners = new ArrayList<Session>();
	}
	
	public static void addSession(Session session){
		listeners.add(session);
	}
	
	public void start(){
		camera.open();
		while(isStopped == false){
			System.out.println("doing something");
			List<TopCode> codes = scanner.scan(camera.getImage());
			String str = "";
			for(TopCode code: codes){
				str += code.getCode() + " ";
			}
			if(str.equals("") == false){
				System.out.println(str);
				for(Session session: TopCodeFinder.listeners){
					try {
						session.getRemote().sendString(str);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
		}
		camera.close();
	}
	
	public void stop(){
		this.isStopped = true;
	}
	
}
