package studio.forface.covid.android.viewmodel

import androidx.lifecycle.ViewModel
import studio.forface.covid.domain.util.DispatcherProvider
import studio.forface.viewstatestore.ViewStateStoreScope

/**
 * Base [ViewModel] for the App
 *
 * Implements [ViewStateStoreScope] for publish to `LockedViewStateStore`
 * Implements [DispatcherProvider] for provide `CoroutineDispatcher`s
 *
 * @author Davide Farella
 */
abstract class BaseViewModel(dispatcherProvider: DispatcherProvider) : ViewModel(),
    ViewStateStoreScope, DispatcherProvider by dispatcherProvider
