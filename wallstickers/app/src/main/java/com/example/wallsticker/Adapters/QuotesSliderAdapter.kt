package com.example.wallsticker.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.wallsticker.Model.quote
import com.example.wallsticker.R
import kotlinx.android.synthetic.main.item_quote_slider.view.*

class QuotesSliderAdapter(private val context: Context,private var quotes: ArrayList<Any>) : PagerAdapter() {


    init {

    }

    private var inflater: LayoutInflater? = null

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun getCount(): Int {

        return quotes.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {


        inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        if (quotes[position] is quote){
            val view = inflater!!.inflate(R.layout.item_quote_slider, null)
            val quote=quotes[position] as quote
            view.txt_quote_slider.text=quote.quote
            val vp = container as ViewPager
            vp.addView(view, 0)
            view.tag = "View$position"
            return view
        }else{
            return inflater!!.inflate(R.layout.fb_ad_view, null)
        }

    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {

        val vp = container as ViewPager
        val view = `object` as View
        vp.removeView(view)
    }


}