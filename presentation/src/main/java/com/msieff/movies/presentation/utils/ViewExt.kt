import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.msieff.movies.presentation.R
import com.msieff.movies.domain.model.Category

fun ImageView.load(url: String?) {
    Glide.with(this)
        .load(url)
        .placeholder(R.color.placeholder)
        .into(this)
}

fun Category.localizedName(context: Context): String {
    return when(this){
        Category.TOP_RATED -> context.getString(R.string.category_top_rated)
        Category.POPULAR -> context.getString(R.string.category_popular)
    }
}