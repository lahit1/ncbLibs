package naifcanbasci.httpserver;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.HashMap;
import java.io.PrintWriter;
import java.io.Writer;
import naifcanbasci.httpserver.headers.Headers;
import org.json.JSONObject;
import org.json.JSONArray;
import naifcanbasci.httpserver.headers.ContentType;
import naifcanbasci.util.ArrayMap;

public class Response extends PrintWriter
{
	public int CODE = 404;
	public final ArrayMap<String, Object> HEADERS = new ArrayMap<String, Object>();
	
	final StringWriter strwrt;
	
	Response(StringWriter w) {
		super(w);
		strwrt = w;
	}
	
	public void redirect(String location) {
		CODE = 301;
		HEADERS.put(Headers.Location, location);
	}
	
	public void json(JSONObject obj) {
		json(obj.toString());
	}
	
	public void json(JSONArray arr) {
		json(arr.toString());
	}
	
	public void json(String str) {
		HEADERS.put(Headers.Content_Type, ContentType.Application.Json);
		print(str);
	}
	
	public void not_found(String message) {
		CODE = 404;
		print(message);
	}
	
	public void not_found() {
		HEADERS.put(Headers.Content_Type, ContentType.Text.Html);
		CODE = 404;
		print("<style>h1 {style: bold}</style>");
		print("<h1>404</h1>");
		print("Page not found!");
	}
}
