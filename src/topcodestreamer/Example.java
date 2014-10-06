package topcodestreamer;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class Example {
	public static void main(String[] args) throws Exception {
		Server server = new Server();
		CameraDaemon daemon = CameraDaemon.getDaemon(new Camera(0));
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
				EventServlet.class);
		context.addServlet(holderEvents, "/events/*");
		try {
			//System.out.println("This is bullshit");
			Thread thread = new Thread(daemon);
			thread.start();
			server.start();
			
			server.dump(System.err);
			server.join();
			thread.join();
			
			
		} catch (Throwable t) {
			t.printStackTrace(System.err);
		}
	}
}
