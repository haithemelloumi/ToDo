package com.training.todo_list.activities.todo_list;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TimePicker;

import com.training.todo_list.R;
import com.training.todo_list.adapter.TodoAdapter;
import com.training.todo_list.model.managers.TodoManager;
import com.training.todo_list.model.managers.TodoTypeManager;
import com.training.todo_list.model.models.Todo;
import com.training.todo_list.model.models.TodoType;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class ActivityAddModifyTodo extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    int  mIndiceTodo =-1;
    Spinner mSpinner;
    EditText mDescription;
    Switch mBIsDone;
    List<Todo> mTodoList ;
    List<TodoType> mTodoTypeList;
    TimePicker mTimeCreation;
    DatePicker mDateCreation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_modify_todo);

        // Spinner element
        mSpinner = (Spinner) findViewById(R.id.spinner);
        mDescription = (EditText) findViewById(R.id.description);
        mBIsDone = (Switch) findViewById(R.id.bIsDone);
        mTimeCreation = (TimePicker) findViewById(R.id.timeCreation);
        mDateCreation = (DatePicker) findViewById(R.id.dateCreation);

        loadSpinner();

        Bundle tBundle = getIntent().getExtras();
        if (tBundle != null) {
            mIndiceTodo = tBundle.getInt(TodoAdapter.cleIndiceTodo);
        }

        TodoManager tTodoManager = new TodoManager();
        mTodoList= tTodoManager.all();

        if (mIndiceTodo!=-1){
            initValues();
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    public void loadSpinner (){

        // Spinner Drop down elements
        TodoTypeManager tTodoTypeManager = new TodoTypeManager();
        mTodoTypeList = tTodoTypeManager.all();
        List<String> tTodoTypeListName = new ArrayList<String>();
        for (int i = 0; i<mTodoTypeList.size();i++)
            tTodoTypeListName.add(mTodoTypeList.get(i).name());
        // Spinner click listener
        mSpinner.setOnItemSelectedListener(this);

        // Creating adapter for spinner
        ArrayAdapter<String> tDataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tTodoTypeListName);
        // Drop down layout style - list view with radio button
        tDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        mSpinner.setAdapter(tDataAdapter);
    }


    public void initValues(){
        mSpinner.setSelection(mIndiceTodo);
        mDescription.setText(mTodoList.get(mIndiceTodo).description());
        mBIsDone.setChecked(mTodoList.get(mIndiceTodo).isDone());

        int tYear=mTodoList.get(mIndiceTodo).timeCreation().getYear();
        int tMonth=mTodoList.get(mIndiceTodo).timeCreation().getMonth();
        int tDay=mTodoList.get(mIndiceTodo).timeCreation().getDate();
        int tHour=mTodoList.get(mIndiceTodo).timeCreation().getHours();
        int tMin=mTodoList.get(mIndiceTodo).timeCreation().getMinutes();

        mDateCreation.updateDate(tYear+1900, tMonth, tDay);
        mTimeCreation.setCurrentHour(tHour);
        mTimeCreation.setCurrentMinute(tMin);
    }


    public void saveToDo(View pView) {

        Date tTimeCreation;
        tTimeCreation = new Date();
        tTimeCreation.setDate ( mDateCreation.getDayOfMonth());
        tTimeCreation.setMonth ( mDateCreation.getMonth());
        tTimeCreation.setYear ( mDateCreation.getYear());
        tTimeCreation.setHours ( mTimeCreation.getCurrentHour());
        tTimeCreation.setMinutes ( mTimeCreation.getCurrentMinute());

        UUID tIdTodoType =  mTodoTypeList.get(mSpinner.getSelectedItemPosition()).id();

        UUID tId = null;
        if (mIndiceTodo!=-1){
            tId= mTodoList.get(mIndiceTodo).id();
        }

        TodoManager tTodoManager = new TodoManager();

        if (mIndiceTodo==-1){
            tTodoManager.create(mDescription.getText().toString(),
                    tTimeCreation,tIdTodoType, mBIsDone.isChecked());
        }else{
            Todo tTodo = new Todo(mDescription.getText().toString(),
                    tTimeCreation,tIdTodoType, mBIsDone.isChecked(), tId);
            tTodoManager.save(tTodo);
            System.out.println("addd");
        }

        returnToList();

    }



    public void returnToList(){
        /////////////
        Intent tIntent = new Intent(this, ActivityTodoList.class);
        startActivity(tIntent);
        finish();
        /////////////
    }


    @Override
    public void onBackPressed() {
        returnToList();
    }


}
