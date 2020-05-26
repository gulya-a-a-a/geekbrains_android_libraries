package geekbrains.ru.hw04.presenters;

import androidx.annotation.NonNull;

import java.util.List;

import geekbrains.ru.hw01.presenters.BasePresenter;
import geekbrains.ru.hw04.models.UsersRepository;
import geekbrains.ru.hw04.retrofit.RetrofitUserItemModel;
import geekbrains.ru.hw04.view.UserListView;
import io.reactivex.observers.DefaultObserver;

public class UserListPresenter extends BasePresenter<List<RetrofitUserItemModel>, UserListView> {

    private UsersRepository mUsersRepository;

    public UserListPresenter() {
        mUsersRepository = new UsersRepository();
    }

    public void loadUserList() {
        if (mUsersRepository != null) {
            mUsersRepository.requestUsers(new UserListObserver());
        }
    }

    @Override
    public void bindView(@NonNull UserListView view) {
        super.bindView(view);
    }

    @Override
    public void updateView() {

    }

    private void showUserList(List<RetrofitUserItemModel> retrofitUserItemModels) {
        mModel = retrofitUserItemModels;
        getView().showUserList(retrofitUserItemModels);
    }

    private void showErrorStatement() {
        getView().showError();
    }

    private void hideLoading() {
        getView().hideLoading();
    }

    class UserListObserver extends DefaultObserver<List<RetrofitUserItemModel>> {
        @Override
        public void onNext(List<RetrofitUserItemModel> retrofitUserItemModels) {
            showUserList(retrofitUserItemModels);
        }

        @Override
        public void onError(Throwable e) {
            showErrorStatement();
        }

        @Override
        public void onComplete() {
            hideLoading();
        }
    }
}
