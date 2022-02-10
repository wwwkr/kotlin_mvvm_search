package com.example.kotlin_mvvm_search.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.kotlin_mvvm_search.base.BaseKotlinViewModel
import com.example.kotlin_mvvm_search.model.DataModel
import com.example.kotlin_mvvm_search.model.response.ImageSearchResponse
import com.example.kotlin_mvvm_search.model.enum.KaKaoSearchSortEnum
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainViewModel(private val model: DataModel): BaseKotlinViewModel() {

    private val TAG : String = "MainViewModel"

    private val _imageSearchResponseLivaData = MutableLiveData<ImageSearchResponse>()

    val imageSearchResponseLiveData: LiveData<ImageSearchResponse>
        get() = _imageSearchResponseLivaData

    fun getImageSearch(query: String, page:Int, size:Int){

        addDisposable(model.getData(query, KaKaoSearchSortEnum.Accuracy, page, size)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                it.run {
                    if(documents.size > 0){
                        Log.d(TAG, "documents : $documents")
                        _imageSearchResponseLivaData.postValue(this)
                    }
                    Log.d(TAG, "meta : $meta")
                }
            }, {
                Log.d(TAG , "respoinse error, message : ${it.message}")
            })
        )
    }


}