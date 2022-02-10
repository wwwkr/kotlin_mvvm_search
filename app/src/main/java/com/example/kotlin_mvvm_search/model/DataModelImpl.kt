package com.example.kotlin_mvvm_search.model

import com.example.kotlin_mvvm_search.model.enum.KaKaoSearchSortEnum
import com.example.kotlin_mvvm_search.model.response.ImageSearchResponse
import com.example.kotlin_mvvm_search.model.service.KakaoSearchService
import io.reactivex.Single

class DataModelImpl(private val service : KakaoSearchService) : DataModel{

    private val KAKAO_APP_KEY = "8c99dd88bce8d36944c6c5cbfbb98049"

    override fun getData(query: String, sort: KaKaoSearchSortEnum, page:Int, size:Int) : Single<ImageSearchResponse> {

        return service.searchImage(auth = "KakaoAK $KAKAO_APP_KEY", query = query, sort = sort.sort , page = page, size = size)
    }



}