package cs594Homework2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Collection;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.apache.commons.io.IOUtils;

public class SourceImpl implements Source<ModelClass>{
	
	JSONObject getData(String d1, String d2) throws ParseException, IOException
	{
		System.out.println(d1+"--"+d2+",");
		JSONObject json=null;
		URL url=new URL("http://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime="+d1+"&endtime="+d2+"&minmagnitude=1&limit=20000");
		System.out.println(url.toString());
		try {
		    String text = IOUtils.toString(url);
		    System.out.println("Text Length: "+text.length());
		    JSONParser parser = new JSONParser();
		    json = (JSONObject) parser.parse(text);		    
		    return json;
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		return json;
		
	}
	public boolean hasNext(String[] arr, int i) {
		// TODO Auto-generated method stub
		
		return (i+2<=arr.length);
	}

	

	public void remove() {
		// TODO Auto-generated method stub
		
	}
	public boolean hasNext() {
		// TODO Auto-generated method stub
		return false;
	}
	public Collection<ModelClass> next() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
