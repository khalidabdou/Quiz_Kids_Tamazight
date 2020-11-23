package com.example.wallsticker.fragments.quotes

import android.app.Activity
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wallsticker.Adapters.QuotesAdapter
import com.example.wallsticker.Interfaces.QuoteClickListener
import com.example.wallsticker.Model.quote
import com.example.wallsticker.R
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class QuotesByCategory : Fragment(), QuoteClickListener {

    val args: QuotesByCategoryArgs by navArgs()


    private lateinit var clipboardManager: ClipboardManager
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private var quotes = ArrayList<quote>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quotes_by_category, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        clipboardManager = context?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        viewManager = GridLayoutManager(activity, 1)
        viewAdapter = QuotesAdapter(this, quotes,context)
        recyclerView = view.findViewById<RecyclerView>(R.id.latest_quote_recycler_view)
        recyclerView.adapter = viewAdapter
        recyclerView.layoutManager = viewManager
        recyclerView.setHasFixedSize(true)


    }


    override fun onQuoteClicked(view: View, quote: quote, pos: Int) {
    }

    override fun onShareClicked(quote: quote) {
        val shareIntent = Intent()
        shareIntent.action = Intent.ACTION_SEND
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_TEXT, quote.quote)
        startActivity(Intent.createChooser(shareIntent, "Share To"))
    }

    override fun onCopyClicked(view: View,quote: quote) {

        //val textToCopy = quote.quote
        //val clipData = ClipData.newPlainText("text", textToCopy)
        //clipboardManager.setPrimaryClip(clipData)
        //Toast.makeText(context, "Text copied to clipboard", Toast.LENGTH_LONG).show()
    }

    override fun onFavClicked(quote: quote,pos: Int) {

    }


}