package com.example.toands.drink.Service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.toands.drink.ContainValue;
import com.example.toands.drink.Functions;
import com.example.toands.drink.Model.DrugAbuse;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class FirebaseService extends Service {

    ContainValue containValue = new ContainValue();
    Functions functions = new Functions();
    private static final String TAG = "FirebaseService";
    private int bilster;

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        functions.logi(TAG,"Start");
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
    public int onStartCommand(final Intent intent, int flags, int startId) {
        Log.e(TAG,"onStartCommand");
        containValue._Messages.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                containValue.id = dataSnapshot.getChildrenCount();
                functions.logi(containValue.FIREBASE_TAG+TAG,containValue.id+"");

                com.example.toands.drink.Model.Message message = new com.example.toands.drink.Model.Message();
                containValue.id = intent.getLongExtra("ID",containValue.id);

                if (!intent.getStringExtra(containValue.DAY).isEmpty()){

                    message.setSortDay(intent.getStringExtra(containValue.MONTH));
                    message.setDate(intent.getStringExtra(containValue.DAY));

                    message.setSortTime(intent.getStringExtra(containValue.MINUTE));
                    message.setTime(intent.getStringExtra(containValue.HOUR)+functions.getTimeZ());
                }else {

                    message.setSortDay(functions.getDayOnly());
                    message.setDate(functions.getDate());

                    message.setSortTime(functions.getTimeOnly());
                    message.setTime(functions.getTime());
                }
                message.set_id(containValue.id);
                message.setTitle(containValue.TITLE_MESS);
                message.setShortContain(containValue.SHORT_DE);
                message.setFullContain(containValue.FULL_DE);

                Log.e(TAG,containValue.id+"");
                containValue._Messages.child(String.valueOf(containValue.id)).setValue(message);
                drug_abuse();
                stopSelf();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        stopSelf();
        return START_NOT_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        functions.logi(TAG,"Stop");
    }

    public FirebaseService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
