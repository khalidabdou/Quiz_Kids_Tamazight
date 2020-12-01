package com.example.wallsticker.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.wallsticker.Interfaces.ImageClickListener
import com.example.wallsticker.Model.category
import com.example.wallsticker.R
import com.example.wallsticker.Utilities.Const
import kotlinx.android.synthetic.main.item_category.view.*

class CategoryAdapter(
    private val Categories: List<category>,
    private val onCatClickListener: ImageClickListener
) : RecyclerView.Adapter<CategoryAdapter.MyViewHolder>() {


    class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view)


    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoryAdapter.MyViewHolder {
        // create a new view
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_category, parent, false) as View


        return MyViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.view.textView.text = Categories[position].name
        Glide.with(holder.view.context)
            .load(Const.directoryUploadCat + Categories[position].image)
            .into(holder.view.img_cat)
        holder.view.setOnClickListener {
            onCatClickListener.onCatClicked(holder.view, Categories[position], position)
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = Categories.size

}