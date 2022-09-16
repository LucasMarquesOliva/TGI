package com.faculdade.tgi.validations

import android.text.InputFilter
import android.text.Spanned
import java.util.regex.Matcher
import java.util.regex.Pattern

class DecimalDigitsInputFilter(maxDigitsIncludingPoint: Int, maxDecimalPlaces: Int) : InputFilter {

    private val pattern: Pattern = Pattern.compile(
        "[0-9]{0," + (maxDigitsIncludingPoint - 1) + "}+((\\.[0-9]{0," + (maxDecimalPlaces - 1) + "})?)||(\\.)?"
    )

    override fun filter(source: CharSequence, start: Int, end: Int, dest: Spanned, dstart: Int, dend: Int): CharSequence? {
        dest.apply {
            val matcher: Matcher = pattern.matcher(dest)
            return if (!matcher.matches()) "" else null
        }
        return null
    }
}