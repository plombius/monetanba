package cz.moneta.monetanba.playerDetail.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import cz.moneta.monetanba.app.RoutePlayerDetail
import cz.moneta.monetanba.app.RouteTeamDetail

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun PlayerDetailScreen(
    routePlayerDetail: RoutePlayerDetail,
    backStack: SnapshotStateList<Any>
){
    Scaffold { innerPadding ->
        Column (Modifier
            .padding(innerPadding)
            .padding(horizontal = 16.dp)
        ){
            Text("Player details", fontSize = 24.sp)
            Spacer(Modifier.height(24.dp))
            Text(routePlayerDetail.playerDTO.first_name)
            Text(routePlayerDetail.playerDTO.last_name)
            Text(routePlayerDetail.playerDTO.position?:"")
            Text(routePlayerDetail.playerDTO.height?:"")
            Text(routePlayerDetail.playerDTO.weight?:"")
            Text(routePlayerDetail.playerDTO.jersey_number?:"")
            Text(routePlayerDetail.playerDTO.college?:"")
            Text(routePlayerDetail.playerDTO.country?:"")
            Text(routePlayerDetail.playerDTO.draft_year?.toString()?:"")
            Text(routePlayerDetail.playerDTO.draft_round?.toString()?:"")
            Text(routePlayerDetail.playerDTO.draft_number?.toString()?:"")
            Text(routePlayerDetail.playerDTO.team.name)
            GlideImage(model = "https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Army_vs_Navy_Basketball_game%2C_2004.jpg/500px-Army_vs_Navy_Basketball_game%2C_2004.jpg", contentDescription = null)

            Button(
                onClick = {
                    backStack.add(RouteTeamDetail(routePlayerDetail.playerDTO.team.id))
                }
            ) {
                Text("More info about team")
            }
        }
    }
}