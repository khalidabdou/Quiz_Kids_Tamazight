package com.example.wallsticker.fragments.quotes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.wallsticker.Adapters.ImgPagerAdapter
import com.example.wallsticker.R
import com.google.android.material.tabs.TabLayout

class HomeQuotes : Fragment() {

    private lateinit var viewPager: ViewPager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home_quotes, container, false)
    }


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


        adapter.addFragment(QuotesLatest(), "Latest Quotes")
        adapter.addFragment(QuotesCategory(), "Categories")
        adapter.addFragment(FavoriteQuotes(), "Favorites")
        // setting adapter to view pager.
        viewpager.adapter = adapter
    }

}