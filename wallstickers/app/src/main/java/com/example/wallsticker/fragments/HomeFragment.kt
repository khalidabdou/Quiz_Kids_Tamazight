package com.example.wallsticker.fragments

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.wallsticker.Interfaces.QuotesApi
import com.example.wallsticker.MainViewModel
import com.example.wallsticker.Model.quote
import com.example.wallsticker.R
import com.example.wallsticker.Repository.DataStoreRepository
import com.example.wallsticker.Repository.QuotesRepo
import com.example.wallsticker.Utilities.Const
import com.example.wallsticker.Utilities.InternetCheck
import com.example.wallsticker.ViewModel.QuotesViewModel
import com.example.wallsticker.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeFragment : Fragment(R.layout.fragment_home) {
    private var offset = 0
    private lateinit var todyaQuote: TextView
    private lateinit var shareTodayQuote: ImageView
    private var trying: Int = 0
    private lateinit var lightMode: Switch
    val styles = arrayOf("Light", "Dark", "System default")
    var firstcheck :Boolean= false
    private lateinit var internetCheck: InternetCheck

    private lateinit var mainViewModel: MainViewModel
    private lateinit var viewmodelQuotes: QuotesViewModel


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val quotesRepo= QuotesRepo()
        val viewModelFactory= ViewModelFactory(quotesRepo)


        initView(view)
        viewmodelQuotes= ViewModelProvider(this,viewModelFactory).get(QuotesViewModel::class.java)
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        if (Const.QuotesTemp.size<=0)
        viewmodelQuotes.getLatestQuotes(0,null)




        mainViewModel.readFromDataStore.observe(viewLifecycleOwner, { MODE ->
            when (MODE) {
                "NO" -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    lightMode.isChecked=false
                }
                "YES" -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    lightMode.isChecked=true
                }
                else -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                }
            }
        })
        viewmodelQuotes.latestquotes.observe(viewLifecycleOwner,{quotes->
            if (quotes.isSuccessful){
                quotes.body()?.let {
                    Const.QuotesTemp.addAll(it)
                    setRandomQuote()
                }
            }
        })


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



        lightMode.setOnCheckedChangeListener { buttonview, ischakced ->
            if (ischakced) {
                //AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                mainViewModel.saveToDataStore("YES")
            } else {
                //AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                mainViewModel.saveToDataStore("NO")
            }
        }

    }

    private fun initView(view: View) {
        todyaQuote = view.findViewById(R.id.txt_today)
        shareTodayQuote = view.findViewById(R.id.share)
        lightMode = view.findViewById(R.id.modeChange)
        internetCheck=InternetCheck()

    }


    //set random quote
    private fun setRandomQuote() {


        if (Const.QuotesTemp.size <= 0) {
        } else {
            var rnds: Int = (0..Const.QuotesTemp.size - 1).random()
            if (Const.QuotesTemp[rnds] is quote) {
                val quoteString: quote = Const.QuotesTemp[rnds] as quote
                todyaQuote.text = quoteString.quote
            } else {
                rnds--
                val quoteString: quote = Const.QuotesTemp[rnds] as quote
                todyaQuote.text = quoteString.quote
            }
        }
    }


}