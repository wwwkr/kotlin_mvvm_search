package com.example.kotlin_mvvm_search

import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.kotlin_mvvm_search.databinding.ActivityMainBinding
import com.example.kotlin_mvvm_search.databinding.ItemMainImageBinding
import com.squareup.picasso.Callback
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso
import java.lang.Exception
import com.bumptech.glide.load.model.LazyHeaders

import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.request.RequestOptions


class MainSearchRecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        val TAG = MainSearchRecyclerViewAdapter.javaClass.name
    }

    data class ImageItem(var imageUrl:String, var documentUrl:String)


    class ImageHolder(val binding : ItemMainImageBinding):RecyclerView.ViewHolder(binding.root) {



        fun onBind(item:ImageItem){
            itemView.run {

                //Glide 에 이미지가 로드되지 않는 경우 [https://upload.gettyimagesbank.com/thumb/202107/jv12315563.jpg]
                //위 url은 로드가 되지 않았다 검색을 해보니 캐시 관련 오류로 해당 설정을 해서 해결했다
                val options = RequestOptions().skipMemoryCache(true).diskCacheStrategy(
                    DiskCacheStrategy.NONE)

                Glide.with(context)
                    .load(item.imageUrl)
                    .override(300,300)
                    .apply(options)
                    .error(R.drawable.ic_image_black_24dp)
                    .listener(createLogListener())
                    .into(binding.itemMainImageView)




                binding.itemMainImageView.setOnClickListener {
                    ContextCompat.startActivity(context, Intent(Intent.ACTION_VIEW, Uri.parse(item.documentUrl)), null)
                }

            }
        }

        private fun createLogListener(): RequestListener<Drawable> {
            return object : RequestListener<Drawable> {

                // Image Load 실패 시 CallBack
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {

                    Log.e(TAG, " ERROR ${e?.message}")
                    return false
                }

                // Image Load 후 CallBack
                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    if (resource is BitmapDrawable) {
                        val bitmap = resource.bitmap
                        Log.d(
                            "Glide", String.format(
                                "bitmap %,d btyes, size: %d x %d",
                                bitmap.byteCount,		// 리사이징된 이미지 바이트
                                bitmap.width,			// 이미지 넓이
                                bitmap.height			// 이미지 높이
                            )
                        )
                    }
                    return false
                }
            }
        }

    }




    private val imageItemList = ArrayList<ImageItem>()

//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ImageHolder(parent)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : ImageHolder {

        val binding = ItemMainImageBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return ImageHolder(binding)

    }

    override fun getItemCount() = imageItemList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {


        (holder as? ImageHolder)?.onBind(imageItemList[position])


    }

    fun addImageItem(imageUrl: String, documentUrl: String){
        imageItemList.add(ImageItem(imageUrl, documentUrl))
    }

    fun removeAll(){
        imageItemList.clear()
    }


}