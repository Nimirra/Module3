package ru.obessonova.module3;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.RadioGroup;

public class Settings extends AppCompatActivity {
    private Storage mStorage;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        
        RadioGroup mRadioGroup = findViewById(R.id.radioGroup);
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                Intent intent = new Intent();
                Task settingsTask = new Task();
                
                switch (i) {
                    case R.id.shPref:
                        mStorage = new MySharedPreferences();
                        break;
                    case R.id.intStor:
                        mStorage = new InternalStorage();
                        break;
                    case R.id.extStor:
                        mStorage = new ExternalStorage();
                        break;
                    case R.id.dataBase:
                        mStorage = new Database();
                        break;
                }
                settingsTask.setMyStorage(mStorage);
                intent.putExtra(Settings.class.getSimpleName(), settingsTask);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}
