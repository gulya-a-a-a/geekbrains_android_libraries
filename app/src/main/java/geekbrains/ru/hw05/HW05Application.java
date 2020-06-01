package geekbrains.ru.hw05;

import android.app.Application;

import androidx.room.Room;

import geekbrains.ru.hw05.database.sqlite.UserItemDatabase;
import geekbrains.ru.hw05.database.sqlite.UserItemModelDao;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class HW05Application extends Application {

    private static HW05Application mInstance;
    private UserItemDatabase mDb;
    private Realm mRealm;

    public static HW05Application getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;
        initRoom();
        initRealmDatabase();
    }

    private void initRealmDatabase() {
        Realm.init(this);
        RealmConfiguration configuration = new RealmConfiguration.Builder()
                .name("useritemrealmdb.realm")
                .build();

        mRealm = Realm.getInstance(configuration);
    }

    private void initRoom() {
        mDb = Room.databaseBuilder(
                getApplicationContext(),
                UserItemDatabase.class,
                "useritemdatabase.sqlite")
                .build();
    }

    public UserItemDatabase getDb() {
        return mDb;
    }

    public UserItemModelDao getDao() {
        return mDb.getDao();
    }

    public Realm getRealm() {
        return mRealm;
    }
}
