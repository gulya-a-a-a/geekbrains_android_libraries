package geekbrains.ru.hw05;

import android.app.Application;

import androidx.room.Room;

import geekbrains.ru.hw05.database.UserItemDatabase;
import geekbrains.ru.hw05.database.UserItemModelDao;

public class HW05Application extends Application {

    private static HW05Application mInstance;
    private UserItemDatabase mDb;

    public static HW05Application getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;

        mDb = Room.databaseBuilder(
                getApplicationContext(),
                UserItemDatabase.class,
                "useritemdatabase")
                .build();
    }

    public UserItemDatabase getDb() {
        return mDb;
    }

    public UserItemModelDao getDao() {
        return mDb.getDao();
    }
}
