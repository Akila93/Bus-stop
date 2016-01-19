package row.pack;

public class Route extends Item{
	private int route_id;
	private String name;
	
	public Route(int route_id,String name){
		this.setRoute_id(route_id);
		this.setName(name);
	}

	public int getRoute_id() {
		return route_id;
	}

	public void setRoute_id(int route_id) {
		this.route_id = route_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
