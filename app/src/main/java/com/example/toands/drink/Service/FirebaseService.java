package com.example.toands.drink.Service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.NonNull;

import com.example.toands.drink.ContainValue;
import com.example.toands.drink.Functions;
import com.example.toands.drink.Model.DrugAbuse;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class FirebaseService extends Service {

    ContainValue containValue = new ContainValue();
    Functions functions = new Functions();
    private String TAG = ".FirebaseService";
    private int bilster;

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        functions.logi(containValue.FIREBASE_TAG+TAG,"Start");
    }

    private void drug_abuse(){

        containValue._DrugAbuse.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                DrugAbuse drugAbuse = dataSnapshot.getValue(DrugAbuse.class);
                int present_drug = drugAbuse.getCurrent_drug();
                int constant_drug = drugAbuse.getConstant_blister();
                int the_number_blister = drugAbuse.getThe_number_blister();

                if (present_drug == constant_drug){
                    present_drug = 1;
                    the_number_blister++;

                    DrugAbuse drugAbuse1 = new DrugAbuse();
                    drugAbuse1.setConstant_blister(constant_drug);
                    drugAbuse1.setCurrent_drug(present_drug);
                    drugAbuse1.setThe_number_blister(the_number_blister);

                    containValue._DrugAbuse.setValue(drugAbuse1);

                }else{
                    present_drug++;

                    DrugAbuse drugAbuse1 = new DrugAbuse();
                    drugAbuse1.setConstant_blister(constant_drug);
                    drugAbuse1.setCurrent_drug(present_drug);
                    drugAbuse1.setThe_number_blister(the_number_blister);

                    containValue._DrugAbuse.setValue(drugAbuse1);
                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onCreate() {
        super.onCreate();

        containValue._Messages.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                containValue.id = dataSnapshot.getChildrenCount();
                functions.logi(containValue.FIREBASE_TAG+TAG,containValue.id+"");

                com.example.toands.drink.Model.Message message = new com.example.toands.drink.Model.Message();
                message.set_id(containValue.id);
                message.setTitle(containValue.TITLE_MESS);
                message.setDate(functions.getDate());
                message.setTime(functions.getTime());
                message.setShortContain(containValue.SHORT_DE);
                message.setFullContain(containValue.FULL_DE);

                containValue._Messages.child(String.valueOf(containValue.id)).setValue(message);

                drug_abuse();

                stopSelf();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        functions.logi(containValue.FIREBASE_TAG+TAG,"Stop");
    }

    public FirebaseService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
