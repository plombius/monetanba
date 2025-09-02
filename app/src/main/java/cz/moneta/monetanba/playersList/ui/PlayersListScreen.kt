package cz.moneta.monetanba.playersList.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.NavBackStack
import cz.moneta.monetanba.app.RoutePlayerDetail
import cz.moneta.monetanba.playersList.viewmodel.PlayersListViewModel

private val buffer = 1 // load more when scroll reaches last n item, where n >= 1

@Composable
fun PlayersListScreen(
    viewmodel: PlayersListViewModel,
    backStack: SnapshotStateList<Any>
){
    val players = viewmodel.players.collectAsState()
    val listState = rememberLazyListState()
    val isLoading = viewmodel.isLoading.collectAsState()

    Scaffold { innerPadding ->


        Column (
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ){

            Box(
                modifier = Modifier.fillMaxWidth().height(16.dp)
            ) {
                if (isLoading.value) {
                    LinearProgressIndicator(Modifier.fillMaxWidth())
                }
            }

            LazyColumn(
                state = listState
            ) {
                items(players.value) {
                    Row {
                        Text(
                            modifier = Modifier.weight(6f),
                            text = it.last_name + " " + it.first_name + " - " + it.country
                        )

                        Button(
                            modifier = Modifier.weight(1f),
                            onClick = { backStack.add(RoutePlayerDetail(it)) }
                        ) {
                            Text(">")
                        }
                    }
                }
            }

            val reachedBottom: Boolean by remember {
                derivedStateOf {
                    val lastVisibleItem = listState.layoutInfo.visibleItemsInfo.lastOrNull()
                    lastVisibleItem?.index != 0 && lastVisibleItem?.index == listState.layoutInfo.totalItemsCount - buffer
                }
            }

            // act when end of list reached
            LaunchedEffect(reachedBottom) {
                if (reachedBottom) {
                    viewmodel.callPlayers()
                }
            }
        }
    }
}