package studio.forface.covid.android.classic.utils

import android.content.Context
import android.content.Intent
import androidx.core.os.bundleOf
import studio.forface.covid.android.ui.BaseActivity

/** Start Activity with [Intent] of class of [BaseActivity] [A] */
inline fun <reified A : BaseActivity> Context.startActivity(vararg extras: Pair<String, Any>) =
    startActivity(intentFor<A>().putExtras(bundleOf(*extras)))

/** @return class [Intent] for [BaseActivity] [A] */
inline fun <reified A : BaseActivity> Context.intentFor() = Intent(this, A::class.java)
