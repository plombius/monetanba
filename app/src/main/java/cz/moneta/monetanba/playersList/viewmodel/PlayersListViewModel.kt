package cz.moneta.monetanba.playersList.viewmodel

import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.moneta.monetanba.common.di.AppModule
import cz.moneta.monetanba.common.performRepositoryRetrieval
import cz.moneta.monetanba.playersList.domain.CursorLoadingDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PlayersListViewModel(val appModule: AppModule): ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val cursorLoadingDomain = CursorLoadingDomain(
        viewModelScope
    ) {
        val result = performRepositoryRetrieval(
            unsuccessfulCallHandler = {
                viewModelScope.launch(Dispatchers.Main) {
                    Toast.makeText(
                        appModule.appContext,
                        it.getMessage(appModule.appContext),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            },
            call = {
                _isLoading.value = true
                val result = appModule.ballDontLieRepository.getPlayers(35, it)
                _isLoading.value = false
                result
            }
        )
        if (result != null) {
            Pair(result.data, result.meta.next_cursor ?: 0)
        } else {
            null
        }
    }

    val players = cursorLoadingDomain.loadedItems

    init {
        callPlayers()
    }

    fun callPlayers(){
        cursorLoadingDomain.loadMore()
    }
}