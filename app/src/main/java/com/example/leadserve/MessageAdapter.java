package com.example.leadserve;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {
    private static final int SENT = 0;
    private static final int RECEIVED = 1;

    private String userId;
    private List<Message> messages;

    public MessageAdapter(List<Message> messages, String userId) {
        this.messages = messages;
        this.userId = userId;
    }


    @Override
    public MessageAdapter.MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == SENT) {
            System.out.println("inside sent");
            view = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.item_chat_sent,
                    parent,
                    false
            );
        } else {
            System.out.println("else ");
            view = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.item_chat_received,
                    parent,
                    false);
        }
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.MessageViewHolder holder, int position) {
        holder.bind(messages.get(position));
    }

    @Override
    public int getItemViewType(int position) {
        String senderID = messages.get(position).getSenderID();
        System.out.println("sender Id " + senderID);
        System.out.println("user id " + userId);
        if (senderID.equals(userId)) {
            return SENT;
        } else {
            return RECEIVED;
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    class MessageViewHolder extends RecyclerView.ViewHolder {
        TextView message;

        public MessageViewHolder(View itemView) {
            super(itemView);
            message = itemView.findViewById(R.id.chat_message);
        }

        public void bind(Message messageSent) {
            message.setText(messageSent.getContent());
        }
    }
}
