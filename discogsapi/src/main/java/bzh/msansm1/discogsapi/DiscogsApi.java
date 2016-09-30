package bzh.msansm1.discogsapi;

import bzh.msansm1.discogsapi.json.search.SearchResponse;

/**
 * Created by ronan on 30/09/2016.
 */

public class DiscogsApi {

    private DiscogsApiRetrofit apiRetrofit;

    private DiscogsApi(){
        apiRetrofit = new DiscogsApiRetrofit();
    }

    private static class InstanceHolder {
        private static final DiscogsApi instance = new DiscogsApi();
    }

    public static DiscogsApi getInstance(){
        return  InstanceHolder.instance;
    }

    public void initApi() {
        apiRetrofit.init("http://api.discogs.com/");
    }

    public void searchAlbumByBarcode(String token, String barcode,
                                     DiscogsApiRetrofit.DiscogsCallBack<SearchResponse> callback){
        apiRetrofit.searchAlbumByCode(token, barcode, callback);
    }
}
