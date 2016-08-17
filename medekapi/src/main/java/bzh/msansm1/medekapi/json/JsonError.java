package bzh.msansm1.medekapi.json;

/**
 * Created by ronan on 10/08/2016.
 */
public class JsonError {
    private String title;
    private String message;

    public JsonError() {
        super();
    }

    public JsonError(String message, String title) {
        this.message = message;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
