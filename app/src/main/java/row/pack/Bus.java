package row.pack;

public class Bus extends Item{
	private String id;
	private String path_id;
	private String pathName;
	
	public Bus(String id,String path_id, String pathName){
		this.id = id;
		this.path_id = path_id;
		this.pathName = pathName;
	}
	public String toString(){
		return id;
	}
	public void setBusId(String bus_id){
		this.id = bus_id;
	}
	public String getPath_id() {
		return path_id;
	}
	public void setPath_id(String path_id) {
		this.path_id = path_id;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Bus other = (Bus) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	public String getPathName() {
		return pathName;
	}
	public void setPathName(String getPathName) {
		this.pathName = getPathName;
	}
	
	
	
}
