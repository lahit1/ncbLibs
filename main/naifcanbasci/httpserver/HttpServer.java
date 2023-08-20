package naifcanbasci.httpserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.function.BiConsumer;
import naifcanbasci.httpserver.headers.ContentType;
import naifcanbasci.httpserver.headers.Headers;
import org.json.JSONObject;
import naifcanbasci.util.ArrayMap;

public class HttpServer {


	private final ArrayMap<Path, Handler> contextMap = new ArrayMap<Path, Handler>();

	private ExceptionListener exception_listener;

	public final static ArrayMap<String, Object> DEFAULT_HEADERS = new ArrayMap<String, Object>();
	static {
		DEFAULT_HEADERS.put("Server", "NCB HTTP 1.0");
		DEFAULT_HEADERS.put(Headers.Content_Type, ContentType.Text.Html);
	}

	public HttpServer setExceptionListener(ExceptionListener el) {
		exception_listener = el;
		return this;
	}

	public HttpServer listen(int port) throws IOException {
		final ServerSocket server = new ServerSocket(port);
		listen(server);
		return this;
	}

	public HttpServer listen(String host, int port) throws IOException {
		final ServerSocket server = new ServerSocket();
		server.bind(new InetSocketAddress(host, port));
		listen(server);
		return this;
	}

	protected HttpServer listen(final ServerSocket server) {
		while (!server.isClosed()) {
			try {
				new Thread(new Runnable() {

						Socket cli = server.accept();
						InputStream is = cli.getInputStream();

						@Override
						public void run() {
							while (cli.isConnected())
								try {
									if (is.available() > 0) {
										byte[] buff = new byte[is.available()];
										is.read(buff);
										String reqCon = new String(buff);
										String[] parts = reqCon.split("\r\n\r\n");
										String[] lines = parts[0].split("\r\n");

										String[] l0 = lines[0].split(" ");
										String pathname = l0[1].split("[?]")[0];

										Path path=new Path(URLDecoder.decode(pathname));

										String req = l0[0].replace(" ", "");
										Response res = new Response(new StringWriter());
										res.HEADERS.putAll(DEFAULT_HEADERS);

										Request _req;

										_req = new Request(Request.Method.valueOf(req.toUpperCase()), cli);

										{ // Parse url query
											String[] arr = l0[1].split("[?]");
											if (arr.length > 1) {
												arr = URLDecoder.decode(arr[1]).split("&");
												if (arr.length > 0)
													for (String def: arr) {
														String[] arr2 = def.split("=");
														if (arr.length > 1)
															_req.QUERY.put(arr2[0], arr2[1]);
													}
											}
										}
										if (parts.length > 1) {
											if (parts[1].matches("[{].*[}]")) {
												JSONObject obj = new JSONObject(parts[1]);
												Iterator<String> it = obj.keys();
												while (it.hasNext()) {
													String key = it.next();
													_req.BODY.put(key, obj.getString(key));
												}
											} else if (parts[1].contains(".*=.*")) { // Parse body
												String[] arr = parts[1].split("&");
												if (arr.length > 0)
													for (String def: arr) {
														String[] arr2 = URLDecoder.decode(def).split("=");
														if (arr2.length > 1)
															_req.BODY.put(arr2[0], arr2[1]);
													}
											}
										}

										for (Path p: contextMap.keySet()) 
											if (p.equals(path)) {
												res.CODE = 200;
												p.getParams(path.path,
															_req.PARAMETERS);

												contextMap.get(p).handle(_req, res);
												break;
											}
										if (res.CODE == 404)
											res.not_found();

										String content = res.strwrt.toString();
										res.HEADERS.put("Content-Length", content.length());

										final StringWriter resp = new StringWriter();
										resp.write(String.format("HTTP/1.0 %d OK\r\n", res.CODE));

										res.HEADERS.forEach(new BiConsumer<String, Object>() {

												@Override
												public void accept(String p1, Object p2) {
													resp.write(String.format("%s: %s\r\n", p1, p2));
												}
											});

										resp.write("\r\n");
										resp.write(content);

										OutputStream out = cli.getOutputStream();
										out.write(resp.toString().getBytes());
									}
								} catch (Exception e) {
									if (exception_listener != null)
										exception_listener.catched(e);
								}
						}}).start();
			} catch (Exception e) {
				if (exception_listener != null)
					exception_listener.catched(e);
			}

		}
		return this;
	}

	public HttpServer handle(String path, Handler handler) {
		if (path == null || handler == null) throw new IllegalArgumentException("Path or Handler can't be null.");
		contextMap.put(new Path(path), handler);
		return this;
	}
}
