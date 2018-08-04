package com.example.toands.drink;

import android.annotation.SuppressLint;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

import com.dzaitsev.android.widget.RadarChartView;
import com.example.toands.drink.Database.SQLiteDB;
import com.example.toands.drink.Model.MainNode;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static android.graphics.Paint.Style.FILL_AND_STROKE;
import static com.example.toands.drink.Database.SQLiteDB.TBname;

public class CircleActivity extends AppCompatActivity {

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle);

        Window window = CircleActivity.this.getWindow();
        window.setStatusBarColor(ContextCompat.getColor(CircleActivity.this,R.color.fbutton_color_midnight_blue));

        SQLiteDB sqLiteDB = new SQLiteDB(getApplicationContext());
        int count = (int) sqLiteDB.getNodesCount(TBname);

        List<MainNode> listwarning = new ArrayList<>();

        final Map<String, Float> axis = new LinkedHashMap<>();
        for (int i =1;i<=count;i++){
            float value = Float.parseFloat(sqLiteDB.getNode(i).getHour())*(float)60
                    +Float.parseFloat(sqLiteDB.getNode(i).getMinute())
                    - (float)(8*60);
            axis.put(sqLiteDB.getNode(i).getDay()+"/"+sqLiteDB.getNode(i).getMounth()
                    ,value);
            if (value>=90) {
                MainNode node = new MainNode();
                node.setDay(sqLiteDB.getNode(i).getDay());
                node.setMounth(sqLiteDB.getNode(i).getMounth());
                node.setTime_type(sqLiteDB.getNode(i).getTime_type());
                node.setHour(sqLiteDB.getNode(i).getHour());
                node.setMinute(sqLiteDB.getNode(i).getMinute());
                listwarning.add(node);
            }
        }

        final RadarChartView chartView = (RadarChartView) findViewById(R.id.radar_chart);
        TextView txtvTitle = (TextView) findViewById(R.id.Title);
        TextView txtvContent = (TextView) findViewById(R.id.Content);

        String s = "";

        for (MainNode node:listwarning){
            int temp = (int) (Float.parseFloat(node.getHour())*(float)60+Float.parseFloat(node.getMinute())- (float)(9*60+15));
            s = s + node.getDay()+"/"+node.getMounth()
                    +" ["+node.getHour()+":" +node.getMinute()+" "+node.getTime_type()+"] :"
                    +" over "+temp+" minute"+
                    "\n";
        }

        txtvTitle.setText("Warning days("+listwarning.size()+")");
        txtvTitle.setTextColor(R.color.fbutton_color_pomegranate);

        txtvContent.setText(s);
        txtvContent.setTextColor(R.color.fbutton_color_midnight_blue);

        chartView.setAxis(axis);

        chartView.setAutoSize(false);             // auto balance the chart
        chartView.setAxisTick(30);          // if you want circles instead of polygons
        chartView.setChartStyle(FILL_AND_STROKE);           // chart drawn with this style will be filled not stroked
        chartView.setAxisMax(240);

    }
}
