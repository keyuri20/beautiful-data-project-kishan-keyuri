package cs594Homework2;

public class SimpleModelClass {

	String title,place,magType,type,id;
	long time,updatedTime,timezone,sig;
	Double mag,rms,dmin,gap,longitude,latitude,depth;
	public SimpleModelClass(String title, String place, String magType, String type,
			long time, long updatedTime, long timezone, long sig, Double mag,
			Double rms, Double dmin, Double gap, Double longitude,
			Double latitude, Double depth, String id) {
		super();
		this.title = title;
		this.place = place;
		this.magType = magType;
		this.type = type;
		this.time = time;
		this.updatedTime = updatedTime;
		this.timezone = timezone;
		this.sig = sig;
		this.mag = mag;
		this.rms = rms;
		this.dmin = dmin;
		this.gap = gap;
		this.longitude = longitude;
		this.latitude = latitude;
		this.depth = depth;
		this.id=id;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public String getMagType() {
		return magType;
	}
	public void setMagType(String magType) {
		this.magType = magType;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	public long getUpdatedTime() {
		return updatedTime;
	}
	public void setUpdatedTime(long updatedTime) {
		this.updatedTime = updatedTime;
	}
	public long getTimezone() {
		return timezone;
	}
	public void setTimezone(long timezone) {
		this.timezone = timezone;
	}
	public long getSig() {
		return sig;
	}
	public void setSig(long sig) {
		this.sig = sig;
	}
	public Double getMag() {
		return mag;
	}
	public void setMag(Double mag) {
		this.mag = mag;
	}
	public Double getRms() {
		return rms;
	}
	public void setRms(Double rms) {
		this.rms = rms;
	}
	public Double getDmin() {
		return dmin;
	}
	public void setDmin(Double dmin) {
		this.dmin = dmin;
	}
	public Double getGap() {
		return gap;
	}
	public void setGap(Double gap) {
		this.gap = gap;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public Double getDepth() {
		return depth;
	}
	public void setDepth(Double depth) {
		this.depth = depth;
	}
	
	public static SimpleModelClass build(MockData data)
	{
		return new SimpleModelClass(data.getTitle(), data.getPlace(), data.getMagType(), data.getType(),
			data.getTime(), data.getUpdatedTime(), data.getTimezone(), data.getSig(), data.getMag(),
			data.getRms(), data.getDmin(), data.getGap(), data.getLongitude(),
			data.getLatitude(), data.getDepth(), data.getId());
	}
}
