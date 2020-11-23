package com.example.wallsticker.fragments.images

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.wallsticker.Adapters.ImgPagerAdapter
import com.example.wallsticker.R
import com.google.android.material.tabs.TabLayout

class ImagesFragment : Fragment(R.layout.fragment_images) {


    private lateinit var viewPager: ViewPager

    @Override
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var tablayout = view.findViewById<TabLayout>(R.id.taps)
        viewPager = view.findViewById(R.id.viewPager)

        setupViewPager(viewPager)
        tablayout.setupWithViewPager(viewPager)


    }

    private fun setupViewPager(viewpager: ViewPager) {
        var adapter: ImgPagerAdapter = ImgPagerAdapter(childFragmentManager)

        // LoginFragment is the name of Fragment and the Login
        // is a title of tab


        adapter.addFragment(ImgLatestFragment(), "Latest")
        adapter.addFragment(ImgCategoryFragment(), "Categories")
        adapter.addFragment(FavoriteImages(), "Favorites")
        // setting adapter to view pager.
        viewpager.adapter = adapter
    }
}