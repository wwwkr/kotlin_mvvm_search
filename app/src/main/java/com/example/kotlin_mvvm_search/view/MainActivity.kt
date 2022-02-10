package com.example.kotlin_mvvm_search.view

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.kotlin_mvvm_search.MainSearchRecyclerViewAdapter
import com.example.kotlin_mvvm_search.R
import com.example.kotlin_mvvm_search.base.BaseKotlinActivity
import com.example.kotlin_mvvm_search.viewmodel.MainViewModel
import com.example.kotlin_mvvm_search.databinding.ActivityMainBinding
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseKotlinActivity<ActivityMainBinding, MainViewModel>(){

    override val layoutResourceId: Int
        get() = R.layout.activity_main
    override val viewModel: MainViewModel by viewModel()

    private val mainSearchRecyclerViewAdapter: MainSearchRecyclerViewAdapter by inject()



    override fun initStartView() {
        viewDataBinding.mainActivitySearchRecyclerView.run {
            adapter = mainSearchRecyclerViewAdapter
            layoutManager = StaggeredGridLayoutManager(3, 1).apply {
                gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS
                orientation = StaggeredGridLayoutManager.VERTICAL
            }
            setHasFixedSize(true)
        }

    }

    override fun initDataBinding() {
        viewModel.imageSearchResponseLiveData.observe(this, Observer {

            mainSearchRecyclerViewAdapter.removeAll()

            it.documents.forEach {document ->
                mainSearchRecyclerViewAdapter.addImageItem(document.image_url, document.doc_url)
            }


            mainSearchRecyclerViewAdapter.notifyDataSetChanged()
        })
    }

    override fun initAfterBinding() {
        viewDataBinding.mainActivitySearchButton.setOnClickListener {
            viewModel.getImageSearch(viewDataBinding.mainActivitySearchEditText.text.toString(), 1, 80)
            viewModel.showSnackbarStr(viewDataBinding.mainActivitySearchEditText.text.toString())
        }
    }

}