package com.example.toands.drink.Service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.toands.drink.ContainValue;
import com.example.toands.drink.Functions;
import com.example.toands.drink.Model.Push;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class ConstantService extends Service {

    private String TAG = ".ConstantService";
    ContainValue containValue = new ContainValue();
    Functions functions = new Functions();

    @Override
    public void onCreate() {
        super.onCreate();
        containValue._Push.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                containValue.id = dataSnapshot.getChildrenCount();
                stopSelf();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        functions.logi(containValue.FIREBASE_TAG+TAG,"start");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        functions.logi(containValue.FIREBASE_TAG+TAG,"stop");
    }

    public ConstantService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
