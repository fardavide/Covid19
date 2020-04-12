package studio.forface.covid.android.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import studio.forface.covid.android.error.NoQueryError
import studio.forface.covid.android.error.NoResultError
import studio.forface.covid.domain.entity.Country
import studio.forface.covid.domain.entity.Name
import studio.forface.covid.domain.usecase.SearchCountry
import studio.forface.covid.domain.util.DispatcherProvider
import studio.forface.viewstatestore.ViewState
import studio.forface.viewstatestore.ViewState.Loading
import studio.forface.viewstatestore.ViewStateStore
import studio.forface.viewstatestore.ViewStateStoreScope

/**
 * A [ViewModel] for search Countries
 *
 * * Input:
 *  * send a query [Name] to [search]
 *
 * * Output:
 *  * search results delivered by [countries]
 *
 * @author Davide Farella
 */
class SearchViewModel(
    private val searchCountry: SearchCountry,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel(), ViewStateStoreScope, DispatcherProvider by dispatcherProvider {

    /** Delivers [ViewState] for search result */
    val countries = ViewStateStore<List<Country>>(NoQueryError).lock

    /** Actor that receives a query [Name] for search a [Country] */
    val search = viewModelScope.actor<String> {

        for (searchQuery in channel) {
            lastQuery = Name(searchQuery)

            if (searchQuery.isNotBlank()) {
                countries.set(Loading)
                query.send(lastQuery)

            } else {
                countries.set(NoQueryError)
            }
        }
    }

    private var lastQuery: Name = Name("")
    private val query = Channel<Name>()

    init {
        viewModelScope.launch(Io) {

            searchCountry(query).collect { countriesList ->

                if (countriesList.isNotEmpty()) {
                    countries.post(countriesList)

                } else if (countries.state() is Loading) {
                    countries.post(NoResultError(lastQuery))
                }
            }
        }
    }
}
