package ru.obessonova.module3;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

public class NewTask extends AppCompatActivity {
    private EditText mTitle;
    private EditText mDescript;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        
        mTitle = findViewById(R.id.title);
        mDescript = findViewById(R.id.descript);
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
                Intent intent = new Intent();
                Task newTask = new Task();
                newTask.setTitle(mTitle.getText().toString());
                newTask.setDescript(mDescript.getText().toString());
                intent.putExtra(NewTask.class.getSimpleName(), newTask);
                setResult(RESULT_OK, intent);
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
