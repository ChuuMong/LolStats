package space.chuumong.data.remote.model

import com.google.gson.annotations.SerializedName
import space.chuumong.domain.entities.ImageUrl

data class ImageUrlResponse(
    @SerializedName("imageUrl")
    val imageUrl: String
) {
    fun toEntity() = ImageUrl(imageUrl)
}