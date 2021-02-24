package com.example.foursquaresearch.data.respository

import com.example.foursquaresearch.features.search.model.SearchResult
import com.example.foursquaresearch.utls.LocationHelper.LatLng

interface SearchRepository {
    suspend fun search(query: String, currentLocation: LatLng): List<SearchResult>
}
