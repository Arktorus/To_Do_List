package com.To_Do_List.abhilash.internship;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Map;

public class TaskList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);

        Intent intent = getIntent();
        name = intent.getStringExtra("name"); //The string reference for Username

        myListView = (ListView) findViewById(R.id.listView);
        mArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        myListView.setAdapter(mArrayAdapter);

        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String Full = parent.getItemAtPosition(position).toString();

                //final String[] Contact_Details = Full.split("\n");
                //Contact Details contains Name (Contact_Details[0] + \n +  Number Contact_Details[1]
                String msg = "Do you want to move this to Tasks Done?";
                new AlertDialog.Builder(TaskList.this)
                        .setTitle("Move Task")
                        .setMessage(msg)
                        .setNegativeButton("Cancel", null) // dismisses by default
                        .setNeutralButton("Delete", new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Delete_Task(Full,name+"todo");
                            }
                        })
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // do the acknowledged action, beware, this is run on UI thread
                                Remove_Task(Full);
                            }
                        })
                        .create()
                        .show();
            }
        });

        nmyListView = (ListView) findViewById(R.id.listView2);
        nmArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        nmyListView.setAdapter(nmArrayAdapter);

        nmyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String Full = parent.getItemAtPosition(position).toString();

                //final String[] Contact_Details = Full.split("\n");
                //Contact Details contains Name (Contact_Details[0] + \n +  Number Contact_Details[1]
                String msg = "Do you want to delete this task?";
                new AlertDialog.Builder(TaskList.this)
                        .setTitle("Delete Task")
                        .setMessage(msg)
                        .setNegativeButton("Cancel", null) // dismisses by default
                        /*.setNeutralButton("Delete", new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Edit_Task(Full,name+"done");
                            }
                        })*/
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // do the acknowledged action, beware, this is run on UI thread
                                Delete_Task(Full,name+"done");
                            }
                        })
                        .create()
                        .show();
            }
        });


        Enter_Task_Name = (EditText) findViewById(R.id.editText3);
        Enter_Task_Details = (EditText) findViewById(R.id.editText4);

        Update();
    }


    String name;
    private ListView myListView;
    private ArrayAdapter<String> mArrayAdapter;
    private ListView nmyListView;
    private ArrayAdapter<String> nmArrayAdapter;

    EditText Enter_Task_Name;
    EditText Enter_Task_Details;

    //Adds new task to list
    public void New_Task(View V) {
        String FILE_NAME = name + "todo";
        if (Enter_Task_Name.getText().toString().length() != 0) {
            if (Enter_Task_Details.getText().toString() != null) {
                SharedPreferences.Editor editor = getSharedPreferences(FILE_NAME, MODE_PRIVATE).edit();
                editor.putString(Enter_Task_Name.getText().toString(), Enter_Task_Details.getText().toString());
                editor.apply();
                Toast.makeText(getApplicationContext(), "New Task Created!", Toast.LENGTH_SHORT).show();
            }
        }
        Enter_Task_Name.setText("");
        Enter_Task_Details.setText("");
        Update();
    }

    //Moves from to_do to done when Task is sent from array list
    private void Remove_Task(String Task) {
        String FILE_NAME = name + "todo";
        String[] Task_Details = Task.split("\n");
        SharedPreferences.Editor editor = getSharedPreferences(FILE_NAME, MODE_PRIVATE).edit();
        editor.remove(Task_Details[0]);
        editor.apply();

        String FILE_NAME_2 = name + "done";
        editor = getSharedPreferences(FILE_NAME_2, MODE_PRIVATE).edit();
        editor.putString(Task_Details[0], Task_Details[1]);
        editor.apply();

        Update();
    }

    //deletes task from specified list
    private void Delete_Task(String Task, String FILE_NAME) {
        String[] Task_Details = Task.split("\n");
        SharedPreferences.Editor editor = getSharedPreferences(FILE_NAME, MODE_PRIVATE).edit();
        editor.remove(Task_Details[0]);
        editor.apply();
        Update();
    }

    private void Edit_Task(String Task, String FILE_NAME){
        //SharedPreferences prefs = getSharedPreferences(FILE_NAME, MODE_PRIVATE);
        String[] Task_Details = Task.split("\n");
        Dialog_Box_Editor(Task_Details[0],Task_Details[1],FILE_NAME);
        SharedPreferences.Editor editor = getSharedPreferences(FILE_NAME, MODE_PRIVATE).edit();
        editor.remove(Task_Details[0]);
        editor.apply();
        Update();
    }

    private void Dialog_Box_Editor(String Task_Name, String Task_Details, String FILE_NAME){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Edit Task");
        //alert.setMessage("Message");

        // Set EditText view to get user input
        final EditText input_name = new EditText(this);
        //final EditText input_details = new EditText(this);
        alert.setView(input_name);
        //alert.setView(input_details);
        input_name.setText(Task_Name);
        //input_details.setText(Task_Details);
        alert.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String FILE_NAME = name + "todo";
                if (input_name.getText().toString().length() != 0) {
                    //if (input_details.getText().toString() != null) {
                        SharedPreferences.Editor editor = getSharedPreferences(FILE_NAME, MODE_PRIVATE).edit();
                        editor.putString(input_name.getText().toString(),null);
                        editor.apply();
                        //Toast.makeText(getApplicationContext(), "New Task Created!", Toast.LENGTH_SHORT).show();
                    //}
                }
            }
        });
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
            }
        });
        alert.show();
    }

    //Adds any changes to listview
    private void Update() {
        //Toast.makeText(getApplicationContext(), "oh my god ", Toast.LENGTH_SHORT).show();
        mArrayAdapter.clear();
        nmArrayAdapter.clear();

        String FILE_NAME = name + "done";
        SharedPreferences prefs = getSharedPreferences(FILE_NAME, MODE_PRIVATE);
        Map<String, ?> keys = prefs.getAll();
        for (Map.Entry<String, ?> entry : keys.entrySet()) {
            //Toast.makeText(getApplicationContext(), "oh my god ", Toast.LENGTH_SHORT).show();
            nmArrayAdapter.add(entry.getKey() + "\n" + entry.getValue().toString());
            //Toast.makeText(getApplicationContext(),entry.getKey(), Toast.LENGTH_SHORT).show();
        }

        //Toast.makeText(getApplicationContext(), "Updating task lists!", Toast.LENGTH_SHORT).show();

        String FILE_NAME_2 = name + "todo";
        SharedPreferences prefs_2 = getSharedPreferences(FILE_NAME_2, MODE_PRIVATE);
        Map<String, ?> keys_2 = prefs_2.getAll();
        for (Map.Entry<String, ?> entry : keys_2.entrySet()) {
            mArrayAdapter.add(entry.getKey() + "\n" + entry.getValue().toString());
            //Toast.makeText(getApplicationContext(),"sab" + entry.getKey(), Toast.LENGTH_SHORT).show();
        }
    }
}
