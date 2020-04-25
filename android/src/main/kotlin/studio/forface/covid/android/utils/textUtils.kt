package studio.forface.covid.android.utils

import android.graphics.Typeface.BOLD
import android.text.SpannableString
import android.text.style.StyleSpan

/**
 * @return [CharSequence] representation of the receiver [Int] where the chars before the last 3
 * are Bold
 */
fun Int.toStyledText(): CharSequence {
    val charsToStyle = 3
    return SpannableString(this.toString()).apply {
        val (start, end) = (0 to length - charsToStyle).takeIf { it.second > 0 } ?: return@apply
        setSpan(StyleSpan(BOLD), start, end, 0)
    }
}
