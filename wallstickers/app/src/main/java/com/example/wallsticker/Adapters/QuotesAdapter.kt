package com.example.wallsticker.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.wallsticker.Interfaces.QuoteClickListener
import com.example.wallsticker.Model.quote
import com.example.wallsticker.R
import com.example.wallsticker.Utilities.AdItem_Fb
import com.example.wallsticker.Utilities.FbAdHolder
import com.facebook.ads.AdOptionsView
import com.facebook.ads.NativeAd
import java.util.*

class QuotesAdapter(
    private val quoteClickListerner: QuoteClickListener,
    private val quotes: List<Any>,
    private val context: Context?
) : RecyclerView.Adapter<QuotesAdapter.BaseViewHolder<*>>() {


    companion object {
        private const val TYPE_QUOTE = 0
        private const val TYPE_AD = 1

    }

    //class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view)
    init {

    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): QuotesAdapter.BaseViewHolder<*> {
        // create a new view
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_quote, parent, false) as View
        return when (viewType) {
            TYPE_QUOTE -> {
                val view = LayoutInflater.from(context)
                    .inflate(R.layout.item_quote, parent, false)
                QuoteViewHolder(view)
            }
            TYPE_AD -> {
                val view = LayoutInflater.from(context)
                    .inflate(R.layout.fb_ad_view, parent, false)
                AdViewHolder(view)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }


    }


    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        if (quotes.isEmpty())
            return
        val element = quotes[position]
        when (holder) {
            is QuoteViewHolder -> holder.bind(element as quote)
            is AdViewHolder -> holder.bind(element as AdItem_Fb)
            else -> throw IllegalArgumentException()
        }


//        holder.view.txt_quote.text = quotes[position].quote
//
//
//        if (quotes[position].isfav == 1)
//            holder.view.fav.setImageDrawable(context?.getDrawable(R.drawable.ic_is_fav))
//        else if (quotes[position].isfav == 0)
//            holder.view.fav.setImageDrawable(context?.getDrawable(R.drawable.ic_baseline_favorite_border_24))
//
//        holder.view.btn_share.setOnClickListener {
//            quoteClickListerner.onShareClicked(quotes[position])
//        }
//        holder.view.btn_copy.setOnClickListener {
//            quoteClickListerner.onCopyClicked(holder.view, quotes[position])
//        }
//        holder.view.txt_quote.setOnClickListener {
//            quoteClickListerner.onQuoteClicked(holder.view, quotes[position], position)
//        }
//        holder.view.fav.setOnClickListener {
//            try {
//                quoteClickListerner.onFavClicked(quotes[position], position)
//            } catch (e: Exception) {
//            }
//
//        }

    }

    override fun getItemViewType(position: Int): Int {

        return when (quotes[position]) {
            is quote -> TYPE_QUOTE
            is AdItem_Fb -> TYPE_AD
            else -> throw IllegalArgumentException("Invalid type of data " + position)
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = quotes.size


    abstract class BaseViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
        abstract fun bind(item: T)
    }


    inner class QuoteViewHolder(quoteView: View) : BaseViewHolder<quote>(quoteView) {
        var q: TextView = quoteView.findViewById(R.id.txt_quote)
        override fun bind(item: quote) {
            q.text = item.quote
        }

    }

    inner class AdViewHolder(adView: View) : BaseViewHolder<AdItem_Fb>(adView) {

        private var adHolder: FbAdHolder?=null

        override fun bind(item: AdItem_Fb) {
            var nativeAd_fb: NativeAd? = item.getUnifiedNativeAd()
            adHolder?.tvAdTitle?.text = nativeAd_fb?.advertiserName
            adHolder?.tvAdBody?.text = nativeAd_fb?.adBodyText
            adHolder?.tvAdSocialContext?.text = nativeAd_fb?.adSocialContext
            adHolder?.tvAdSponsoredLabel?.text = "sponsored"
            adHolder?.btnAdCallToAction?.text = nativeAd_fb?.adCallToAction
            adHolder?.btnAdCallToAction?.visibility = if (nativeAd_fb?.hasCallToAction()!!) View.VISIBLE else View.INVISIBLE
            val adOptionsView =
                AdOptionsView(context, nativeAd_fb, adHolder?.nativeAdLayout)
            adHolder?.adChoicesContainer?.addView(adOptionsView, 0)

            val clickableViews: MutableList<View> =
                ArrayList()
            clickableViews.add(adHolder?.ivAdIcon!!)
            clickableViews.add(adHolder!!.mvAdMedia!!)
            adHolder!!.btnAdCallToAction?.let { clickableViews.add(it) }
            nativeAd_fb.registerViewForInteraction(
                adHolder!!.nativeAdLayout,
                adHolder!!.mvAdMedia,
                adHolder!!.ivAdIcon,
                clickableViews
            )

        }

    }

}


