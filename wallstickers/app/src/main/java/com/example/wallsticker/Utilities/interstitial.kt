package com.example.wallsticker.Utilities

import android.content.Context
import com.example.wallsticker.R
import com.facebook.ads.Ad
import com.facebook.ads.AdError
import com.facebook.ads.InterstitialAd
import com.facebook.ads.InterstitialAdListener


class interstitial(context: Context) {
    private var interstitialAd: InterstitialAd? = null
    private var context: Context = context

    fun loadInter(): InterstitialAd {
        if (interstitialAd == null)
            interstitialAd = InterstitialAd(context, context.resources.getString(R.string.interstitial_facebook))
        val interstitialAdListener: InterstitialAdListener = object : InterstitialAdListener {
            override fun onInterstitialDisplayed(ad: Ad) {
                // Interstitial ad displayed callback

            }

            override fun onInterstitialDismissed(ad: Ad) {


            }

            override fun onError(ad: Ad, adError: AdError) {
                // Ad error callback
                //Toast.makeText(context,"error"+adError.errorMessage,Toast.LENGTH_LONG).show()

            }

            override fun onAdLoaded(ad: Ad) {
                // Interstitial ad is loaded and ready to be displayed
                // Show the ad
                //interstitialAd!!.show()
                //Toast.makeText(context,"loaded",Toast.LENGTH_LONG).show()
            }

            override fun onAdClicked(ad: Ad) {
                // Ad clicked callback
            }

            override fun onLoggingImpression(ad: Ad) {
                // Ad impression logged callback

            }


        }

        interstitialAd!!.loadAd(
            interstitialAd!!.buildLoadAdConfig()
                .withAdListener(interstitialAdListener).build()
        )

        return interstitialAd as InterstitialAd
    }


    fun showInter() {
        if (interstitialAd?.isAdLoaded!!)
            interstitialAd?.show()
    }


}