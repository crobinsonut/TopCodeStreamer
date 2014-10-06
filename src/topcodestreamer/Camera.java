package topcodestreamer;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.VideoCapture;

public class Camera {
	Mat matrix;
	VideoCapture capture;
	int deviceId;
	
	public static BufferedImage matToBufferedImage(Mat m){
		 int width = m.width(), height = m.height(), channels = m.channels() ;
	     byte[] sourcePixels = new byte[width * height * channels];
	     m.get(0, 0, sourcePixels);

	     // create new BufferedImage and
	     BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);

	     // Get reference to backing data
	     final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
	     System.arraycopy(sourcePixels, 0, targetPixels, 0, sourcePixels.length);
	     
	     return image;
	}
	
	public Camera(int deviceId){
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		this.deviceId = deviceId;
	}
	
	public void open(){
		capture = new VideoCapture(deviceId);
		matrix = new Mat();
	}
	
	public boolean isOpen(){
		return capture.isOpened();
	}
	
	public BufferedImage getImage(){
		capture.retrieve(matrix);
		if(matrix != null && !matrix.empty()){
			Core.flip(matrix, matrix, 1);
			return Camera.matToBufferedImage(matrix);
		}
		
		return null;
	}
	
	public void close(){
		capture.release();
	}
}
