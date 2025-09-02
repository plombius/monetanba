package cz.moneta.monetanba.playersList.domain

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


/**
 * Cursor-based incremental loader for paged data.
 *
 * @param scope Coroutine scope used to launch loads (work runs on [Dispatchers.IO]).
 * @param performCall Called with the current cursor (null on first page) and should return
 * a pair of (newItems, nextCursor). Return `null` to signal an error/no data; set
 * `nextCursor = null` to signal the end of the list.
 *
 * @property loadedItems Accumulated items as a cold [kotlinx.coroutines.flow.StateFlow].
 * @property isLoading  Whether a load is currently in flight.
 */
class CursorLoadingDomain<LIST_ITEM> (
    val scope: CoroutineScope,
    val performCall: suspend (Int) -> Pair<List<LIST_ITEM>, Int>?
){
    private val _loadedItems: MutableStateFlow<List<LIST_ITEM>> = MutableStateFlow(listOf())
    val loadedItems = _loadedItems.asStateFlow()

    var nextCursor: Int = 0

    /**
     * Loads the next page if not already loading and if another page exists.
     * No-op when a load is in progress or the end has been reached.
     */
    fun loadMore(){
        scope.launch (Dispatchers.IO) {
            val result = performCall(nextCursor)
            if(result != null) {
                _loadedItems.value =
                    _loadedItems.value.toMutableList().apply { addAll(result.first) }
                nextCursor = result.second
            }
        }
    }
}