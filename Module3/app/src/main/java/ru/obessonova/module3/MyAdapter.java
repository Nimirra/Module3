package ru.obessonova.module3;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    
    private List<Task> mTtask;
    private Context mContext;
    private Listener listener;
    
    public MyAdapter(Context context, List<Task> task) {
        mTtask = task;
        mContext = context;
    }
    
    @Override
    public int getItemCount() {
        return mTtask.size();
    }
    
    public void setListener(Listener listener) {
        this.listener = listener;
    }
    
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(
            ViewGroup parent, int viewType) {
        CardView cv = (CardView) LayoutInflater.from(mContext)
                .inflate(R.layout.card_view, parent, false);
        return new ViewHolder(cv);
    }
    
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        CardView cardView = holder.cardView;
        TextView textView = cardView.findViewById(R.id.title_text);
        textView.setText(mTtask.get(position).getTitle());
        TextView textView2 = cardView.findViewById(R.id.description_text);
        textView2.setText(mTtask.get(position).getDescript());
        ImageView imageView = cardView.findViewById(R.id.selectAction);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    PopupMenu popupMenu = new PopupMenu(mContext, view);
                    popupMenu.inflate(R.menu.popupmenu);
                    popupMenu
                            .setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                @Override
                                public boolean onMenuItemClick(MenuItem item) {
                                    switch (item.getItemId()) {
                                        case R.id.change:
                                            listener.onClickChange(position);
                                            return true;
                                        case R.id.delete:
                                            listener.onClickDel(position);
                                            return true;
                                        case R.id.add:
                                            listener.onClickAdd(position);
                                            return true;
                                        default:
                                            return false;
                                    }
                                }
                            });
                    popupMenu.show();
                }
            }
        });
    }
    
    interface Listener {
        void onClickChange(int position);
        
        void onClickDel(int position);
        
        void onClickAdd(int position);
    }
    
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        
        
        public ViewHolder(CardView v) {
            super(v);
            cardView = v;
        }
    }
}
