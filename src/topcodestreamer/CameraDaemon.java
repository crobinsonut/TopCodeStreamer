package topcodestreamer;

import java.awt.image.BufferedImage;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;

import base.Generator;
import topcodes.Scanner;
import topcodes.TopCode;

public class CameraDaemon implements Runnable{
	private Camera camera;
	private boolean isRunning;
	
	BlockingQueue<BufferedImage> imageQueue;
	
	public CameraDaemon(Camera camera, BlockingQueue<BufferedImage> imageQueue){
		this.camera = camera;
		this.imageQueue = imageQueue;
		this.isRunning = false;
	}
	
	public void stop(){
		this.isRunning = false;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		this.isRunning = true;
		this.camera.open();
		
		while(this.isRunning){
			BufferedImage image = this.camera.getImage();
			if(image != null){
				imageQueue.offer(image);
			}
		}
		
		this.camera.close();
	}
	
	
}
