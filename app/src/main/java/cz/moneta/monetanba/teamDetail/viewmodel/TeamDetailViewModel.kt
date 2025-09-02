package cz.moneta.monetanba.teamDetail.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.moneta.monetanba.app.RouteTeamDetail
import cz.moneta.monetanba.common.http.dto.TeamDTO
import cz.moneta.monetanba.common.http.dto.TeamResponseDTO
import cz.moneta.monetanba.common.http.performNetworkCall
import cz.moneta.monetanba.common.performRepositoryRetrieval
import cz.moneta.monetanba.repository.BallDontLieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TeamDetailViewModel (
    ballDontLieRepository: BallDontLieRepository,
    routeTeamDetail: RouteTeamDetail
): ViewModel(){
    private val _teamDetail = MutableStateFlow<TeamResponseDTO?>(null)
    val teamDetail = _teamDetail.asStateFlow()

    init {
        viewModelScope.launch (Dispatchers.IO) {
            _teamDetail.value = performRepositoryRetrieval(
                call = { ballDontLieRepository.getTeam(routeTeamDetail.teamId) }
            )
        }
    }
}