package geekbrains.ru.hw04.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import geekbrains.ru.hw01.PresenterManager;
import geekbrains.ru.hw01.R;
import geekbrains.ru.hw04.presenters.UserListPresenter;
import geekbrains.ru.hw04.recycler.UserListItemAdapter;
import geekbrains.ru.hw04.retrofit.RetrofitUserItemModel;
import geekbrains.ru.hw04.view.UserListView;

public class UserListFragment extends Fragment implements UserListView {


    public interface UserListListener {
        void onUserItemClicked(String userName);
    }

    private UserListPresenter mUserListPresenter;
    private UserListItemAdapter mUserListItemAdapter;
    private UserListListener mUserListListener;

    private RecyclerView mRecyclerView;
    private TextView mLoadingTV;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_user_list, container, false);

        initRecycler(v);
        initPresenter(savedInstanceState);

        return v;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof UserListListener)
            mUserListListener = (UserListListener) context;
    }

    private void initPresenter(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState == null)
            mUserListPresenter = new UserListPresenter();
        else
            mUserListPresenter = (UserListPresenter) PresenterManager.getInstance()
                    .restorePresenter(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        PresenterManager.getInstance().savePresenter(mUserListPresenter, outState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initControls(view);
        mUserListPresenter.bindView(this);
        if (savedInstanceState == null)
            mUserListPresenter.loadUserList();
    }

    private void initControls(View view) {
        mLoadingTV = view.findViewById(R.id.loading_text);
        mLoadingTV.setText("Loading...");
    }

    private void initRecycler(View v) {
        mRecyclerView = v.findViewById(R.id.user_recycler_list);
        mUserListItemAdapter = new UserListItemAdapter();
        mUserListItemAdapter.setOnUserItemClickListener(mOnUserItemClickListener);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(v.getContext()));
        mRecyclerView.setAdapter(mUserListItemAdapter);
    }

    @Override
    public void showUserList(List<RetrofitUserItemModel> retrofitUserItemModels) {
        mUserListItemAdapter.setUserList(retrofitUserItemModels);
    }

    @Override
    public void showError() {
        Toast.makeText(getContext(), "User list download failed", Toast.LENGTH_LONG).show();
        mLoadingTV.setText("User list download failed");
    }

    @Override
    public void hideLoading() {
        mLoadingTV.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private UserListItemAdapter.OnUserItemClickListener mOnUserItemClickListener =
            userModel -> {
                if (mUserListListener != null)
                    mUserListListener.onUserItemClicked(userModel.getLogin());
            };
}
