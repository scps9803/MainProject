package com.example.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class AddRecipeResultActivity extends AppCompatActivity implements View.OnClickListener {

    LinearLayout
            ll_in_sv,ll ;

    View
            buttonView;


    int n,m;
    String showfood="",showmethod="",Name;
    String[] Food=new String[30];
    String[] Number=new String[30];
    String[] Method=new String[10];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe_result);
        ll_in_sv = (LinearLayout)findViewById(R.id.ll_in_sv);


        Bundle bundle =this.getIntent().getExtras();
        Food = bundle.getStringArray("Food");
        n=bundle.getInt("n");
        m=bundle.getInt("m");
        Number = bundle.getStringArray("Number");
        Method = bundle.getStringArray("Method");
        Name = bundle.getString("Name");



        for(int i=0;i<n;i++)
            showfood=showfood+Food[i]+Number[i]+"  ";

        for(int i=0;i<m;i++)
            showmethod=showmethod+"Step"+(i+1)+":"+Method[i]+"\n";



        TextView tv1 = (TextView)findViewById(R.id.ett6);
        TextView tv2 = (TextView)findViewById(R.id.ett7);
        TextView tv3 = (TextView)findViewById(R.id.ett8);
        Button btnUpload = findViewById(R.id.btnUpload);
        btnUpload.setOnClickListener(this);

        tv1.setText("菜名: "+Name);
        tv2.setText("食材和個數: "+showfood);
        tv3.setText("作法: "+showmethod);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnUpload:
                Toast.makeText(AddRecipeResultActivity.this,"新增成功!",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AddRecipeResultActivity.this,MainActivity.class);
                startActivity(intent);
                break;
        }
    }
}
