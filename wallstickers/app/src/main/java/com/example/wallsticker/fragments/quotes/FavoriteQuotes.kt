package com.example.wallsticker.fragments.quotes

import android.os.Bundle
import android.provider.BaseColumns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
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



        nofav=view.findViewById(R.id.nofav)
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
    }

    override fun onShareClicked(quote: quote) {

    }

    override fun onCopyClicked(view: View, quote: quote) {

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
            if (Const.QuotesTempFav.size<=0)
                nofav.visibility=View.VISIBLE
            else nofav.visibility=View.GONE
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
            Toast.makeText(context, deletedRows.toString(), Toast.LENGTH_LONG).show()
        }

    }
}