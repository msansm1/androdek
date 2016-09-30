package bzh.msansm1.discogsapi;

import bzh.msansm1.discogsapi.json.search.SearchResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by ronan on 30/09/2016.
 */

public interface DiscogsApiService {

    @GET("database/search")
    Call<SearchResponse> searchAlbumWithCode(@Query("q") String code, @Query("token") String token,
                                             @Query("callback") String callback);

}
