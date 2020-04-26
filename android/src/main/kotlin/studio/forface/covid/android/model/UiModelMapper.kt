package studio.forface.covid.android.model

import studio.forface.covid.domain.mapper.OneWayMapper

/**
 * [OneWayMapper] that map a [BusinessModel] into an [UiModel]
 * @author Davide Farella
 */
interface UiModelMapper<in BusinessModel, out UiModel> : OneWayMapper<BusinessModel, UiModel> {

    suspend fun BusinessModel.toUiModel(): UiModel
}
