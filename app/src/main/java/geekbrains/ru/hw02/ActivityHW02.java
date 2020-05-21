package geekbrains.ru.hw02;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import geekbrains.ru.hw01.R;
import io.reactivex.Observable;

import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

public class ActivityHW02 extends AppCompatActivity {

    private TextView mTextView, bottomText;
    private PublishSubject<Integer> mPublisherSubject;
    private Button button1, button2;
    private List<Integer> list1, list2;
    private Observable<Integer> obs1, obs2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hw2);

        publisherCreate();

        initControls();

        initEventBus();

    }

    private void initControls() {
        EditText mEditText = findViewById(R.id.editText);
        mTextView = findViewById(R.id.textView);
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mTextView.setText(s);
            }
        });

        button1 = findViewById(R.id.button1);
        button1.setOnClickListener(v -> {
            Observable<Integer> obs1 = getObserverable(list1).
                    subscribeOn(Schedulers.newThread()).
                    observeOn(AndroidSchedulers.mainThread());
            mPublisherSubject.subscribe(getObserver(0));
            obs1.subscribe(mPublisherSubject);
        });
        button2 = findViewById(R.id.button2);
        bottomText = findViewById(R.id.textView1);
    }


    private void initEventBus() {
        list1 = new ArrayList<>(10);
        list2 = new ArrayList<>(10);
        for (int i = 10; i < 20; i++) {
            list1.add(i + 2);
            list2.add(i * 2);
        }

    }

    private Observable<Integer> getObserverable(List<Integer> list) {
        return Observable.create(emitter -> {
            for (int s : list) {
                Thread.sleep(1000);
                emitter.onNext(s);
            }
            emitter.onComplete();
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
                bottomText.setText(String.format("Integer: %d", integer));
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
