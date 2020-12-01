package com.example.wallsticker.fragments.quotes

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Typeface
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.viewpager.widget.ViewPager
import com.example.wallsticker.Adapters.QuotesSliderAdapter
import com.example.wallsticker.Interfaces.IncrementServiceQuote
import com.example.wallsticker.Model.quote
import com.example.wallsticker.R
import com.example.wallsticker.Utilities.Const
import kotlinx.android.synthetic.main.fragment_quotes_slider.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class QuotesSlider : Fragment() {

    val currentArg: QuotesSliderArgs by navArgs()
    private lateinit var viewpager: ViewPager
    private lateinit var btnShare: ImageView
    private lateinit var btnShareinsta: ImageView
    private lateinit var btnSharewtsp: ImageView
    private lateinit var btnCopy: ImageView
    private lateinit var quotesize: SeekBar
    private lateinit var screenshot: View
    private lateinit var navSliderQuotes: LinearLayout
    private lateinit var bottom: LinearLayout
    private var _xDelta = 0
    private var quote: quote? = null
    private var _yDelta = 0

    private lateinit var fontsButton: Array<TextView>

    val bagrounds: IntArray = intArrayOf(
        R.drawable.quotesalone,
        R.drawable.quotecoffee,
        R.drawable.quotelion,
        R.drawable.quotelove,
        R.drawable.quotes5
    )

    val colors: IntArray = intArrayOf(
        R.color.white,
        R.color.color1,
        R.color.colorb,
        R.color.colorPrimaryDark,
        R.color.background
    )


    var arrayof = Const.quotesarrayof

    var quotes: ArrayList<Any> =
        if (arrayof == "latest") Const.QuotesTemp
        else if (arrayof == "byCat") Const.QuotesByCat
        else Const.QuotesTempFav


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quotes_slider, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)


        val adapter = context?.let { QuotesSliderAdapter(it, quotes) }!!

        viewpager.adapter = adapter

        if (currentArg.current == viewpager.currentItem)
            viewpager.currentItem = currentArg.current + 1

        val h = Handler(Looper.getMainLooper())
        val r: Runnable = object : Runnable {
            override fun run() {
                viewpager.setCurrentItem(currentArg.current, true)
                //h.postDelayed(this, 1000)
            }
        }
        h.postDelayed(r, 10)

        viewpager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                if (quotes[position] is quote) {

                    screenshot = viewpager.findViewWithTag("View" + position)
                    quote = quotes[position] as quote


                    var incrementView = quote?.count_views?.plus(1)
//                    IncrementViewQuote().incrementViews(quote?.id,incrementView).enqueue(object : Callback<Any> {
//                        override fun onFailure(call: Call<Any>, t: Throwable) {
//                            //Toast.makeText(context,t.message,Toast.LENGTH_LONG).show()
//                        }
//                        override fun onResponse(call: Call<Any>, response: Response<Any>) {
//                            //Toast.makeText(context,"Response :${response}",Toast.LENGTH_LONG).show()
//                        }
//
//                    })
                    viewVisible()
                } else {
                    viewGone()
                }
            }
        })

        //var i=viewpager.currentItem
        //Toast.makeText(context,viewpager.count_views.toString(),Toast.LENGTH_LONG).show()


        quotesize.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                screenshot.findViewById<TextView>(R.id.txt_quote_slider).textSize = p1.toFloat()
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }

        })

        val layoutParams = RelativeLayout.LayoutParams(150, 50)
        layoutParams.leftMargin = 50
        layoutParams.topMargin = 50
        layoutParams.bottomMargin = -250
        layoutParams.rightMargin = -250

        btnShare.setOnClickListener {
            viewGone()
            val bitmapscreen = takeScreenshotOfView(view, view.height, view.width)
            val uriImg = saveImage(bitmapscreen)
            uriImg?.let { shareImageUri(it, null) }
            viewVisible()
        }

        btnSharewtsp.setOnClickListener {
            viewGone()
            val bitmapscreen = takeScreenshotOfView(view, view.height, view.width)
            val uriImg = saveImage(bitmapscreen)
            uriImg?.let { shareImageUri(it, "com.whatsapp") }
            viewVisible()
        }

        btnShareinsta.setOnClickListener {
            viewGone()
            val bitmapscreen = takeScreenshotOfView(view, view.height, view.width)
            val uriImg = saveImage(bitmapscreen)
            uriImg?.let { shareImageUri(it, "com.instagram.android") }
            viewVisible()

        }

        //set image background
        img1.setOnClickListener {
            screenshot.findViewById<ImageView>(R.id.background_quote)
                .setImageResource(bagrounds.get(0))
        }
        img2.setOnClickListener {
            screenshot.findViewById<ImageView>(R.id.background_quote)
                .setImageResource(bagrounds.get(1))
        }
        img3.setOnClickListener {
            screenshot.findViewById<ImageView>(R.id.background_quote)
                .setImageResource(bagrounds.get(2))
        }
        img4.setOnClickListener {
            screenshot.findViewById<ImageView>(R.id.background_quote)
                .setImageResource(bagrounds.get(3))
        }
        img5.setOnClickListener {
            screenshot.findViewById<ImageView>(R.id.background_quote)
                .setImageResource(bagrounds.get(4))
        }

        //set color to text
        color1.setOnClickListener {
            context?.let { it1 ->
                ContextCompat.getColor(
                    it1, colors[0]
                )
            }?.let { it2 ->
                screenshot.findViewById<TextView>(R.id.txt_quote_slider).setTextColor(
                    it2
                )
            }
        }
        color2.setOnClickListener {
            context?.let { it1 ->
                ContextCompat.getColor(
                    it1, colors[1]
                )
            }?.let { it2 ->
                screenshot.findViewById<TextView>(R.id.txt_quote_slider).setTextColor(
                    it2
                )
            }
        }
        color3.setOnClickListener {
            context?.let { it1 ->
                ContextCompat.getColor(
                    it1, colors[2]
                )
            }?.let { it2 ->
                screenshot.findViewById<TextView>(R.id.txt_quote_slider).setTextColor(
                    it2
                )
            }
        }
        color4.setOnClickListener {
            context?.let { it1 ->
                ContextCompat.getColor(
                    it1, colors[3]
                )
            }?.let { it2 ->
                screenshot.findViewById<TextView>(R.id.txt_quote_slider).setTextColor(
                    it2
                )
            }
        }
        color5.setOnClickListener {
            context?.let { it1 ->
                ContextCompat.getColor(
                    it1, colors[4]
                )
            }?.let { it2 ->
                screenshot.findViewById<TextView>(R.id.txt_quote_slider).setTextColor(
                    it2
                )
            }
        }

        //set font family to text
        fontsButton[0].setOnClickListener { setFont(0) }
        fontsButton[1].setOnClickListener { setFont(1) }
        fontsButton[2].setOnClickListener { setFont(2) }
        fontsButton[3].setOnClickListener { setFont(3) }

    }

    private fun viewVisible() {
        bottom.visibility = View.VISIBLE
        navSliderQuotes.visibility = View.VISIBLE
    }

    private fun viewGone() {
        bottom.visibility = View.GONE
        navSliderQuotes.visibility = View.GONE
    }

    private fun init(view: View) {
        viewpager = view.findViewById(R.id.quotes_view_pager)
        quotesize = view.findViewById(R.id.quote_size)
        navSliderQuotes = view.findViewById(R.id.navSlider)
        bottom = view.findViewById(R.id.bottomfeature)
        btnShare = view.findViewById(R.id.btnShare)
        btnShareinsta = view.findViewById(R.id.share_insta)
        btnSharewtsp = view.findViewById(R.id.share_wtsp)
        btnCopy = view.findViewById(R.id.btn_copy)

        fontsButton = arrayOf(
            view.findViewById(R.id.font1) as TextView,
            view.findViewById(R.id.font2) as TextView,
            view.findViewById(R.id.font3) as TextView,
            view.findViewById(R.id.font4) as TextView,
        )
    }


    fun takeScreenshotOfView(view: View, height: Int, width: Int): Bitmap {
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        val bgDrawable = view.background
        if (bgDrawable != null) {
            bgDrawable.draw(canvas)
        } else {
            canvas.drawColor(Color.WHITE)
        }
        view.draw(canvas)
        return bitmap
    }

    fun isExternalStorageWritable(): Boolean {
        val state = Environment.getExternalStorageState()
        return Environment.MEDIA_MOUNTED == state
    }

    private fun saveImage(image: Bitmap): Uri? {
        //TODO - Should be processed in another thread
        val imagesFolder = File(context?.getCacheDir(), "images")
        var uri: Uri? = null
        try {
            imagesFolder.mkdirs()
            val file = File(imagesFolder, "shared_image.png")
            val stream = FileOutputStream(file)
            image.compress(Bitmap.CompressFormat.PNG, 90, stream)
            stream.flush()
            stream.close()
            uri = context?.let { FileProvider.getUriForFile(it, "com.mydomain.fileprovider", file) }
        } catch (e: IOException) {
            Log.d(
                "TAG",
                "IOException while trying to write file for sharing: " + e.message
            )
        }
        return uri
    }

    private fun shareImageUri(uri: Uri, ToPackage: String?) {

        val intent = Intent(Intent.ACTION_SEND)
        intent.putExtra(Intent.EXTRA_STREAM, uri)
        if (Const.enable_share_with_package)
            intent.putExtra(
                Intent.EXTRA_TEXT,
                resources.getString(R.string.share_text) + "\n${resources.getString(R.string.store_prefix) + context?.packageName}"
            )
        ToPackage?.let { intent.setPackage(it) }
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        intent.type = "image/png"
        startActivity(intent)
        var incrementShare = quote?.count_shared?.plus(1)
        IncrementServiceQuote().incrementShare(quote?.id, incrementShare)
            .enqueue(object : Callback<Any> {
                override fun onFailure(call: Call<Any>, t: Throwable) {
                    //Toast.makeText(context,t.message,Toast.LENGTH_LONG).show()
                }

                override fun onResponse(call: Call<Any>, response: Response<Any>) {
                    //Toast.makeText(context,"Response :${response}",Toast.LENGTH_LONG).show()
                }

            })
    }

    fun setFont(font: Int) {
        when (font) {
            0 -> {
                val face = Typeface.createFromAsset(
                    context?.resources?.assets,
                    "coll.otf"
                )
                screenshot.findViewById<TextView>(R.id.txt_quote_slider).typeface = face
            }
            1 -> {
                val face = Typeface.createFromAsset(
                    context?.resources?.assets,
                    "nekro.ttf"
                )
                screenshot.findViewById<TextView>(R.id.txt_quote_slider).typeface = face
            }
            2 -> {
                val face = Typeface.createFromAsset(
                    context?.resources?.assets,
                    "niconne.ttf"
                )
                screenshot.findViewById<TextView>(R.id.txt_quote_slider).typeface = face
            }
            3 -> {
                val face = Typeface.createFromAsset(
                    context?.resources?.assets,
                    "patrick.ttf"
                )
                screenshot.findViewById<TextView>(R.id.txt_quote_slider).typeface = face
            }
        }
    }


}