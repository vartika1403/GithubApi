package com.example.vartikasharma.githubapi;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import java.util.List;


public class ListAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private List<CommitItem> commitItemList;

    public ListAdapter(Context context, List<CommitItem> commitItemList) {
        this.context = context;
        this.commitItemList = commitItemList;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public int getCount() {
        return commitItemList.size();
    }

    @Override
    public Object getItem(int i) {
        return commitItemList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (inflater == null) {
            inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (view == null) {
            view = inflater.inflate(R.layout.list_item, null);
        }

        TextView personName = (TextView) view.findViewById(R.id.person_name_text);
        final ImageView personImage = (ImageView) view.findViewById(R.id.person_image);
        TextView commitId = (TextView) view.findViewById(R.id.commit_id_text);
        TextView commitMessage = (TextView) view.findViewById(R.id.commit_message_text);

        CommitItem commitItem = commitItemList.get(i);
        personName.invalidate();
        personName.setText(commitItem.getPersonName());
        commitId.invalidate();
        commitId.setText(commitItem.getCommitId());
        commitMessage.invalidate();
        commitMessage.setText(commitItem.getCommitMessage());
        Glide.with(context).load(commitItem.getPersonImageUrl())
                .asBitmap()
                .centerCrop()
                .into(new BitmapImageViewTarget(personImage) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                personImage.setImageDrawable(circularBitmapDrawable);
            }
        });

        return view;
    }

    @Override
    public int getItemViewType(int i) {
        return i;
    }

    @Override
    public int getViewTypeCount() {
        return commitItemList.size();
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
