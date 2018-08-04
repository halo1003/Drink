package com.example.toands.drink;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.toands.drink.Adapter.AppStackAdapter;
import com.example.toands.drink.Adapter.TimelineAdapter;
import com.example.toands.drink.Database.SQLiteDB;
import com.example.toands.drink.Model.MainNode;
import com.example.toands.drink.Model.Message;
import com.example.toands.drink.Model.Timeline;
import com.example.toands.drink.Model.Version;
import com.example.toands.drink.Service.AutoPush;

import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.github.javiersantos.materialstyleddialogs.enums.Style;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.jeevandeshmukh.glidetoastlib.GlideToast;
import com.loopeer.cardstack.AllMoveDownAnimatorAdapter;
import com.loopeer.cardstack.CardStackView;
import com.loopeer.cardstack.UpDownAnimatorAdapter;
import com.loopeer.cardstack.UpDownStackAnimatorAdapter;
import com.pedro.library.AutoPermissions;
import com.qhutch.bottomsheetlayout.BottomSheetLayout;
import com.yalantis.phoenix.PullToRefreshView;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import mehdi.sakout.fancybuttons.FancyButton;
import xyz.sangcomz.stickytimelineview.RecyclerSectionItemDecoration;
import xyz.sangcomz.stickytimelineview.TimeLineRecyclerView;
import xyz.sangcomz.stickytimelineview.model.SectionInfo;

import static com.example.toands.drink.Database.SQLiteDB.TB_version_name;
import static com.example.toands.drink.Database.SQLiteDB.TBname;

public class MainActivity extends AppCompatActivity implements CardStackView.ItemExpendListener{

    private static final String TAG = "MainActivity";

    ContainValue containValue = new ContainValue();
    Functions functions = new Functions();

    FancyButton fancyButton,fbtnGraph,fbtnDownload,fbtnSwitch, fbtnUI;
    PullToRefreshView mPullToRefreshView;

    private CardStackView mStackView;
    private TimeLineRecyclerView recyclerView;
    private RelativeLayout mActionButtonContainer;
    private AppStackAdapter mStackAdapter;
    private RelativeLayout relativeLayout;
    private BottomSheetLayout bottomSheetLayout;
    private ImageView imageView;

    private int control = -1;
    private int ui;
    SharedPreferences sharedPreferences;

    List<Integer> list_date = new ArrayList<Integer>();
    List<Timeline> listTimeline = new ArrayList<Timeline>();

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

    List<Integer> integersList = new ArrayList<Integer>();
    CharSequence layout[] = new CharSequence[] {"Card View", "Timeline View"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Window window = MainActivity.this.getWindow();
        window.setStatusBarColor(ContextCompat.getColor(MainActivity.this,R.color.fbutton_color_midnight_blue));
        sharedPreferences = getSharedPreferences("UI", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();

        Intent i_auto = new Intent(MainActivity.this,AutoPush.class);
        startService(i_auto);

        _DefaultCaller();

        AutoPermissions.Companion.loadAllPermissions(MainActivity.this, 1);

        String machine = functions.getDeviceName();

        containValue._drink3e7a9.child("Device").setValue(machine);

        relativeLayout.setVisibility(View.GONE);
        final SQLiteDB sqLiteDB = new SQLiteDB(getApplicationContext());

        sqLiteDB.Create_table();
        sqLiteDB.Create_table_ver();

        mPullToRefreshView.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPullToRefreshView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPullToRefreshView.setRefreshing(false);
                        Intent i_auto = new Intent(MainActivity.this,AutoPush.class);
                        startService(i_auto);
                        Log.e(TAG,"refreshing...");
                    }
                }, 1000);
            }
        });

        returnUI();

        fbtnUI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ui = sharedPreferences.getInt("layout", 1);
                new MaterialDialog.Builder(MainActivity.this)
                        .title("Pick a theme")
                        .items(layout)
                        .itemsCallbackSingleChoice(ui, new MaterialDialog.ListCallbackSingleChoice() {
                            @Override
                            public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                                editor.putInt("layout",which);
                                editor.apply();
                                returnUI();
                                return true;
                            }
                        })
                        .positiveText("Choose")
                        .negativeText("Cancel")
                        .show();

            }
        });

        fbtnSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i_cir = new Intent(MainActivity.this,CircleActivity.class);
                startActivity(i_cir);
            }
        });

        fancyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sqLiteDB.getNodesCount(TBname)==0 || (Integer.parseInt(sqLiteDB.getNew(TBname).getDay())!=Integer.parseInt(functions.getDay()))){

                    MainNode mainNode = new MainNode();

                    mainNode.setHour(functions.getHour());
                    mainNode.setMinute(functions.getMinute());
                    mainNode.setSecond(functions.getSecond());
                    mainNode.setTime_type(functions.getType());
                    mainNode.setDay(functions.getDay());
                    mainNode.setMounth(functions.getMouth());
                    mainNode.setYear(functions.getYear());

                    if (!functions.getDeviceName().equals("Lenovo A7010a48")) sqLiteDB.addNode(mainNode);

                    new GlideToast.makeToast(MainActivity.this,"Push success"
                            ,GlideToast.LENGTHTOOLONG,GlideToast.SUCCESSTOAST,GlideToast.BOTTOM).show();
                }else {
                    String temp = sqLiteDB.getNew(TBname).getHour()+":"+sqLiteDB.getNew(TBname).getMinute()+" "
                            +sqLiteDB.getNew(TBname).getTime_type();
                    String hour = functions.getHour();
                    String minute = functions.getMinute();
                    String second = functions.getSecond();
                    String type = functions.getType();
                    Popup_Warning(MainActivity.this,temp,hour,minute,second,type);
                }
            }
        });

        fbtnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
