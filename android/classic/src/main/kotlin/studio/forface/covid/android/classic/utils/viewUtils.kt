package studio.forface.covid.android.classic.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import studio.forface.covid.android.classic.R
import studio.forface.covid.android.ui.BaseActivity
import studio.forface.viewstatestore.ViewState
import com.google.android.material.snackbar.Snackbar as AndroidSnackbar

// region ViewGroup
fun ViewGroup.inflate(@LayoutRes layoutId: Int, attachToRoot: Boolean = false) =
    LayoutInflater.from(context).inflate(layoutId, this, attachToRoot)!!
// endregion

// region View
fun <V : View> V.onClick(clickListener: ViewClickListener<V>) =
    setOnClickListener { clickListener(this) }
// endregion

// region Snackbar
val DEFAULT_SNACKBAR_DURATION = SnackbarDuration.SHORT

fun Snackbar(view: View, text: CharSequence, duration: SnackbarDuration = DEFAULT_SNACKBAR_DURATION) =
    AndroidSnackbar.make(view, text, duration.androidValue)

fun Snackbar(view: View, @StringRes resId: Int, duration: SnackbarDuration = DEFAULT_SNACKBAR_DURATION) =
    AndroidSnackbar.make(view, resId, duration.androidValue)

fun Snackbar(view: View, error: ViewState.Error, duration: SnackbarDuration = DEFAULT_SNACKBAR_DURATION) =
    Snackbar(view, error.getMessage(view.context), duration)

fun BaseActivity.showSnackbar(
    text: CharSequence,
    duration: SnackbarDuration = DEFAULT_SNACKBAR_DURATION,
    config: AndroidSnackbar.() -> Unit = {}
) = Snackbar(findViewById(R.id.coordinator_layout), text, duration).apply(config).show()

fun BaseActivity.showSnackbar(
    @StringRes resId: Int,
    duration: SnackbarDuration = DEFAULT_SNACKBAR_DURATION,
    config: AndroidSnackbar.() -> Unit = {}
) = Snackbar(findViewById(R.id.coordinator_layout), resId, duration).apply(config).show()

fun BaseActivity.showSnackbar(
    error: ViewState.Error,
    duration: SnackbarDuration = DEFAULT_SNACKBAR_DURATION,
    config: AndroidSnackbar.() -> Unit = {}
) = Snackbar(findViewById(R.id.coordinator_layout), error, duration).apply(config).show()

enum class SnackbarDuration(val androidValue: Int) {
    SHORT(AndroidSnackbar.LENGTH_SHORT),
    LONG(AndroidSnackbar.LENGTH_LONG),
    INDEFINITE(AndroidSnackbar.LENGTH_INDEFINITE)
}
// endregion
