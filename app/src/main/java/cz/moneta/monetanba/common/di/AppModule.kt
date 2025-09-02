package cz.moneta.monetanba.common.di

import android.content.Context
import cz.moneta.monetanba.common.http.api.BallDontLieApi
import cz.moneta.monetanba.common.http.createRetrofit
import cz.moneta.monetanba.common.localdata.TeamCache
import cz.moneta.monetanba.repository.BallDontLieRepository

class AppModule(
    val appContext: Context
) {

    val retrofit by lazy { createRetrofit() }

    val teamCache by lazy { TeamCache() }

    val ballDontLieRepository by lazy { BallDontLieRepository(retrofit.create(BallDontLieApi::class.java), teamCache) }
}