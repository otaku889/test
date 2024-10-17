package com.example.mvvmcountry.view

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mvvmcountry.R

class CountryAdapter(
    private val commonName: List<String>,
    private val officialList: List<String>,
    private val flagURL: List<String>
) : RecyclerView.Adapter<CountryAdapter.ViewHolder>() {

    private var expandedPosition = -1
   inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val countryName: TextView = view.findViewById(R.id.CountryName)
        val countryOfficial: TextView = view.findViewById(R.id.countryOfficial)
        val countryFlag: ImageView = view.findViewById(R.id.image)

        @SuppressLint("NotifyDataSetChanged")
        fun bind(item: String, isExpanded: Boolean) {

            //Click and check if expanded
            itemView.setOnClickListener {
                expandedPosition = if (isExpanded) -1 else adapterPosition
                notifyDataSetChanged()
            }

            //Item List Container Expanded
            val params = itemView.layoutParams
            val initialHeight = params.height
            val targetHeight = if (isExpanded) 1100 else 220
            val valueAnimatorExpansion = ValueAnimator.ofInt(initialHeight, targetHeight)
            valueAnimatorExpansion.addUpdateListener { animation ->
                params.height = animation.animatedValue as Int
                itemView.layoutParams = params
            }
            valueAnimatorExpansion.duration = 200
            valueAnimatorExpansion.start()


            //Expanded
            if(isExpanded) countryFlag.visibility=View.GONE else countryFlag.visibility=View.VISIBLE

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val isExpanded = position == expandedPosition
        //Setter of data for each item in item_list.xml
        holder.bind(officialList[0], isExpanded)
        holder.countryName.text = commonName[position]
        holder.countryOfficial.text = officialList[position]
        Glide.with(holder.itemView.context)
            .load(flagURL[position])
            .into(holder.countryFlag)

    }

    override fun getItemCount() = commonName.size
}
