package cz.moneta.monetanba.teamDetail.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cz.moneta.monetanba.app.RouteTeamDetail
import cz.moneta.monetanba.teamDetail.viewmodel.TeamDetailViewModel

@Composable
fun TeamDetailScreen(
    viewmodel: TeamDetailViewModel
){
    val team = viewmodel.teamDetail.collectAsState()

    Scaffold { innerPadding ->
        Column (Modifier
            .padding(innerPadding)
            .padding(horizontal = 16.dp)
        ) {
            Text("Team details", fontSize = 24.sp)
            Spacer(Modifier.height(24.dp))

            team.value?.data?.let {
                Text(it.full_name)
                Text(it.city)
                Text(it.abbreviation)
                Text(it.conference)
                Text(it.division)
            }
        }
    }
}