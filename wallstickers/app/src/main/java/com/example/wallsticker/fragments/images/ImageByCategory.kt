package com.example.wallsticker.fragments.images

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.wallsticker.Adapters.ImagesAdapter
import com.example.wallsticker.Interfaces.ImageClickListener
import com.example.wallsticker.Interfaces.ImagesApi
import com.example.wallsticker.Model.category
import com.example.wallsticker.Model.image
import com.example.wallsticker.R
import com.example.wallsticker.Utilities.Const
import kotlinx.android.synthetic.main.fragment_img_latest.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ImageByCategory : Fragment(), ImageClickListener {

    val args: ImageByCategoryArgs by navArgs()

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var progressBar: ProgressBar
    private lateinit var refresh: SwipeRefreshLayout
    lateinit var layoutManager: LinearLayoutManager
    var offset: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_img_latest, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val viewManager = GridLayoutManager(activity, 3)
        layoutManager = LinearLayoutManager(activity)
        refresh = view.findViewById(R.id.refreshLayout)
        refresh.setOnRefreshListener {
            fetchImages()
        }

        progressBar=view.findViewById(R.id.progress)

        viewAdapter = ImagesAdapter(this, Const.ImagesByCatTemp)
        recyclerView = view.findViewById<RecyclerView>(R.id.images_recycler_view)
        recyclerView.adapter = viewAdapter
        recyclerView.layoutManager = viewManager
        recyclerView.setHasFixedSize(true)
        addScrollerListener()
        if (Const.ImagesByCatTemp.size <= 0) {
            refreshLayout.isRefreshing = true
            fetchImages()
        }

    }


    private fun fetchImages() {

        ImagesApi().getImages(offset, "get_category_detail", args.CatId)
            .enqueue(object : Callback<List<image>> {
                override fun onFailure(call: Call<List<image>>, t: Throwable) {
                    refresh.isRefreshing = false
                    Toast.makeText(context, t.message, Toast.LENGTH_LONG).show()
                }

                override fun onResponse(
                    call: Call<List<image>>,
                    response: Response<List<image>>
                ) {

                    refresh.isRefreshing = false
                    val images = response.body()
                    images?.let {
                        Const.ImagesByCatTemp.addAll(it)
                        viewAdapter.notifyItemInserted(Const.ImagesByCatTemp.size - 1)
                        progressBar.visibility = View.GONE
                    }

                }
            })
    }


    override fun onImageClicked(view: View, image: image, pos: Int) {
        Const.arrayOf = "byCat"
        val action2 =
            ImageByCategoryDirections.actionImageByCategoryToImgSlider(
                pos
            )
        findNavController().navigate(action2)
    }


    private fun addScrollerListener() {
        //attaches scrollListener with RecyclerView
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {


            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)


                if (dy > 0) {
                    if (!recyclerView.canScrollVertically(RecyclerView.FOCUS_DOWN)) {
                        offset += 30
                        progressBar.visibility = View.VISIBLE
                        fetchImages()
                    }
                }


            }


        })
    }


    override fun onCatClicked(view: View, category: category, pos: Int) {
        //this lister for categoty clicked
    }

}