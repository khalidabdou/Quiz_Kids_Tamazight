package com.example.wallsticker.Utilities

import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.example.wallsticker.R
import com.facebook.ads.MediaView
import com.facebook.ads.NativeAdLayout

class FbAdHolder {

    var nativeAdLayout: NativeAdLayout? = null
    var mvAdMedia: MediaView? = null
    var ivAdIcon: MediaView? = null
    var tvAdTitle: TextView? = null
    var tvAdBody: TextView? = null
    var tvAdSocialContext: TextView? = null
    var tvAdSponsoredLabel: TextView? = null
    var btnAdCallToAction: Button? = null
    var adChoicesContainer: LinearLayout? = null

    fun FbAdHolder(adLayout: NativeAdLayout) {

        nativeAdLayout = adLayout
        mvAdMedia = adLayout.findViewById(R.id.native_ad_media)
        tvAdTitle = adLayout.findViewById(R.id.native_ad_title)
        tvAdBody = adLayout.findViewById(R.id.native_ad_body)
        tvAdSocialContext = adLayout.findViewById(R.id.native_ad_social_context)
        tvAdSponsoredLabel = adLayout.findViewById(R.id.native_ad_sponsored_label)
        btnAdCallToAction =
            adLayout.findViewById(R.id.native_ad_call_to_action)
        ivAdIcon = adLayout.findViewById(R.id.native_ad_icon)
        adChoicesContainer = adLayout.findViewById(R.id.ad_choices_container)
    }
}