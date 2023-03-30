package com.example.acronymsmeaningsapp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.acronymsmeaningsapp.R
import com.example.acronymsmeaningsapp.model.LfsItem

class AcronymResultsAdapter(private val dataSet: List<LfsItem>) :
    RecyclerView.Adapter<AcronymResultsAdapter.ViewHolder>()  {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvAbvMeaning: TextView
        val tvFreq: TextView
        val tvSinceYear: TextView

        init {
            // Define click listener for the ViewHolder's View
            tvAbvMeaning = view.findViewById(R.id.tv_abv_meaning)
            tvFreq = view.findViewById(R.id.tv_freq)
            tvSinceYear = view.findViewById(R.id.tv_since_year)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_acronym, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvAbvMeaning.text = "Abbreviation: ".plus(dataSet[position].lf)
        holder.tvFreq.text = "Number of occurrences: ".plus(dataSet[position].freq.toString())
        holder.tvSinceYear.text = "Since: ".plus(dataSet[position].since.toString())
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }
}