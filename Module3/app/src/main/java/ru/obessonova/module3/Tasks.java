package ru.obessonova.module3;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class Tasks extends AppCompatActivity implements AsincTaskListener{
    private static final String APP_PREFERENCES = "mysettings";
    static SharedPreferences sSettings;
    static FileOutputStream sOutputStream;
    private final int REQUEST_CODE_LIST = 1;
    private final int REQUEST_CODE_SETTINGS = 2;
    private List<Task> mTaskList = new ArrayList<>();
    private List<Task> mFavouriteTaskList = new ArrayList<>();
    File mFile;
    private Storage mMyStorage = new InternalStorage();
    private TabLayout mTabs;
    private RecyclerView mRecyclerView;
    private RecyclerView mFavouriteRecyclerView;
    private RecyclerView.Adapter mMyAdapter;
    private RecyclerView.Adapter mMyAdapterForFavourite;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.LayoutManager mLayoutManagerforFavour;
    private ProgressBar mProgressRound;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(R.string.tasks);
        initUI();
        sSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        mFile = new File(this.getFilesDir(), "FileInternalStorage.txt");
        try {
            sOutputStream = openFileOutput(mFile.getName(), Context.MODE_PRIVATE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settingsMenu:
                Intent intent = new Intent(this, Settings.class);
                startActivityForResult(intent, REQUEST_CODE_SETTINGS);
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    
    private void initUI() {
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        mProgressRound = findViewById(R.id.progressBarRound);
        mTabs = findViewById(R.id.tabs);
        mRecyclerView = findViewById(R.id.myList);
        mFavouriteRecyclerView = findViewById(R.id.myFavouritelist);
        startSetAdapter();
        mTabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        mFavouriteRecyclerView.setVisibility(View.GONE);
                        mRecyclerView.setVisibility(View.VISIBLE);
                        startSetAdapter();
                        break;
                    case 1:
                        mFavouriteRecyclerView.setVisibility(View.VISIBLE);
                        mRecyclerView.setVisibility(View.GONE);
                        mLayoutManagerforFavour = new LinearLayoutManager(Tasks.this);
                        mMyAdapterForFavourite = new MyAdapter(Tasks.this, mFavouriteTaskList);
                        mFavouriteRecyclerView.setLayoutManager(mLayoutManagerforFavour);
                        mFavouriteRecyclerView.setAdapter(mMyAdapterForFavourite);
                        break;
                }
            }
            
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                //do nothing
            }
            
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                //do nothing
            }
        });
    }
    
    private void startSetAdapter() {
        mLayoutManager = new LinearLayoutManager(Tasks.this);
        mMyAdapter = new MyAdapter(Tasks.this, mTaskList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mMyAdapter);
    }
    
    public void clickFAB(View view) {
        Intent intent = new Intent(this, NewTask.class);
        startActivityForResult(intent, REQUEST_CODE_LIST);
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bundle arguments = data.getExtras();
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_LIST:
                    Task task = (Task) arguments.getSerializable(NewTask.class.getSimpleName());
                    task.setMyStorage(mMyStorage);
                    mTaskList.add(task);
                    break;
                case REQUEST_CODE_SETTINGS:
                    mMyStorage = ((Task) arguments.getSerializable(Settings.class.getSimpleName()))
                            .getStorage();
                    break;
            }
        }
    }
    
    @Override
    public void onStartProgress() {
        mProgressRound.setVisibility(View.VISIBLE);
    }
    
    @Override
    public void onFinishProgress() {
        mProgressRound.setVisibility(View.GONE);
    }
    
    private static class MyAsyncTask extends AsyncTask<Task, Integer, Void> {
        private final WeakReference<AsincTaskListener> mWeakListener;
        
        MyAsyncTask(AsincTaskListener listener) {
            mWeakListener = new WeakReference<>(listener);
        }
        
        @Override
        protected void onPreExecute() {
            AsincTaskListener listener = mWeakListener.get();
            if (listener != null) listener.onStartProgress();
        }
        
        @Override
        protected Void doInBackground(Task... task) {
            
            
            
            
            
            return null;
        }
        
        
        @Override
        protected void onPostExecute(Void aVoid) {
            AsincTaskListener listener = mWeakListener.get();
            if (listener != null) listener.onFinishProgress();
        }
    }
}
