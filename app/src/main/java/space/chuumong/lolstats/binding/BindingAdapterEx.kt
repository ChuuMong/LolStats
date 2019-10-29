package space.chuumong.lolstats.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

@BindingAdapter("loadCircleImage")
fun setLoadCircleImage(iv: ImageView, url: String?) {
    url ?: return

    Glide.with(iv.context).load(url).apply(RequestOptions().circleCrop()).into(iv)
}