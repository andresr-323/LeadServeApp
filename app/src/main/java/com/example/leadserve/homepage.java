package com.example.leadserve;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class homepage extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private ArrayList<Student> Students = new ArrayList();
    private ArrayList<Event> Events = new ArrayList();
    private String ID;
    public Spinner spinner;
    private String tier;
    private String name;
    private String userID;
    private FloatingActionButton createRoom;
    private RecyclerView channelsRV;
    private ChannelAdapter adapter;
    private List<Channel> channels = new ArrayList<>();

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference channelReference = db.collection("channels");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        spinner = (Spinner) findViewById(R.id.navSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.spinner_list_item_array, R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        Intent intent = getIntent();
        ID = intent.getExtras().getString("ID");
        tier = intent.getExtras().getString("tier");
        name = intent.getExtras().getString("name");
        System.out.println("name " + name);
        getCurrentUserID();

        Bundle studs = intent.getBundleExtra("STUDBUNDLE");
        Students = (ArrayList<Student>) studs.getSerializable("STUD");
        Bundle events = intent.getBundleExtra("EVENTBUNDLE");
        Events = (ArrayList<Event>) events.getSerializable("EVEN");

        createRoom = findViewById(R.id.createRoom);
        handleTierChannel();
        initUI();
        getChannels();
       // setChannelName();
    }

    private void getChannels(){
        channelReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (e != null){
                    Log.e("Main", "Listen for channels failed");
                    return;
                }else{
                    for(QueryDocumentSnapshot document: queryDocumentSnapshots){
                        String toName = "";
                        if(document.get("name") != null){
                            toName = document.get("name").toString();
                        }
                        String from = "";
                        if (document.get("from") != null) {
                            from = document.get("from").toString();
                        }
                        //set tier channel
                        if (document.get("from") == null && document.get("name") == null){
                            String tier = document.get("tier").toString();
                            channels.add(new Channel((tier)));
                            //set sender
                        } else if (toName.equals(name)){
                            System.out.println("inside name");
                            channels.add(new Channel(document.getId(), toName, from));
                            //set recipient
                        } else if (from.equals(name)){
                            System.out.println("inside from");
                            channels.add(new Channel(document.getId(), toName, from));
                        }
                    }
                    adapter = new ChannelAdapter(channels, listener, name);
                    channelsRV.setAdapter(adapter);
                }
            }
        });
    }

    ChannelAdapter.onChannelClickListener listener = new ChannelAdapter.onChannelClickListener() {
        @Override
        public void onClick(Channel channel) {
            Intent intent = new Intent(homepage.this, MessagesActivity.class);
            intent.putExtra("name", channel.getName());
            intent.putExtra("from", channel.getFrom());
            intent.putExtra("id", channel.getId());
            intent.putExtra("userID", userID);
            intent.putExtra("userName", name);
            System.out.println("channel id" + channel.getId());
            startActivity(intent);

        }
    };

    private void initUI() {
        channelsRV = findViewById(R.id.rooms);
        channelsRV.setLayoutManager(new LinearLayoutManager(this));
        createRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(homepage.this, NewChannelController.class);
                intent.putExtra("name", name);
                startActivity(intent);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_bar, menu);

        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();
        Intent i;
        Bundle args = new Bundle();
        switch (item) {
            case "Navigation":
                //do nothing
                break;
            case "Events":
                i = new Intent(homepage.this, EventsActivity.class);
                args.putSerializable("EVENT", Events);
                i.putExtra("EVENTBUNDLE",args);
                startActivity(i);
                spinner.setSelection(0);
                break;
            case "Progress":
                i = new Intent(homepage.this, ProgressActivity.class);
                i.putExtra("ID", ID);
                i.putExtra("tier", tier);
                startActivity(i);
                spinner.setSelection(0);
                 break;
            case "My Information":
                i = new Intent(homepage.this, MyInfoActivity.class);
                args.putSerializable("STUD", Students);
                i.putExtra("STUDBUNDLE",args);
                i.putExtra("ID", ID);
                startActivity(i);
                spinner.setSelection(0);
                break;
            default:
                break;
        }

    }

    private void getCurrentUserID(){
        db.collection("user-info").whereEqualTo("name", name).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            System.out.println("inside query");
                            for (QueryDocumentSnapshot document : task.getResult()){
                                userID = document.getId();
                                System.out.println("userid "+userID);
                            }
                        }else{
                            Log.e("Error", "Error getting user ID: ", task.getException());
                        }
                    }
                });
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void handleTierChannel(){
        switch (tier){
            case "1":
                System.out.println("tier 1");
                channelReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            Channel channel = new Channel("Tier One");
                            if(!channels.contains(channel)){
                                System.out.println("not includes tier channel");
                                channels.add(channel);
                                adapter = new ChannelAdapter(channels, listener, name);
                                channelsRV.setAdapter(adapter);
                                if(channelReference.document().getId() == ""){
                                    channelReference.document("Tier One").set("Tier One");
                                }
                            }
                        }
                    }
                });
                break;
            case "2":
                System.out.println("tier 2");
                channelReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            Channel channel = new Channel("Tier Two");
                            if(!channels.contains(channel)){
                                System.out.println("not includes tier channel");
                                channels.add(channel);
                                adapter = new ChannelAdapter(channels, listener, name);
                                channelsRV.setAdapter(adapter);
                                if(channelReference.document().getId() == ""){
                                    System.out.println("create tier channel");
                                    channelReference.document("Tier Two").set("Tier One");
                                }
                            }
                        }
                    }
                });
                break;
            case "3":
                channelReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            Channel channel = new Channel("Tier Three");
                            if(!channels.contains(channel)){
                                channels.add(channel);
                                adapter = new ChannelAdapter(channels, listener, name);
                                channelsRV.setAdapter(adapter);
                                if(channelReference.document().getId() == ""){
                                    channelReference.document("Tier Three").set("Tier One");
                                }
                            }
                        }
                    }
                });
                break;
        }
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            // action with ID action_refresh was selected
////            case R.id.action_refresh:
////                Toast.makeText(this, "Refresh selected", Toast.LENGTH_SHORT).show();
////                break;
//            default:
//                break;
//        }
//
//        return true;
//    }

    @Override
    public void onBackPressed() {
    }

}
