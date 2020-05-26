package geekbrains.ru.hw04.view;

import java.util.List;

import geekbrains.ru.hw04.retrofit.RetrofitUserItemModel;

public interface UserListView {
    void showUserList(List<RetrofitUserItemModel> retrofitUserItemModels);

    void showError();

    void hideLoading();
}
