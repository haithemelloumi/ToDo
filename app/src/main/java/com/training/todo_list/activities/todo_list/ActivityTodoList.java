package com.training.todo_list.activities.todo_list;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.training.todo_list.R;
import com.training.todo_list.adapter.TodoAdapter;
import com.training.todo_list.animation.DividerItemDecoration;
import com.training.todo_list.model.managers.TodoManager;
import com.training.todo_list.model.models.Todo;

import java.util.List;

public class ActivityTodoList extends AppCompatActivity {

    RecyclerView mRecyclerViewListTodo;
    List<Todo> mTodoList ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lt_act_todo_list);

        TodoManager tTodoManager = new TodoManager();
        mTodoList= tTodoManager.all();

        mRecyclerViewListTodo = (RecyclerView) findViewById(R.id.list);
        mRecyclerViewListTodo.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        mRecyclerViewListTodo.setItemAnimator(new DefaultItemAnimator());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerViewListTodo.setLayoutManager(mLayoutManager);

        TodoAdapter tTodoAdapter = new TodoAdapter(mTodoList, this);
        mRecyclerViewListTodo.setAdapter(tTodoAdapter);

    }


    public void askAddTodo(View pView) {
        Toast.makeText(this, "Ask add todo", Toast.LENGTH_SHORT).show();
        Intent tIntent = new Intent(this, ActivityAddModifyTodo.class);
        startActivity(tIntent);
        finish();
    }


    public void askSurprise(View pView) {
        AlertDialog.Builder tBuilder = new AlertDialog.Builder(this);
        tBuilder.setTitle(R.string.dialog_title_surprise);
        tBuilder.setMessage(R.string.dialog_message_surprise);
        tBuilder.setPositiveButton(R.string.confirm, null);
        tBuilder.show();
    }
}
