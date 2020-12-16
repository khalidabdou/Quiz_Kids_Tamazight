package com.example.wallsticker

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.wallsticker.Repository.ImagesRepo
import com.example.wallsticker.Repository.QuotesRepo
import com.example.wallsticker.ViewModel.ImagesViewModel
import com.example.wallsticker.ViewModel.QuotesViewModel

class ViewModelFactory(private val repo:QuotesRepo):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return QuotesViewModel(repo) as T
    }
}

class ImagesViewModelFactory(private val repo:ImagesRepo):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ImagesViewModel(repo) as T
    }
}


