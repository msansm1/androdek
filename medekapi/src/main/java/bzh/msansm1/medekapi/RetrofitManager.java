package bzh.msansm1.medekapi;

import android.util.Log;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import bzh.msansm1.androdek.persistence.MedekConfig;
import bzh.msansm1.medekapi.json.JsonError;
import bzh.msansm1.medekapi.json.album.JsonAlbum;
import bzh.msansm1.medekapi.json.auth.JsonAuth;
import bzh.msansm1.medekapi.json.auth.JsonLogin;
import bzh.msansm1.medekapi.json.book.JsonBook;
import bzh.msansm1.medekapi.json.home.JsonCollectionStats;
import bzh.msansm1.medekapi.json.movie.JsonMovie;
import bzh.msansm1.medekapi.json.tvshow.JsonShow;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by msansm1 on 11/07/2016.
 */
public class RetrofitManager {

    private MedekApiService service;
    private MedekConfig config;

    public void init(MedekConfig config){
        this.config=config;
        buildService(new OkHttpClient.Builder());
    }

    private void buildService(OkHttpClient.Builder client) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        client.addInterceptor(logging);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(config.getApiUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client.build())
                .build();

        service = retrofit.create(MedekApiService.class);
    }

    public void login(JsonLogin loginData, final MedekCallBack cb){
        Call<JsonAuth> call = service.login(loginData);
        call.enqueue(new Callback<JsonAuth>() {
            @Override
            public void onResponse(Call<JsonAuth> call, Response<JsonAuth> response) {
                if (response.isSuccessful()) {
                    final JsonAuth auth = response.body();
                    final okhttp3.OkHttpClient.Builder httpClient = new okhttp3.OkHttpClient.Builder()
                            .readTimeout(20, TimeUnit.SECONDS)
                            .connectTimeout(20, TimeUnit.SECONDS);
                    httpClient.interceptors().add(new Interceptor() {
                        @Override
                        public okhttp3.Response intercept(Chain chain) throws IOException {
                            Request original = chain.request();
                            Request.Builder requestBuilder = original.newBuilder()
                                    .addHeader("Content-Type", "application/json")
                                    .addHeader("securtoken", auth.getToken());
                            Request request = requestBuilder.build();

                            return chain.proceed(request);
                        }
                    });
                    buildService(httpClient);

                    cb.success(response.body());
                } else {
                    cb.failure(new JsonError("Login failed", "Error"));
                }
            }

            @Override
            public void onFailure(Call<JsonAuth> call, Throwable t) {
                cb.failure(new JsonError("Login failed", "Error"));
            }
        });
    }

    public void logout(final JsonAuth request, final MedekCallBack<String> cb){
        service.logout(request).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                int responseCode = response.code();
                if(responseCode!=200) {
                    manageError(cb,responseCode);
                }else {
                    if(response.body()!=null) {
                        manageResponse(response, cb);
                    }else {
                        cb.failure(new JsonError("An error has occurred", "Error"));
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                manageError(t,cb);
            }
        });
    }

    public void getMyCollection(final String token, final MedekCallBack cb){

        // set token
        final okhttp3.OkHttpClient.Builder httpClient = new okhttp3.OkHttpClient.Builder()
                .readTimeout(20, TimeUnit.SECONDS)
                .connectTimeout(20, TimeUnit.SECONDS);
        httpClient.interceptors().add(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request.Builder requestBuilder = original.newBuilder()
                        .addHeader("Content-Type", "application/json")
                        .addHeader("securtoken", token);
                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });
        buildService(httpClient);

        getMyCollection(cb);
    }

    public void getMyCollection(final MedekCallBack cb){
        Call<JsonCollectionStats> call = service.myCollection();

        call.enqueue(new Callback<JsonCollectionStats>() {
            @Override
            public void onResponse(Call<JsonCollectionStats> call, Response<JsonCollectionStats> response) {
                if (response.isSuccessful()) {
                    cb.success(response.body());
                } else if (response.code() == 401) {
                    cb.failure(new JsonError("Authentication failed", "Error"));
                } else {
                    cb.failure(new JsonError("Get collection failed", "Error"));
                }
            }

            @Override
            public void onFailure(Call<JsonCollectionStats> call, Throwable t) {
                cb.failure(new JsonError("Get collection failed", "Error"));
            }
        });
    }

    public void getAllAlbums(int from, int limit, String orderBy, String orderDir, final MedekCallBack<List<JsonAlbum>> cb) {
        service.allAlbums(from, limit, orderBy, orderDir).enqueue(new Callback<List<JsonAlbum>>() {
            @Override
            public void onResponse(Call<List<JsonAlbum>> call, Response<List<JsonAlbum>> response) {
                cb.success(response.body());
            }

            @Override
            public void onFailure(Call<List<JsonAlbum>> call, Throwable t) {
                cb.failure(new JsonError("Get albums failed", "Error"));
            }
        });
    }

    public void getAllBooks(int from, int limit, String orderBy, String orderDir, final MedekCallBack<List<JsonBook>> cb) {
        service.allBooks(from, limit, orderBy, orderDir).enqueue(new Callback<List<JsonBook>>() {
            @Override
            public void onResponse(Call<List<JsonBook>> call, Response<List<JsonBook>> response) {
                cb.success(response.body());
            }

            @Override
            public void onFailure(Call<List<JsonBook>> call, Throwable t) {
                cb.failure(new JsonError("Get books failed", "Error"));
            }
        });
    }

    public void getAllMovies(int from, int limit, String orderBy, String orderDir, final MedekCallBack<List<JsonMovie>> cb) {
        service.allMovies(from, limit, orderBy, orderDir).enqueue(new Callback<List<JsonMovie>>() {
            @Override
            public void onResponse(Call<List<JsonMovie>> call, Response<List<JsonMovie>> response) {
                cb.success(response.body());
            }

            @Override
            public void onFailure(Call<List<JsonMovie>> call, Throwable t) {
                cb.failure(new JsonError("Get movies failed", "Error"));
            }
        });
    }

    public void getAllTvshows(int from, int limit, String orderBy, String orderDir, final MedekCallBack<List<JsonShow>> cb) {
        service.allTvshows(from, limit, orderBy, orderDir).enqueue(new Callback<List<JsonShow>>() {
            @Override
            public void onResponse(Call<List<JsonShow>> call, Response<List<JsonShow>> response) {
                cb.success(response.body());
            }

            @Override
            public void onFailure(Call<List<JsonShow>> call, Throwable t) {
                cb.failure(new JsonError("Get tv shows failed", "Error"));
            }
        });
    }

    private void manageResponse(Response response, MedekCallBack cb) {
        if(response.body() instanceof JsonError){
            cb.failure((JsonError) response.body());
        } else {
            cb.success(response.body());
        }
    }

    private void manageError(Throwable t, MedekCallBack cb) {
        if (t instanceof SocketTimeoutException) {
            cb.failure(new JsonError("The server does not respond", "Timeout error"));
        } else if (t instanceof IOException){
            cb.failure(new JsonError("Network error", "Error"));
        }
    }

    private void manageError(final MedekCallBack cb, final int code) {

        switch (code){
            case 401:
                cb.failure(new JsonError("Unidentified user", "User error"));
                break;
            case 403:
                cb.failure(new JsonError("Unauthorised", "Access error"));
                break;
            case 404:
                cb.failure(new JsonError("Resource not found", "Resource error"));
                break;
            case 500:
                cb.failure(new JsonError("An error has occurred\non the server", "Server error"));
                break;
            default:
                cb.failure(new JsonError("Unknown error", "Error"));
                break;
        }
    }

    public interface MedekCallBack<T> {

        void success(T t);

        void failure(JsonError error);
    }
}
