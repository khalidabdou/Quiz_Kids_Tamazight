package com.example.wallsticker.fragments.quotes

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
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
import com.example.wallsticker.R
import com.example.wallsticker.Utilities.Const
import com.facebook.ads.Ad
import com.facebook.ads.AdError
import com.facebook.ads.AdListener
import com.facebook.ads.NativeAdsManager
import kotlinx.android.synthetic.main.fragment_quotes_slider.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


class QuotesSlider : Fragment() {

    val currentArg: QuotesSliderArgs by navArgs()
    private lateinit var viewpager: ViewPager
    private lateinit var btnShare  : ImageView
    private lateinit var btnShareinsta  : ImageView
    private lateinit var btnSharewtsp  : ImageView
    private lateinit var btnCopy  : ImageView
    private lateinit var quotesize: SeekBar
    private lateinit var screenshot: View
    private lateinit var navSliderQuotes: LinearLayout
    private lateinit var bottom:LinearLayout
    private var _xDelta = 0
    private var _yDelta = 0


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


        val adapter = context?.let { QuotesSliderAdapter(it, Const.QuotesTemp) }!!

        viewpager.adapter = adapter

        if (currentArg.current==viewpager.currentItem)
            viewpager.currentItem=currentArg.current+1

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

                screenshot = viewpager.findViewWithTag("View" + position)
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
            btnShare.visibility = View.GONE
            navSliderQuotes.visibility=View.GONE
            val bitmapscreen = takeScreenshotOfView(view, view.height, view.width)
            val uriImg = saveImage(bitmapscreen)
            uriImg?.let { shareImageUri(it,null) }
            btnShare.visibility = View.VISIBLE
            navSliderQuotes.visibility=View.VISIBLE
        }

        btnSharewtsp.setOnClickListener {
            bottom.visibility = View.GONE
            navSliderQuotes.visibility=View.GONE
            val bitmapscreen = takeScreenshotOfView(view, view.height, view.width)
            val uriImg = saveImage(bitmapscreen)
            uriImg?.let { shareImageUri(it,"com.whatsapp") }
            bottom.visibility = View.VISIBLE
            navSliderQuotes.visibility=View.VISIBLE
        }

        btnShareinsta.setOnClickListener {

            val bitmapscreen = takeScreenshotOfView(view, view.height, view.width)
            val uriImg = saveImage(bitmapscreen)
            uriImg?.let { shareImageUri(it,"com.instagram.android") }
            ViewVisible()

        }


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


    }

    private fun ViewVisible() {
        btnShare.visibility = View.VISIBLE
        navSliderQuotes.visibility=View.VISIBLE
    }

    private fun init(view: View){
        viewpager = view.findViewById(R.id.quotes_view_pager)

        quotesize = view.findViewById(R.id.quote_size)
        navSliderQuotes=view.findViewById(R.id.navSlider)
        bottom=view.findViewById(R.id.bottomfeature)
        btnShare = view.findViewById(R.id.btnShare)
        btnShareinsta=view.findViewById(R.id.share_insta)
        btnSharewtsp=view.findViewById(R.id.share_wtsp)
        btnCopy=view.findViewById(R.id.btn_copy)
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

    private fun shareImageUri(uri: Uri,ToPackage:String?) {

        val intent = Intent(Intent.ACTION_SEND)
        intent.putExtra(Intent.EXTRA_STREAM, uri)
        if (Const.enable_share_with_package)
        intent.putExtra(Intent.EXTRA_TEXT,resources.getString(R.string.share_text)+"\n${resources.getString(R.string.store_prefix)+context?.packageName}")
        ToPackage?.let { intent.setPackage(it) }
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        intent.type = "image/png"
        startActivity(intent)
    }





}