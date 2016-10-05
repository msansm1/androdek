package bzh.msansm1.discogsapi.json.release;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * Created by ronan on 02/10/2016.
 */

public class Release {
    private List<Video> videos;
    private List<String> series;
    private List<Label> labels;
    private Integer year;
    private Community community;
    private List<Artist> artists;
    private List<Image> images;
    private Integer format_quantity;
    private Integer id;
    private List<String> genres;
    private String thumb;
    private Integer num_for_sale;
    private String title;
    private String date_changed;
    private Integer master_id;
    private Float lowest_price;
    private String status;
    private String released_formatted;
    private Integer estimated_weight;
    private String master_url;
    private String released;
    private String date_added;
    private List<Track> tracklist;
//    private List<String> extraartists;
    private String country;
    private String notes;
    private List<Identifier> identifiers;
//    private List<String> companies;
    private String uri;
    private List<Format> formats;
    private String resource_url;
    private String data_quality;

    public List<Video> getVideos() {
        return videos;
    }

    public void setVideos(List<Video> videos) {
        this.videos = videos;
    }

    public List<String> getSeries() {
        return series;
    }

    public void setSeries(List<String> series) {
        this.series = series;
    }

    public List<Label> getLabels() {
        return labels;
    }

    public void setLabels(List<Label> labels) {
        this.labels = labels;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Community getCommunity() {
        return community;
    }

    public void setCommunity(Community community) {
        this.community = community;
    }

    public List<Artist> getArtists() {
        return artists;
    }

    public void setArtists(List<Artist> artists) {
        this.artists = artists;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public Integer getFormat_quantity() {
        return format_quantity;
    }

    public void setFormat_quantity(Integer format_quantity) {
        this.format_quantity = format_quantity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public Integer getNum_for_sale() {
        return num_for_sale;
    }

    public void setNum_for_sale(Integer num_for_sale) {
        this.num_for_sale = num_for_sale;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate_changed() {
        return date_changed;
    }

    public void setDate_changed(String date_changed) {
        this.date_changed = date_changed;
    }

    public Integer getMaster_id() {
        return master_id;
    }

    public void setMaster_id(Integer master_id) {
        this.master_id = master_id;
    }

    public Float getLowest_price() {
        return lowest_price;
    }

    public void setLowest_price(Float lowest_price) {
        this.lowest_price = lowest_price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReleased_formatted() {
        return released_formatted;
    }

    public void setReleased_formatted(String released_formatted) {
        this.released_formatted = released_formatted;
    }

    public Integer getEstimated_weight() {
        return estimated_weight;
    }

    public void setEstimated_weight(Integer estimated_weight) {
        this.estimated_weight = estimated_weight;
    }

    public String getMaster_url() {
        return master_url;
    }

    public void setMaster_url(String master_url) {
        this.master_url = master_url;
    }

    public String getReleased() {
        return released;
    }

    public void setReleased(String released) {
        this.released = released;
    }

    public String getDate_added() {
        return date_added;
    }

    public void setDate_added(String date_added) {
        this.date_added = date_added;
    }

    public List<Track> getTracklist() {
        return tracklist;
    }

    public void setTracklist(List<Track> tracklist) {
        this.tracklist = tracklist;
    }

/*    public List<String> getExtraartists() {
        return extraartists;
    }

    public void setExtraartists(List<String> extraartists) {
        this.extraartists = extraartists;
    } */

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public List<Identifier> getIdentifiers() {
        return identifiers;
    }

    public void setIdentifiers(List<Identifier> identifiers) {
        this.identifiers = identifiers;
    }

/*    public List<String> getCompanies() {
        return companies;
    }

    public void setCompanies(List<String> companies) {
        this.companies = companies;
    } */

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public List<Format> getFormats() {
        return formats;
    }

    public void setFormats(List<Format> formats) {
        this.formats = formats;
    }

    public String getResource_url() {
        return resource_url;
    }

    public void setResource_url(String resource_url) {
        this.resource_url = resource_url;
    }

    public String getData_quality() {
        return data_quality;
    }

    public void setData_quality(String data_quality) {
        this.data_quality = data_quality;
    }
}
