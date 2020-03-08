package studio.forface.covid.domain

/** A typealias of [Nothing] for unsupported operations */
typealias Unsupported = Nothing

/**
 * [Unsupported]
 * @throws UnsupportedOperationException
 */
val unsupported: Nothing get() = throw UnsupportedOperationException("unsupported")
