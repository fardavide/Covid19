package studio.forface.covid.android.viewmodel

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import studio.forface.covid.android.mapper.CountryStatUiModelMapper
import studio.forface.covid.android.model.CountryStatUiModel
import studio.forface.covid.domain.entity.CountryId
import studio.forface.covid.domain.mapper.map
import studio.forface.covid.domain.usecase.GetCountryStat
import studio.forface.covid.domain.util.DispatcherProvider
import studio.forface.viewstatestore.ViewState
import studio.forface.viewstatestore.ViewState.Loading
import studio.forface.viewstatestore.ViewStateStore

/**
 * A `ViewModel` for get Stats for a Country
 * Inherit from [BaseViewModel]
 *
 * @constructor requires [CountryId] of the Country to observe
 *
 * * Output:
 *  * Country Stats delivered by [stats]
 *
 * @author Davide Farella
 */
class CountryStatViewModel(
    private val countryId: CountryId,
    private val getCountryStat: GetCountryStat,
    private val mapper: CountryStatUiModelMapper,
    dispatcherProvider: DispatcherProvider
) : BaseViewModel(dispatcherProvider) {

    /** Delivers [ViewState] with Country Stats */
    val stats = ViewStateStore<CountryStatUiModel>(Loading)

    init {
        viewModelScope.launch(Io) {

            getCountryStat(countryId)
                .catch { stats.postError(it) }
                .map(mapper) { it.toUiModel() }
                .collect { stats.post(it) }
        }
    }
}
