package com.example.restro.dashboard.views

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.restro.R
import com.example.restro.dashboard.dataModel.Results
import kotlinx.android.synthetic.main.row_item_restaurant_list.view.*
import kotlin.collections.ArrayList

class ScrollAdapter(
    private val list: ArrayList<Results>?
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ChildAdapterViewHolder) {
            holder?.apply {
                list?.let {
                    name.text = it[position].name
                    loc.text = it[position].vicinity
//                    it[position].opening_hours?.open_now?.let { isOpened ->
//                        country?.text = when (isOpened?:false) {
//                            true -> "Closed"
//                            else -> "Open"
//                        }
//                    }
                    restroPrice.text = it[position].business_status
                    val url = it[position].icon
                    Glide.with(holder.itemView.context)
                        .load(url)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .fitCenter()
                        .dontTransform()
                        .crossFade(20)
                        .placeholder(android.R.drawable.stat_notify_error)
                        .into(img)
                    review.text = it[position].rating.toString()
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_item_restaurant_list, parent, false)
        return ChildAdapterViewHolder(
            view
        )
    }

    fun updateAdapter(updatedList: ArrayList<Results>?) {
        updatedList?.let {
            list?.clear()
            list?.addAll(updatedList)
            notifyDataSetChanged()
        }
    }

    class ChildAdapterViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val name = item.tv_restro_name
        val loc = item.tv_restro_loc
        val img = item.iv_restro
        val restroPrice = item.tv_restro_price
        val review = item.tv_rate
    }

}
