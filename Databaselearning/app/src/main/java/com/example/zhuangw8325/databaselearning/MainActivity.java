package com.example.zhuangw8325.databaselearning;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by zhuangw8325 on 5/12/2017.
 */
public class MainActivity extends AppCompatActivity {

    DataBaseHelper myDb;
    EditText editName;
    Button btnAddData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDb = new DataBaseHelper(this);
    }
}
