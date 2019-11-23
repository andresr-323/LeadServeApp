package com.example.leadserve;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MessagesActivity extends AppCompatActivity {
    String ID;
    String from;
    String name;
    private String toId;
    private EditText messageBox;
    private ImageButton send;
    private String userID = "";
    private RecyclerView messageView;
    private MessageAdapter adapter;
    private String userName;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference channels = db.collection("channels");
    DocumentReference channel;
    CollectionReference thread;
    Context context;
    Toolbar toolbar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);
        toolbar = findViewById(R.id.toolbar4);
        toolbar.setTitleTextColor(getResources().getColor(R.color.SLgold));
        setSupportActionBar(toolbar);
        context = getApplicationContext();

        Intent intent = getIntent();
        from = intent.getExtras().getString("from");
        name = intent.getExtras().getString("name");
        ID = intent.getExtras().getString("id");
        System.out.println("ID " +ID);
        userID = intent.getExtras().getString("userID");
        userName = intent.getExtras().getString("userName");
        channel = channels.document(ID);
        thread = channel.collection("thread");
        setTitle();

        initUI();
        showMessages();
        getToId();
    }

    private void orderMessages(EventListener<QuerySnapshot> listener){
        thread.orderBy("created", Query.Direction.DESCENDING).addSnapshotListener(listener);
    }

    private void setTitle(){
        channel.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    System.out.println("set title");
                    System.out.println("task result " +task.getResult());
                    DocumentSnapshot document = task.getResult();
                    if(!document.exists()){
                        toolbar.setTitle(ID);
                    }else{
                        if(document.get("name").equals(userName)){
                            toolbar.setTitle(from);
                        }else{
                            toolbar.setTitle(name);
                        }
                    }
                }
            }
        });
    }

    private void getToId(){
        db.collection("user-info").whereEqualTo("name", name).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for(QueryDocumentSnapshot document : task.getResult()){
                        toId = document.getId();
                    }
                }
            }
        });
    }


    private void showMessages(){
        orderMessages(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (e!= null){
                    Log.e("Error", "Listen failed ", e);
                    return;
                }
                List<Message> messages = new ArrayList<>();
                for(QueryDocumentSnapshot document : queryDocumentSnapshots){
                    if (document.getId() != null){
                        System.out.println("inside snapshot");
                        String content = document.get("content").toString();
                        String id = document.getId();
                        String senderID = document.get("senderID").toString();
                        System.out.println("sender id " + senderID);
                        String senderName = document.get("senderName").toString();
                        String toId = "";
                        if (document.get("toId") != null){
                            toId = document.get("toId").toString();
                        }
                        Message message = new Message(content, senderID, senderName, toId);
                        if (!messages.contains(message)){
                            messages.add(message);
                            System.out.println("new message");
                        }
                    }
                }
                adapter = new MessageAdapter(messages, userID);
                messageView.setAdapter(adapter);
            }
        });
    }

    private void initUI(){
        messageBox = findViewById(R.id.message_text);
        send = findViewById(R.id.send_message);

        messageView = findViewById(R.id.chats);
        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setReverseLayout(true);
        messageView.setLayoutManager(manager);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (messageBox.getText().toString().isEmpty()) {
                    Toast.makeText(context, getString(R.string.error_empty_message), Toast.LENGTH_SHORT).show();
                } else {
                    handleSend();
                }
            }
        });
    }

    private void handleSend(){
        String message = messageBox.getText().toString();
        messageBox.setText("");
        send.setEnabled(false);

        Message messageSend = new Message(message, userID, from, toId);
        thread.add(messageSend).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                send.setEnabled(true);
            }
        });

    }
}
