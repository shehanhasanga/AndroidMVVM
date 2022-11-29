package com.shehan.apprichitecture.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.shehan.apprichitecture.databinding.LocationItemBinding
import com.shehan.apprichitecture.databinding.RecipesRowLayoutBinding
import com.shehan.apprichitecture.models.FoodRecipe
import com.shehan.apprichitecture.models.Result
import com.shehan.apprichitecture.models.location.LocationResponse
import com.shehan.apprichitecture.util.RecipesDiffUtil

class LocationAdapter  : RecyclerView.Adapter<LocationAdapter.MyViewHolder>()  {
    private var locations = emptyList<LocationResponse>()
    class MyViewHolder(private val binding: LocationItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(result: LocationResponse){
            binding.locationName.text = result.LocationName
        }

        companion object {
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = LocationItemBinding.inflate(layoutInflater, parent, false)
                return MyViewHolder(binding)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentRecipe = locations[position]
        holder.bind(currentRecipe)
    }

    override fun getItemCount(): Int {
        return locations.size
    }

    fun setData(newData: List<LocationResponse>){
        val recipesDiffUtil =
            RecipesDiffUtil(locations, newData)
//        val diffUtilResult = DiffUtil.calculateDiff(recipesDiffUtil)
        locations = newData
//        diffUtilResult.dispatchUpdatesTo(this)
        notifyDataSetChanged()
    }
}