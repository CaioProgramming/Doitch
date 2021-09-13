package com.ilustris.doitch.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ilustris.doitch.R
import com.ilustris.doitch.base.models.Item
import com.ilustris.doitch.databinding.ListItemLayoutBinding

class ItemAdapter(val items: List<Item>): RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(private val itemListItemBinding: ListItemLayoutBinding): RecyclerView.ViewHolder(itemListItemBinding.root) {

        fun bind() {
            items[adapterPosition].run {
                itemListItemBinding.item = this
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
       return ItemViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.list_item_layout, parent, false ))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int = items.size

}