package topcodestreamer;

import java.awt.image.BufferedImage;
import java.util.ArrayDeque;
import java.util.List;
import topcodes.Scanner;
import topcodes.TopCode;

public class Thing {
	ArrayDeque<BufferedImage> imageQueue;
	Scanner scanner;
	
	public Thing(){
		imageQueue = new ArrayDeque<BufferedImage>(10);
		scanner = new Scanner();
	}
	
	public void addImage(BufferedImage image){
		this.imageQueue.add(image);
	}
	
	public List<TopCode> getTopCodes(){
		return scanner.scan(imageQueue.pop());
	}
}

