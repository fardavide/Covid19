package studio.forface.covid.android.classic.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes

fun ViewGroup.inflate(@LayoutRes layoutId: Int, attachToRoot: Boolean = false) =
    LayoutInflater.from(context).inflate(layoutId, this, attachToRoot)!!

fun <V : View> V.onClick(clickListener: ViewClickListener<V>) =
    setOnClickListener { clickListener(this) }
