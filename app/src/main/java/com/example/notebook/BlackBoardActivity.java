package com.example.notebook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class BlackBoardActivity extends AppCompatActivity {
    private EditText blackboard;
    private TextView texttitle;
    FileInputStream fileInputStream = null;
    File file =null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_black_board);
        blackboard = findViewById(R.id.blackboard);
        texttitle = findViewById(R.id.filetitle);
        String fullfile = getIntent().getExtras().getString("fullfilekey", "null");
        String filename = getIntent().getExtras().getString("filenamekey", "null");
        texttitle.setText(filename);
        file=new File(fullfile);

        try {
            fileInputStream = new FileInputStream(file);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuffer stringBuffer = new StringBuffer();
            String text;
            while ((text = bufferedReader.readLine()) != null) {
                stringBuffer.append(text).append("\n");
            }
            blackboard.setText(stringBuffer);
            fileInputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.blackboardmenu, menu);

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.edit:
                blackboard.setEnabled(true);
                break;
            case R.id.reopen:
                try {
                    fileInputStream = new FileInputStream(file);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                StringBuffer stringBuffer = new StringBuffer();
                String text = null;

                    try {
                        while ((text = bufferedReader.readLine()) != null){
                            stringBuffer.append(text).append("\n");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    blackboard.setText(stringBuffer);


                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.save:
                SaveDialogFragment saveDialogFragment=new SaveDialogFragment(blackboard.getText().toString());
                saveDialogFragment.show(getSupportFragmentManager(),"SaveDialogFragment");
                break;
            case R.id.exit:
                finish();
                break;
            case R.id.delete:
                file.delete();
                finish();
                break;
            default:
                Toast.makeText(getApplicationContext(),"Something went wrong",Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}