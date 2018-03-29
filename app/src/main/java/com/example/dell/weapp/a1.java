package com.example.dell.weapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class a1 extends AppCompatActivity {
    private Button button;
    public static final String EXTRA_TEXT1 ="ab";
    public static final String EXTRA_TEXT2 ="de";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a1);
        button= findViewById(R.id.b2);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                openup1();}
        });


    }
    public void openup1(){
        EditText editText =findViewById(R.id.et) ;
        String t1 =editText.getText().toString();

        EditText editText2=findViewById(R.id.et2);
        String t2 = editText2.getText().toString();

        Intent intent2 = new Intent(this,a2.class);
        intent2.putExtra(EXTRA_TEXT1,t1);
        intent2.putExtra(EXTRA_TEXT2,t2);

        startActivity(intent2);

    }
}
