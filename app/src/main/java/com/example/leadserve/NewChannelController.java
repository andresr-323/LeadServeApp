package com.example.leadserve;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.HashMap;
import java.util.Map;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class NewChannelController extends AppCompatActivity {
    TableLayout table;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference userReference = db.collection("user-info");
    Context context;
    private String name;
    int i = 0;
    int j = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_room);
        setTitle("New Message");

        context = MainActivity.getContext();
        table = (TableLayout) findViewById(R.id.channelsTable);
        generateRows();
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        Intent intent = getIntent();
        name = intent.getExtras().getString("name");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.create_room_menu, menu);
        return true;
    }

    private void generateRows(){
        //for each user in user info, create a table row
        userReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        TableRow head = new TableRow(NewChannelController.this);
                        head.setId(j);
                        head.setTag("Head");

                        head.setLayoutParams(new TableRow.LayoutParams(
                                TableRow.LayoutParams.MATCH_PARENT,
                                TableRow.LayoutParams.WRAP_CONTENT
                        ));

                        System.out.println("document ID: " + document.getId());
                        final TextView nameText = new TextView(NewChannelController.this);
                        nameText.setId(i);
                        nameText.setHeight(150);
                        nameText.setTextSize(20);
                        nameText.setTextColor(Color.BLACK);
                        nameText.setPadding(30, 30, 0, 0);
                        nameText.setText(document.get("name").toString());
                        head.addView(nameText);
                        if (i % 2 ==0){
                            head.setBackgroundColor(Color.LTGRAY);
                        } else{
                            head.setBackgroundColor(Color.WHITE);
                        }

                        table.addView(head, new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT,
                                TableLayout.LayoutParams.MATCH_PARENT));
                        i++;
                        j++;

                        head.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Map<String, Object> newChannel = new HashMap<>();
                                newChannel.put("name", nameText.getText().toString());
                                newChannel.put("from", name);
                                Channel channel = new Channel(nameText.getText().toString(), name);
                                db.collection("channels").add(channel);
                            }
                        });
                    }
                } else {
                    Log.d("error", "error fetching documents: ", task.getException());
                }
            }
        });
    }




}
