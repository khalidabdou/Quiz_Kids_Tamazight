package com.example.wallsticker.fragments.images

import android.os.Bundle
import android.provider.BaseColumns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wallsticker.Adapters.ImagesAdapter
import com.example.wallsticker.Interfaces.ImageClickListener
import com.example.wallsticker.Model.category
import com.example.wallsticker.Model.image
import com.example.wallsticker.Model.quote
import com.example.wallsticker.R
import com.example.wallsticker.Utilities.Const
import com.example.wallsticker.Utilities.FeedReaderContract
import com.example.wallsticker.Utilities.helper


class FavoriteImages : Fragment(), ImageClickListener {


    private var itemIds: ArrayList<quote>? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var nofav: LinearLayout

    val projection = arrayOf(BaseColumns._ID, FeedReaderContract.FeedEntryImage.COLUMN_NAME_IMAGE)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_images_fav, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        nofav = view.findViewById(R.id.nofav)
        recyclerView = view.findViewById<RecyclerView>(R.id.fav_images_recycler_view)
        viewManager = GridLayoutManager(activity, 3)
        viewAdapter = ImagesAdapter(this, Const.ImageTempFav, context)
        recyclerView.adapter = viewAdapter
        recyclerView.layoutManager = viewManager
        recyclerView.setHasFixedSize(true)



        getFavImages()


    }


    private fun getFavImages() {
        Const.ImageTempFav.clear()
        val dbHelper = context?.let { helper(it) }
        val db = dbHelper?.readableDatabase
        val cursor = db?.query(
            FeedReaderContract.FeedEntryImage.TABLE_NAME,   // The table to query
            projection,             // The array of columns to return (pass null to get all)
            null,              // The columns for the WHERE clause
            null,          // The values for the WHERE clause
            null,                   // don't group the rows
            null,                   // don't filter by row groups
            null               // The sort order
        )

        with(cursor) {
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    val itemId = this?.getInt(getColumnIndexOrThrow(BaseColumns._ID))
                    val image =
                        this?.getString(getColumnIndexOrThrow(FeedReaderContract.FeedEntryImage.COLUMN_NAME_IMAGE))
                    val imageOb = image(itemId, image, 0, 0, 1)
                    Const.ImageTempFav.add(imageOb)

                }
            }
            if (Const.ImageTempFav.size <= 0)
                nofav.visibility = View.VISIBLE
            else nofav.visibility = View.GONE
        }
        viewAdapter.notifyDataSetChanged()
        //Toast.makeText(context, itemIds!!.count().toString(),Toast.LENGTH_LONG).show()
    }

    override fun onImageClicked(view: View, image: image, pos: Int) {
        Const.arrayOf = "fav"
        val action2 =
            ImagesFragmentDirections.actionImagesFragmentToImgSlider(
                pos
            )
        findNavController().navigate(action2)
    }

    override fun onCatClicked(view: View, category: category, pos: Int) {
        //don't override this it's for category adapter
    }


//    override fun onFavClicked(image: image, pos: Int) {
//        Const.isFavChanged = true
//        val dbHelper = context?.let { helper(it) }
//        val db = dbHelper?.writableDatabase
//        if (image.isfav == 1) {
//            val selection = "${BaseColumns._ID} like ?"
//            val selectionArgs = arrayOf(image.image_id.toString())
//            val deletedRows =
//                db?.delete(FeedReaderContract.FeedEntryImage.TABLE_NAME, selection, selectionArgs)
//            image.isfav = 0
//            Const.ImageTempFav.remove(image)
//            viewAdapter.notifyItemRemoved(pos)
//            Toast.makeText(context, deletedRows.toString(), Toast.LENGTH_LONG).show()
//        }
//
//    }
}