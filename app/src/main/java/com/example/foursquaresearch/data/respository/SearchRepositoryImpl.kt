package com.example.foursquaresearch.data.respository

import com.example.foursquaresearch.BuildConfig
import com.example.foursquaresearch.data.api.SearchApi
import com.example.foursquaresearch.features.search.model.SearchResult
import com.example.foursquaresearch.utls.ApiErrorException
import com.example.foursquaresearch.utls.LocationHelper.LatLng
import com.example.foursquaresearch.utls.NoInternetException
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

private const val SEARCH_API_VERSION = "20180323"

class SearchRepositoryImpl @Inject constructor(
    private val searchApi: SearchApi
) : SearchRepository {

    override suspend fun search(query: String, currentLocation: LatLng): List<SearchResult> {
        try {
            val apiResult = searchApi.search(
                clientId = BuildConfig.CLIENT_ID,
                clientSecret = BuildConfig.CLIENT_SECRET,
                version = SEARCH_API_VERSION,
                latlang = "${currentLocation.latitude},${currentLocation.longitude}",
                query = query
            )
            return apiResult.response.venues.map {
                SearchResult(
                    name = it.name ?: "",
                    address = it.location?.address ?: "",
                    distance = it.location?.distance?.toString() ?: ""
                )
            }
        } catch (e: HttpException) {
            throw ApiErrorException()
        } catch (e: IOException) {
            throw NoInternetException()
        }
    }
}
