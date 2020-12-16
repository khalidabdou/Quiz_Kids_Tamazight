package com.example.wallsticker.fragments.images

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.wallsticker.Adapters.CategoryAdapter
import com.example.wallsticker.ImagesViewModelFactory
import com.example.wallsticker.Interfaces.ImageClickListener
import com.example.wallsticker.MainViewModel
import com.example.wallsticker.Model.category
import com.example.wallsticker.Model.image
import com.example.wallsticker.R
import com.example.wallsticker.Repository.ImagesRepo
import com.example.wallsticker.Repository.QuotesRepo
import com.example.wallsticker.Utilities.Const
import com.example.wallsticker.ViewModel.ImagesViewModel
import com.example.wallsticker.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_img_category.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ImgCategoryFragment : Fragment(), ImageClickListener {


    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var refresh: SwipeRefreshLayout
    private lateinit var imagesViewMode :ImagesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_img_category, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initView(view)

        refreshLayout.setOnRefreshListener {
            fetchCategories()
        }


        if (Const.CatImages.size <= 0) {
            refresh.isRefreshing = true
            fetchCategories()
        }

        imagesViewMode.getImagesCategories()
        imagesViewMode.imagesCategories.observe(viewLifecycleOwner,  { response->
            if(response.isSuccessful){
                refresh.isRefreshing=false
                //Toast.makeText(context, "success", Toast.LENGTH_SHORT).show()
                response.body()?.let {
                    Const.CatImages.clear()
                    Const.CatImages.addAll(it)
                    viewAdapter.notifyDataSetChanged()
                }
            }else {
                Toast.makeText(context, "Please Try Again", Toast.LENGTH_SHORT).show()
            }
        })
    }


    private fun initView(view: View){
        val imagesRepo= ImagesRepo()
        val imageviewModelFactory= ImagesViewModelFactory(imagesRepo)
        imagesViewMode=ViewModelProvider(this,imageviewModelFactory).get(ImagesViewModel::class.java)

        viewManager = GridLayoutManager(activity, 1)
        refresh = view.findViewById(R.id.refreshLayout)
        viewAdapter = CategoryAdapter(Const.CatImages, this)
        recyclerView = view.findViewById(R.id.CatImg_recycler_view)
        recyclerView.adapter = viewAdapter
        recyclerView.layoutManager = viewManager
        recyclerView.setHasFixedSize(true)
    }



    private fun fetchCategories() {

    /*    CategoriesImageApi().getCategories().enqueue(object : Callback<List<category>> {
            override fun onFailure(call: Call<List<category>>, t: Throwable) {
                refresh.isRefreshing = false
                Toast.makeText(context, t.message, Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                call: Call<List<category>>,
                response: Response<List<category>>
            ) {

                refresh.isRefreshing = false
                val categories = response.body()
                categories?.let {
                    Const.CatImages.clear()
                    Const.CatImages.addAll(it)
                    viewAdapter.notifyItemInserted(Const.ImagesTemp.size - 1)

                }

            }
        })*/
    }


    override fun onCatClicked(view: View, category: category, pos: Int) {
        Const.ImagesByCatTemp.clear()
        Const.arrayOf = "byCat"
        val actionToImageByCat =
            ImagesFragmentDirections.actionImagesFragmentToImageByCategory(
                category.id
            )
        findNavController().navigate(actionToImageByCat)
    }

    override fun onImageClicked(view: View, image: image, pos: Int) {
        //this for image clicked
    }

}