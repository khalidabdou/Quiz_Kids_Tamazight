package com.example.wallsticker.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.wallsticker.Interfaces.QuotesApi
import com.example.wallsticker.Model.quote
import com.example.wallsticker.R
import com.example.wallsticker.Utilities.Const
import kotlinx.android.synthetic.main.fragment_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeFragment : Fragment(R.layout.fragment_home) {
    private var offset = 0
    private lateinit var todyaQuote: TextView
    private lateinit var shareTodayQuote: ImageView
    private var trying: Int = 0


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        todyaQuote = view.findViewById(R.id.txt_today)
        shareTodayQuote = view.findViewById(R.id.share)


        btn_Images.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToLoginFragment()
            findNavController().navigate(action)

            //ShareTask(context).execute ("https://media.tenor.com/images/2d7258334277cd56b0fca431286df23e/tenor.gif")

        }

        btn_Stickers.setOnClickListener {
            val actionToStickers = HomeFragmentDirections.actionHomeFragmentToStickersFragment()
            findNavController().navigate(actionToStickers)
        }

        btn_quotes.setOnClickListener {
            val goToQuotes = HomeFragmentDirections.actionHomeFragmentToHomeQuotes()
            findNavController().navigate(goToQuotes)
        }


        if (Const.QuotesTemp.size <= 0) {
            //refresh.isRefreshing = true
            fetchQuotes()
        } else {
            var rnds: Int = (0..Const.QuotesTemp.size - 1).random()
            if (Const.QuotesTemp[rnds] is quote) {
                val quoteString: quote = Const.QuotesTemp[rnds] as quote
                setRandomQuote(quoteString.quote.toString())
            } else {
                rnds--
                val quoteString: quote = Const.QuotesTemp[rnds] as quote
                setRandomQuote(quoteString.quote.toString())
            }

        }

        shareTodayQuote.setOnClickListener {

            var packageTxt: String? = ""
            if (Const.enable_share_with_package)
                packageTxt =
                    "\n" + resources.getString(R.string.share_text) + "\n${resources.getString(R.string.store_prefix) + context?.packageName}"

            val shareIntent = Intent()
            shareIntent.action = Intent.ACTION_SEND
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_TEXT, todyaQuote.text.toString() + packageTxt)
            startActivity(Intent.createChooser(shareIntent, "Share To"))
        }

    }

    private fun fetchQuotes() {

        QuotesApi().getQuotes(offset).enqueue(object : Callback<List<quote>> {
            override fun onFailure(call: Call<List<quote>>, t: Throwable) {
                trying++
                if (trying < 5)
                    fetchQuotes()
                //Toast.makeText(context, t.message, Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                call: Call<List<quote>>,
                response: Response<List<quote>>
            ) {

                val quotes = response.body()

                quotes?.let {
                    quotes[5].quote?.let { it1 -> setRandomQuote(it1) }
                    quotes.forEach {
                        if (Const.QuotesTempFav.contains(it))
                            it.id = 1
                    }
                    Const.QuotesTemp.addAll(it)
                }

            }
        })
    }


    //set random quote
    private fun setRandomQuote(quote: String) {
        todyaQuote.text = quote
    }


}