package tk.tk_test_androidapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MainActivity extends Activity implements View.OnClickListener{

    Button asyncTask_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button asyncTask_btn = (Button)findViewById(R.id.ayncTask_btn);
        asyncTask_btn.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ayncTask_btn:
                Intent i = new Intent(this,AsyncTaskActivity.class);
                startActivity(i);
                break;
        }
    }
}
