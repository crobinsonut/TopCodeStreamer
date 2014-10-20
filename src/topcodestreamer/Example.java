package topcodestreamer;

import java.awt.image.BufferedImage;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import async.AsynchronousDistributor;
import async.AsynchronousGenerator;
import base.Distributor;
import base.Generator;
import topcodes.TopCode;

public class Example {
	public static void main(String[] args) throws Exception {
		Server server = new Server();
		
		BlockingQueue<BufferedImage> queue = new ArrayBlockingQueue<BufferedImage>(10);
		
		CameraDaemon cdaemon = new CameraDaemon(new Camera(0), queue);
		TopCodeDaemon tdaemon = new TopCodeDaemon(queue);
		
		ServerConnector connector = new ServerConnector(server);
		connector.setPort(8080);
		server.addConnector(connector);

		// Setup the basic application "context" for this application at "/"
		// This is also known as the handler tree (in jetty speak)
		ServletContextHandler context = new ServletContextHandler(
				ServletContextHandler.SESSIONS);
		context.setContextPath("/");
		server.setHandler(context);

		// Add a websocket to a specific path spec
		ServletHolder holderEvents = new ServletHolder("ws-events",
				new EventServlet(tdaemon));
		context.addServlet(holderEvents, "/events/*");
		try {
			//System.out.println("This is bullshit");
			Thread thread = new Thread(cdaemon);
			Thread tcthread = new Thread(tdaemon);
			
			thread.start();
			tcthread.start();
			server.start();
			
			server.dump(System.err);
			server.join();
			
			thread.join();
			tcthread.join();
			
			
		} catch (Throwable t) {
			t.printStackTrace(System.err);
		}
	}
}
