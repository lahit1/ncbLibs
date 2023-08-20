package naifcanbasci.httpserver.headers;

public final class ContentType
{
	public static final String All = "*/*";

	public static class Text {
		public static final String All="text/*";
		public static final String Plain="text/plain";
		public static final String Html="text/html";
		public static final String Css="text/css";
		public static final String Javascript="text/javascript";
		public static final String X_Javascript="text/x-javascript";
		public static final String X_Json="text/x-json";
		
	}
	
	public static class Application {
		public static final String All="application/*";
		public static final String X_Javascript="application/x-javascript";
		public static final String Json="application/json";
		public static final String Pdf="application/pdf";
		public static final String Zip="application/zip";
	}
	
	public static class Audio {
		public static final String All="audio/*";
		public static final String Mpeg="audio/mpeg";
		public static final String Vorbis="audio/vorbis";
	}
	
	ContentType() {}
}