//                String url = "https://github.com/Piashsarker/AndroidAppUpdateLibrary/raw/master/app-debug.apk";

                containValue._Version.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        double ver = (double) dataSnapshot.child("ver").getValue();
                        String link = dataSnapshot.child("link").getValue().toString();
                        Version version = new Version(ver,functions.getDayOnly());

                        if (sqLiteDB.getNodesCount(TB_version_name)>0){
                            Log.e(TAG,sqLiteDB.getNewVersion().getVer()+"/"+ver);
                            if (sqLiteDB.getNewVersion().getVer()<ver){
                                sqLiteDB.addNodeVersion(version);
                                Download(link);
                            }else{
                                Popup_NoExist(MainActivity.this,ver,sqLiteDB.getNewVersion().getDay());
                            }
                        }else{
                            sqLiteDB.addNodeVersion(version);
                            Download(link);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        fbtnGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i_Graph = new Intent(MainActivity.this,GraphActivity.class);
                startActivity(i_Graph);
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                control = control * -1;
                imageView.animate().rotationBy(180*control).setDuration(500).setInterpolator(new LinearInterpolator()).start();
                bottomSheetLayout.toggle();
            }
        });

        bottomSheetLayout.setOnProgressListener(new BottomSheetLayout.OnProgressListener() {
            @Override
            public void onProgress(float v) {
                if (bottomSheetLayout.isExpended()){

                }else{

                }
            }
        });
    }

    private void returnUI(){
        ui = sharedPreferences.getInt("layout", 1);
        switch (ui){
            case 0:
                CardUI();
                break;
            case 1:
                TimelineUI();
                break;
            case 2:
                break;
            default:
                break;
        }
    }

    private void TimelineUI(){
        Log.e(TAG,"TimelineUI");

        containValue._Messages.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listTimeline = new ArrayList<Timeline>();
                for (DataSnapshot singleMess: dataSnapshot.getChildren()){
                    Message message = singleMess.getValue(Message.class);
                    String time[] = message.getSortDay().split(",");

                    listTimeline.add(new Timeline(time[0],time[1],message.getTime(),message.getDate()));

                }

                integersList = functions.detectSectionEnd(listTimeline);
                TimelineAdapter timelineAdapter = new TimelineAdapter(listTimeline);

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL,false);
                recyclerView.addItemDecoration(getSectionCallback(listTimeline));

                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(timelineAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private RecyclerSectionItemDecoration.SectionCallback getSectionCallback(final List<Timeline> Timeline){
        return new RecyclerSectionItemDecoration.SectionCallback() {
            @Override
            public boolean isSection(int i) {
                for (Integer j : integersList){
                    if (j == i) return true;
                }
                return false;
            }

            @Nullable
            @Override
            public SectionInfo getSectionHeader(int i) {
                String s[] = Timeline.get(i).month.split(" ");
                String month = functions.standaraze(s[1])+" "+s[2];
                return new SectionInfo(month,s[3]);
            }
        };
    }

    private void CardUI(){
        Log.e(TAG,"CardUI");
        mStackView.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);

        mStackView.setItemExpendListener(MainActivity.this);
        mStackAdapter = new AppStackAdapter(getApplicationContext());
        mStackView.setAdapter(mStackAdapter);
        LoadData();
    }

    private void Download(String link){
        if (link.startsWith("https://")) {
            DownloadApk downloadApk = new DownloadApk(MainActivity.this);
            downloadApk.startDownloadingApk(link);
        }else {
            new GlideToast.makeToast(MainActivity.this, "Wrong URL"
                    , GlideToast.LENGTHTOOLONG, GlideToast.FAILTOAST, GlideToast.BOTTOM).show();
        }
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String message = intent.getStringExtra(containValue.SIGNAL);
            if (!message.isEmpty()){
                if (message.equals("rewrite")) {
                    new GlideToast.makeToast(MainActivity.this, "Update success"
                            , GlideToast.LENGTHTOOLONG, GlideToast.WARNINGTOAST, GlideToast.BOTTOM).show();
                }else{
                    new GlideToast.makeToast(MainActivity.this, "Server synchronous"
                            , GlideToast.LENGTHTOOLONG, GlideToast.WARNINGTOAST, GlideToast.BOTTOM).show();
                }
            }
        }
    };

    public void Popup_Warning(Context context, String temp, final String h, final String m, final String s, final String type){
        new MaterialStyledDialog.Builder(context)
                .setTitle("Exist Detected!!!")
                .setDescription("You already touched at "+temp+"\nDo you want to overwrite this?")
                .setHeaderColor(R.color.fbutton_color_pomegranate)
                .setStyle(Style.HEADER_WITH_TITLE)
                .setPositiveText("Yes")
                .setNegativeText("No")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        SQLiteDB sqLiteDB = new SQLiteDB(getApplicationContext());
                        sqLiteDB.UpdateTB(TBname,h,m,s,type);
                        new GlideToast.makeToast(MainActivity.this, "Update success"
                                , GlideToast.LENGTHTOOLONG, GlideToast.SUCCESSTOAST, GlideToast.BOTTOM).show();
                    }
                })
                .show();
    }

    public void Popup_NoExist(Context context, double ver,String day){
        new MaterialStyledDialog.Builder(context)
                .setTitle("Your version is newest!!")
                .setDescription("Your version is "+ver+"\nLast released in "+day)
                .setHeaderColor(R.color.fbutton_color_pomegranate)
                .setStyle(Style.HEADER_WITH_TITLE)
                .setPositiveText("Okay")
                .show();
    }

    public void LoadData(){

        containValue._Messages.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list_date = new ArrayList<Integer>();
                int n = (int) dataSnapshot.getChildrenCount();

                for (int i = 0 ; i < n ; i++){
                    int j = i;
                    if (i>=TEST_DATAS.length) {
                        int temp = i/TEST_DATAS.length;
                        j = i - (TEST_DATAS.length*temp);
                    }

                    list_date.add(TEST_DATAS[j]);
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

    //TODO: _DefaultCaller
    public void _DefaultCaller() {

        fancyButton = (FancyButton) findViewById(R.id.btnMain);
        fbtnGraph = (FancyButton)findViewById(R.id.btnGraph);
        fbtnDownload = (FancyButton) findViewById(R.id.btnDownload);
        fbtnSwitch = (FancyButton) findViewById(R.id.btnSwitch);
        fbtnUI = (FancyButton) findViewById(R.id.btnUI);

        mActionButtonContainer = (RelativeLayout) findViewById(R.id.button_container);

        relativeLayout = (RelativeLayout) findViewById(R.id.button_container);
        mStackView = (CardStackView) findViewById(R.id.stackview_main);
        recyclerView = (TimeLineRecyclerView)findViewById(R.id.recycler_view);
        mPullToRefreshView = (PullToRefreshView) findViewById(R.id.pull_to_refresh);
        bottomSheetLayout = (BottomSheetLayout) findViewById(R.id.bottom_sheet_layout);

        imageView = (ImageView) findViewById(R.id.imageViewArrow);
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
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter(containValue.SIGNAL));
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        super.onPause();
    }
}