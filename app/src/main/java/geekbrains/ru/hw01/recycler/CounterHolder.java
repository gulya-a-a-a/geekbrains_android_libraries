package geekbrains.ru.hw01.recycler;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import geekbrains.ru.hw01.R;
import geekbrains.ru.hw01.presenters.CounterPresenter;
import geekbrains.ru.hw01.views.CounterView;

class CounterHolder extends RecyclerView.ViewHolder implements CounterView {
    private Button mCounterButton;

    private TextView mCounterName;
    private CounterPresenter mPresenter;


    CounterHolder(LayoutInflater layoutInflater, ViewGroup parent) {
        super(layoutInflater.inflate(R.layout.counter_item, parent, false));
        mCounterButton = itemView.findViewById(R.id.counter_btn);
        mCounterName = itemView.findViewById(R.id.counter_name_text);

        mCounterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.onCounterButtonClicked();
            }
        });
    }

    void bindPresenter(CounterPresenter presenter) {
        mPresenter = presenter;
        mPresenter.bindView(this);
    }

    @Override
    public void setCounterName(String name) {
        mCounterName.setText(name);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void setCounterValues(int value) {
        mCounterButton.setText(String.format("Количество = %d", value));
    }
}
