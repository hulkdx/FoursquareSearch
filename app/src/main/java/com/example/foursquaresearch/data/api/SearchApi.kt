package com.example.foursquaresearch.data.api

import retrofit2.http.GET
import retrofit2.http.Query

const val BASE_URL = "https://api.foursquare.com"

/**
 *  Forsquare's Search Api
 *  https://developer.foursquare.com/docs/places-api/getting-started/
 */
interface SearchApi {

    @GET("/v2/venues/search")
    suspend fun search(
        @Query("client_id") clientId: String,
        @Query("client_secret") clientSecret: String,
        @Query("v") version: String,
        @Query("ll") latlang: String,
        @Query("query") query: String
    ): SearchApiResponse
}
