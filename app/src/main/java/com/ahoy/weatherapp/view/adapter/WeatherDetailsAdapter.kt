package com.ahoy.weatherapp.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ahoy.weatherapp.data.model.ListItem
import com.ahoy.weatherapp.data.model.WeatherInfo
import com.ahoy.weatherapp.databinding.ListItemWeatherInfoBinding
import com.ahoy.weatherapp.utils.DataUtils.getDataForUI
import javax.inject.Inject

class WeatherDetailsAdapter @Inject constructor() :
    RecyclerView.Adapter<WeatherDetailsAdapter.WeatherItemViewHolder>(){

    private var data = ArrayList<WeatherInfo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemWeatherInfoBinding.inflate(inflater, parent, false)
        return WeatherItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WeatherItemViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun updateResults(listItems: List<ListItem>) {

        data.clear()
        val newList = getDataForUI(listItems).values

        for(items in newList) {
            data.addAll(items)
        }

        notifyDataSetChanged()
    }

    inner class WeatherItemViewHolder(private var binding: ListItemWeatherInfoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(weather: WeatherInfo) {
            binding.time.text = weather.timeToDisplay
            binding.temprature.text = weather.temprature.toString()
            binding.humidity.text = weather.humidity.toString()
        }
    }


    inner class DiffCallback(private val oldList: ArrayList<WeatherInfo>, private val newList: List<WeatherInfo>) : DiffUtil.Callback() {

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