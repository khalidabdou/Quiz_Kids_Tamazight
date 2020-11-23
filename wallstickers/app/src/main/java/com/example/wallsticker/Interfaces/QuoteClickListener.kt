package com.example.wallsticker.Interfaces

import android.view.View
import com.example.wallsticker.Model.quote

interface QuoteClickListener {
    fun onQuoteClicked(view: View, quote: quote, pos: Int)
    fun onShareClicked(quote: quote)
    fun onCopyClicked(view: View,quote: quote)
    fun onFavClicked(quote: quote,pos :Int)
}