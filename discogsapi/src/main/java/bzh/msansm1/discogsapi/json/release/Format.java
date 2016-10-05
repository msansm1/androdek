package bzh.msansm1.discogsapi.json.release;

import java.util.List;

/**
 * Created by ronan on 02/10/2016.
 */

public class Format {
    private List<String> descriptions;
    private String name;
    private String qty;

    public List<String> getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(List<String> descriptions) {
        this.descriptions = descriptions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }
}
