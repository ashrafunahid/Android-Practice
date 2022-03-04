package com.ashrafunahid.aponjon.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ashrafunahid.aponjon.Adapter.UserAdapter;
import com.ashrafunahid.aponjon.ModelClass.Messages;
import com.ashrafunahid.aponjon.Adapter.MessagesAdapter;
import com.ashrafunahid.aponjon.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatActivity extends AppCompatActivity {

    String ReceiverName, ReceiverImage, ReceiverUID, SenderUID;
    CircleImageView recprofileImg;
    TextView receiver_name;
    FirebaseDatabase database;
    FirebaseAuth firebaseAuth;

    public static String sImage;
    public static String rImage;

    CardView sendBtn;
    EditText edtMessage;

    String senderRoom, receiverRoom;
    RecyclerView messageShow;
    ArrayList<Messages> messagesArrayList;

    MessagesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        database = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        ReceiverName = getIntent().getStringExtra("name");
        ReceiverImage = getIntent().getStringExtra("ReceiverImage");
        ReceiverUID = getIntent().getStringExtra("uid");

        sendBtn = (CardView) findViewById(R.id.sendBtn);
        edtMessage = (EditText) findViewById(R.id.edtMessage);

        messageShow = (RecyclerView) findViewById(R.id.msg_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        messageShow.setLayoutManager(linearLayoutManager);
        adapter = new MessagesAdapter(ChatActivity.this, messagesArrayList);
        messageShow.setAdapter(adapter);

        messagesArrayList = new ArrayList<>();

        recprofileImg = (CircleImageView) findViewById(R.id.profile_img);
        Picasso.get().load(ReceiverImage).into(recprofileImg);

        receiver_name = (TextView) findViewById(R.id.receiver_name);
        receiver_name.setText(""+ReceiverName);

        SenderUID = firebaseAuth.getUid();

        senderRoom = SenderUID+ReceiverUID;
        receiverRoom = ReceiverUID+SenderUID;

        DatabaseReference reference = database.getReference().child("user").child(firebaseAuth.getUid());
        DatabaseReference chatReference = database.getReference().child("chats").child(senderRoom).child("messages");

        chatReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                messagesArrayList.clear();
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    Messages messages = dataSnapshot.getValue(Messages.class);
                    messagesArrayList.add(messages);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                sImage = snapshot.child("imageUri").getValue().toString();
                rImage = ReceiverImage;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String message = edtMessage.getText().toString();
                if(message.isEmpty()){
                    Toast.makeText(ChatActivity.this, "Empty message", Toast.LENGTH_SHORT).show();
                    return;
                }
                edtMessage.setText("");
                Date date = new Date();
                Messages messages = new Messages(message, SenderUID, date.getTime());

                database = FirebaseDatabase.getInstance();
                database.getReference().child("chats")
                        .child(senderRoom)
                        .child("messages")
                        .push()
                        .setValue(messages).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        database.getReference().child("chats")
                                .child(receiverRoom)
                                .child("messages")
                                .push()
                                .setValue(messages).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                            }
                        });
                    }
                });
            }
        });
    }
}