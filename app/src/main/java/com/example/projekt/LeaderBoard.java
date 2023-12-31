package com.example.projekt;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projekt.DAO.UserData;
import com.example.projekt.recview.CustomAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class LeaderBoard extends AppCompatActivity {

    RecyclerView recyclerView;
    FirebaseFirestore db;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        FirebaseUser user = auth.getCurrentUser();

        db.collection("leaderboard").get().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                List<UserData> userDataList = new ArrayList<>();
                for(QueryDocumentSnapshot document: task.getResult()){
                    int score = document.getLong("score").intValue();
                    String id = document.getString("email");

                    UserData userData = new UserData(id, score);
                    userDataList.add(userData);
                }
                CustomAdapter adapter = new CustomAdapter(userDataList);
                recyclerView.setAdapter(adapter);
            }
            else{
                Log.d("Error:", "Error fetching document: ", task.getException());
            }
        });
    }
}
