package topcodestreamer;

import java.awt.image.BufferedImage;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import topcodes.Scanner;
import topcodes.TopCode;

public class CameraDaemon implements Runnable{
	private static CameraDaemon d;
	private Camera camera;
	private boolean isRunning;
	private static ConcurrentLinkedQueue<TopCodeHandler> handlerQueue;
	
	public CameraDaemon(Camera camera){
		this.camera = camera;
		this.isRunning = false;
		handlerQueue = new ConcurrentLinkedQueue<TopCodeHandler>();
	}
	
	public static CameraDaemon getDaemon(Camera camera){
		if(d == null){
			d = new CameraDaemon(camera);
		}
		
		return d; 
	}
	
	public void stop(){
		this.isRunning = false;
	}
	
	public static void addHandler(TopCodeHandler handler){
		handlerQueue.add(handler);
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("CamaeraDeamon started with id 0");
		this.isRunning = true;
		this.camera.open();
		Scanner scanner = new Scanner();
		List<TopCode> codes;
		
		while(this.isRunning){
			BufferedImage image = this.camera.getImage();
			if(image != null){
				codes = scanner.scan(this.camera.getImage());
			
				for(TopCodeHandler handler: handlerQueue){
					handler.onTopCodes(codes);
				}
			}
		}
		
		this.camera.close();
	}
	
	
}
