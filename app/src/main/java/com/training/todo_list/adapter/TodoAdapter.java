package com.training.todo_list.adapter;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import com.training.todo_list.R;
import com.training.todo_list.activities.todo_list.ActivityAddModifyTodo;
import com.training.todo_list.model.managers.TodoTypeManager;
import com.training.todo_list.model.models.Todo;
import com.training.todo_list.model.models.TodoType;

import java.util.List;
import java.util.UUID;


public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.MyViewHolder> {

    private List<Todo> mTodoList;
    private Context mContext;
    public static String cleIndiceTodo= "indiceTodo";


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView mSDescription, mTimeCreation, mTodoType;
        public Switch mBIsDone;

        public MyViewHolder(View pView) {
            super(pView);
            mSDescription = (TextView) pView.findViewById(R.id.description);
            mTimeCreation = (TextView) pView.findViewById(R.id.timeCreation);
            mTodoType = (TextView) pView.findViewById(R.id.todoType);
            mBIsDone = (Switch) pView.findViewById(R.id.bIsDone);
            mBIsDone.setEnabled(false);
        }
    }

    public TodoAdapter(List<Todo> pTodoList, Context pContext) {
        this.mTodoList = pTodoList;
        this.mContext = pContext;

    }

    public MyViewHolder onCreateViewHolder(ViewGroup pParent, int pViewType) {
        View tItemView = LayoutInflater.from(pParent.getContext()).inflate(R.layout.todo_list_row, pParent, false);
        return new MyViewHolder(tItemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        final Todo tTodo = mTodoList.get(position);
        holder.mTimeCreation.setText(tTodo.timeCreation().toString());
        holder.mSDescription.setText(tTodo.description());

        final UUID tIdTodoType =  tTodo.idTodoType();
        TodoTypeManager tTodoTypeManager = new TodoTypeManager();
        TodoType tType = tTodoTypeManager.todoTypeFor(tIdTodoType);
        holder.mTodoType.setText(tType.name());
        holder.mTodoType.setBackgroundColor(Color.parseColor(tType.color()));

        holder.mBIsDone.setChecked(tTodo.isDone());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ActivityAddModifyTodo.class);
                Bundle bundle = new Bundle();
                bundle.putInt(cleIndiceTodo, position);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
                ((Activity) mContext).finish();
            }
        });
    }

    public int getItemCount() {
        return mTodoList.size();
    }
}

