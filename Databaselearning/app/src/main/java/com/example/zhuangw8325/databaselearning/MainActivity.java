package com.example.zhuangw8325.databaselearning;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by zhuangw8325 on 5/12/2017.
 */
public class MainActivity extends AppCompatActivity {

    DataBaseHelper myDb;
    EditText editName;
    EditText editAge;
    EditText editAddress;
    Button btnAddData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDb = new DataBaseHelper(this);
    }



    public void addData (View v){
        Log.d("MyContact", "Adding Data");
        boolean isInserted = myDb.insertData(editName.getText().toString(),editAge.getText().toString(),editAddress.getText().toString());

        if(isInserted) {
            Log.d("MyContact", "Success Inserting data");
        }else{
            Log.d("MyContact", "Failure Inserting data");
        }
    }

    public void viewData(View v){
        Cursor res = myDb.getAllData();
        if(res.getCount() == 0) {
            showMessage("Error", "No data found in database");
            return;
        }

        StringBuffer buffer = new StringBuffer();
        res.moveToFirst();
        for(res.moveToFirst();!res.isAfterLast();res.moveToNext()){
            for(int i = 0; i<res.getColumnCount();i++){
                buffer.append(res.getString(i) + "  ");
            }
            buffer.append("\n");
        }
        showMessage("Data", buffer.toString());
    }

    public void showMessage(String Title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message).setTitle(Title)
            .setPositiveButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                //do nothing kek
            }
        });
        builder.show();
    }
}
