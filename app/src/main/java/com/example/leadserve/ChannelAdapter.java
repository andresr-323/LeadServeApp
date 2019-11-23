package com.example.leadserve;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ChannelAdapter extends RecyclerView.Adapter<ChannelAdapter.ChannelViewHolder> {
    interface onChannelClickListener {
        void onClick(Channel channel);
    }
    private onChannelClickListener listener;
    private List<Channel> channels;
    private String userName;
    @NonNull
    @Override
    public ChannelAdapter.ChannelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_chat_room,
                parent,
                false
        );
        return new ChannelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChannelAdapter.ChannelViewHolder holder, int position) {
        holder.bind(channels.get(position));
    }

    @Override
    public int getItemCount() {
        return channels.size();
    }

    public ChannelAdapter(List<Channel> channels, onChannelClickListener listener, String userName){
        this.channels = channels;
        this.listener = listener;
        this.userName = userName;
    }

    class ChannelViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        Channel channel;

        public ChannelViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.item_chat_room_name);
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    listener.onClick(channel);
                }
            });

        }

        public void bind(Channel channel) {
            this.channel = channel;
            System.out.println("name " + userName);
            if (userName.equals(channel.getName())) {
                System.out.println("to: " + channel.getName());
                name.setText(channel.getFrom());
            } else if (userName.equals(channel.getFrom())){
                System.out.println("from: " + channel.getFrom());
                name.setText(channel.getName());
            } else {
                name.setText(channel.getName());
            }
        }

    }
}

