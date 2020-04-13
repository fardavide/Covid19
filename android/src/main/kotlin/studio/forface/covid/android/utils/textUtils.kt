package studio.forface.covid.android.utils

import android.graphics.Typeface.BOLD
import android.text.SpannableString
import android.text.style.StyleSpan

/**
 * @return [CharSequence] representation of the receiver [Int] where the chars before the last 3
 * are Bold
 */
fun Int.toStyledText(): CharSequence {
    return SpannableString(this.toString()).apply {
        val (start, end) = (0 to length - 3).takeIf { it.second > 0 } ?: return@apply
        setSpan(StyleSpan(BOLD), start, end, 0)
    }
}
