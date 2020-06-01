package geekbrains.ru.hw04.fragments;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.List;

import geekbrains.ru.hw01.PresenterManager;
import geekbrains.ru.hw01.R;
import geekbrains.ru.hw04.presenters.UserDetailPresenter;
import geekbrains.ru.hw04.recycler.RepoListAdapter;
import geekbrains.ru.hw04.retrofit.RetrofitRepoModel;
import geekbrains.ru.hw04.retrofit.RetrofitUserDetailModel;
import geekbrains.ru.hw04.view.UserDetailView;

public class UserDetailFragment extends Fragment implements UserDetailView, Target {
    private String mSelectedUserName;
    private UserDetailPresenter mUserDetailPresenter;

    private TextView mUserName, mLogin;
    private ImageView mUserAvatar;

    private RepoListAdapter mRepoListAdapter;

    public UserDetailFragment() {
    }

    public UserDetailFragment(String userName) {
        mSelectedUserName = userName;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_user_detail, container, false);
        initPresenter(savedInstanceState);
        initRecycler(v);
        return v;
    }

    private void initRecycler(View v) {
        RecyclerView recyclerView = v.findViewById(R.id.user_repo_list);
        mRepoListAdapter = new RepoListAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(v.getContext()));
        recyclerView.setAdapter(mRepoListAdapter);
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
        mLogin = view.findViewById(R.id.user_login_detailed);
        mUserAvatar = view.findViewById(R.id.user_image_detailed);
    }

    @Override
    public void showUserInfo(RetrofitUserDetailModel userDetailModel) {
        if (userDetailModel != null) {
            mUserName.setText(userDetailModel.getName());
            mLogin.setText(userDetailModel.getLogin());
            Picasso.get().load(userDetailModel.getAvatarUrl()).into(this);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        PresenterManager.getInstance().savePresenter(mUserDetailPresenter, outState);
    }

    @Override
    public void showRepoInfo(List<RetrofitRepoModel> repoModelList) {
        mRepoListAdapter.setRepoList(repoModelList);
    }

    @Override
    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
        mUserAvatar.setImageBitmap(bitmap);
    }

    @Override
    public void onBitmapFailed(Exception e, Drawable errorDrawable) {

    }

    @Override
    public void onPrepareLoad(Drawable placeHolderDrawable) {

    }
}
