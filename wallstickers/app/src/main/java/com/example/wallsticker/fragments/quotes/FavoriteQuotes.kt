package com.example.wallsticker.fragments.quotes

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.BaseColumns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wallsticker.Adapters.QuotesAdapter
import com.example.wallsticker.Interfaces.QuoteClickListener
import com.example.wallsticker.Model.quote
import com.example.wallsticker.R
import com.example.wallsticker.Utilities.Const
import com.example.wallsticker.Utilities.FeedReaderContract
import com.example.wallsticker.Utilities.helper


class FavoriteQuotes : Fragment(), QuoteClickListener {


    private var itemIds: ArrayList<quote>? = null
    private lateinit var clipboardManager: ClipboardManager
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var nofav: LinearLayout
    val projection = arrayOf(BaseColumns._ID, FeedReaderContract.FeedEntry.COLUMN_NAME_QUOTE)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quotes_fav, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        clipboardManager = context?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

        nofav = view.findViewById(R.id.nofav)
        recyclerView = view.findViewById<RecyclerView>(R.id.fav_quote_recycler_view)
        viewManager = GridLayoutManager(activity, 1)
        viewAdapter = QuotesAdapter(this, Const.QuotesTempFav, context)
        recyclerView.adapter = viewAdapter
        recyclerView.layoutManager = viewManager
        recyclerView.setHasFixedSize(true)


        if (Const.isFavChanged) {
            getFavQuotes()
            Const.isFavChanged = false
        }


    }


    override fun onQuoteClicked(view: View, quote: quote, pos: Int) {
        Const.quotesarrayof = "favs"
        val GoToSlider = HomeQuotesDirections.actionHomeQuotesToQuotesSlider(pos)
        findNavController().navigate(GoToSlider)
    }

    override fun onShareClicked(quote: quote) {
        var packageTxt: String? = ""
        if (Const.enable_share_with_package)
            packageTxt =
                "\n" + resources.getString(R.string.share_text) + "\n${resources.getString(R.string.store_prefix) + context?.packageName}"

        val shareIntent = Intent()
        shareIntent.action = Intent.ACTION_SEND
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_TEXT, quote.quote + packageTxt)
        startActivity(Intent.createChooser(shareIntent, "Share To"))
    }

    override fun onCopyClicked(view: View, quote: quote) {
        val textToCopy = quote.quote
        val clipData = ClipData.newPlainText("text", textToCopy)
        clipboardManager.setPrimaryClip(clipData)
        Toast.makeText(context, "Text copied to clipboard", Toast.LENGTH_LONG).show()
    }


    private fun getFavQuotes() {
        Const.QuotesTempFav.clear()
        val dbHelper = context?.let { helper(it) }
        val db = dbHelper?.readableDatabase
        val cursor = db?.query(
            FeedReaderContract.FeedEntry.TABLE_NAME,   // The table to query
            projection,             // The array of columns to return (pass null to get all)
            null,              // The columns for the WHERE clause
            null,          // The values for the WHERE clause
            null,                   // don't group the rows
            null,                   // don't filter by row groups
            null               // The sort order
        )

        with(cursor) {
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    val itemId = this?.getInt(getColumnIndexOrThrow(BaseColumns._ID))
                    val quoteText =
                        this?.getString(getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_QUOTE))
                    val quoteOb = quote(itemId, quoteText, 0, 0, 1)
                    Const.QuotesTempFav.add(quoteOb)

                }
            }
            if (Const.QuotesTempFav.size <= 0)
                nofav.visibility = View.VISIBLE
            else nofav.visibility = View.GONE
        }
        viewAdapter.notifyDataSetChanged()
        //Toast.makeText(context, itemIds!!.count().toString(),Toast.LENGTH_LONG).show()
    }


    override fun onFavClicked(quote: quote, pos: Int) {
        Const.isFavChanged = true
        val dbHelper = context?.let { helper(it) }
        val db = dbHelper?.writableDatabase
        if (quote.isfav == 1) {
            val selection = "${BaseColumns._ID} like ?"
            val selectionArgs = arrayOf(quote.id.toString())
            val deletedRows =
                db?.delete(FeedReaderContract.FeedEntry.TABLE_NAME, selection, selectionArgs)
            quote.isfav = 0
            Const.QuotesTempFav.remove(quote)
            viewAdapter.notifyItemRemoved(pos)
            //Toast.makeText(context, deletedRows.toString(), Toast.LENGTH_LONG).show()
        }

    }
}