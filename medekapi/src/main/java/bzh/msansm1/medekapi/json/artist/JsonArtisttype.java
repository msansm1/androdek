package bzh.msansm1.medekapi.json.artist;

public class JsonArtisttype {
	private Integer id;
	private String name;
	
	public JsonArtisttype() {
		super();
	}

	public JsonArtisttype(Integer id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
