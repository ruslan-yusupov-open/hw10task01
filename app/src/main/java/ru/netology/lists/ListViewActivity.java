package ru.netology.lists;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ListView list = findViewById(R.id.list);

        List<Map<String, String>> values = prepareContent();

        BaseAdapter listContentAdapter = createAdapter(values);

        list.setAdapter(listContentAdapter);
    }

    @NonNull
    private BaseAdapter createAdapter(List<Map<String, String>> values) {
        return new SimpleAdapter(
                this,
                values,
                R.layout.my_list_item,
                new String[]{"text", "len"}, new int[]{R.id.textView1, R.id.textView2});
    }

    @NonNull
    private List<Map<String, String>> prepareContent() {
        String[] strList = getString(R.string.large_text).split("\n\n");
        List<Map<String, String>> content = new ArrayList<>();

        for (String str : strList) {
            Map<String, String> row = new HashMap<>();
            row.put("text", str);
            row.put("len", String.valueOf(str.length()));

            content.add(row);
        }

        return content;
    }
}
