package ms.imagine.foodiemate.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import ms.imagine.foodiemate.R
import ms.imagine.foodiemate.data.Eggs


/**
 * Created by eugen on 3/30/2018.
 */
class ResViewAdapter(private val myDataset: ArrayList<Eggs>, private val context: Context) :
        RecyclerView.Adapter<ResViewAdapter.ViewHolder>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById<View>(R.id.egg_tag) as TextView
        val imgView: ImageView = view.findViewById<View>(R.id.imgview_thumbnail) as ImageView
        val timestamp: TextView = view.findViewById<View>(R.id.egg_timestamp) as TextView
        val status: TextView = view.findViewById<View>(R.id.egg_info) as TextView
        val entity = view.findViewById<View>(R.id.card_view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val egg = myDataset[position]
        holder.title.text = egg.eggtag
        holder.timestamp.text = egg.displayTime()
        holder.status.text = egg.displayStatus()
        holder.imgView.setImageDrawable(egg.displayStatusThumbnail(context))
        holder.entity.setOnClickListener { onClick.onItemClick(position) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.card, parent, false) as View
        return ViewHolder(itemView)
    }

    override fun getItemCount() = myDataset.size
    private lateinit var onClick: OnItemClicked
    fun setOnClick(onClick: OnItemClicked) {
        this.onClick = onClick
    }
    interface OnItemClicked {
        fun onItemClick(position: Int)
    }
}