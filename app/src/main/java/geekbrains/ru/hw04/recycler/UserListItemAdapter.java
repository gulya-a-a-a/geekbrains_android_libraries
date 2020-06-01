package geekbrains.ru.hw04.recycler;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.List;

import geekbrains.ru.hw01.R;
import geekbrains.ru.hw04.retrofit.RetrofitUserItemModel;

public class UserListItemAdapter extends RecyclerView.Adapter<UserListItemAdapter.UserListItemHolder> {


    public interface OnUserItemClickListener {
        void onItemClicked(RetrofitUserItemModel userModel);
    }

    private List<RetrofitUserItemModel> mUserList;
    private OnUserItemClickListener mOnUserItemClickListener;

    @NonNull
    @Override
    public UserListItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new UserListItemHolder(inflater, parent);
    }

    @Override
    public void onBindViewHolder(@NonNull UserListItemHolder holder, int position) {
        RetrofitUserItemModel user = mUserList.get(position);
        holder.bind(user);
        holder.itemView.setOnClickListener(v -> {
            if (mOnUserItemClickListener != null && mUserList != null)
                mOnUserItemClickListener.onItemClicked(user);
        });
    }

    @Override
    public int getItemCount() {
        return mUserList != null ? mUserList.size() : 0;
    }

    public void setOnUserItemClickListener(OnUserItemClickListener onUserItemClickListener) {
        mOnUserItemClickListener = onUserItemClickListener;
    }

    public void setUserList(List<RetrofitUserItemModel> list) {
        mUserList = list;
        notifyDataSetChanged();
    }

    static class UserListItemHolder extends RecyclerView.ViewHolder implements Target {

        TextView mUserNameTV, mUserIdTV;
        ImageView mImageView;

        UserListItemHolder(LayoutInflater inflater, final ViewGroup parent) {
            super(inflater.inflate(R.layout.user_list_item, parent, false));
            initHolderControls();
        }

        private void initHolderControls() {
            mUserNameTV = itemView.findViewById(R.id.user_name_text);
            mImageView = itemView.findViewById(R.id.user_item_image);
            mUserIdTV = itemView.findViewById(R.id.user_id_text);
        }

        void bind(RetrofitUserItemModel user) {
            mUserNameTV.setText(String.format("%s", user.getLogin()));
            mUserIdTV.setText(String.format("%d", user.getId()));
            Picasso.get().load(user.getAvatarUrl()).into(this);
        }

        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            mImageView.setImageBitmap(bitmap);
        }

        @Override
        public void onBitmapFailed(Exception e, Drawable errorDrawable) {
            Log.d("TAG", "Load error");
        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {
            Log.d("TAG", "Load prepared");
        }
    }
}
