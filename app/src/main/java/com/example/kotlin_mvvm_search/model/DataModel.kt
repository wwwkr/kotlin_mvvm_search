package com.example.kotlin_mvvm_search.model

import com.example.kotlin_mvvm_search.model.enum.KaKaoSearchSortEnum
import com.example.kotlin_mvvm_search.model.response.ImageSearchResponse
import io.reactivex.Single

interface DataModel {
    fun getData(query :String, sort : KaKaoSearchSortEnum, page:Int, size:Int): Single<ImageSearchResponse>


}

