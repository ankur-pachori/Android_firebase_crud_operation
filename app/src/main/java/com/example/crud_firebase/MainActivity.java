package com.example.crud_firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.example.crud_firebase.DBHelper.DBHelper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements  View.OnClickListener {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private static ArrayList<DBHelper> l = new ArrayList<>();
    private RecyclerView list;
    private Button btnCreate;
    public static Activity Fa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        l.clear();
        Fa =this;
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference =firebaseDatabase.getReference("Users");
        getUsers();
        btnCreate = findViewById(R.id.btn_CreateMain);
        list = findViewById(R.id.list);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(new ListAdapter(l,this ));
        list.addOnItemTouchListener(new RecyclerView.SimpleOnItemTouchListener());
        btnCreate.setOnClickListener(this);

    }
    public void getUsers(){
        databaseReference.addValueEventListener(new ValueEventListener() {
            String userName, email, number;
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                l.clear();
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    userName =dataSnapshot.child("userName").getValue().toString();
                    email = dataSnapshot.child("email").getValue().toString();
                    number = dataSnapshot.child("number").getValue().toString();
                    l.add(new DBHelper(userName,email,number));;
                     }
                    list.setAdapter(new ListAdapter(l,MainActivity.this));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_CreateMain:
                Intent i = new Intent(MainActivity.this,SecondActivity.class);
                String s;
                s="Create";
                i.putExtra("Button",s);
                startActivity(i);
                finish();
                break;
        }
    }
    public static void cleanList(){
        l.clear();
    }
}
