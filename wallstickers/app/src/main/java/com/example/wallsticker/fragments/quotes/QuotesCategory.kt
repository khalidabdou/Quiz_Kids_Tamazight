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
import com.example.wallsticker.Adapters.CategoryAdapter
import com.example.wallsticker.Interfaces.CategoriesApi
import com.example.wallsticker.Interfaces.ImageClickListener
import com.example.wallsticker.Model.category
import com.example.wallsticker.Model.image
import com.example.wallsticker.R
import com.example.wallsticker.Utilities.Const
import kotlinx.android.synthetic.main.fragment_img_category.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class QuotesCategory : Fragment(), ImageClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quotes_category, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewManager = GridLayoutManager(activity, 1)
        refreshLayout.setOnRefreshListener {
            fetchCategories()
        }

        viewAdapter = CategoryAdapter(Const.CatImages, this)
        recyclerView = view.findViewById<RecyclerView>(R.id.cat_quotes_recycler_view)
        recyclerView.adapter = viewAdapter
        recyclerView.layoutManager = viewManager
        recyclerView.setHasFixedSize(true)

        if (Const.CatImages.size <= 0) {
            refreshLayout.isRefreshing = true
            fetchCategories()
        }
    }

    override fun onImageClicked(view: View, image: image, pos: Int) {

    }

    override fun onCatClicked(view: View, category: category, pos: Int) {
        val GoToQuotesByCategory =
            HomeQuotesDirections.actionHomeQuotesToQuotesByCategory(category.category_id)
        findNavController().navigate(GoToQuotesByCategory)
    }

    private fun fetchCategories() {

        CategoriesApi().getCategories().enqueue(object : Callback<List<category>> {
            override fun onFailure(call: Call<List<category>>, t: Throwable) {
                refreshLayout.isRefreshing = false
                Toast.makeText(context, t.message, Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                call: Call<List<category>>,
                response: Response<List<category>>
            ) {

                //refreshLayout.isRefreshing = false
                val categories = response.body()
                categories?.let {
                    Const.CatImages.clear()
                    Const.CatImages.addAll(it)
                    viewAdapter.notifyItemInserted(Const.ImagesTemp.size - 1)

                }

            }
        })
    }
}