package com.example.wallsticker.fragments.images

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
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
import com.example.wallsticker.Utilities.interstitial
import com.facebook.ads.AdSettings
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ImgLatestFragment : Fragment(), ImageClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var progressBar: ProgressBar
    private var isLoading: Boolean = false
    lateinit var layoutManager: LinearLayoutManager
    private lateinit var refresh: SwipeRefreshLayout
    private lateinit var interstitialad: interstitial
    var offset: Int = 0
    //val progressBar: ProgressBar = this.progressBar2


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_img_latest, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewManager = GridLayoutManager(activity, 3)
        layoutManager = LinearLayoutManager(activity)



        refresh = view.findViewById(R.id.refreshLayout)
        refresh.setOnRefreshListener {
            fetchImages()
        }

        progressBar = view.findViewById(R.id.progress)

        viewAdapter = ImagesAdapter(this, Const.ImagesTemp, context)
        recyclerView = view.findViewById<RecyclerView>(R.id.images_recycler_view)
        recyclerView.adapter = viewAdapter
        recyclerView.layoutManager = viewManager
        recyclerView.setHasFixedSize(true)
        addScrollerListener()
        if (Const.ImagesTemp.size <= 0) {
            refresh.isRefreshing = true
            fetchImages()
        }

        AdSettings.addTestDevice(resources.getString(R.string.addTestDevice))
        interstitialad = context?.let { interstitial(it) }!!
        interstitialad.loadInter()
        //Toast.makeText(context, interstitialad.hashCode().toString(), Toast.LENGTH_LONG).show()

    }


    private fun fetchImages() {

        ImagesApi().getImages(offset).enqueue(object : Callback<List<image>> {
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
                    Const.ImagesTemp.addAll(it)
                    viewAdapter.notifyItemInserted(Const.ImagesTemp.size - 1)
                    progressBar.visibility = View.GONE


                }

            }
        })
    }


    override fun onImageClicked(view: View, image: image, pos: Int) {
        //val action = HomeFragmentDirections.actionHomeFragmentToLoginFragment()

        Const.INCREMENT_COUNTER++
        if (Const.INCREMENT_COUNTER % Const.COUNTER_AD_SHOW == 0)
            interstitialad.showInter()
        else {
            Const.arrayOf = "latest"
            val action2 =
                ImagesFragmentDirections.actionImagesFragmentToImgSlider(
                    pos
                )
            findNavController().navigate(action2)
        }
        //Toast.makeText(context,image.image_id.toString(),Toast.LENGTH_LONG).show()
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