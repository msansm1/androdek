package bzh.msansm1.discogsapi.json.release;

/**
 * Created by ronan on 02/10/2016.
 */

public class Label {
    private String name;
    private String entity_type;
    private String catno;
    private String resource_url;
    private Integer id;
    private String entity_type_name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEntity_type() {
        return entity_type;
    }

    public void setEntity_type(String entity_type) {
        this.entity_type = entity_type;
    }

    public String getCatno() {
        return catno;
    }

    public void setCatno(String catno) {
        this.catno = catno;
    }

    public String getResource_url() {
        return resource_url;
    }

    public void setResource_url(String resource_url) {
        this.resource_url = resource_url;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEntity_type_name() {
        return entity_type_name;
    }

    public void setEntity_type_name(String entity_type_name) {
        this.entity_type_name = entity_type_name;
    }
}
