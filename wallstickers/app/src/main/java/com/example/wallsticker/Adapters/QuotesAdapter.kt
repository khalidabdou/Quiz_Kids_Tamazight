package com.example.wallsticker.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.wallsticker.Interfaces.QuoteClickListener
import com.example.wallsticker.Model.quote
import com.example.wallsticker.R
import com.example.wallsticker.Utilities.AdItem_Fb
import com.facebook.ads.AdOptionsView
import com.facebook.ads.MediaView
import com.facebook.ads.NativeAd
import com.facebook.ads.NativeAdLayout
import kotlinx.android.synthetic.main.item_quote.view.*
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
//        val view = LayoutInflater.from(parent.context)
//            .inflate(R.layout.item_quote, parent, false) as View
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
            is QuoteViewHolder -> holder.bind(element as quote, position)
            is AdViewHolder -> holder.bind(element as AdItem_Fb, position)
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
        abstract fun bind(item: T, position: Int)
    }


    inner class QuoteViewHolder(quoteView: View) : BaseViewHolder<quote>(quoteView) {
        var holder = quoteView


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
        override fun bind(item: quote, position: Int) {
            holder.txt_quote.text = item.quote
            holder.animation =
                AnimationUtils.loadAnimation(
                    context,
                    R.anim.holder_slide
                )


            if (item.isfav == 1)
                holder.fav.setImageDrawable(context?.getDrawable(R.drawable.ic_is_fav))
            else if (item.isfav == 0)
                holder.fav.setImageDrawable(context?.getDrawable(R.drawable.ic_baseline_favorite_border_24))

            holder.fav.setOnClickListener {
                try {
                    quoteClickListerner.onFavClicked(item, position)
                } catch (e: Exception) {
                }
            }
            holder.btn_share.setOnClickListener {
                quoteClickListerner.onShareClicked(item)
            }

            holder.setOnClickListener {
                quoteClickListerner.onQuoteClicked(holder, item, position)
            }
            holder.btn_copy.setOnClickListener {
                quoteClickListerner.onCopyClicked(holder, item)
            }


        }


    }

    inner class AdViewHolder(adView: View) : BaseViewHolder<AdItem_Fb>(adView) {


        private var adHolder = adView
        private var mvAdMedia = adHolder.findViewById<MediaView>(R.id.native_ad_media)
        private var tvAdTitle = adHolder.findViewById<TextView>(R.id.native_ad_title)
        private var tvAdBody = adHolder.findViewById<TextView>(R.id.native_ad_body)
        private var tvAdSocialContext =
            adHolder.findViewById<TextView>(R.id.native_ad_social_context)
        private var tvAdSponsoredLabel =
            adHolder.findViewById<TextView>(R.id.native_ad_sponsored_label)
        private var btnAdCallToAction =
            adHolder.findViewById<Button>(R.id.native_ad_call_to_action)
        private var ivAdIcon = adHolder.findViewById<MediaView>(R.id.native_ad_icon)
        private var adChoicesContainer =
            adHolder.findViewById<LinearLayout>(R.id.ad_choices_container)

        override fun bind(item: AdItem_Fb, position: Int) {
            var nativeAd_fb: NativeAd? = item.unifiedNativeAd
            tvAdTitle?.text = nativeAd_fb?.advertiserName
            tvAdBody?.text = nativeAd_fb?.adBodyText
            tvAdSocialContext?.text = nativeAd_fb?.adSocialContext
            tvAdSponsoredLabel?.text = "sponsored"
            btnAdCallToAction?.text = nativeAd_fb?.adCallToAction
            btnAdCallToAction?.visibility =
                if (nativeAd_fb?.hasCallToAction()!!) View.VISIBLE else View.INVISIBLE
            val adOptionsView =
                AdOptionsView(context, nativeAd_fb, adHolder as NativeAdLayout)
            adChoicesContainer?.addView(adOptionsView, 0)

            val clickableViews: MutableList<View> =
                ArrayList()
            clickableViews.add(ivAdIcon!!)
            clickableViews.add(mvAdMedia!!)
            btnAdCallToAction?.let { clickableViews.add(it) }
            nativeAd_fb.registerViewForInteraction(
                adHolder,
                mvAdMedia,
                ivAdIcon,
                clickableViews
            )

        }

    }

}


