package geekbrains.ru.hw06.dagger;

import android.content.Context;

import androidx.room.Room;

import dagger.Module;
import dagger.Provides;
import geekbrains.ru.hw05.database.sqlite.UserItemDatabase;
import geekbrains.ru.hw05.database.sqlite.UserItemModelDao;

@Module(includes = ContextModule.class)
public class DaggerRoomModule {

    @Provides
    public UserItemModelDao getDao(UserItemDatabase db) {
        return db.getDao();
    }

    @Provides
    public UserItemDatabase getRoomDatabase(Context context) {
        return Room.databaseBuilder(
                context,
                UserItemDatabase.class,
                "useritemdatabase.sqlite")
                .build();
    }
}
