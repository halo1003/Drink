package com.example.toands.drink;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.toands.drink.Adapter.AppStackAdapter;
import com.example.toands.drink.Database.SQLiteDB;
import com.example.toands.drink.Model.MainNode;
import com.example.toands.drink.Service.ConstantService;
import com.example.toands.drink.Service.FirebaseService;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.loopeer.cardstack.AllMoveDownAnimatorAdapter;
import com.loopeer.cardstack.CardStackView;
import com.loopeer.cardstack.UpDownAnimatorAdapter;
import com.loopeer.cardstack.UpDownStackAnimatorAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.saeid.fabloading.LoadingView;

import static com.example.toands.drink.Database.SQLiteDB.TBname;

public class MainActivity extends AppCompatActivity implements CardStackView.ItemExpendListener{

    ContainValue containValue = new ContainValue();
    Functions functions = new Functions();

    Button btnMain;
    private CardStackView mStackView;
    private RelativeLayout mActionButtonContainer;
    private AppStackAdapter mStackAdapter;

    private String tagMain = "btnMain";
    private String pluse = "+";

    LoadingView mLoadingView;
    List<Integer> list_date = new ArrayList<Integer>();

    public static Integer[] Initial = new Integer[]{};

    public static Integer[] TEST_DATAS = new Integer[]{
            R.color.color_1,
            R.color.color_2,
            R.color.color_3,
            R.color.color_4,
            R.color.color_5,
            R.color.color_6,
            R.color.color_7,
            R.color.color_8,
            R.color.color_9,
            R.color.color_10,
            R.color.color_11,
            R.color.color_12,
            R.color.color_13,
            R.color.color_14,
            R.color.color_15,
            R.color.color_16,
            R.color.color_17,
            R.color.color_18,
            R.color.color_19,
            R.color.color_20,
            R.color.color_21,
            R.color.color_22,
            R.color.color_23,
            R.color.color_24,
            R.color.color_25,
            R.color.color_26
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _DefaultCaller();

        final SQLiteDB sqLiteDB = new SQLiteDB(getApplicationContext());
        sqLiteDB.Create_table();

        mLoadingView = (LoadingView) findViewById(R.id.loading_view);
        int marvel_1 = R.drawable.marvel_1;
        int marvel_2 = R.drawable.marvel_2;
        int marvel_3 = R.drawable.marvel_3;
        int marvel_4 = R.drawable.marvel_4;

        mLoadingView.addAnimation(Color.parseColor("#FFD200"), marvel_1, LoadingView.FROM_LEFT);
        mLoadingView.addAnimation(Color.parseColor("#2F5DA9"), marvel_2, LoadingView.FROM_TOP);
        mLoadingView.addAnimation(Color.parseColor("#FF4218"), marvel_3, LoadingView.FROM_RIGHT);
        mLoadingView.addAnimation(Color.parseColor("#C7E7FB"), marvel_4, LoadingView.FROM_BOTTOM);

        mLoadingView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLoadingView.setDuration(200);
                mLoadingView.setRepeat(10);
                mLoadingView.startAnimation();

                LoadData();

                new Handler().postDelayed(
                        new Runnable() {
                            @Override
                            public void run() {
                                functions.loge("MainActivity","fresh: success");
                                mStackAdapter.updateData(list_date);
                            }
                        }
                        , 3000
                );

                mStackView.setItemExpendListener(MainActivity.this);
                mStackAdapter = new AppStackAdapter(getApplicationContext());
                mStackView.setAdapter(mStackAdapter);
            }
        });

        mLoadingView.performClick();

        btnMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                final Intent i = new Intent(MainActivity.this, FirebaseService.class);
//                startService(i);
//
//                Intent i_constantService = new Intent(MainActivity.this, ConstantService.class);
//                startService(i_constantService);

                if (sqLiteDB.getNodesCount(TBname)==0 || (Integer.parseInt(sqLiteDB.getNew(TBname).getDay())<Integer.parseInt(functions.getDay()))){

                    MainNode mainNode = new MainNode();

                    mainNode.setHour(functions.getHour());
                    mainNode.setMinute(functions.getMinute());
                    mainNode.setSecond(functions.getSecond());
                    mainNode.setTime_type(functions.getType());
                    mainNode.setDay(functions.getDay());
                    mainNode.setMounth(functions.getMouth());
                    mainNode.setYear(functions.getYear());

                    sqLiteDB.addNode(mainNode);

                    Toast.makeText(getApplicationContext(),"Push Success",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getApplicationContext(),"Today/"+sqLiteDB.getNew(TBname).getDay()+" clicked! Waiting, next day",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void LoadData(){

        list_date = new ArrayList<Integer>();
        containValue._Messages.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int n = (int) dataSnapshot.getChildrenCount();

                for (int i = 0 ; i < n ; i++){
                    list_date.add(TEST_DATAS[i]);
                }
                functions.loge("MainActivity","Data-load: success "+n);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void _DefaultCaller() {
        btnMain = (Button) findViewById(R.id.btnMain);
        mStackView = (CardStackView) findViewById(R.id.stackview_main);
        mActionButtonContainer = (RelativeLayout) findViewById(R.id.button_container);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_all_down:
                mStackView.setAnimatorAdapter(new AllMoveDownAnimatorAdapter(mStackView));
                break;
            case R.id.menu_up_down:
                mStackView.setAnimatorAdapter(new UpDownAnimatorAdapter(mStackView));
                break;
            case R.id.menu_up_down_stack:
                mStackView.setAnimatorAdapter(new UpDownStackAnimatorAdapter(mStackView));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onPreClick(View view) {
        mStackView.pre();
    }

    public void onNextClick(View view) {
        mStackView.next();
    }

    @Override
    public void onItemExpend(boolean expend) {
        mActionButtonContainer.setVisibility(expend ? View.VISIBLE : View.GONE);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mLoadingView.resumeAnimation();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mLoadingView.pauseAnimation();
    }
}