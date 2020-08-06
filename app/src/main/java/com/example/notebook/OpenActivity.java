package com.example.notebook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

public class OpenActivity extends AppCompatActivity {
    private ListView listView;
    ArrayList<String> fileList;
    File[] files;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open);
        listView = findViewById(R.id.allfiles);
        onFileload();
    }



    @Override
    protected void onRestart() {
        super.onRestart();
        onFileload();
    }
    void onFileload(){
        File dir = new File(String.valueOf(getFilesDir()));
        files = dir.listFiles();
        assert files != null;
        fileList = new ArrayList<>();
        for (File f : files) {

            try {

                fileList.add(f.getCanonicalFile().toString().substring(f.toString().lastIndexOf("/") - 1));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, fileList);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), BlackBoardActivity.class);
                intent.putExtra("fullfilekey", files[position].toString());
                intent.putExtra("filenamekey", fileList.get(position));
                startActivity(intent);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), position + "", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }
}