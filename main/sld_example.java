
import naifcanbasci.util.SLD;
import java.io.IOException;
import java.util.Date;
import naifcanbasci.util.Array;

public class sld_example 
{
	public static void main(String[] args) throws IOException {
		String filepath = "/sdcard/test.sld",
				mode = "rw";
		SLD sld = new SLD(filepath, mode);
		
		sld.log("George", "Orwell");
		sld.log("Naif Can", "Basci");
		sld.log("Jhon3", "Wick", "11");
		sld.log("Gum123", "Human", "Luffy");


		//contains "n" in key
		sld.search("%n%", new SLD.CallBack() {

				@Override
				public void find(String key, Array<String> data) {
					System.out.print(key);
					System.out.println(":");
					System.out.println(data.toString());
				}
			});
		/*
		Output:
		 Naif Can:
		 ["Basci"]
		 Naif Can:
		 ["Wick",
		 "11"]
		*/


		//contains any number
		sld.search(".*[0-9]+.*", new SLD.CallBack() {

				@Override
				public void find(String key, Array<String> data) {
					System.out.print(key);
					System.out.println(":");
					System.out.println(data.toString());
				}
			});
		/*
		 Gum123:
		 ["Human",
		 "Luffy"]
		*/
	}
}
