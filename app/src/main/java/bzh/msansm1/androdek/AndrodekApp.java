package bzh.msansm1.androdek;

import android.app.Application;

import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.MaterialModule;

import bzh.msansm1.androdek.persistence.utils.PersistenceUtils;
import bzh.msansm1.medekapi.MedekApi;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by ronan on 09/08/2016.
 */
public class AndrodekApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Iconify.with(new MaterialModule());

        String pass = new String("Medek@Android");

        RealmConfiguration config = new RealmConfiguration.Builder(this)
                .name("MEDEK")
                .schemaVersion(1)
                .deleteRealmIfMigrationNeeded()
                .encryptionKey(PersistenceUtils.getPass(pass))
                .build();

        Realm.setDefaultConfiguration(config);

        MedekApi.getInstance().initApi();

    }
}