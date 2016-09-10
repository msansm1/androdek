package bzh.msansm1.androdek.persistence.media;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by ronan on 10/09/2016.
 */
public class Album  extends RealmObject {
    @PrimaryKey
    private Integer id;

    private String title;
    private String artist;
    private String coverURL;
    private String genre;
    private Date releaseDate;

}
