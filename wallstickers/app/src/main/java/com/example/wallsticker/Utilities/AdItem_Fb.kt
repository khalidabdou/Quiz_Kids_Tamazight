package com.example.wallsticker.Utilities

import com.facebook.ads.NativeAd

class AdItem_Fb {
    private var unifiedNativeAd: NativeAd? = null

    fun getUnifiedNativeAd(): NativeAd? {
        return unifiedNativeAd
    }

    fun setUnifiedNativeAd(unifiedNativeAd: NativeAd?) {
        this.unifiedNativeAd = unifiedNativeAd
    }
}