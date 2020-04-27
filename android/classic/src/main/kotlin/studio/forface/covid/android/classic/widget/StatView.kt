package studio.forface.covid.android.classic.widget

import android.annotation.SuppressLint
import android.content.Context
import android.text.SpannableStringBuilder
import android.util.AttributeSet
import androidx.annotation.StyleRes
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.item_stat.view.*
import studio.forface.covid.android.classic.R
import studio.forface.covid.android.classic.utils.inflate
import studio.forface.covid.android.utils.toStyledText
import studio.forface.covid.domain.util.takeIfGreaterThanZero

/**
 * A View representing a Stat
 * Inherit from [ConstraintLayout]
 *
 * @author Davide Farella
 */
class StatView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    @StyleRes defStyleRes: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr, defStyleRes) {

    init {
        inflate(R.layout.item_stat, attachToRoot = true)
    }

    fun setStat(
        infected: Int,
        infectedDiff: Int,
        deaths: Int,
        deathsDiff: Int,
        recovered: Int,
        recoveredDiff: Int
    ) {
        // Data
        infected_data_text.text = infected.toStyledText()
        deaths_data_text.text = deaths.toStyledText()
        recovered_data_text.text = recovered.toStyledText()

        // Diff
        deaths_diff_text.text = deathsDiff.toDiffStyledText()
        infected_diff_text.text = infectedDiff.toDiffStyledText()
        recovered_diff_text.text = recoveredDiff.toDiffStyledText()
    }

    private fun Int.toDiffStyledText() = with(SpannableStringBuilder()) {
            takeIfGreaterThanZero()?.let {
                append("+ ")
                append(toStyledText())
            }
        }
}
