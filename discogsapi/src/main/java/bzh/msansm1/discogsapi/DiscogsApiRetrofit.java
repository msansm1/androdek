package bzh.msansm1.discogsapi;

import android.util.Log;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONObject;

import bzh.msansm1.discogsapi.json.DiscogsError;
import bzh.msansm1.discogsapi.json.release.Release;
import bzh.msansm1.discogsapi.json.search.SearchResponse;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;

/**
 * Created by ronan on 30/09/2016.
 */

public class DiscogsApiRetrofit {

    private DiscogsApiService apiService;

    public void init(String url){
        buildService(new OkHttpClient.Builder(), url);
    }

    private void buildService(OkHttpClient.Builder client, String url) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        client.addInterceptor(logging);
        ObjectMapper jacksonMapper = new ObjectMapper().configure(FAIL_ON_UNKNOWN_PROPERTIES, false);
        jacksonMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        jacksonMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(JacksonConverterFactory.create(jacksonMapper))
                .client(client.build())
                .build();

        apiService = retrofit.create(DiscogsApiService.class);
    }

    public void searchAlbumByCode(String token, String code, final DiscogsCallBack callBack) {
        apiService.searchAlbumWithCode(code, token, "").enqueue(new Callback<SearchResponse>() {
            @Override
            public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                if (response.isSuccessful()) {
                    callBack.success(response.body());
                } else {
                    callBack.failure(new DiscogsError("Search Album failed", "Discogs Error"));
                }
            }

            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t) {
                Log.e("DISCOGS", "search album by code", t);
            }
        });
    }

    public void getRelease(String token, Integer id, final DiscogsCallBack callBack) {
        apiService.getRelease(id, token, "").enqueue(new Callback<Release>() {
            @Override
            public void onResponse(Call<Release> call, Response<Release> response) {
                if (response.isSuccessful()) {
                    callBack.success(response.body());
                } else {
                    callBack.failure(new DiscogsError("Get release failed", "Discogs Error"));
                }
            }

            @Override
            public void onFailure(Call<Release> call, Throwable t) {
                Log.e("DISCOGS", "get release", t);
            }
        });
    }

    public interface DiscogsCallBack<T> {
        void success(T t);
        void failure(DiscogsError error);
    }
}

