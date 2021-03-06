package geekbrains.ru.hw04.presenters;

import java.util.List;

import geekbrains.ru.hw01.presenters.BasePresenter;
import geekbrains.ru.hw04.models.UsersRepository;
import geekbrains.ru.hw04.retrofit.RetrofitRepoModel;
import geekbrains.ru.hw04.retrofit.RetrofitUserDetailModel;
import geekbrains.ru.hw04.view.UserDetailView;
import io.reactivex.observers.DefaultObserver;

public class UserDetailPresenter extends BasePresenter<List<RetrofitRepoModel>, UserDetailView> {

    private String mSelectedName;
    private UsersRepository mUsersRepository;
    private RetrofitUserDetailModel mUserDetailModel;

    public UserDetailPresenter(String selectedName) {
        mUsersRepository = new UsersRepository();
        mSelectedName = selectedName;
    }

    public void loadUserInfo() {
        mUsersRepository.requestDetailedData(new UserDetailedObserver(), mSelectedName);
    }

    @Override
    public void updateView() {
    }

    private void updateUserInfo() {
        getView().showUserInfo(mUserDetailModel);
    }

    private void updateRepoList() {
        getView().showRepoInfo(mModel);
    }

    class UserDetailedObserver extends DefaultObserver<RetrofitUserDetailModel> {
        @Override
        public void onNext(RetrofitUserDetailModel retrofitUserDetailModel) {
            mUserDetailModel = retrofitUserDetailModel;
            updateUserInfo();
            mUsersRepository.requestRepoList(new UserRepoListObserver(), mSelectedName);
        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onComplete() {

        }
    }

    class UserRepoListObserver extends DefaultObserver<List<RetrofitRepoModel>> {
        @Override
        public void onNext(List<RetrofitRepoModel> retrofitRepoModels) {
            setModel(retrofitRepoModels);
            updateRepoList();
        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onComplete() {

        }
    }
}
