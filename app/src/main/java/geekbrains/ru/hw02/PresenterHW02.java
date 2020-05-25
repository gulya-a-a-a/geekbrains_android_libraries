package geekbrains.ru.hw02;

import geekbrains.ru.hw01.presenters.BasePresenter;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.PublishSubject;

public class PresenterHW02 extends BasePresenter {
    private String lastString = "";

    Disposable bindView(Observable<String> src, Consumer<String> dst) {
        PublishSubject<String> ps = PublishSubject.create();
        Disposable disposable = ps.subscribe(dst);
        ps.onNext(lastString);
        disposable.dispose();

        return src.map(s -> lastString = s).subscribe(dst);
    }

    public void unbindView(Disposable disposable) {
        if (!disposable.isDisposed())
            disposable.dispose();
    }

    @Override
    public void updateView() {

    }
}
