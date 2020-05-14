package geekbrains.ru.hw01;

import android.os.Bundle;

import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

import geekbrains.ru.hw01.presenters.BasePresenter;

class PresenterManager {
    private static final String PRESENTER_ID_KEY = "presenter_id";
    private static PresenterManager instance;
    private int nextPresenterID;
    private Map<Integer, BasePresenter<?, ?>> mPresenters;


    private PresenterManager() {
        mPresenters = new HashMap<>();
    }

    static PresenterManager getInstance() {
        if (instance == null)
            instance = new PresenterManager();
        return instance;
    }

    void savePresenter(BasePresenter<?, ?> presenter, @NonNull Bundle outState) {
        mPresenters.put(nextPresenterID, presenter);
        outState.putInt(PRESENTER_ID_KEY, nextPresenterID);
        nextPresenterID++;
    }

    BasePresenter<?, ?> restorePresenter(@NonNull Bundle restoredState) {
        int presenter_id = restoredState.getInt(PRESENTER_ID_KEY);
        BasePresenter<?, ?> presenter = mPresenters.get(presenter_id);
        mPresenters.remove(presenter_id);
        return presenter;
    }
}
