package com.imDigital.chitchat.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.imDigital.chitchat.R;
import com.imDigital.chitchat.beans.responsebeans.ResPostListingBean;
import com.imDigital.chitchat.preferences.AppPreferences;

import java.util.ArrayList;

/**
 * Created by madstech on 11/8/17.
 */

public class AdapterPost extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    private static final int VIEW_TYPE_MESSAGE_SENT = 1;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;
    ArrayList<ResPostListingBean.ResponseData> responseData;

    public AdapterPost(Context context, ArrayList<ResPostListingBean.ResponseData> responseData) {
        this.context = context;
        this.responseData = responseData;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        if (viewType == VIEW_TYPE_MESSAGE_SENT) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.postlist_item_user, parent, false);
            return new SentMessageHolder(view);
        } else if (viewType == VIEW_TYPE_MESSAGE_RECEIVED) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.postlist_item_other, parent, false);
            return new ReceivedMessageHolder(view);
        }

        return null;
    }

    @Override
    public int getItemViewType(int position) {

        if (responseData.get(position).user_id.equals(""+AppPreferences.INSTANCE.getUserId())) {
            // If the current user is the sender of the message
            return VIEW_TYPE_MESSAGE_SENT;
        } else {
            // If some other user sent the message
            return VIEW_TYPE_MESSAGE_RECEIVED;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        switch (holder.getItemViewType()) {
            case VIEW_TYPE_MESSAGE_SENT:
                ((SentMessageHolder) holder).messageText.setText(responseData.get(position).message);
                ((SentMessageHolder) holder).timeText.setText(responseData.get(position).createdOn);
                break;
            case VIEW_TYPE_MESSAGE_RECEIVED:
                ((ReceivedMessageHolder) holder).messageText.setText(responseData.get(position).message);
                ((ReceivedMessageHolder) holder).timeText.setText(responseData.get(position).createdOn);
                ((ReceivedMessageHolder) holder).nameText.setText(responseData.get(position).title);
                ((ReceivedMessageHolder) holder).profileImage.setImageResource(R.drawable.glory);
                break;
        }

    }

    @Override
    public int getItemCount() {
        return responseData.size();
    }

    private class SentMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText, timeText;

        SentMessageHolder(View itemView) {
            super(itemView);

            messageText = (TextView) itemView.findViewById(R.id.text_message_body);
            timeText = (TextView) itemView.findViewById(R.id.text_message_time);
        }
    }

    public class ReceivedMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText, timeText, nameText;
        ImageView profileImage;

        public ReceivedMessageHolder(View itemView) {
            super(itemView);
            messageText = (TextView) itemView.findViewById(R.id.text_message_body);
            timeText = (TextView) itemView.findViewById(R.id.text_message_time);
            nameText = (TextView) itemView.findViewById(R.id.text_message_name);
            profileImage = (ImageView) itemView.findViewById(R.id.image_message_profile);
        }
    }
}
