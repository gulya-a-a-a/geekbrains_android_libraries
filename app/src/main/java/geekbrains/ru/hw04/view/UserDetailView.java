package geekbrains.ru.hw04.view;

import java.util.List;

import geekbrains.ru.hw04.retrofit.RetrofitRepoModel;
import geekbrains.ru.hw04.retrofit.RetrofitUserDetailModel;

public interface UserDetailView {
    void showUserInfo(RetrofitUserDetailModel userDetailModel);

    void showRepoInfo(List<RetrofitRepoModel> repoModelList);
}
