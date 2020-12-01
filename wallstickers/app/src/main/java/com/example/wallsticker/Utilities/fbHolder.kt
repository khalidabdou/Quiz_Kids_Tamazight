package com.example.wallsticker.Utilities

import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.wallsticker.R
import com.facebook.ads.MediaView
import com.facebook.ads.NativeAdLayout

class fbHolder(var nativeAdLayout: NativeAdLayout) : RecyclerView.ViewHolder(nativeAdLayout) {
    var mvAdMedia: MediaView
    var ivAdIcon: MediaView
    var tvAdTitle: TextView
    var tvAdBody: TextView
    var tvAdSocialContext: TextView
    var tvAdSponsoredLabel: TextView
    var btnAdCallToAction: Button
    var adChoicesContainer: LinearLayout

    init {
        mvAdMedia = nativeAdLayout.findViewById(R.id.native_ad_media)
        tvAdTitle = nativeAdLayout.findViewById(R.id.native_ad_title)
        tvAdBody = nativeAdLayout.findViewById(R.id.native_ad_body)
        tvAdSocialContext = nativeAdLayout.findViewById(R.id.native_ad_social_context)
        tvAdSponsoredLabel = nativeAdLayout.findViewById(R.id.native_ad_sponsored_label)
        btnAdCallToAction = nativeAdLayout.findViewById(R.id.native_ad_call_to_action)
        ivAdIcon = nativeAdLayout.findViewById(R.id.native_ad_icon)
        adChoicesContainer = nativeAdLayout.findViewById(R.id.ad_choices_container)
    }
}