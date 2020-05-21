package geekbrains.ru.hw02;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import geekbrains.ru.hw01.R;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.PublishSubject;

public class ActivityHW02 extends AppCompatActivity {

    private TextView mTextView;
    private PublishSubject<String> mPublisherSubject;
    private Button button1, button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hw2);

        initControls();

        initEventBus();

        publisherCreate();
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
//            mPublisherSubject.onNext(button1.getTag());
        });
        Button button2 = findViewById(R.id.button2);
    }


    private void initEventBus() {
        Observable<String> observable = Observable.fromArray("A", "F", "G");
        observable.safeSubscribe(new Observer<String>() {
            private static final String TAG = "Observer";
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "onSubscribe = " + d.toString());
            }

            @Override
            public void onNext(String s) {
                Log.d(TAG, "onNext = " + s);
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError = " + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete");
            }
        });
    }

    private void publisherCreate(){
        mPublisherSubject = PublishSubject.create();
        mPublisherSubject.subscribe(System.out::println);
        mPublisherSubject.onNext("D");
        mPublisherSubject.onNext("T");

    }


}
