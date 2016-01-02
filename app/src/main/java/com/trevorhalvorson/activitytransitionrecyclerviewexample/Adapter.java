package com.trevorhalvorson.activitytransitionrecyclerviewexample;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by Trevor Halvorson on 1/1/2016.
 */
public class Adapter extends RecyclerView.Adapter<Adapter.Holder> {

    private List<Production> mProductions;
    private Context mContext;

    public Adapter(List<Production> productions, Context context) {
        mProductions = productions;
        mContext = context;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);

        return new Holder(itemView);
    }

    @Override
    public int getItemCount() {
        return (mProductions != null ? mProductions.size() : 0);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        Production production = mProductions.get(position);
        holder.bindProduction(production);
    }

    public class Holder extends RecyclerView.ViewHolder {
        private Production mProduction;
        private ImageView mPosterImageView;
        private TextView mTitleTextView;

        public Holder(View itemView) {
            super(itemView);
            mPosterImageView = (ImageView) itemView.findViewById(R.id.list_item_poster_image_view);
            mTitleTextView = (TextView) itemView.findViewById(R.id.list_item_title_text_view);
        }

        private void bindProduction(Production production) {
            mProduction = production;
            Glide.with(mContext)
                    .load(mProduction.getPoster())
                    .into(mPosterImageView);
            mTitleTextView.setText(mProduction.getShowTitle());
        }
    }

}
