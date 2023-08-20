package naifcanbasci.httpserver;
import java.nio.file.Paths;
import java.util.HashMap;
import naifcanbasci.util.ArrayMap;

public class Path
{
	public final String path, matchPath;
	private final String[] pathAr;
	
	public Path(String path) {
		this.path = Paths.get(path).toString();
		pathAr = this.path.split("/");
		String matchPath="";
		for(String s: pathAr)
			if(s.matches("[:].+"))
				matchPath += "/.+";
			else if(!s.isEmpty())
				matchPath += '/' + s;
		
		this.matchPath = matchPath.isEmpty() ? "/" : matchPath;
	}

	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Path)) return false;
		return ((Path)obj).path.matches(matchPath);
	}

	public void getParams(String path, ArrayMap<String, String> params) {
		if(pathAr.length == 0) return;
		String[] ar = path.split("/");
		for(int i=0; i < pathAr.length; i++) 
			if(pathAr[i].matches("[:].+"))
				if(i < ar.length)
				params.put(pathAr[i].substring(1), ar[i]);
	}

	@Override
	public String toString() {
		return path;
	}

}
