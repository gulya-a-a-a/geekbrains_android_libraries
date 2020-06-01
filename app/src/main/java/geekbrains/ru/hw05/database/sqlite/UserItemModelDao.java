package geekbrains.ru.hw05.database.sqlite;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserItemModelDao {
    @Query("SELECT * FROM useritemroommodel")
    List<UserItemRoomModel> getAll();

    @Insert
    void insertAll(List<UserItemRoomModel> items);

    @Update
    void update(UserItemRoomModel item);

    @Delete
    void delete(UserItemRoomModel item);

    @Query("DELETE FROM useritemroommodel")
    void deleteAll();
}
