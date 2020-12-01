package com.example.wallsticker.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.wallsticker.Model.quote
import com.example.wallsticker.R
import com.example.wallsticker.Utilities.AdItem_Fb
import com.example.wallsticker.Utilities.fbHolder
import com.facebook.ads.AdOptionsView
import com.facebook.ads.NativeAd
import com.facebook.ads.NativeAdLayout
import kotlinx.android.synthetic.main.item_quote_slider.view.*

class QuotesSliderAdapter(private val context: Context, private var quotes: ArrayList<Any>) :
    PagerAdapter() {


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
        if (quotes[position] is quote) {
            val view = inflater!!.inflate(R.layout.item_quote_slider, null)
            val quote = quotes[position] as quote
            view.txt_quote_slider.text = quote.quote
            val vp = container as ViewPager
            vp.addView(view, 0)
            view.tag = "View$position"
            return view
        } else if (quotes[position] is AdItem_Fb) {
            val adview = inflater!!.inflate(R.layout.fb_ad_view, null)

            var adlyaout = fbHolder(adview as NativeAdLayout)

            var nativeAd: AdItem_Fb? = quotes[position] as AdItem_Fb
            var nativeAd_fb: NativeAd? = nativeAd?.unifiedNativeAd
            adlyaout.tvAdTitle.text = nativeAd_fb?.advertiserName
            adlyaout.tvAdBody.text = nativeAd_fb?.adBodyText
            adlyaout.tvAdSocialContext.text = nativeAd_fb?.adSocialContext
            adlyaout.tvAdSponsoredLabel.text = "sponsored"
            adlyaout.btnAdCallToAction.text = nativeAd_fb?.adCallToAction
            adlyaout.btnAdCallToAction.visibility =
                if (nativeAd_fb?.hasCallToAction()!!) View.VISIBLE else View.INVISIBLE
            val adOptionsView =
                AdOptionsView(context, nativeAd_fb, adview)
            adlyaout.adChoicesContainer.addView(adOptionsView, 0)

            val clickableViews: MutableList<View> =
                java.util.ArrayList()
            clickableViews.add(adlyaout.ivAdIcon)
            clickableViews.add(adlyaout.mvAdMedia)
            adlyaout.btnAdCallToAction.let { clickableViews.add(it) }
            nativeAd_fb.registerViewForInteraction(
                adview,
                adlyaout.mvAdMedia,
                adlyaout.ivAdIcon,
                clickableViews
            )
            val vp = container as ViewPager
            vp.addView(adview, 0)

            return adview
        } else return inflater!!.inflate(R.layout.fb_ad_view, null)

    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {

        val vp = container as ViewPager
        val view = `object` as View
        vp.removeView(view)
    }


}