package com.ahoy.weatherapp.view.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ahoy.weatherapp.data.local.database.entity.FavCitiesEntity
import com.ahoy.weatherapp.databinding.ListItemFavoriteBinding
import javax.inject.Inject

class FavoriteLocationsAdapter @Inject constructor() :
    RecyclerView.Adapter<FavoriteLocationsAdapter.FavoriteViewHolder>(){

    private var data = ArrayList<FavCitiesEntity>()
    var onItemClick: ((item: FavCitiesEntity, view: View) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemFavoriteBinding.inflate(inflater, parent, false)
        return FavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun updateResults(newList: List<FavCitiesEntity>) {
        Log.d("updateResults ", newList.size.toString())
        data.clear()
        data.addAll(newList)
        notifyDataSetChanged()
    }

    inner class FavoriteViewHolder(private var binding: ListItemFavoriteBinding) :
        RecyclerView.ViewHolder(binding.root){

        fun bind(favPlace: FavCitiesEntity) {
            binding.cityName.text = favPlace.cityName

            // send the click event to the listener
            binding.root.setOnClickListener{
                onItemClick?.invoke(data[adapterPosition], itemView)
            }
        }
    }

    inner class DiffCallback(private val oldList: ArrayList<FavCitiesEntity>, private val newList: List<FavCitiesEntity>) : DiffUtil.Callback() {

        override fun getOldListSize() : Int = oldList.size

        override fun getNewListSize() : Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return true
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {

            val oldVersion = if (oldItemPosition < oldList.size) oldList[oldItemPosition] else null
            val newVersion = if (newItemPosition < newList.size) newList[newItemPosition] else null

            return oldVersion == newVersion
        }
    }
}