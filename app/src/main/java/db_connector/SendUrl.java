package db_connector;

public class SendUrl {
	private String url = null;
	private int parameter_count;
	
	public SendUrl(String url) {
		super();
		this.url = url;
		this.parameter_count = 0;
	}
	public void set_sending_address(String address){
		this.url = address;
	}
	public void set_parameter(Object value){
		if(this.parameter_count==0){
			this.url = this.url + "?"+value.toString();
			return;
		}
		this.url = this.url+"&"+value.toString();
	}
	public String get_url(){
		return this.url;
	}
}
