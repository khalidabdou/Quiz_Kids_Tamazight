package com.example.wallsticker.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.wallsticker.Interfaces.ImageClickListener
import com.example.wallsticker.Model.image
import com.example.wallsticker.R
import com.example.wallsticker.Utilities.Const
import kotlinx.android.synthetic.main.item_image.view.*

class ImagesAdapter(
    private val imageClickListerner: ImageClickListener,
    private val Images: List<image>,
    private val context: Context?
) : RecyclerView.Adapter<ImagesAdapter.MyViewHolder>() {


    class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view)


    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ImagesAdapter.MyViewHolder {
        // create a new view
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_image, parent, false) as View


        return MyViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.view.animation =
            AnimationUtils.loadAnimation(
                context,
                R.anim.holder_slide
            )
        Glide.with(holder.view.context)
            .load(Const.directoryUpload + Images[position].image_upload)
            .centerCrop()
            .placeholder(R.drawable.placeholder)
            .into(holder.view.id_image)

        if (Images[position].isfav == 1)
            holder.view.item_img_bottom.visibility = View.GONE
        holder.view.count_views.text = Images[position].view_count.toString()
        holder.view.id_image.setOnClickListener {
            imageClickListerner.onImageClicked(holder.view, Images[position], position)
        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = Images.size

}