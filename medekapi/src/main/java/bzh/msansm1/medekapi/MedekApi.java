package bzh.msansm1.medekapi;

import bzh.msansm1.androdek.persistence.MedekConfig;
import bzh.msansm1.medekapi.json.auth.JsonAuth;
import bzh.msansm1.medekapi.json.auth.JsonLogin;
import bzh.msansm1.medekapi.json.home.JsonCollectionStats;
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

    public void getMyCollection(RetrofitManager.MedekCallBack<JsonCollectionStats> callback){
        medekService.getMyCollection(callback);
    }

}
