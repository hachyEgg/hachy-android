package ms.imagine.foodiemate.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import ms.imagine.foodiemate.R;
import ms.imagine.foodiemate.data.Eggs;

import java.util.ArrayList;


/**
 * Created by eugen on 3/30/2018.
 */
public class ResViewAdapter extends RecyclerView.Adapter<ResViewAdapter.ViewHolder> {
    ArrayList<Eggs> myDataset;
    Context context;

    public ResViewAdapter(ArrayList<Eggs> myDataset, Context context){
        this.myDataset = myDataset;
        this.context = context;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView imgView;
        TextView timestamp;
        TextView status;
        CardView entity;

        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.egg_tag);
            imgView = itemView.findViewById(R.id.imgview_thumbnail);
            timestamp = itemView.findViewById(R.id.egg_timestamp);
            status = itemView.findViewById(R.id.egg_info);
            entity = itemView.findViewById(R.id.card_view);
        }
    }

    @Override public void onBindViewHolder(ViewHolder holder, int position) {
        Eggs egg = myDataset.get(position);
        holder.title.setText(egg.getEggtag());
        holder.timestamp.setText(egg.displayTime());
        holder.status.setText(egg.displayStatus());
        holder.imgView.setImageDrawable(egg.displayStatusThumbnail(context));
        holder.entity.setOnClickListener ((it)->onClick.onItemClick(position));
    }

    // onClick.onItemClick(position)

    @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card, parent, false);
        return new ViewHolder(itemView);
    }

    @Override public int getItemCount() {
        return  myDataset.size();
    }

    private OnItemClicked onClick;

    public void setOnClick(OnItemClicked onClick) {
        this.onClick = onClick;
    }

    public interface OnItemClicked {
        void onItemClick(int position);
    }
}