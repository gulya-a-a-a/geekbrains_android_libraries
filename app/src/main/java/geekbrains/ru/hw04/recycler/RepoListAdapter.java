package geekbrains.ru.hw04.recycler;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RepoListAdapter extends RecyclerView.Adapter<RepoListAdapter.RepoListHolder> {

    @NonNull
    @Override
    public RepoListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RepoListHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class RepoListHolder extends RecyclerView.ViewHolder {

        public RepoListHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
