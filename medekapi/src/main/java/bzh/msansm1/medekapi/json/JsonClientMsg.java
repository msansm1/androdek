package bzh.msansm1.medekapi.json;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class JsonClientMsg extends JsonResponse {

    private List<String> erreurs;

    public JsonClientMsg() {
    }

    public JsonClientMsg(String error) {
        super();
        erreurs = new ArrayList<String>();
        if (error != null) {
            erreurs.add(error);
        } else {
            erreurs.add("Unidentified error");
            Log.e("Unidentified error", "Unidentified error", new Exception("Unidentified error"));
        }
    }

    public JsonClientMsg(List<String> erreurs) {
        super();
        this.erreurs = erreurs;
    }

    public JsonClientMsg(Error error) {
        super();
        erreurs = new ArrayList<String>();
        if (error != null) {
            erreurs.add(error.getMessage());
        } else {
            erreurs.add("Unidentified error");
            Log.e("Unidentified error", "Unidentified error", new Exception("Unidentified error"));
        }
    }

    public JsonClientMsg(Throwable e) {
        super();
        erreurs = new ArrayList<String>();
        erreurs.add(e.getMessage());
    }

    public List<String> getErreurs() {
        return erreurs;
    }

    public void setErreurs(List<String> erreurs) {
        this.erreurs = erreurs;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((erreurs == null) ? 0 : erreurs.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        JsonClientMsg other = (JsonClientMsg) obj;
        if (erreurs == null) {
            if (other.erreurs != null) {
                return false;
            }
        } else if (!erreurs.equals(other.erreurs)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("JsonErrorMsg [" + erreurs + "=");
        for (String s : erreurs) {
            str.append(s+" ;");
        }
        str.append("]");
        return str.toString();
    }

}
