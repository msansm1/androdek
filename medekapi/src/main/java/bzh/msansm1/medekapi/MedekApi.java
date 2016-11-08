package bzh.msansm1.medekapi;

import java.util.List;

import bzh.msansm1.androdek.persistence.MedekConfig;
import bzh.msansm1.medekapi.json.JsonError;
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
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by ronan on 04/08/2016.
 */
public class MedekApi {

    private RetrofitManager medekService;

    private MedekApi(){
        medekService = new RetrofitManager();
    }

    private static class InstanceHolder {
        private static final MedekApi instance = new MedekApi();
    }

    public static MedekApi getInstance(){
        return  InstanceHolder.instance;
    }

    public MedekConfig getConfig() {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<MedekConfig> res = realm.where(MedekConfig.class).findAll();
        if(res.size()>0){
            return res.first();
        }
        return null;
    }

    public void initApi() {
        MedekConfig conf = getConfig();
        if(conf!=null){
            medekService.init(conf);
        }

    }

    public void login(JsonLogin loginInfos, RetrofitManager.MedekCallBack<JsonAuth> callback){
        medekService.login(loginInfos,callback);
    }

    public void logout(JsonAuth userInfos, RetrofitManager.MedekCallBack<String> callback){
        medekService.logout(userInfos, callback);
    }

    public void getMyCollection(String token, RetrofitManager.MedekCallBack<JsonCollectionStats> callback){
        medekService.getMyCollection(token, callback);
    }

    public void getAllCollection(RetrofitManager.MedekCallBack<JsonCollectionStats> callback){
        medekService.getMyCollection(callback);
    }

    public void getMyCollection(RetrofitManager.MedekCallBack<JsonCollectionStats> callback){
        medekService.getMyCollection(callback);
    }

    public void getAllAlbums(int from, int limit, String orderBy, String orderDir,
                             RetrofitManager.MedekCallBack<List<JsonAlbum>> callback){
        medekService.getAllAlbums(from, limit, orderBy, orderDir, callback);
    }

    public void getUserAlbums(int from, int limit, String orderBy, String orderDir, int userId,
                             RetrofitManager.MedekCallBack<List<JsonAlbum>> callback){
        medekService.getUserAlbums(from, limit, orderBy, orderDir, userId, callback);
    }

    public void createUpdateAlbum(JsonAlbum album, final RetrofitManager.MedekCallBack<JsonAlbum> callback) {
        medekService.createUpdateAlbum(album, callback);
    }

    public void addAlbumToMyCollec(JsonMyAlbum album, final RetrofitManager.MedekCallBack<JsonSimpleResponse> callback) {
        medekService.addAlbumToMyCollec(album, callback);
    }

    public void getAlbumTracks(Integer albumId, final RetrofitManager.MedekCallBack<List<JsonTrack>> callback) {
        medekService.getAlbumTracks(albumId, callback);
    }

    public void createUpdateTrack(JsonTrack track, final RetrofitManager.MedekCallBack<JsonTrack> callback) {
        medekService.createUpdateTrack(track, callback);
    }

    public void getAllBooks(int from, int limit, String orderBy, String orderDir,
                            RetrofitManager.MedekCallBack<List<JsonBook>> callback) {
        medekService.getAllBooks(from, limit, orderBy, orderDir, callback);
    }

    public void getAllMovies(int from, int limit, String orderBy, String orderDir,
                            RetrofitManager.MedekCallBack<List<JsonMovie>> callback) {
        medekService.getAllMovies(from, limit, orderBy, orderDir, callback);
    }

    public void getAllTvshows(int from, int limit, String orderBy, String orderDir,
                            RetrofitManager.MedekCallBack<List<JsonShow>> callback) {
        medekService.getAllTvshows(from, limit, orderBy, orderDir, callback);
    }

    public void getAllArtists(final RetrofitManager.MedekCallBack<List<JsonArtist>> callback) {
        medekService.getAllArtists(callback);
    }

    public void createUpdateArtist(JsonArtist artist, final RetrofitManager.MedekCallBack<JsonArtist> callback) {
        medekService.createUpdateArtist(artist, callback);
    }

}
