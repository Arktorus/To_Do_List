package com.To_Do_List.abhilash.internship;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Enter_Name = (EditText) findViewById(R.id.editText2);
        Enter_Password = (EditText) findViewById(R.id.editText);

    }


    String FILE_NAME = "User_base";
    EditText Enter_Name;
    EditText Enter_Password;

    public void Sign_In(View V) {
        SharedPreferences prefs = getSharedPreferences(FILE_NAME, MODE_PRIVATE);
        String Password = prefs.getString(Enter_Name.getText().toString() + "password", null);
        if (Enter_Password.getText().toString().equals(Password)) {
            Intent i=new Intent(getApplicationContext(),TaskList.class);
            i.putExtra("name",Enter_Name.getText().toString());
            startActivity(i);
        } else {
            Toast.makeText(getApplicationContext(), "Failed To Login", Toast.LENGTH_SHORT).show();
            //update_list();
        }
    }

    public void Sign_Up(View V) {
        SharedPreferences prefs = getSharedPreferences(FILE_NAME, MODE_PRIVATE);
        if(prefs.getString(Enter_Name.getText().toString() + "password", null)== null)
        if (Enter_Name.getText().toString().length() != 0) {
            if (Enter_Password.getText().toString().length() != 0) {
                SharedPreferences.Editor editor = getSharedPreferences(FILE_NAME, MODE_PRIVATE).edit();
                editor.putString(Enter_Name.getText().toString() + "password", Enter_Password.getText().toString());
                editor.apply();
                Toast.makeText(getApplicationContext(), "New User Created!", Toast.LENGTH_SHORT).show();
            }
        }
    }


}
