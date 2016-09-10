package bzh.msansm1.medekapi;

import java.util.List;
import java.util.Map;

import bzh.msansm1.medekapi.json.album.JsonAlbum;
import bzh.msansm1.medekapi.json.album.JsonMyAlbum;
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
import retrofit2.http.Query;

/**
 * Created by msansm1 on 11/07/2016.
 */
public interface MedekApiService {

    @POST("services/auth/login")
    Call<JsonAuth> login(@Body JsonLogin request);

    @POST("services/auth/logout")
    Call<String> logout(@Body JsonAuth request);

    @GET("services/home/mycollec")
    Call<JsonCollectionStats> myCollection();

    //	@QueryParam("from") int from, @QueryParam("limit") int limit,
    //  @QueryParam("orderBy") String orderBy, @QueryParam("orderDir") String orderDir) {
    @GET("services/albums")
    Call<List<JsonAlbum>> allAlbums(@Query("from") int from, @Query("limit") int limit,
                                    @Query("orderBy") String orderBy, @Query("orderDir") String orderDir);

    @GET("services/books")
    @FormUrlEncoded
    Call<JsonBook> allBooks(@FieldMap Map<String, String> params);

    @GET("services/movies")
    @FormUrlEncoded
    Call<JsonMovie> allMovies(@FieldMap Map<String, String> params);

    @GET("services/tvshows")
    @FormUrlEncoded
    Call<JsonShow> allTvShows(@FieldMap Map<String, String> params);

}
