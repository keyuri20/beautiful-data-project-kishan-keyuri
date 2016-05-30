package Homework3;


import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.util.JSON;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.bson.Document;
import org.elasticsearch.action.bulk.*;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.node.Node;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static org.elasticsearch.node.NodeBuilder.nodeBuilder;

//Mapping: 
/*DELETE  earthquake
PUT /earthquake
 {
     "mappings" : {
         "data-points" : {
             "properties" : {
                 "place" : {
                     "type" : "string",
                     "index" : "not_analyzed"
                 },
                 "id" : {
                     "type" : "string",
                     "index" : "not_analyzed"
                 },
                 "sig" : {
                      "type": "long",
                      "index" : "not_analyzed"
                 },
                 "time" : {
                     "type" : "date"
                     
                 },
                 "updatedTime": {
                     "type": "date",
                     "index" : "not_analyzed"
                 },
                 "mag" : {
                      "type": "double"
                      
                 },
                 "rms" : {
                   "type": "double",
                   "index" : "not_analyzed"
                 },
                 "dmin" : {
                   "type": "double",
                   "index" : "not_analyzed"
                 },
                 "gap" : {
                   "type": "double",
                   "index" : "not_analyzed"
                 },
                 "longitude" : {
                   "type": "double"
                   
                 },
                 "latitude" : {
                   "type": "double"
                   
                 },
                 "depth" :  {
                   "type": "double",
                   "index" : "not_analyzed"
                 }
             }
         }
     }
 }

GET /earthquake/data-points/_mapping
GET /earthquake/_count
GET earthquake/_search
*/
//



public class elasticSearch {
	public final static String indexName="earthquake";
	public final static String typeName="data-points";
	public static void main(String[] args) throws URISyntaxException, IOException, ParseException, java.text.ParseException {
        Node node = nodeBuilder().settings(Settings.builder()
            .put("cluster.name", "kishan2")
            .put("path.home", "elasticsearch-data")).node();
        Client client = node.client();
        String fname=System.getProperty("user.dir")+"/src/main/resources/data.json";
        /*File json = new File(
                ClassLoader.getSystemResource("data.json")
                    .toURI()
            );*/
        Gson gson=new Gson();
     // create bulk processor
        BulkProcessor bulkProcessor = BulkProcessor.builder(
            client,
            new BulkProcessor.Listener() {
                @Override
                public void beforeBulk(long executionId,
                                       BulkRequest request) {
                }

                @Override
                public void afterBulk(long executionId,
                                      BulkRequest request,
                                      BulkResponse response) {
                	System.out.println("bulk insertion success");
                }

                @Override
                public void afterBulk(long executionId,
                                      BulkRequest request,
                                      Throwable failure) {
                    System.out.println("Facing error while importing data to elastic search");
                    failure.printStackTrace();
                }
            })
            .setBulkActions(10000)
            .setBulkSize(new ByteSizeValue(1, ByteSizeUnit.GB))
            .setFlushInterval(TimeValue.timeValueSeconds(5))
            .setConcurrentRequests(1)
            .setBackoffPolicy(
                BackoffPolicy.exponentialBackoff(TimeValue.timeValueMillis(100), 3))
            .build();    
        
        //bulkProcessor.add(new IndexRequest(indexName,typeName).source(XContentFactory.jsonBuilder().startObject().startObject("Savin").field("hey").value("hello").endObject().endObject()));
        /*JSONParser parser=new JSONParser();
        JSONObject object=(JSONObject) parser.parse(new FileReader(fname));
        System.out.println(object);*/
        //JsonObject object=(JsonObject)obj;
        /*JsonArray features=(JsonArray) object.get("features");
        for(int i=0;i<features.size();i++)
        {    
        	JsonObject row=(JsonObject) features.get(i);
        	String id=row.get("id").toString();
			JsonObject properties=(JsonObject) row.get("properties");					
			String place=properties.get("place").toString();
			long time=(long)properties.get("time").getAsLong();
			long updatedTime=(long)properties.get("time").getAsLong();
			Double mag=properties.get("mag").getAsDouble();
			long sig=(Long) properties.get("sig").getAsLong();
			Double rms;
			if(properties.get("rms")==null)
			{
				rms=0.0;
			}
			else
			{
				rms=Double.parseDouble(properties.get("rms").toString());
			}
			Double dmin;
			if(properties.get("dmin")==null)
			{
				dmin=0.0;
			}
			else
			{
				dmin=Double.parseDouble(properties.get("dmin").toString());
			}
			Double gap;
			if(properties.get("gap")==null)
			{
				gap=0.0;
			}
			else
			{
				gap=Double.parseDouble(properties.get("gap").toString());
			}
			JsonObject geometry=(JsonObject) row.get("geometry");			
			JsonArray coordinates=(JsonArray) geometry.get("coordinates");
			Double longitude=Double.parseDouble(coordinates.get(0).toString());
			Double latitude=Double.parseDouble(coordinates.get(1).toString());	
			Double depth;//=Double.parseDouble(coordinates.get(2).toString());
			if(coordinates.get(2)==null)
			{
				depth=0.0;
			}
			else
			{
				depth=Double.parseDouble(coordinates.get(2).toString());
			}*/
        //try using MongoDB
        MongoClient mongoClient=new MongoClient("localhost",27017);
        MongoDatabase database=mongoClient.getDatabase("earthquake");
        MongoCollection<Document> collection = database.getCollection("data");
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
                Double latitude=Double.parseDouble(mainObject.get("latitude").toString());
                Double longitude=Double.parseDouble(mainObject.get("longitude").toString());
                Double depth=Double.parseDouble(mainObject.get("depth").toString());
                Double gap=Double.parseDouble(mainObject.get("gap").toString());
                Double rms=Double.parseDouble(mainObject.get("rms").toString());
                Double dmin=Double.parseDouble(mainObject.get("dmin").toString());
                Double mag=Double.parseDouble(mainObject.get("mag").toString());                                
                earthQuake q=new earthQuake(place, id, time,updatedTime,sig,mag,rms,dmin,gap,longitude,latitude,depth);
                System.out.println("toJson: "+gson.toJson(q));
                bulkProcessor.add(new IndexRequest(indexName,typeName).source(gson.toJson(q)));
                
            }
            System.out.println("finished");
        }
			//earthQuake q=new earthQuake(place, id, time, updatedTime, sig, mag, rms, dmin, gap, longitude, latitude, depth);
			//bulkProcessor.add(new IndexRequest(indexName,typeName).source(gson.toJson(q)));
			
        }
	}
	class earthQuake{
		String place,id;
		String time,updatedTime;
		long sig;		
		Double mag,rms,dmin,gap,longitude,latitude,depth;
		public earthQuake(String place, String id, String time,
				String updatedTime, long sig, Double mag, Double rms,
				Double dmin, Double gap, Double longitude, Double latitude,
				Double depth) {
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
			this.longitude = longitude;
			this.latitude = latitude;
			this.depth = depth;
		}
				
	}
	
