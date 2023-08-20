package naifcanbasci.httpserver;
import java.util.HashMap;
import java.net.InetSocketAddress;
import java.net.InetAddress;
import java.net.Socket;
import naifcanbasci.util.ArrayMap;

public class Request {
	
	public final Method METHOD;
	public final ArrayMap<String, String>
	QUERY = new ArrayMap<String, String>(),
	BODY = new ArrayMap<String, String>(),
	PARAMETERS = new ArrayMap<String, String>();
	
	public final Socket socket;

	public Request(Method m, Socket socket) {
		this.METHOD = m;
		this.socket = socket;
	}
	
	public enum Method {
		GET,
		POST;
	}
}
