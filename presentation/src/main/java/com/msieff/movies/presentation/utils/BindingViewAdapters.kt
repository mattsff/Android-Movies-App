package com.msieff.movies.presentation.utils


import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions


@BindingAdapter("goneIfNull")
internal fun View.goneIfNull(data: Any?) {
    visibility = if (data == null) {
        View.GONE
    } else {
        View.VISIBLE
    }
}

@BindingAdapter("goneIfTrue")
internal fun View.goneIfTrue(data: Boolean) {
    visibility = if (data) {
        View.GONE
    } else {
        View.VISIBLE
    }
}

@BindingAdapter("invisibleIfTrue")
internal fun View.invisibleIfTrue(value: Boolean) {
    visibility = if (value) {
        View.INVISIBLE
    } else {
        View.VISIBLE
    }
}

@BindingAdapter("visibleIfTrue")
internal fun View.visibleIfTrue(value: Boolean) {
    visibility = if (value) {
        View.VISIBLE
    } else {
        View.INVISIBLE
    }
}

@BindingAdapter("goneIfFalse")
internal fun View.goneIfFalse(value: Boolean) {
    visibility = if (value) {
        View.VISIBLE
    } else {
        View.GONE
    }
}

@BindingAdapter(
    value = ["glideSrc", "glidePlaceHolder"],
    requireAll = false
)
internal fun ImageView.glideSrc(
    src: String?,
    placeholder: Drawable?
) {
    var args = RequestOptions()

    if (placeholder != null) {
        args = args.placeholder(placeholder)
    }

    Glide.with(this)
        .load(src)
        .apply(args)
        .into(this)
}
