

import java.io.IOException;
import naifcanbasci.httpserver.Handler;
import naifcanbasci.httpserver.HttpServer;
import naifcanbasci.httpserver.Request;
import naifcanbasci.httpserver.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class http_example {

	static final JSONObject status = new JSONObject();

	public static void main(String[] args) throws IOException, JSONException {
		HttpServer sv = new HttpServer();
		status.put("available", true);
		status.put("code", 0);
		status.put("message", "");
		JSONArray ver = new JSONArray();
		ver.put(1);
		ver.put("1.0");
		status.put("version", ver);

		sv.handle("/status", new Handler() {

				@Override
				public void handle(Request req, Response res) {
					res.json(status);
				}
			});

		sv.handle("/say_it/:m", new Handler() {

				@Override
				public void handle(Request req, Response res) {
					res.write(req.PARAMETERS.get("m"));
				}
			});
		sv.listen(5000);
	}
}
