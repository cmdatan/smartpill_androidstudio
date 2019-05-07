package com.example.smartpill;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MedsListAdapter extends RecyclerView.Adapter<MedsListAdapter.MedsViewHolder> {

    public class MedsViewHolder extends RecyclerView.ViewHolder {
        TextView boxNo, medicine;
        RelativeLayout parentLayout;

        public MedsViewHolder(final View itemView) {
            super(itemView);
            boxNo = itemView.findViewById(R.id.boxNo);
            medicine = itemView.findViewById(R.id.medicine);
            parentLayout = itemView.findViewById(R.id.parent_layout);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListener.onItemClick(view, getAdapterPosition());
                }
            });
        }
    }

    private final LayoutInflater mInflater;
    private List<Meds> mMeds; // Cached copy of words
    private static ClickListener clickListener;
    private Context mContext;

    MedsListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        mContext = context;
    }

    @Override
    public MedsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_medicine, parent, false);
        return new MedsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MedsViewHolder holder, int position) {
        final Meds current = mMeds.get(position);

        holder.boxNo.setText("Box " + current.getBoxNo());
        holder.medicine.setText(current.getMedicine());

    }

    void setMeds(List<Meds> meds){
        mMeds = meds;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mMeds != null)
            return mMeds.size();
        else return 0;
    }

    public Meds getMedsAtPosition (int position) {
        return mMeds.get(position);
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        MedsListAdapter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(View v, int position);
    }



}