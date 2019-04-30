package ru.obessonova.module3;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    
    private List<Task> mTtask;
    private Context mContext;
    
    
    public MyAdapter(Context context, List<Task> task) {
        mTtask = task;
        mContext = context;
    }
    
    @Override
    public int getItemCount() {
        return mTtask.size();
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
       /* ImageView imageView= cardView.findViewById(R.id.selectAction);
        
        AlertDialog.Builder ad;
        final int ID_LIST_SELECT = 1;
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(ID_LIST_SELECT);
            }
    
                @Override
                protected Dialog onCreateDialog(int id) {
                    switch (id) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                        case ID_LIST_SELECT:
                
                            final String[] mSelectItems ={"Change", "Delete", "Add to favourite"};
                
                            builder = new AlertDialog.Builder(mContext);
                            builder.setTitle("Select action");
                
                            builder.setItems(mSelectItems, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int item) {
                                    switch (item){
                                        case 0:
                                            
                                            break;
                                        case 1:
                                            
                                            break;
                                        case 2:
                                            
                                            break;
                                    }
                                }
                            });
                            builder.setCancelable(false);
                            return builder.create();
            
                        default:
                            return null;
                    }
    }*/
    }
    
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        
        
        public ViewHolder(CardView v) {
            super(v);
            cardView = v;
        }
    }
}
