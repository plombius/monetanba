package cz.moneta.monetanba.app

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.rememberSavedStateNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.navigation3.ui.rememberSceneSetupNavEntryDecorator
import cz.moneta.monetanba.common.http.dto.PlayerDTO
import cz.moneta.monetanba.common.http.dto.TeamDTO
import cz.moneta.monetanba.common.viewModelFactory
import cz.moneta.monetanba.playerDetail.ui.PlayerDetailScreen
import cz.moneta.monetanba.playersList.ui.PlayersListScreen
import cz.moneta.monetanba.playersList.viewmodel.PlayersListViewModel
import cz.moneta.monetanba.teamDetail.ui.TeamDetailScreen
import cz.moneta.monetanba.teamDetail.viewmodel.TeamDetailViewModel


data object RoutePlayersList
data class RoutePlayerDetail(val playerDTO: PlayerDTO)
data class RouteTeamDetail(val teamId: Int)

@Composable
fun AppNavigation(){

    val backStack = remember { mutableStateListOf<Any>(RoutePlayersList) }

    NavDisplay(
        backStack = backStack,
        entryDecorators = listOf(
            rememberSceneSetupNavEntryDecorator(),
            rememberSavedStateNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        entryProvider = { key ->
            when(key){
                is RoutePlayersList -> NavEntry(key){
                    PlayersListScreen(
                        viewmodel = viewModel(
                            factory = viewModelFactory {
                                PlayersListViewModel(MonetaNBAApp.appModule)
                            }
                        ),
                        backStack = backStack
                    )
                }
                is RoutePlayerDetail -> NavEntry(key){
                    PlayerDetailScreen(key, backStack)
                }
                is RouteTeamDetail -> NavEntry(key){
                    TeamDetailScreen(
                        viewmodel = viewModel(
                            factory = viewModelFactory {
                                TeamDetailViewModel(MonetaNBAApp.appModule.ballDontLieRepository, key)
                            }
                        )
                    )
                }
                else -> NavEntry(Unit) { Text("Unknown route") }
            }
        }
    )
}
