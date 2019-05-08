package ru.obessonova.module3;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Tasks extends AppCompatActivity {
    private static final String APP_PREFERENCES = "mysettings";
    static SharedPreferences sSettings;
    static FileOutputStream sOutputStream;
    private final int REQUEST_CODE_LIST = 1;
    private final int REQUEST_CODE_SETTINGS = 2;
    private final int REQUEST_CODE_CHANGE = 3;
    private final int REQUEST_CODE_CHANGE_FAVOURITE = 4;
    File mFile;
    private List<Task> mTaskList = new ArrayList<>();
    private List<Task> mFavouriteTaskList = new ArrayList<>();
    private Storage mMyStorage = new InternalStorage();
    private TabLayout mTabs;
    private RecyclerView mRecyclerView;
    private RecyclerView mFavouriteRecyclerView;
    private MyAdapter mMyAdapter;
    private MyAdapter mMyAdapterForFavourite;
    private Task mChangeTask;
    private Task mTask;
    private MyHandler mMyHandler = new MyHandler(this);
    private Message mMsg;
    
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
        mTabs = findViewById(R.id.tabs);
        mRecyclerView = findViewById(R.id.myList);
        mFavouriteRecyclerView = findViewById(R.id.myFavouritelist);
        setAllTasks();
        mTabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        setAllTasks();
                        break;
                    case 1:
                        setFavouriteTasks();
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
    
    private void setAllTasks() {
        mFavouriteRecyclerView.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(Tasks.this);
        mMyAdapter = new MyAdapter(Tasks.this, mTaskList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mMyAdapter);
        mMyAdapter.setListener(new MyAdapter.Listener() {
            
            @Override
            public void onClickChange(int position) {
                mChangeTask = mTaskList.get(position);
                Intent intent = new Intent(Tasks.this, ChangeTask.class);
                intent.putExtra(ChangeTask.EXTRA_KEY_TITLE, mChangeTask.getTitle());
                intent.putExtra(ChangeTask.EXTRA_KEY_DESCRIPT, mChangeTask.getDescript());
                startActivityForResult(intent, REQUEST_CODE_CHANGE);
                mTaskList.remove(position);
                mRecyclerView.setAdapter(mMyAdapter);
            }
            
            @Override
            public void onClickDel(int position) {
                mTaskList.remove(position);
                mRecyclerView.setAdapter(mMyAdapter);
            }
            
            @Override
            public void onClickAdd(int position) {
                mFavouriteTaskList.add(mTaskList.get(position));
                setFavouriteTasks();
                Objects.requireNonNull(mTabs.getTabAt(1)).select();
            }
        });
    }
    
    private void setFavouriteTasks() {
        mFavouriteRecyclerView.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
        RecyclerView.LayoutManager mLayoutManagerForFavour = new LinearLayoutManager(Tasks.this);
        mMyAdapterForFavourite = new MyAdapter(Tasks.this, mFavouriteTaskList);
        mFavouriteRecyclerView.setLayoutManager(mLayoutManagerForFavour);
        mFavouriteRecyclerView.setAdapter(mMyAdapterForFavourite);
        mMyAdapterForFavourite.setListener(new MyAdapter.Listener() {
            @Override
            public void onClickChange(int position) {
                mChangeTask = mFavouriteTaskList.get(position);
                Intent intent = new Intent(Tasks.this, ChangeTask.class);
                intent.putExtra(ChangeTask.EXTRA_KEY_TITLE, mChangeTask.getTitle());
                intent.putExtra(ChangeTask.EXTRA_KEY_DESCRIPT, mChangeTask.getDescript());
                startActivityForResult(intent, REQUEST_CODE_CHANGE_FAVOURITE);
                for (Task el : mTaskList) {
                    if (el.equals(mChangeTask)) {
                        mTaskList.remove(el);
                    }
                }
                mFavouriteTaskList.remove(position);
                mFavouriteRecyclerView.setAdapter(mMyAdapterForFavourite);
            }
            
            @Override
            public void onClickDel(int position) {
                mFavouriteTaskList.remove(position);
                mRecyclerView.setAdapter(mMyAdapter);
            }
            
            @Override
            public void onClickAdd(int position) {
                Toast.makeText(Tasks.this, "Task  is already in favorites", Toast.LENGTH_LONG).show();
            }
        });
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
            if (arguments != null) {
                switch (requestCode) {
                    case REQUEST_CODE_LIST:
                        mTask = (Task) arguments.getSerializable(NewTask.class.getSimpleName());
                        Thread newThread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                mMsg = mMyHandler.obtainMessage(MyHandler.MESSAGE_TYPE_1, mMyStorage);
                                mMsg.sendToTarget();
                            }
                        });
                        newThread.start();
                        mTaskList.add(mTask);
                        break;
                    case REQUEST_CODE_SETTINGS:
                        mMyStorage = (Storage) arguments.getSerializable(Settings.class.getSimpleName());
                        break;
                    case REQUEST_CODE_CHANGE:
                        Task changeTask = (Task) arguments.getSerializable(ChangeTask.class.getSimpleName());
                        mChangeTask.setTitle(changeTask.getTitle());
                        mChangeTask.setDescript(changeTask.getDescript());
                        mTaskList.add(mChangeTask);
                        break;
                    case REQUEST_CODE_CHANGE_FAVOURITE:
                        Task changeTaskFavourite = (Task) arguments.getSerializable(ChangeTask.class.getSimpleName());
                        mChangeTask.setTitle(changeTaskFavourite.getTitle());
                        mChangeTask.setDescript(changeTaskFavourite.getDescript());
                        mTaskList.add(mChangeTask);
                        mFavouriteTaskList.add(mChangeTask);
                        break;
                }
            }
        }
    }
    
    private static class MyHandler extends Handler {
        public static final int MESSAGE_TYPE_1 = 1;
        
        private final WeakReference<Tasks> mWeakActivity;
        
        MyHandler(Tasks activity) {
            mWeakActivity = new WeakReference<>(activity);
        }
        
        @Override
        public void handleMessage(Message msg) {
            Tasks activity = mWeakActivity.get();
            if (activity != null) {
                activity.mTask.setMyStorage((Storage) msg.obj);
            }
        }
    }
}
