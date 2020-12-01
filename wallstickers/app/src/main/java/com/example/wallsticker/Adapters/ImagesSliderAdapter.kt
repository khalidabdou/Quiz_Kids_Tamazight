package com.example.wallsticker.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.example.wallsticker.Model.image
import com.example.wallsticker.R
import com.example.wallsticker.Utilities.Const
import kotlinx.android.synthetic.main.item_img_slider.view.*

class ImagesSliderAdapter(private val context: Context) : PagerAdapter() {

    var arrayOf = Const.arrayOf
    private var inflater: LayoutInflater? = null
    var images: ArrayList<image> =
        if (arrayOf == "latest") Const.ImagesTemp
        else if (arrayOf == "byCat") Const.ImagesByCatTemp
        else Const.ImageTempFav

    override fun isViewFromObject(view: View, `object`: Any): Boolean {

        return view === `object`
    }

    override fun getCount(): Int {

        return images.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater!!.inflate(R.layout.item_img_slider, null)

        //view.imgSliders.text=images[position]
        view.tag = "V$position"
        Glide.with(view.context)
            .load(Const.directoryUpload + images[position].image_upload)
            .placeholder(R.drawable.placeholder)
            .into(view.imgSliders)


        val vp = container as ViewPager
        vp.addView(view, 0)
        return view
    }


    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {

        val vp = container as ViewPager
        val view = `object` as View
        vp.removeView(view)
    }


}