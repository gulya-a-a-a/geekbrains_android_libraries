package geekbrains.ru.hw04.recycler;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import geekbrains.ru.hw01.R;
import geekbrains.ru.hw04.retrofit.RetrofitRepoModel;

public class RepoListAdapter extends RecyclerView.Adapter<RepoListAdapter.RepoListHolder> {

    private List<RetrofitRepoModel> mRepoList;

    @NonNull
    @Override
    public RepoListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new RepoListHolder(inflater, parent);
    }

    @Override
    public void onBindViewHolder(@NonNull RepoListHolder holder, int position) {
        holder.bind(mRepoList.get(position));
    }

    @Override
    public int getItemCount() {
        return mRepoList != null ? mRepoList.size() : 0;
    }

    public void setRepoList(List<RetrofitRepoModel> repoList) {
        mRepoList = repoList;
        notifyDataSetChanged();
    }

    class RepoListHolder extends RecyclerView.ViewHolder {
        TextView mTextView;

        public RepoListHolder(LayoutInflater inflater, @NonNull ViewGroup parent) {
            super(inflater.inflate(R.layout.repo_list_item, parent, false));
            initHolderControls();
        }

        private void initHolderControls() {
            mTextView = itemView.findViewById(R.id.repo_name_text);
        }

        private void bind(RetrofitRepoModel repoModel) {
            mTextView.setText(repoModel.getRepoName());
        }
    }
}
