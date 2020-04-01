package aido.firebase;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import aido.file.fileReadWrite;


import aido.properties.ConfigProperties;
import aido.properties.StorageProperties;

/**
 * Created by sumeendranath on 25/01/17.
 */
public class FireDB {


//    FirebaseDatabase _database = FirebaseDatabase.getInstance();
//    DatabaseReference databaseReference = _database.getReference("IP");
FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    static DatabaseReference mreff;
    public DatabaseReference getMreff() {
        mreff = firebaseDatabase.getReference("IP");
        return mreff;
    }

    public void setMreff(DatabaseReference mreff) {
        FireDB.mreff = mreff;
    }
    public static String data1(){
        mreff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DataSnapshot dataSnapshot1 = dataSnapshot.child("ip");
                String s = (String) dataSnapshot1.getValue();
                Bundle bundle = new Bundle();
                bundle.putString("valu",s);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    return null;
}


    public FireDB() {
getMreff();
setMreff(mreff);

    }

}