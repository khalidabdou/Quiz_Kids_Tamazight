package com.example.wallsticker.ViewModel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.wallsticker.Model.category
import com.example.wallsticker.Model.image
import com.example.wallsticker.Repository.ImagesRepo
import com.example.wallsticker.Utilities.NetworkResults
import com.example.wallsticker.data.databsae.ImageEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

class ImagesViewModel @ViewModelInject constructor(
    private val imageRepo: ImagesRepo, application: Application
) : AndroidViewModel(application) {
    /**ROOM DATABASE**/
    val readImages: LiveData<List<ImageEntity>> = imageRepo.local.readdatabase().asLiveData()

    private fun insert(imageEntity: ImageEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            imageRepo.local.insertImage(imageEntity)
        }

    /** RETROFIT **/
    var imagesCategories: MutableLiveData<Response<List<category>>> = MutableLiveData()
    var images: MutableLiveData<NetworkResults<List<image>>> = MutableLiveData()

    fun getImagesCategories() {
        viewModelScope.launch {

            val categoreisRespo = imageRepo.getImagescategories()
            imagesCategories.value = categoreisRespo
        }
    }


    fun getImages(offset: Int, id: Int?) {
        viewModelScope.launch {
            getImagesSafeCall(offset, id)

        }
    }

    private suspend fun getImagesSafeCall(offset: Int, id: Int?) {
        images.value = NetworkResults.Loading()
        if (hasInternetConnection()) {
            try {
                val imagesResponse = imageRepo.remot.getImages(offset, id)
                images.value = handlImagesResponse(imagesResponse)

                val imageCache = images.value!!.data
                if (imageCache != null) {
                   // offlineCacheImages(imageCache)
                }
            } catch (ex: Exception) {
                images.value = NetworkResults.Error(ex.message)
            }

        } else {
            images.value = NetworkResults.Error("No Internet Connection")
        }
    }

    private fun offlineCacheImages(imageCache: image) {
       // val imageEntity:ImageEntity(imageCache)
    }

    private fun handlImagesResponse(imagesResponse: Response<List<image>>): NetworkResults<List<image>>? {
        when {
            imagesResponse.message().toString()
                .contains("Timeout") -> return NetworkResults.Error("Timeout")
            imagesResponse.code() == 402 -> return NetworkResults.Error("Api KEy Limited.")
            imagesResponse.body().isNullOrEmpty() -> return NetworkResults.Error("No Data Found")
            imagesResponse.isSuccessful -> {
                val images = imagesResponse.body()
                return NetworkResults.Success(images!!)
            }
            else -> return NetworkResults.Error(imagesResponse.message())
        }
    }


    fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<Application>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilites = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when {
            capabilites.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilites.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilites.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }


}