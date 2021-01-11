package com.example.wallsticker

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.CompoundButton
import android.widget.LinearLayout
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.*
import com.facebook.ads.*
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var navController: NavController
    private var adlistener: AdListener? = null
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var adView: AdView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        drawerLayout = findViewById(R.id.drawerLayout)



        //init facebook audience
        AudienceNetworkAds.initialize(this)
        AdSettings.addTestDevice(resources.getString(R.string.addTestDevice))
        adView =
            AdView(this, resources.getString(R.string.banner_facebook_id), AdSize.BANNER_HEIGHT_50)
        val adContainer = findViewById<LinearLayout>(R.id.banner_container)
        adContainer.removeAllViews()
        adContainer.addView(adView)

        adlistener = object : AdListener {
            override fun onError(ad: Ad, adError: AdError) {

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



        navController = navHostFragment.findNavController()
        appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)
        navigationView.setupWithNavController(navController)
        setSupportActionBar(toolbar)
        setupActionBarWithNavController(navController, appBarConfiguration)




        

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.rate) {
            openAppInStore()
        }
        return item.onNavDestinationSelected(navController) or super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        adView.destroy()
        super.onDestroy()

    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        Toast.makeText(applicationContext,item.itemId.toString(),Toast.LENGTH_LONG).show()
        when (item.itemId) {
            R.id.rate -> {
                openAppInStore()
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    private fun openAppInStore() {
        val appPackageName =
            packageName // getPackageName() from Context or Activity object

        try {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=$appPackageName")
                )
            )
        } catch (anfe: ActivityNotFoundException) {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")
                )
            )
        }
    }


    private fun chooseThemeDialog() {

        val builder = AlertDialog.Builder(this)
        builder.setTitle("chose the")
        val styles = arrayOf("Light","Dark","System default")
        val checkedItem = 0

        builder.setSingleChoiceItems(styles, checkedItem) { dialog, which ->

            when (which) {
                0 -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    delegate.applyDayNight()
                    dialog.dismiss()
                }
                1 -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    delegate.applyDayNight()

                    dialog.dismiss()
                }
                2 -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                    delegate.applyDayNight()
                    dialog.dismiss()
                }

            }
        }

        val dialog = builder.create()
        dialog.show()
    }

}


