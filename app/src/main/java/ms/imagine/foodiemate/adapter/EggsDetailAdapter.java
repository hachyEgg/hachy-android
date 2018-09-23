package ms.imagine.foodiemate.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import ms.imagine.foodiemate.R;
import ms.imagine.foodiemate.data.Egg;
import ms.imagine.foodiemate.data.Eggs;
import ms.imagine.foodiemate.presenters.FbStorageRead;


/**
 * Created by eugen on 3/30/2018.
 */
public class EggsDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Eggs myDataset;
    private Context context;
    private FbStorageRead fbStorageRead;
    private OnItemClicked onClick;

    public EggsDetailAdapter(Eggs myDataset, Context context) {
        this.myDataset = myDataset;
        this.context = context;
        this.fbStorageRead = new FbStorageRead();
    }


    static class VH_HEAD extends RecyclerView.ViewHolder {
        ImageView imgView;
        TextView status;

        VH_HEAD(View itemView) {
            super(itemView);
            imgView = itemView.findViewById(R.id.imgview_thumbnail);
            status = itemView.findViewById(R.id.egg_info);
        }
    }


    static class VH_BODY extends RecyclerView.ViewHolder {
        TextView status;
        ImageView imgView;

        VH_BODY(View view) {
            super(view);
            status = view.findViewById(R.id.status);
            imgView = view.findViewById(R.id.imgView);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            return new VH_HEAD(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.card_detailhead, parent, false));
        } else {
            return new VH_BODY(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.card_detailbody, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder raw_holder, int position) {
        Eggs egg = myDataset;
        if (raw_holder instanceof VH_HEAD) {
            VH_HEAD holder = (VH_HEAD) raw_holder;
            holder.status.setText(egg.status());
            holder.imgView.setImageResource(egg.getThumbnail());
        }
        if (raw_holder instanceof VH_BODY) {
            VH_BODY holder = (VH_BODY) raw_holder;
            if (egg.isLegacyEgg()) {
                holder.status.setText(egg.status());
                if (egg.isIsnewEgg()) {
                    holder.imgView.setImageURI(egg.getLocalImgUri());
                } else {
                    fbStorageRead.downloadImage(context, egg.getRemoteImgURL(), holder.imgView);
                }
            } else {
                Egg place = (egg.getEgg().get(position - 1));
                holder.status.setText(place.getStatus());
                holder.imgView.setImageURI(egg.getLocalImgUri());
                fbStorageRead.downloadImage(context, egg.getEgg().get(position - 1).getRemoteImgURL(), holder.imgView);
            }

        }
    }


    @Override
    public int getItemCount() {
        if (myDataset.isLegacyEgg()) {
            return 2;
        } else {
            return myDataset.getEgg().size() + 1;
        }
    }


    interface OnItemClicked {
        void onItemClick(int position);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return 0;
        } else {
            return 1;
        }
    }
}