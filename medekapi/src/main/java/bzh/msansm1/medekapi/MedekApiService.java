package bzh.msansm1.medekapi;

import java.util.List;
import java.util.Map;

import bzh.msansm1.medekapi.json.JsonSimpleResponse;
import bzh.msansm1.medekapi.json.album.JsonAlbum;
import bzh.msansm1.medekapi.json.album.JsonMyAlbum;
import bzh.msansm1.medekapi.json.album.JsonTrack;
import bzh.msansm1.medekapi.json.artist.JsonArtist;
import bzh.msansm1.medekapi.json.auth.JsonAuth;
import bzh.msansm1.medekapi.json.auth.JsonLogin;
import bzh.msansm1.medekapi.json.book.JsonBook;
import bzh.msansm1.medekapi.json.home.JsonCollectionStats;
import bzh.msansm1.medekapi.json.movie.JsonMovie;
import bzh.msansm1.medekapi.json.tvshow.JsonShow;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by msansm1 on 11/07/2016.
 */
public interface MedekApiService {

    @POST("services/auth/login/mobile")
    Call<JsonAuth> login(@Body JsonLogin request);

    @POST("services/auth/logout/mobile")
    Call<String> logout(@Body JsonAuth request);

    @GET("services/home/mycollec")
    Call<JsonCollectionStats> myCollection();

    @GET("services/home/allcollec")
    Call<JsonCollectionStats> allCollection();

    @GET("services/albums")
    Call<List<JsonAlbum>> allAlbums(@Query("from") int from, @Query("limit") int limit,
                                    @Query("orderBy") String orderBy, @Query("orderDir") String orderDir);

    @POST("services/albums")
    Call<JsonAlbum> createUpdateAlbum(@Body JsonAlbum album);

    @GET("services/albums/user")
    Call<List<JsonAlbum>> userAlbums(@Query("from") int from, @Query("limit") int limit,
                                    @Query("orderBy") String orderBy, @Query("orderDir") String orderDir,
                                    @Query("userId") int userId);

    @POST("services/albums/addtocollec")
    Call<JsonSimpleResponse> addAlbumToMyCollec(@Body JsonMyAlbum album);

    @GET("services/tracks/album/{albumId}")
    Call<List<JsonTrack>> getAlbumTracks(@Path("albumId") Integer albumId);

    @POST("services/tracks")
    Call<JsonTrack> createUpdateTrack(JsonTrack track);

    @GET("services/books")
    Call<List<JsonBook>> allBooks(@Query("from") int from, @Query("limit") int limit,
                            @Query("orderBy") String orderBy, @Query("orderDir") String orderDir);

    @GET("services/movies")
    Call<List<JsonMovie>> allMovies(@Query("from") int from, @Query("limit") int limit,
                              @Query("orderBy") String orderBy, @Query("orderDir") String orderDir);

    @GET("services/tvshows")
    Call<List<JsonShow>> allTvshows(@Query("from") int from, @Query("limit") int limit,
                              @Query("orderBy") String orderBy, @Query("orderDir") String orderDir);

    @GET("services/artists")
    Call<List<JsonArtist>> allArtists();

    @POST("services/artists")
    Call<JsonArtist> createUpdateArtist(@Body JsonArtist artist);

}
