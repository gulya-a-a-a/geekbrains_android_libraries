package geekbrains.ru.hw01.presenters;

import androidx.annotation.NonNull;

import java.lang.ref.WeakReference;

public abstract class BasePresenter<M, V> {

    protected M mModel;
    protected WeakReference<V> mView;

    public abstract void updateView();

    public void bindView(@NonNull V view) {
        mView = new WeakReference<>(view);
        if ((getView() != null) && (mModel != null))
            updateView();
    }

    public void unbindView() {
        mView = null;
    }

    public V getView() {
        if (mView == null) {
            return null;
        } else {
            return mView.get();
        }
    }

    public M getModel() {
        return mModel;
    }

    public void setModel(M model) {
        mModel = model;
        if ((getView() != null) && (mModel != null))
            updateView();
    }
}
