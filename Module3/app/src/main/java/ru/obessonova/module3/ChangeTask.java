package ru.obessonova.module3;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

public class ChangeTask extends AppCompatActivity {
    public static final String EXTRA_KEY_TITLE = "ru.obessonova.module3.extra_key_title";
    public static final String EXTRA_KEY_DESCRIPT = "ru.obessonova.module3.extra_key_descript";
    private EditText mTitle;
    private EditText mDescript;
    private Intent mIntent;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        
        mTitle = findViewById(R.id.title);
        mDescript = findViewById(R.id.descript);
        
        mIntent = new Intent();
        //не смогла разобраться, почему не вставляется текст
        mTitle.setText(mIntent.getStringExtra(EXTRA_KEY_TITLE));
        mDescript.setText(mIntent.getStringExtra(EXTRA_KEY_DESCRIPT));
        
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_for_save, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.saveMenu:
                Task newTask = new Task();
                newTask.setTitle(mTitle.getText().toString());
                newTask.setDescript(mDescript.getText().toString());
                mIntent.putExtra(ChangeTask.class.getSimpleName(), newTask);
                setResult(RESULT_OK, mIntent);
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
