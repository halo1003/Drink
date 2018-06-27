package com.example.toands.drink;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.toands.drink.Adapter.AppStackAdapter;
import com.example.toands.drink.Database.SQLiteDB;
import com.example.toands.drink.Model.MainNode;
import com.example.toands.drink.Service.AutoPush;

import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.github.javiersantos.materialstyleddialogs.enums.Style;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.loopeer.cardstack.AllMoveDownAnimatorAdapter;
import com.loopeer.cardstack.CardStackView;
import com.loopeer.cardstack.UpDownAnimatorAdapter;
import com.loopeer.cardstack.UpDownStackAnimatorAdapter;
import com.yalantis.phoenix.PullToRefreshView;

import java.util.ArrayList;
import java.util.List;

import static com.example.toands.drink.Database.SQLiteDB.TBname;

public class MainActivity extends AppCompatActivity implements CardStackView.ItemExpendListener{

    private static final String TAG = "MainActivity";

    ContainValue containValue = new ContainValue();
    Functions functions = new Functions();

    Button btnMain;
    PullToRefreshView mPullToRefreshView;

    private CardStackView mStackView;
    private RelativeLayout mActionButtonContainer;
    private AppStackAdapter mStackAdapter;

    private String tagMain = "btnMain";
    private String pluse = "+";

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

        Window window = MainActivity.this.getWindow();
        window.setStatusBarColor(ContextCompat.getColor(MainActivity.this,R.color.fbutton_color_midnight_blue));

        final Intent i_auto = new Intent(MainActivity.this,AutoPush.class);
        startService(i_auto);

        _DefaultCaller();

        final SQLiteDB sqLiteDB = new SQLiteDB(getApplicationContext());
        sqLiteDB.Create_table();

        mStackView.setItemExpendListener(MainActivity.this);
        mStackAdapter = new AppStackAdapter(getApplicationContext());
        mStackView.setAdapter(mStackAdapter);

        LoadData();

        mPullToRefreshView.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPullToRefreshView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPullToRefreshView.setRefreshing(false);
                        startService(i_auto);
                    }
                }, 1000);
            }
        });

        btnMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                final Intent i = new Intent(MainActivity.this, FirebaseService.class);
//                startService(i);
//
//                Intent i_constantService = new Intent(MainActivity.this, ConstantService.class);
//                startService(i_constantService);

                if (sqLiteDB.getNodesCount(TBname)==0 || (Integer.parseInt(sqLiteDB.getNew(TBname).getDay())!=Integer.parseInt(functions.getDay()))){

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
                    String daymonthstr = sqLiteDB.getNew(TBname).getHour()+":"+sqLiteDB.getNew(TBname).getMinute()+" "
                            +sqLiteDB.getNew(TBname).getTime_type();
                    Popup_Warning(MainActivity.this,daymonthstr);
                }
            }
        });
    }

    public void Popup_Warning(Context context,String s){
        new MaterialStyledDialog.Builder(context)
                .setTitle("Exist Detected!!!")
                .setDescription("You already touched at "+s+"\nDo you want to overwrite this?")
                .setHeaderColor(R.color.fbutton_color_pomegranate)
                .setStyle(Style.HEADER_WITH_TITLE)
                .setPositiveText("Yes")
                .setNegativeText("No")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        Toast.makeText(getApplicationContext(),"Call back test",Toast.LENGTH_SHORT).show();
                    }
                })
                .show();
    }

    public void LoadData(){

        containValue._Messages.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list_date = new ArrayList<Integer>();
                int n = (int) dataSnapshot.getChildrenCount();

                for (int i = 0 ; i < n ; i++){
                    list_date.add(TEST_DATAS[i]);
                }

                functions.loge("MainActivity","fresh: success");
                mStackAdapter.updateData(list_date);
                mStackAdapter.notifyDataSetChanged();
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
        mPullToRefreshView = (PullToRefreshView) findViewById(R.id.pull_to_refresh);
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
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}