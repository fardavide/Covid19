package studio.forface.covid.android.classic.widget

import android.content.Context
import android.text.SpannableStringBuilder
import android.util.AttributeSet
import androidx.annotation.StyleRes
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.item_stat.view.*
import studio.forface.covid.android.classic.R
import studio.forface.covid.android.classic.utils.inflate
import studio.forface.covid.android.model.StatUiModel
import studio.forface.covid.android.utils.toThousandsEmphasizedText
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

    fun setStat(stat: StatUiModel) {
        with(stat) {
            // Data
            infected_data_text.text = infectedCount.toThousandsEmphasizedText()
            deaths_data_text.text = deathsCount.toThousandsEmphasizedText()
            recovered_data_text.text = recoveredCount.toThousandsEmphasizedText()

            // Diff
            deaths_diff_text.text = deathsDiff.toDiffThousandsEmphasizedText()
            infected_diff_text.text = infectedDiff.toDiffThousandsEmphasizedText()
            recovered_diff_text.text = recoveredDiff.toDiffThousandsEmphasizedText()

            // Timestamp
            timestamp_text.text = lastUpdatedTime
        }
    }

    private fun Int.toDiffThousandsEmphasizedText() = with(SpannableStringBuilder()) {
        takeIfGreaterThanZero()?.let {
            append("+ ")
            append(toThousandsEmphasizedText())
        }
    }
}
