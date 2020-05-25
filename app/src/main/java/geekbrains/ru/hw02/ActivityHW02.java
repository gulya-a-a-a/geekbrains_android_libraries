package geekbrains.ru.hw02;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import geekbrains.ru.hw01.PresenterManager;
import geekbrains.ru.hw01.R;
import io.reactivex.Observable;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

interface SimpleTextWatcher extends TextWatcher {
    @Override
    default void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    default void onTextChanged(CharSequence s, int start, int before, int count) {
    }
}

public class ActivityHW02 extends AppCompatActivity {

    private TextView mTextView, bottomText1, bottomText2;
    private PublishSubject<Integer> mPublisherSubject;
    private List<Integer> list1, list2;

    private PresenterHW02 mPresenterHW02;
    private Disposable mDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hw2);

        if (savedInstanceState == null)
            mPresenterHW02 = new PresenterHW02();
        else
            mPresenterHW02 = (PresenterHW02) PresenterManager.getInstance().restorePresenter(savedInstanceState);


        publisherCreate();

        initControls();

        initEventBus();

    }

    private void initControls() {
        EditText mEditText = findViewById(R.id.editText);
        mTextView = findViewById(R.id.textView);

        Button button1 = findViewById(R.id.button1);
        button1.setOnClickListener(v -> {
            Observable<Integer> obs = getObserverable(list1).
                    subscribeOn(Schedulers.newThread()).
                    observeOn(AndroidSchedulers.mainThread());
            mPublisherSubject.subscribe(getObserver(1));
            mPublisherSubject.subscribe(getObserver(2));
            obs.subscribe(mPublisherSubject);
        });

        Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener(v -> {
            Observable<Integer> obs = getObserverable(list2).
                    subscribeOn(Schedulers.newThread()).
                    observeOn(AndroidSchedulers.mainThread());
            mPublisherSubject.subscribe(getObserver(2));
            mPublisherSubject.subscribe(getObserver(1));
            obs.subscribe(mPublisherSubject);
        });

        Button button3 = findViewById(R.id.button3);
        button3.setOnClickListener(v -> {
            mPublisherSubject.onNext(99);
        });

        bottomText1 = findViewById(R.id.textView1);
        bottomText2 = findViewById(R.id.textView2);
    }


    private void initEventBus() {
        list1 = new ArrayList<>(10);
        list2 = new ArrayList<>(10);
        for (int i = 10; i < 20; i++) {
            list1.add(i + 2);
            list2.add(i * 2);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        Observable<String> observable = Observable.create(emitter -> {
            ((EditText) findViewById(R.id.editText))
                    .addTextChangedListener(
                            (SimpleTextWatcher) (editable -> emitter.onNext(editable.toString())));
        });

        mDisposable = mPresenterHW02.bindView(observable, value -> ((TextView) findViewById(R.id.textView)).setText(value));
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        PresenterManager.getInstance().savePresenter(mPresenterHW02, outState);
    }

    private Observable<Integer> getObserverable(List<Integer> list) {
        return Observable.create(emitter -> {
            for (int s : list) {
                Thread.sleep(1000);
                emitter.onNext(s);
            }
        });
    }

    private void publisherCreate() {
        mPublisherSubject = PublishSubject.create();
        mPublisherSubject.subscribe(System.out::println);
    }

    private Observer<Integer> getObserver(int id) {
        return new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer integer) {
                if (id == 1)
                    bottomText1.setText(String.format("List%d: %d", id, integer));
                else
                    bottomText2.setText(String.format("List%d %d", id, integer));
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
    }

}
