package com.imDigital.chitchat.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.QuickContactBadge;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.imDigital.chitchat.activity.Channel;
import com.imDigital.chitchat.R;
import com.imDigital.chitchat.activity.MainActivity;
import com.imDigital.chitchat.interfaces.OnItemClick;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

/**
 * Created by madstech on 11/8/17.
 */

public class AdapterChannels extends RecyclerView.Adapter<AdapterChannels.MessageViewHolder> {
    private List<Channel> mChannelList;
    private DatabaseReference mChannelDatabase;

    OnItemClick onItemClick;

    Context context;

    public AdapterChannels(Context context, List<Channel> mChannelList) {

        this.context = context;
        this.mChannelList = mChannelList;

        this.onItemClick = (MainActivity) context;

    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context)
                .inflate(R.layout.thread_item ,parent, false);

        return new MessageViewHolder(v,context);
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder {

        Context con;
        RelativeLayout action_ll;
        ImageView bgimage;
        TextView tv_desc, tv_title;
        ImageView tv_status;
        View item_view;
        public MessageViewHolder(View itemView, Context con) {
            super(itemView);
            this.con = con;
            item_view = itemView;
            bgimage = itemView.findViewById(R.id.bgimage);

            tv_desc = itemView.findViewById(R.id.tv_desc);
            tv_title = itemView.findViewById(R.id.tv_title);
            action_ll = itemView.findViewById(R.id.action_ll);
        }
    }

    @Override
    public void onBindViewHolder(final MessageViewHolder viewHolder, final int i) {

        Channel lChannel = mChannelList.get(i);
        viewHolder.tv_title.setText(lChannel.getName());
        viewHolder.tv_desc.setText(lChannel.getText());
        Glide.with(viewHolder.bgimage.getContext())
                .load(lChannel.getImage())
                .into(viewHolder.bgimage);
        viewHolder.action_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClick.onClickAction(mChannelList.get(i).getName(), i);
            }
        });

        viewHolder.item_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClick.onItemClick(viewHolder.item_view, i);
            }
        });



    }

    @Override
    public int getItemCount() {
        return mChannelList.size();
    }


}
