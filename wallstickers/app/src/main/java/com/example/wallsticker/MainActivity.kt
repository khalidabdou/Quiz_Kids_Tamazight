package com.example.wallsticker

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupActionBarWithNavController
import com.facebook.ads.*
import kotlinx.android.synthetic.main.activity_main.*
import java.net.URL


class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private var adlistener : AdListener? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //init facebook audience

        AudienceNetworkAds.initialize(this)
        AdSettings.addTestDevice("fc76260a-b544-4f35-a317-36ddd4f65545")
        var adView = AdView(this,resources.getString(R.string.banner_facebook_id), AdSize.BANNER_HEIGHT_50)
        val adContainer = findViewById<LinearLayout>(R.id.banner_container)
        adContainer.removeAllViews()
        adContainer.addView(adView)

         adlistener  = object : AdListener {
            override fun onError(ad: Ad, adError: AdError) {
                // Ad error callback
                Toast.makeText(
                    this@MainActivity,
                    "Error: " + adError.errorCode.toString(),
                    Toast.LENGTH_LONG
                )
                    .show()
            }

            override fun onAdLoaded(ad: Ad) {
                // Ad loaded callback
            }

            override fun onAdClicked(ad: Ad) {
                // Ad clicked callback
            }

            override fun onLoggingImpression(ad: Ad) {
                // Ad impression logged callback
            }
        }
        // Request an ad


        adView.loadAd(adView.buildLoadAdConfig().withAdListener(adlistener).build())


        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.findNavController()


        setSupportActionBar(toolbar)
        setupActionBarWithNavController(navController)

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() or super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item.onNavDestinationSelected(navController) or super.onOptionsItemSelected(item)
    }

    fun getJsonFromURL(wantedURL: String): String {
        return URL(wantedURL).readText()
    }




}