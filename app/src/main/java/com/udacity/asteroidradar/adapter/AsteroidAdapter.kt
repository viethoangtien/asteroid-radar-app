package com.udacity.asteroidradar.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.databinding.ItemAsteroidBinding
import com.udacity.asteroidradar.model.Asteroid

class AsteroidAdapter(private val onItemClickListener: ((Asteroid) -> Unit)? = null) :
    ListAdapter<Asteroid, ViewHolder>(AsteroidDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, onItemClickListener)
    }
}

class ViewHolder(private val binding: ItemAsteroidBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Asteroid, onItemClickListener: ((Asteroid) -> Unit)? = null) {
        binding.apply {
            this.item = item
            executePendingBindings()
            root.setOnClickListener {
                onItemClickListener?.invoke(item)
            }
        }
    }

    companion object {
        fun from(parent: ViewGroup): ViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemAsteroidBinding.inflate(layoutInflater, parent, false)
            return ViewHolder(binding)
        }
    }
}
