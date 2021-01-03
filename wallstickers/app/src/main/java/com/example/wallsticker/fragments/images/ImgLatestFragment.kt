package com.example.wallsticker.fragments.images

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.wallsticker.Adapters.ImagesAdapter
import com.example.wallsticker.ImagesViewModelFactory
import com.example.wallsticker.Interfaces.ImageClickListener
import com.example.wallsticker.Interfaces.ImagesApi
import com.example.wallsticker.Model.category
import com.example.wallsticker.Model.image
import com.example.wallsticker.R
import com.example.wallsticker.Repository.ImagesRepo
import com.example.wallsticker.Utilities.Const
import com.example.wallsticker.Utilities.interstitial
import com.example.wallsticker.ViewModel.ImagesViewModel
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
    private lateinit var imagesViewMode : ImagesViewModel
    //val progressBar: ProgressBar = this.progressBar2


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_img_latest, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        iniView(view)
        refresh.setOnRefreshListener {
            refresh.isRefreshing=true
            Const.ImagesTemp.clear()
            imagesViewMode.getImages(offset,null)
        }

        imagesViewMode.getImages(offset,null)
        imagesViewMode.images.observe(viewLifecycleOwner, {images->
            if (images.isSuccessful){
                images.body()?.let {
                    Const.ImagesTemp.addAll(it)
                    viewAdapter.notifyItemInserted(Const.ImagesTemp.size - 1)
                    progressBar.visibility = View.GONE
                    refresh.isRefreshing=false
                }
            }else{
                Toast.makeText(context, "Please Try Again", Toast.LENGTH_LONG).show()
            }
        })

        addScrollerListener()
        if (Const.ImagesTemp.size <= 0) {
            refresh.isRefreshing = true
            //fetchImages()
        }

        AdSettings.addTestDevice(resources.getString(R.string.addTestDevice))
        interstitialad = context?.let { interstitial(it) }!!
        interstitialad.loadInter()
        //Toast.makeText(context, Const.ImagesTemp.size.toString(), Toast.LENGTH_LONG).show()


    }

    private fun iniView(view: View){
        //init ViewModel
        val imagesRepo= ImagesRepo()
        val imageviewModelFactory= ImagesViewModelFactory(imagesRepo)
        imagesViewMode= ViewModelProvider(this,imageviewModelFactory).get(ImagesViewModel::class.java)

        viewManager = GridLayoutManager(activity, 3)
        layoutManager = LinearLayoutManager(activity)
        progressBar = view.findViewById(R.id.progress)
        refresh = view.findViewById(R.id.refreshLayout)

        viewAdapter = ImagesAdapter(this, Const.ImagesTemp, context)
        recyclerView = view.findViewById<RecyclerView>(R.id.images_recycler_view)
        recyclerView.adapter = viewAdapter
        recyclerView.layoutManager = viewManager
        recyclerView.setHasFixedSize(true)

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
                        //fetchImages()
                        imagesViewMode.getImages(offset,null)
                    }
                }
            }
        })
    }


    override fun onCatClicked(view: View, category: category, pos: Int) {
        //this lister for categoty clicked
    }


}