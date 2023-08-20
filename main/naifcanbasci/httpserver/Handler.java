package naifcanbasci.httpserver;
import java.io.OutputStream;
import java.io.Writer;
import java.io.PrintStream;
import java.io.PrintWriter;

public interface Handler {
	public void handle(Request req, Response res);
}
