package studio.forface.covid.android.classic.utils

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.os.bundleOf
import studio.forface.covid.android.ui.BaseActivity

/** Start Activity with [Intent] of class of [BaseActivity] [A] */
inline fun <reified A : BaseActivity> Context.startActivity(vararg extras: Pair<String, Any>) =
    startActivity<A>(bundleOf(*extras))

/** Start Activity with [Intent] of class of [BaseActivity] [A] */
inline fun <reified A : BaseActivity> Context.startActivity(extras: Bundle) =
    startActivity(intentFor<A>(extras))

/** @return class [Intent] for [BaseActivity] [A] */
inline fun <reified A : BaseActivity> Context.intentFor(vararg extras: Pair<String, Any>) =
    intentFor<A>(bundleOf(*extras))

/** @return class [Intent] for [BaseActivity] [A] */
inline fun <reified A : BaseActivity> Context.intentFor(extras: Bundle) =
    Intent(this, A::class.java).putExtras(extras)
