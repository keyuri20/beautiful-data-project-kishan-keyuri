package cs594Homework2;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;

public class CollectorImpl implements Collector<ModelClass, JSONObject>{

	//mongo client for saving data
	MongoClient mongoClient;
	MongoDatabase database;
	MongoCollection<Document> collection;
	long count=0;
	public CollectorImpl() {
		MongoClient mongoClient=new MongoClient();
		MongoDatabase database = mongoClient.getDatabase("earthquake");
		collection=database.getCollection("data");
		
	}

	public Collection<ModelClass> mungee(JSONObject json) {
		//JSONArray array=(JSONArray) json.get("bbox");
		JSONObject metadata=(JSONObject) json.get("metadata"); 
		count+=(Long) metadata.get("count");		
		
		JSONArray features=(JSONArray) json.get("features");
		//System.out.println("size: "+features.size());
		//File file=new File("E:/MS/cs594 Big Data/data/filteredjson.txt");
		Collection<ModelClass> collection_modelclass=new ArrayList<ModelClass>();
		for(int i=0;i<features.size();i++)
		{

			JSONObject row=(JSONObject) features.get(i);
			String id=(String) row.get("id");
			JSONObject properties=(JSONObject) row.get("properties");		
			String title=(String) properties.get("title");
			long time=(Long) properties.get("time");
			long updatedTime=(Long) properties.get("updated");
			Double mag=Double.parseDouble(properties.get("mag").toString());
			String place=(String) properties.get("place");
			Double rms;
			if(properties.get("rms")==null)
			{
				rms=0.0;
			}
			else
			{
				rms=Double.parseDouble(properties.get("rms").toString());
			}			
			String magType=(String) properties.get("magType");
			long timezone;
			if(properties.get("tz")==null)
			{
				timezone=0;
			}
			else
			{
				timezone=Long.parseLong(properties.get("tz").toString());
			}		
			long sig=(Long) properties.get("sig");
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
			
			JSONObject geometry=(JSONObject) row.get("geometry");
			String type=(String) geometry.get("type");
			JSONArray coordinates=(JSONArray) geometry.get("coordinates");
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
			}
			//save to modelclass
			ModelClass classObject=new ModelClass(title, place, magType, type, time, updatedTime, timezone, sig, mag, rms, dmin, gap, longitude, latitude, depth,id);
			collection_modelclass.add(classObject);
		}
		//System.out.println("collection size: "+collection_modelclass.size());
		System.out.println("count: "+count);
		return collection_modelclass;
	}

	public void save(Collection<ModelClass> data) {
		// TODO Auto-generated method stub
		for(ModelClass d:data)
		{
			Document doc=new Document().append("id",d.getId())
					.append("title", d.getTitle())
					.append("place",d.getPlace() )
					.append("magnitudeType", d.getMagType())
					.append("type", d.getType())
					.append("time", d.getTime())
					.append("updatedTime",d.getUpdatedTime() )
					.append("timezone", d.getTimezone())
					.append("sig", d.getSig())
					.append("mag", d.getMag() )
					.append("rms", d.getRms())
					.append("dmin",d.getDmin() )
					.append("gap", d.getGap())
					.append("longitude", d.getLongitude())
					.append("latitude", d.getLatitude())
					.append("depth", d.getDepth());
			collection.insertOne(doc);
		}
		
		//System.out.println("insertion into db complete hopefully--");
		
	}

}

