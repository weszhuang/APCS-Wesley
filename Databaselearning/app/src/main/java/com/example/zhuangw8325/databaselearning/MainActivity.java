package com.example.zhuangw8325.databaselearning;

import android.content.DialogInterface;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DataBaseHelper myDb;
    EditText editName;
    EditText editAge;
    EditText editAddress;
    EditText editSearch;
    Button btnAddData;
    String[] fields;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);

        myDb = new DataBaseHelper(this);
        //add the layout bars
        editName = (EditText) findViewById(R.id.editText_Name);
        editAge = (EditText) findViewById(R.id.editText_Age);
        editAddress = (EditText) findViewById(R.id.editText_address);
        editSearch = (EditText) findViewById(R.id.editSearch);
        fields = new String[4];
        fields[0] = "ID: ";
        fields[1] = "Name: ";
        fields[2] = "Age: ";
        fields[3] = "Address: ";
    }
    public void addData(View v) {
        boolean isInserted = (myDb.insertData(editName.getText().toString(),editAge.getText().toString(),editAddress.getText().toString()));

        String text = "";
        if (isInserted == true) {
            Log.d("MyContact", "Success inserting data");
            text = "Data inserted";
            Toast toast = Toast.makeText(getApplicationContext(), text,Toast.LENGTH_SHORT);
            toast.show();
        }
        else {
            text = "Failure";
            Toast toast = Toast.makeText(getApplicationContext(), text,Toast.LENGTH_SHORT);
            toast.show();
            Log.d("MyContact", "Failure inserting data");
        }
        editName.setText("");
        editAge.setText("");
        editAddress.setText("");

    }
    public void viewData(View v) {
        Cursor res = myDb.getAllData();
        if (res.getCount() == 0) {
            showMessage("Error", "No data found in database");
        }
        StringBuffer buffer = new StringBuffer();
        res.moveToFirst();
        for (int i = 0; i < res.getCount(); i++) {
            for (int j = 0; j <= 3; j++) {
                buffer.append(fields[j]);
                buffer.append(res.getString(j));
                buffer.append("\n");
            }
            buffer.append("\n");
            res.moveToNext();
        }
        showMessage("Data", buffer.toString());
        System.out.println(buffer);
    }

    public void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message).setTitle(title)
                .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        //do nothing kek
                    }
                });
        builder.show();
    }

    public void searchMessage(View v) {
        String search = editSearch.getText().toString();
        StringBuffer buffer = new StringBuffer();
        Cursor res = myDb.getAllData();
        for (res.moveToFirst();!res.isAfterLast(); res.moveToNext()) {
            if (res.getString(1).equals(search)) {
                for (int j = 0; j < 4; j++) {
                    buffer.append(fields[j]);
                    buffer.append(res.getString(j));
                    buffer.append("\n");
                }
            }
        }
        if (buffer.toString().equals("")) {
            showMessage("No results found", "");
        } else {
            showMessage("Results", buffer.toString());
        }
        editSearch.setText("");
    }
}
