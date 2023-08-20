package naifcanbasci.util;
import java.io.RandomAccessFile;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.net.URLEncoder;
import java.net.URLDecoder;

//Serial Logging Database .sdb
public class SLD
{
	private final RandomAccessFile raf;
	
	public SLD(String p, String m) throws FileNotFoundException, IOException {
		raf = new RandomAccessFile(p, m);
		raf.seek(raf.length());
	}

	public void log(String key, String... data) throws IOException {
		raf.seek(raf.length());
		raf.writeBytes(encode(key));
		for(String str: data) {
			raf.write('&');
			raf.writeBytes(encode(str));
		}
		raf.write(':');
	}

	public void search(String key, CallBack res) throws IOException {
		raf.seek(0);
		String chunk="";
		while(raf.getFilePointer() < raf.length()) {
			byte b = raf.readByte();
			
			if(b == '&') {
				chunk = decode(chunk);
				if(chunk.matches(key.replace("%", ".*").replace("_", "."))) {
					String fkey = chunk;
					chunk = "";
					while((b = raf.readByte()) != ':') {
						chunk += (char)b;
					}
					Array<String> data = new Array<String>();
					for(String di: chunk.split("&"))
						data.put(decode(di));
					res.find(fkey, data);
					
				} else {
					while((b = raf.readByte()) != ':');
				}
				
				chunk = "";
			} else {
				chunk += (char)b;
			}
		}
	}

	public long size() throws IOException {
		return raf.length();
	}

	private String encode(String str) {
		return str.replace("&", "\\sc1").replace(":", "\\sc2");
	}

	private String decode(String str) {
		return str.replace("\\sc1", "&").replace("\\sc2", ":");
	}

	public interface CallBack {
		public void find(String key, Array<String> data);
	}

}
