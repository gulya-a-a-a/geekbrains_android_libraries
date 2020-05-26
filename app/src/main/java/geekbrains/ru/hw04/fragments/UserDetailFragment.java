package geekbrains.ru.hw04.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.List;

import geekbrains.ru.hw01.PresenterManager;
import geekbrains.ru.hw01.R;
import geekbrains.ru.hw04.presenters.UserDetailPresenter;
import geekbrains.ru.hw04.retrofit.RetrofitRepoModel;
import geekbrains.ru.hw04.retrofit.RetrofitUserDetailModel;
import geekbrains.ru.hw04.view.UserDetailView;

public class UserDetailFragment extends Fragment implements UserDetailView {
    private String mSelectedUserName;
    private UserDetailPresenter mUserDetailPresenter;

    private TextView mUserName;

    public UserDetailFragment(String userName) {
        mSelectedUserName = userName;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_user_detail, container, false);
        initPresenter(savedInstanceState);
        return v;
    }

    private void initPresenter(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState == null)
            mUserDetailPresenter = new UserDetailPresenter(mSelectedUserName);
        else
            mUserDetailPresenter = (UserDetailPresenter) PresenterManager.getInstance()
                    .restorePresenter(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initControls(view);
        mUserDetailPresenter.bindView(this);
        if (savedInstanceState == null)
            mUserDetailPresenter.loadUserInfo();
    }

    private void initControls(View view) {
        mUserName = view.findViewById(R.id.user_name_detailed);
    }

    @Override
    public void showUserInfo(RetrofitUserDetailModel userDetailModel) {
        mUserName.setText(userDetailModel.getName());
    }

    @Override
    public void showRepoInfo(List<RetrofitRepoModel> repoModelList) {

    }
}
