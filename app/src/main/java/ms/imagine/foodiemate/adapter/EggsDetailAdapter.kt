package ms.imagine.foodiemate.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import ms.imagine.foodiemate.Presenter.FbStorageRead
import ms.imagine.foodiemate.R
import ms.imagine.foodiemate.data.Eggs


/**
 * Created by eugen on 3/30/2018.
 */
class EggsDetailAdapter(private val myDataset: Eggs, private val context: Context) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val fbStorageRead =  FbStorageRead()

    inner class VH_HEAD(view: View) : RecyclerView.ViewHolder(view) {
        val imgView: ImageView = view.findViewById<View>(R.id.imgview_thumbnail) as ImageView
        val status: TextView = view.findViewById<View>(R.id.egg_info) as TextView
    }


    inner class VH_BODY(view: View) : RecyclerView.ViewHolder(view) {
        val status: TextView = view.findViewById<View>(R.id.status) as TextView
        val imgView: ImageView = view.findViewById<View>(R.id.imgView) as ImageView
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val egg = myDataset
        if(holder is VH_HEAD){
            holder.status.text = egg.displayStatus()
            holder.imgView.setImageDrawable(egg.displayStatusThumbnail(context))
        }


        if (holder is VH_BODY){
            if (egg.isLegacyEgg()){
                holder.status.text = egg.displayStatus()
                if (egg.isnewEgg) {
                    holder.imgView.setImageURI(egg.localImgUri)
                } else {
                    fbStorageRead.downloadImage(context, egg.remoteImgURL, holder.imgView)
                }
            } else {
                holder.status.text = egg.egg.get(position-1).status.toString()
                holder.imgView.setImageURI(egg.localImgUri)
                fbStorageRead.downloadImage(context, egg.egg.get(position-1).remoteImgURL, holder.imgView)
            }

        }
        //holder.entity.setOnClickListener { onClick.onItemClick(position) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == 0) {
            VH_HEAD(LayoutInflater.from(parent.context)
                    .inflate(R.layout.card_detailhead, parent, false) as View)
        } else {
            VH_BODY(LayoutInflater.from(parent.context)
                    .inflate(R.layout.card_detailbody, parent, false) as View)
        }
    }

    override fun getItemCount(): Int {
        return if(myDataset.isLegacyEgg()){
            2
        } else {
            myDataset.egg.size + 1
        }
    }




    private lateinit var onClick: OnItemClicked


    interface OnItemClicked {
        fun onItemClick(position: Int)
    }

    override fun getItemViewType(position: Int): Int {
        return if(position ==0) {
            0
        } else {
            1
        }
    }

    companion object {
        val HEADER = 0
        val BODY = 1
    }
}