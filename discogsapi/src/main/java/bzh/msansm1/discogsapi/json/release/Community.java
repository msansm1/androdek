package bzh.msansm1.discogsapi.json.release;

import java.util.List;

/**
 * Created by ronan on 02/10/2016.
 */

public class Community {
    private String status;
    private Rating rating;
    private Integer have;
    private List<Contributor> contributors;
    private Integer want;
    private Contributor submitter;
    private String data_quality;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public Integer getHave() {
        return have;
    }

    public void setHave(Integer have) {
        this.have = have;
    }

    public List<Contributor> getContributors() {
        return contributors;
    }

    public void setContributors(List<Contributor> contributors) {
        this.contributors = contributors;
    }

    public Integer getWant() {
        return want;
    }

    public void setWant(Integer want) {
        this.want = want;
    }

    public Contributor getSubmitter() {
        return submitter;
    }

    public void setSubmitter(Contributor submitter) {
        this.submitter = submitter;
    }

    public String getData_quality() {
        return data_quality;
    }

    public void setData_quality(String data_quality) {
        this.data_quality = data_quality;
    }
}
