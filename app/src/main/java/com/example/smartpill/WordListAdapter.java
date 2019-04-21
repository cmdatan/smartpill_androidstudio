package com.example.smartpill;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import static java.security.AccessController.getContext;

public class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.WordViewHolder> {

    class WordViewHolder extends RecyclerView.ViewHolder {
        private final TextView time, medicine, quantity;

        private WordViewHolder(View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.time);
            medicine = itemView.findViewById(R.id.medicine);
            quantity = itemView.findViewById(R.id.quantity);
        }
    }

    private final LayoutInflater mInflater;
    private List<Word> mWords; // Cached copy of words

    WordListAdapter(Context context) { mInflater = LayoutInflater.from(context); }

    @Override
    public WordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new WordViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(WordViewHolder holder, int position) {
        String colon;
        Word current = mWords.get(position);
        /*
        if (current.getMinute() < 10){
            colon = ":0";
        }
        else{
            colon = ":";
        }
        if (current.getHour() > 12){
            holder.time.setText(current.getHour() - 12 + colon + current.getMinute() + " PM");
        }
        else if (current.getHour() == 12){
            holder.time.setText(current.getHour() + colon + current.getMinute() + " PM");
        }
        else if (current.getHour() == 0){
            holder.time.setText("12" + colon + current.getMinute() + " AM");
        }
        else{
            holder.time.setText(current.getHour()+ colon + current.getMinute()+ " AM");
        }*/
        String timer = String.format("%02d:%02d %s",current.getHour() == 0 || current.getHour() == 12 ? 12 : current.getHour()%12,
                current.getMinute(),current.getHour() > 11 ? "PM" : "AM");

        holder.medicine.setText(current.getMedicine());
        holder.quantity.setText("Take " + current.getQuantity());
        holder.time.setText(timer);
    }

    void setWords(List<Word> words){
        mWords = words;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mWords != null)
            return mWords.size();
        else return 0;
    }
}



