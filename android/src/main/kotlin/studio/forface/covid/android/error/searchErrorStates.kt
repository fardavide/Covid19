package studio.forface.covid.android.error

import studio.forface.covid.android.R
import studio.forface.covid.domain.entity.Name
import studio.forface.viewstatestore.ViewState

/** [ViewState.Error] representing that no search query has been inserted */
object NoQueryError :
    ViewState.Error(IllegalArgumentException("No query"), R.string.error_no_query)

/** [ViewState.Error] representing that no results are found for the given query */
class NoResultError(query: Name) :
    ViewState.Error(IllegalStateException("No results"), R.string.error_no_results)
// FIXME:    ViewState.Error(IllegalStateException("No results"), R.string.error_no_results_arg_name, query.s)
