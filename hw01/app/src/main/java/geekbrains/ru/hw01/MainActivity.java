package geekbrains.ru.hw01;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements MainView {
    Presenter mPresenter;
    Button[] mCounterButtons = new Button[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPresenter = new Presenter(this);
        initButtons();
    }

    private void initButtons() {
        mCounterButtons[0] = findViewById(R.id.btnCounter1);
        mCounterButtons[1] = findViewById(R.id.btnCounter2);
        mCounterButtons[2] = findViewById(R.id.btnCounter3);

        for (int i = 0; i < mCounterButtons.length; i++) {
            mCounterButtons[i]
                    .setOnClickListener(new OnCounterButtonClickListener(i, mPresenter));
        }
    }

    @Override
    public void setButtonText(int index, String text) {
        mCounterButtons[index].setText(String.format("Количество = %s", text));
    }


    private static class OnCounterButtonClickListener implements View.OnClickListener {
        int mIndex;
        Presenter mPresenter;

        OnCounterButtonClickListener(int index, Presenter presenter) {
            mIndex = index;
            mPresenter = presenter;
        }

        @Override
        public void onClick(View v) {
            mPresenter.buttonClick(mIndex);
        }
    }
}
