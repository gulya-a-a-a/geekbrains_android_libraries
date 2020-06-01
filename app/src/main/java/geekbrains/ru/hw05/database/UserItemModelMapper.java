package geekbrains.ru.hw05.database;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import geekbrains.ru.hw04.retrofit.RetrofitUserItemModel;

public class UserItemModelMapper {
    public static UserItemRoomModel transform(RetrofitUserItemModel user) {
        if (user == null) {
            throw new IllegalArgumentException("Null value parameter");
        }
        final UserItemRoomModel userModel = new UserItemRoomModel();
        userModel.setUserID(user.getId());
        userModel.setLogin(user.getLogin());
        userModel.setAvatar_url(user.getAvatarUrl());
        return userModel;
    }

    public static List<UserItemRoomModel> transform(List<RetrofitUserItemModel> users) {
        List<UserItemRoomModel> userModels;
        if ((users == null) || (users.isEmpty())) {
            userModels = Collections.emptyList();
        } else {
            userModels = new ArrayList<>(users.size());
            for (RetrofitUserItemModel user : users) {
                userModels.add(transform(user));
            }
        }
        return userModels;
    }
}
