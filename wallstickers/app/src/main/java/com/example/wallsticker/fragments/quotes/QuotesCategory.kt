package com.example.wallsticker.fragments.quotes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.wallsticker.Adapters.CategoryAdapter
import com.example.wallsticker.Interfaces.CategoriesApi
import com.example.wallsticker.Interfaces.ImageClickListener
import com.example.wallsticker.Model.category
import com.example.wallsticker.Model.image
import com.example.wallsticker.R
import com.example.wallsticker.Utilities.Const
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class QuotesCategory : Fragment(), ImageClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var refresh: SwipeRefreshLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quotes_category, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        refresh = view.findViewById(R.id.refreshLayout)

        viewManager = GridLayoutManager(activity, 1)
        refresh.setOnRefreshListener {
            fetchCategories()
        }

        viewAdapter = CategoryAdapter(Const.QuotesCategories, this)
        recyclerView = view.findViewById<RecyclerView>(R.id.cat_quotes_recycler_view)
        recyclerView.adapter = viewAdapter
        recyclerView.layoutManager = viewManager
        recyclerView.setHasFixedSize(true)

        if (Const.QuotesCategories.size <= 0) {
            refresh.isRefreshing = true
            fetchCategories()
        }
    }

    override fun onImageClicked(view: View, image: image, pos: Int) {

    }

    override fun onCatClicked(view: View, category: category, pos: Int) {
        val GoToQuotesByCategory =
            HomeQuotesDirections.actionHomeQuotesToQuotesByCategory(category.id)
        findNavController().navigate(GoToQuotesByCategory)
    }

    private fun fetchCategories() {

        CategoriesApi().getCategories().enqueue(object : Callback<List<category>> {
            override fun onFailure(call: Call<List<category>>, t: Throwable) {
                refresh.isRefreshing = false
                //Toast.makeText(context, t.message, Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                call: Call<List<category>>,
                response: Response<List<category>>
            ) {

                refresh.isRefreshing = false
                val categories = response.body()
                categories?.let {
                    Const.QuotesCategories.clear()
                    Const.QuotesCategories.addAll(it)
                    viewAdapter.notifyDataSetChanged()

                }

            }
        })
    }
}