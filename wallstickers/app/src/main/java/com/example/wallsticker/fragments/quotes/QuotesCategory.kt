package com.example.wallsticker.fragments.quotes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.wallsticker.Adapters.CategoryAdapter
import com.example.wallsticker.Interfaces.ImageClickListener
import com.example.wallsticker.Model.category
import com.example.wallsticker.Model.image
import com.example.wallsticker.R
import com.example.wallsticker.Repository.QuotesRepo
import com.example.wallsticker.Utilities.Const
import com.example.wallsticker.ViewModel.QuotesViewModel
import com.example.wallsticker.ViewModelFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class QuotesCategory : Fragment(), ImageClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var refresh: SwipeRefreshLayout
    private lateinit var viewmodel: QuotesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quotes_category, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)



        val quotesRepo=QuotesRepo()
        val viewModelFactory=ViewModelFactory(quotesRepo)
        viewmodel=ViewModelProvider(this,viewModelFactory).get(QuotesViewModel::class.java)


        refresh.setOnRefreshListener {
            viewmodel.getQuotesCategories()
        }

        if (Const.QuotesCategories.size <= 0) {
            viewmodel.getQuotesCategories()
        }

        viewmodel.quotesCategories.observe(viewLifecycleOwner,  { response->
            if(response.isSuccessful){
                refresh.isRefreshing=false
                //Toast.makeText(context, "success", Toast.LENGTH_SHORT).show()
                response.body()?.let {
                    Const.QuotesCategories.clear()
                    Const.QuotesCategories.addAll(it)
                    viewAdapter.notifyDataSetChanged()
                }
            }else {
                Toast.makeText(context, "Please Try Again", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onImageClicked(view: View, image: image, pos: Int) {
    }

    override fun onCatClicked(view: View, category: category, pos: Int) {
        val GoToQuotesByCategory =
            HomeQuotesDirections.actionHomeQuotesToQuotesByCategory(category.id)
        findNavController().navigate(GoToQuotesByCategory)
    }

    private fun initView(view: View){
        refresh = view.findViewById(R.id.refreshLayout)
        recyclerView = view.findViewById<RecyclerView>(R.id.cat_quotes_recycler_view)
        viewAdapter = CategoryAdapter(Const.QuotesCategories, this)
        viewManager = GridLayoutManager(activity, 1)
        //RecycleView
        recyclerView.adapter = viewAdapter
        recyclerView.layoutManager = viewManager
        recyclerView.setHasFixedSize(true)

    }
}