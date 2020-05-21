package geekbrains.ru.hw01.recycler;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import geekbrains.ru.hw01.models.Counter;
import geekbrains.ru.hw01.presenters.CounterPresenter;

public class CounterAdapter extends RecyclerView.Adapter<CounterHolder> {
    private List<Counter> mCounters;

    public CounterAdapter() {
        super();
        mCounters = new ArrayList<>();
    }

    @NonNull
    @Override
    public CounterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new CounterHolder(layoutInflater, parent);
    }

    @Override
    public void onBindViewHolder(@NonNull CounterHolder holder, int position) {
        CounterPresenter presenter = new CounterPresenter();
        Counter counter = mCounters.get(position);

        presenter.setModel(counter);

        holder.bindPresenter(presenter);
    }

    @Override
    public int getItemCount() {
        return mCounters.size();
    }

    public void updateCounters(List<Counter> counters) {
        mCounters = counters;
        notifyDataSetChanged();
    }
}
