package ru.netology.lists;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListViewActivity extends AppCompatActivity {

    // MyList constants
    public static final String TEXT = "text";
    public static final String LEN = "len";

    List<Map<String, String>> simpleAdapterContent = new ArrayList<>();
    private SharedPreferences myListSharedPref;
    BaseAdapter listContentAdapter;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        swipeRefreshLayout = findViewById(R.id.swipeRefresh);

        ListView listView = findViewById(R.id.list);

        myListSharedPref = getSharedPreferences("MyList", MODE_PRIVATE);

        if (!myListSharedPref.contains("MyList")) {
            SharedPreferences.Editor myEditor = myListSharedPref.edit();
            myEditor.putString("MyList", getString(R.string.large_text));
            myEditor.apply();
            Toast.makeText(ListViewActivity.this, "данные сохранены", Toast.LENGTH_LONG).show();
        }

        prepareContent(myListSharedPref.getString("MyList", ""));
        listContentAdapter = createAdapter();

        listView.setAdapter(listContentAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                simpleAdapterContent.remove(position);
                listContentAdapter.notifyDataSetChanged();

                Toast.makeText(
                        ListViewActivity.this,
                        "Position " + position + " removed",
                        Toast.LENGTH_LONG).show();
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(
                        ListViewActivity.this,
                        "Swipe Refresh",
                        Toast.LENGTH_LONG).show();

                prepareContent(myListSharedPref.getString("MyList", ""));
                listContentAdapter.notifyDataSetChanged();
                
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @NonNull
    private BaseAdapter createAdapter() {
        return new SimpleAdapter(
                this,
                simpleAdapterContent,
                R.layout.my_list_item,
                new String[]{TEXT, LEN}, new int[]{R.id.textView1, R.id.textView2});
    }

    void prepareContent(String listText) {
        simpleAdapterContent.clear();

        String[] strList = listText.split("\n\n");

        for (String str : strList) {
            Map<String, String> row = new HashMap<>();
            row.put(TEXT, str);
            row.put(LEN, String.valueOf(str.length()));

            simpleAdapterContent.add(row);
        }
    }
}
