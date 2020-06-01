package geekbrains.ru.hw05.database.sqlite;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {UserItemRoomModel.class}, version = 1)
public abstract class UserItemDatabase extends RoomDatabase {
    public abstract UserItemModelDao getDao();
}
