package bzh.msansm1.medekapi.json.book;

public class JsonBooktype {
	private Integer id;
	private String name;
	
	public JsonBooktype() {
		super();
	}

	public JsonBooktype(Integer id, String name) {
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
