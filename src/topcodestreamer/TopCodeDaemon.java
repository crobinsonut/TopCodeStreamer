package topcodestreamer;

import java.awt.image.BufferedImage;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;

import base.Distributor;

public class TopCodeDaemon implements Runnable{
	private BlockingQueue<BufferedImage> imageQueue;
	private ConcurrentLinkedQueue<BlockingQueue<BufferedImage>> queues;
	
	public TopCodeDaemon(BlockingQueue<BufferedImage> imageQueue){
		this.imageQueue = imageQueue;
		this.queues = new ConcurrentLinkedQueue<BlockingQueue<BufferedImage>>();
	}
	
	public void run(){
		//System.out.println("started");
		while(true){
			BufferedImage image = this.imageQueue.poll();
			//System.out.println(codes);
			if(image != null){
				for(BlockingQueue<BufferedImage> queue: queues){
					queue.offer(image);
				}
			}
		}
	}
	
	public void register(BlockingQueue<BufferedImage> imageQueue){
		this.queues.offer(imageQueue);
	}
	
	public void unregister(BlockingQueue<BufferedImage> imageQueue){
		this.queues.remove(imageQueue);
	}
}
