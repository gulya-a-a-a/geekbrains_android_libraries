package geekbrains.ru.hw05.database.realm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import geekbrains.ru.hw04.retrofit.RetrofitUserItemModel;

public class UserItemRealmMapper {
    public static UserItemRealmObject transform(RetrofitUserItemModel user, int nextUid) {
        if (user == null) {
            throw new IllegalArgumentException("Null value parameter");
        }
        final UserItemRealmObject userModel = new UserItemRealmObject();
        userModel.setUid(nextUid);
        userModel.setId(user.getId());
        userModel.setLogin(user.getLogin());
        userModel.setAvatar_url(user.getAvatarUrl());
        return userModel;
    }

    public static List<UserItemRealmObject> transform(List<RetrofitUserItemModel> users, int maxUid) {
        List<UserItemRealmObject> userModels;
        int nextUid = 0;
        if (maxUid != 0)
            nextUid = maxUid + 1;
        if ((users == null) || (users.isEmpty())) {
            userModels = Collections.emptyList();
        } else {
            userModels = new ArrayList<>(users.size());
            for (RetrofitUserItemModel user : users) {
                userModels.add(transform(user, nextUid));
                nextUid++;
            }
        }
        return userModels;
    }
}
