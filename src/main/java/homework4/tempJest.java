package homework4;


import com.google.common.collect.Lists;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import io.searchbox.action.BulkableAction;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;
import io.searchbox.core.Bulk;
import io.searchbox.core.Index;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.bson.Document;
import org.elasticsearch.action.index.IndexRequest;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
public class tempJest {
	public static void main(String[] args) throws ParseException, IOException
	{
		final String indexName="earthquake";
		final String typeName="data-points";
		String awsAddress = "http://search-test3-nbdqyucc4a6626zdqorghdiq4a.us-west-2.es.amazonaws.com";
		JestClientFactory factory = new JestClientFactory();
        factory.setHttpClientConfig(new HttpClientConfig
            .Builder(awsAddress)
            .multiThreaded(true)
            .build());
        JestClient client = factory.getObject();
        MongoClient mongoClient=new MongoClient("localhost",27017);
        MongoDatabase database=mongoClient.getDatabase("earthquake");
        MongoCollection<Document> collection = database.getCollection("data");
        int count=0;
        Collection<EQ> e1=Lists.newArrayList();
        try (MongoCursor<Document> cursor = collection.find().iterator()) {
            while (cursor.hasNext()) {
            	
                //Now fetch data here
                String mainObjectString=cursor.next().toJson();
                //Convert String to Json Object
                JSONParser parser=new JSONParser();
                JSONObject mainObject=(JSONObject) parser.parse(mainObjectString);
                //System.out.println("Getting JSON OBJECTS: "+mainObject.toString());
                String place=(String) mainObject.get("place");
                String id=(String) mainObject.get("id");
                JSONObject timeObject=(JSONObject) mainObject.get("time");
                
                long t=Long.parseLong(timeObject.get("$numberLong").toString());                      
                Date date = new Date(t);
                SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
                //System.out.println(new SimpleDateFormat("yyyy-MM-dd").format(date));
				String time = format.format(date);
                
                //SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
                //Date d1=new Date(t);
                //Date time=format.parse(format.format(d1));
                
                
                JSONObject updatedTimeObject=(JSONObject) mainObject.get("updatedTime");                
                long ut=Long.parseLong(updatedTimeObject.get("$numberLong").toString());
                Date d2=new Date(ut);
                String updatedTime=format.format(d2);
                                               
                
                JSONObject sigObject=(JSONObject) mainObject.get("sig");                
                long sig=Long.parseLong(updatedTimeObject.get("$numberLong").toString());
                String latlong=mainObject.get("latitude").toString()+","+mainObject.get("longitude").toString();
                //Double latitude=Double.parseDouble(mainObject.get("latitude").toString());
                //Double longitude=Double.parseDouble(mainObject.get("longitude").toString());
                Double depth=Double.parseDouble(mainObject.get("depth").toString());
                Double gap=Double.parseDouble(mainObject.get("gap").toString());
                Double rms=Double.parseDouble(mainObject.get("rms").toString());
                Double dmin=Double.parseDouble(mainObject.get("dmin").toString());
                Double mag=Double.parseDouble(mainObject.get("mag").toString());
                EQ eq=new EQ(place, id, time,updatedTime,sig,mag,rms,dmin,gap,depth,new pin(latlong));
                if(count<500)
                {
                	e1.add(eq);                	
                	count++;
                }
                else
                {
                	try
                	{                		
                		Collection<BulkableAction> actions = Lists.newArrayList();
                		e1.stream().forEach(tmp->{
                			actions.add(new Index.Builder(tmp).build());
                		});
                		Bulk.Builder bulk = new Bulk.Builder().defaultIndex(indexName).defaultType(typeName).addAction(actions);
                		client.execute(bulk.build());
                		System.out.println("COUNT RESET");
                		count = 0;
                		e1 = Lists.newArrayList();
                		System.out.println("Inserted 500 documents to cloud");
                	}catch(Exception E)
                	{
                		E.printStackTrace();
                	}
                }                                                               
            }        
        }
        Collection<BulkableAction> actions = Lists.newArrayList();
        e1.stream()
            .forEach(tmp -> {
                actions.add(new Index.Builder(tmp).build());
            });
        Bulk.Builder bulk = new Bulk.Builder()
            .defaultIndex(indexName)
            .defaultType(typeName)
            .addAction(actions);
        client.execute(bulk.build());
        System.out.println("We are done! Yay!");
	}	
}
class EQ{
	String place,id;
	String time,updatedTime;
	long sig;		
	Double mag,rms,dmin,gap,depth;
	pin p;
	public EQ(String place, String id, String time, String updatedTime,
			long sig, Double mag, Double rms, Double dmin, Double gap,
			Double depth, pin p) {
		super();
		this.place = place;
		this.id = id;
		this.time = time;
		this.updatedTime = updatedTime;
		this.sig = sig;
		this.mag = mag;
		this.rms = rms;
		this.dmin = dmin;
		this.gap = gap;
		this.depth = depth;
		this.p=p;
	}			
}
class pin
{
	String latlong;
	public pin(String latlong)
	{
		this.latlong=latlong;
	}
}
