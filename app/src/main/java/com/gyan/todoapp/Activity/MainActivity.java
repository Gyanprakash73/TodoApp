package com.gyan.todoapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.gyan.todoapp.Adapter.CustomAdapter;
import com.gyan.todoapp.Database.SqLiteHelper;
import com.gyan.todoapp.Model.User;
import com.gyan.todoapp.databinding.ActivityMainBinding;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    SqLiteHelper sqLiteHelper;
    User user;
    List<User> dataList;
    CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        user=new User();
        sqLiteHelper=new SqLiteHelper(this);

        //set recyclerView
        binding.recycle.setHasFixedSize(true);
        binding.recycle.setLayoutManager(new LinearLayoutManager(this));
        viewData();

        //Add Data into Database
        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data=binding.editText.getText().toString();
                if (data.isEmpty()){
                    binding.editText.setError("Please Enter Data");
                }else {
                    user.setData(data);
                    sqLiteHelper.addData(user);
                    Toast.makeText(MainActivity.this, "Data Inserted", Toast.LENGTH_SHORT).show();
                    binding.editText.setText("");
                    viewData();
                }
            }
        });

    }

    //view data into recyclerView
    private void viewData() {
        dataList=sqLiteHelper.getData();
        adapter=new CustomAdapter(dataList,MainActivity.this);
        binding.recycle.setAdapter(adapter);
    }

}