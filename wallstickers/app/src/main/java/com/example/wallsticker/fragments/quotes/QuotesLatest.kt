package com.example.wallsticker.fragments.quotes

import android.app.Activity
import android.content.*
import android.os.Bundle
import android.provider.BaseColumns
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.wallsticker.Adapters.QuotesAdapter
import com.example.wallsticker.Interfaces.QuoteClickListener
import com.example.wallsticker.Interfaces.QuotesApi
import com.example.wallsticker.Model.quote
import com.example.wallsticker.R
import com.example.wallsticker.Utilities.AdItem_Fb
import com.example.wallsticker.Utilities.Const
import com.example.wallsticker.Utilities.FeedReaderContract
import com.example.wallsticker.Utilities.helper
import com.facebook.ads.*
import kotlinx.android.synthetic.main.fragment_img_category.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class QuotesLatest : Fragment(), QuoteClickListener {

    private lateinit var clipboardManager: ClipboardManager
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var refresh: SwipeRefreshLayout
    private val mNativeAds_Fb: List<AdItem_Fb> = ArrayList()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        clipboardManager = context?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        return inflater.inflate(R.layout.fragment_quotes_latest, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById<RecyclerView>(R.id.latest_quote_recycler_view)
        refresh = view.findViewById(R.id.refreshLayout)

        viewManager = GridLayoutManager(activity, 1)
        viewAdapter = QuotesAdapter(this, Const.QuotesTemp,context)

        recyclerView.adapter = viewAdapter
        recyclerView.layoutManager = viewManager
        recyclerView.setHasFixedSize(true)
        refresh.setOnRefreshListener {
            fetchQuotes()
        }
        if (Const.QuotesTemp.size <= 0) {
            refreshLayout.isRefreshing = true
            fetchQuotes()
        }




    }

    private fun fetchQuotes() {

        QuotesApi().getQuotes().enqueue(object : Callback<List<quote>> {
            override fun onFailure(call: Call<List<quote>>, t: Throwable) {
                refresh.isRefreshing = false
                    //Toast.makeText(context, t.message, Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                call: Call<List<quote>>,
                response: Response<List<quote>>
            ) {

                refresh.isRefreshing = false
                val quotes = response.body()
                quotes?.let {
                    quotes.forEach{
                        if (Const.QuotesTempFav.contains(it))
                            it.id=1
                    }
                    Const.QuotesTemp.addAll(it)

                    viewAdapter.notifyItemInserted(Const.QuotesTemp.size - 1)
                    LoadNativeAd()
                    //progressBar2.visibility = View.GONE
                }

            }
        })
    }

    override fun onQuoteClicked(view: View, quote: quote, pos: Int) {
        val GoToSlider = HomeQuotesDirections.actionHomeQuotesToQuotesSlider(pos)
        findNavController().navigate(GoToSlider)
    }

    override fun onShareClicked(quote: quote) {
        val shareIntent = Intent()
        shareIntent.action = Intent.ACTION_SEND
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_TEXT, quote.quote)
        startActivity(Intent.createChooser(shareIntent, "Share To"))
    }

    override fun onCopyClicked(view: View, quote: quote) {
        val textToCopy = quote.quote
        val clipData = ClipData.newPlainText("text", textToCopy)
        clipboardManager.setPrimaryClip(clipData)
        Toast.makeText(context, "Text copied to clipboard", Toast.LENGTH_LONG).show()
    }

    override fun onFavClicked(quote: quote,pos: Int) {
        Const.isFavChanged=true
        val dbHelper = context?.let { helper(it) }
        val db = dbHelper?.writableDatabase
        if (quote.isfav==0){
            val values = ContentValues().apply {
                put(BaseColumns._ID,quote.id)
                put(FeedReaderContract.FeedEntry.COLUMN_NAME_QUOTE, quote.quote)
            }
            val newRowId = db!!.insert(FeedReaderContract.FeedEntry.TABLE_NAME, null, values)
            quote.isfav=1
            viewAdapter.notifyItemChanged(pos)
            Toast.makeText(context,newRowId.toString(),Toast.LENGTH_LONG).show()
        }else if(quote.isfav==1){
            val selection = "${BaseColumns._ID} like ?"
            val selectionArgs = arrayOf(quote.id.toString())
            val deletedRows =
                db?.delete(FeedReaderContract.FeedEntry.TABLE_NAME, selection, selectionArgs)
            quote.isfav=0
            viewAdapter.notifyItemChanged(pos)
            Toast.makeText(context,deletedRows.toString(),Toast.LENGTH_LONG).show()
        }



    }




    //load Native Ad
    private fun LoadNativeAd(){
        var mNativeAdsManager = NativeAdsManager(
            activity,
            resources.getString(R.string.native_facebook),
            5
        )
        AdSettings.addTestDevice("fc76260a-b544-4f35-a317-36ddd4f65545")
        mNativeAdsManager.loadAds()
        mNativeAdsManager.setListener(object : AdListener, NativeAdsManager.Listener {
            override fun onError(ad: Ad, adError: AdError) {
                // Ad error callback
                Toast.makeText(
                    context,
                    "Error: " + adError.errorCode.toString(),
                    Toast.LENGTH_LONG
                )
                    .show()
            }

            override fun onAdLoaded(ad: Ad) {
                // Ad loaded callback
                // Ad error callback
                Toast.makeText(
                    context,
                    "loaded",
                    Toast.LENGTH_LONG
                )
                    .show()
            }

            override fun onAdClicked(ad: Ad) {
                // Ad clicked callback
            }

            override fun onLoggingImpression(ad: Ad) {
                // Ad impression logged callback
            }

            override fun onAdsLoaded() {
                Toast.makeText(
                    context,
                    "loaded.kajx",
                    Toast.LENGTH_LONG
                )
                    .show()
                val count = mNativeAdsManager.uniqueNativeAdCount
                for (i in 0 until count) {
                    val ad = mNativeAdsManager.nextNativeAd()
                    val adItem = AdItem_Fb()
                    adItem.setUnifiedNativeAd(ad)
                    if (!ad.isAdInvalidated) {
                        mNativeAds_Fb.plus(adItem)
                    } else {
                        Toast.makeText(
                            context,
                            "loaded.kajx",
                            Toast.LENGTH_LONG
                        )
                            .show()
                    }
                }

                insertAdsInMenuItems()
            }

            override fun onAdError(err: AdError?) {
                // Ad error callback
                Toast.makeText(
                    context,
                    "Error: " + err?.errorMessage.toString(),
                    Toast.LENGTH_LONG
                )
                    .show()
            }
        })


    }

    private fun insertAdsInMenuItems() {
        if (mNativeAds_Fb.size <= 0) {
            Log.d("ADNative", "insertAdsInMenuItems: Empty Facebook Native ad")
            return
        }
        var indexFB = 3
        for (ad in mNativeAds_Fb) {
            //Comment this to close the native Ads
            if (indexFB > Const.QuotesTemp.size) {
                break
            }
            if (Const.QuotesTemp[indexFB] !is AdItem_Fb) {
                Toast.makeText(
                    context,
                    "loaded....",
                    Toast.LENGTH_LONG
                )
                    .show()
                Const.QuotesTemp.add(indexFB, ad)
                viewAdapter.notifyItemInserted(indexFB)
                Log.d("ADNative", "insertAdsInMenuItems Facebook|index is :$indexFB")
            }
            indexFB += 4
        }
    }


}