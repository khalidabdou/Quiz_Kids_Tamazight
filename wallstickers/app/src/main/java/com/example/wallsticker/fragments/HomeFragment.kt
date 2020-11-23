package com.example.wallsticker.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.wallsticker.R
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment(R.layout.fragment_home) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


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


    }


}