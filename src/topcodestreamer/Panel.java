package topcodestreamer;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.opencv.core.Mat;

import topcodes.Scanner;
import topcodes.TopCode;

@SuppressWarnings("serial")
public class Panel extends JPanel implements KeyListener{
	private BufferedImage image;
	boolean shutdown = false;

	public Panel() {
		super();
	}

	public static BufferedImage matToBufferedImage(Mat m) {
		int width = m.width(), height = m.height(), channels = m.channels();
		byte[] sourcePixels = new byte[width * height * channels];
		m.get(0, 0, sourcePixels);

		// create new BufferedImage and
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_3BYTE_BGR);

		// Get reference to backing data
		final byte[] targetPixels = ((DataBufferByte) image.getRaster()
				.getDataBuffer()).getData();
		System.arraycopy(sourcePixels, 0, targetPixels, 0, sourcePixels.length);

		return image;
	}

	@Override
	public void paintComponent(Graphics g) {
		if (this.image != null) {
			g.drawImage(this.image, 10, 10, this.image.getWidth(),
					this.image.getHeight(), this);
		}
	}

	public void paintImage(BufferedImage image){
		this.image = image;
		this.repaint();
	}
	public static void main(String args[]) {
		//System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		//System.load("C:\\opencv\\build\\java\\x86\\opencv_java249.dll");
		JFrame frame = new JFrame("BasicPanel");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400, 400);
		Panel panel = new Panel();
		frame.setContentPane(panel);
		frame.setVisible(true);
		frame.addKeyListener(panel);
		Scanner scanner = new Scanner();
		Camera camera = new Camera(0);
		camera.open();

		if (camera.isOpen()) {
			while (panel.shutdown == false) {
				BufferedImage image = camera.getImage();

				if (image != null) {
					List<TopCode> topcodes = scanner.scan(image);
					frame.setSize(image.getWidth() + 40, image.getHeight() + 60);
					panel.paintImage(image);

					if (topcodes != null && !topcodes.isEmpty()) {
						TopCode code = topcodes.get(0);
						System.out.format("<id=%d, x=%f, y=%f>%n", code.getCode(), code.getCenterX(), code.getCenterY());
					}
				}
			}
			camera.close();
			frame.dispose();
		}
		return;
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		if (arg0.getKeyCode() == KeyEvent.VK_ESCAPE) {
			System.out.println("shutting down");
			shutdown = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}
}
