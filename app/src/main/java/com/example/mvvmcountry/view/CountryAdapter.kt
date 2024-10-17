package com.example.mvvmcountry.view

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextClock
import android.widget.TextView
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmcountry.viewmodel.MainViewModel
import javax.inject.Inject

import androidx.lifecycle.LifecycleOwner
import com.bumptech.glide.Glide
import com.example.mvvmcountry.R
import com.example.mvvmcountry.databinding.ActivityMainBinding
import com.example.mvvmcountry.databinding.ListItemBinding
import com.example.mvvmcountry.model.CountryData


    class CountryAdapter(private val itemList: List<String>) : RecyclerView.Adapter<CountryAdapter.ViewHolder>() {

        class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val textView: TextView = view.findViewById(R.id.CountryName)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = itemList[position]
            holder.textView.text = item
        }

        override fun getItemCount() = itemList.size
    }
