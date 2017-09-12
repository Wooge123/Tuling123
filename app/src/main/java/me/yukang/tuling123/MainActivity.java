package me.yukang.tuling123;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements HttpGetDataListener, View.OnClickListener {

    private HttpData httpData;
    private List<ListData> lists;

    private ListView lv;
    private EditText etSend;
    private Button btnSend;
    private String contentStr;
    private MyAdapter adapter;
    private String[] welcome_array;
    private double currentTime, oldTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    public void initView() {
        lv = findViewById(R.id.lv);
        etSend = findViewById(R.id.etSend);
        btnSend = findViewById(R.id.btnSend);
        lists = new ArrayList<>();
        btnSend.setOnClickListener(this);
        adapter = new MyAdapter(lists, this);
        lv.setAdapter(adapter);
        ListData listData = new ListData(getRandomWelcomeTips(), ListData.RECEIVER, getTime());
        lists.add(listData);
    }

    private String getRandomWelcomeTips() {
        welcome_array = getResources().getStringArray(R.array.welcome_tips);
        int index = (int) (Math.random() * (welcome_array.length - 1));
        return welcome_array[index];
    }

    @Override
    public void getDataUrl(String data) {
        parseText(data);
    }

    public void parseText(String str) {
        try {
            JSONObject jb = new JSONObject(str);
            ListData listData = new ListData(jb.getString("text"), ListData.RECEIVER, getTime());
            lists.add(listData);
            adapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onClick(View view) {
        getTime();
        contentStr = etSend.getText().toString().trim()
                .replace(" ", "")
                .replace("\n", "");
        etSend.setText("");
        ListData listData = new ListData(contentStr, ListData.SEND, getTime());
        lists.add(listData);
        if (lists.size() > 30) {
            for (int i = 0; i < lists.size(); i++) {
                lists.remove(i);
            }
        }
        adapter.notifyDataSetChanged();
        httpData = new HttpData("http://www.tuling123.com/openapi/api?key=4c2a9e25dea84e21a701980ef611fdfb&info=" + contentStr, this);
        httpData.execute();
    }

    public String getTime() {
        currentTime = System.currentTimeMillis();
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        String str = format.format(new Date());
        if (currentTime - oldTime >= 5 * 60 * 1000) {
            oldTime = currentTime;
            return str;
        } else
            return "";
    }
}
