package com.example.foursquaresearch.features.search.presenter

import com.example.foursquaresearch.R
import com.example.foursquaresearch.data.respository.SearchRepository
import com.example.foursquaresearch.features.search.model.SearchResult
import com.example.foursquaresearch.mvp.Presenter
import com.example.foursquaresearch.utls.ApiErrorException
import com.example.foursquaresearch.utls.LocationHelper
import com.example.foursquaresearch.utls.NoInternetException
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@ActivityRetainedScoped
class SearchPresenter @Inject constructor(
    private val searchRepository: SearchRepository,
    private val locationHelper: LocationHelper
) : Presenter<SearchContract.View>(), SearchContract.Presenter {

    private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
    }

    override fun submitSearch(query: String) {
        scope.launch {

            if (query.isEmpty()) {
                mpvView?.clearSearchResult()
                return@launch
            }
            mpvView?.showLoading()
            val lastLocation = locationHelper.getUserLastLocation()
            if (lastLocation == null) {
                mpvView?.showError(R.string.failed_to_get_location)
                mpvView?.hideLoading()
                return@launch
            }
            try {
                val result = searchRepository.search(query, lastLocation)
                if (result.isNotEmpty()) {
                    mpvView?.showSearchResult(result)
                } else {
                    mpvView?.clearSearchResult()
                }
            } catch (e: ApiErrorException) {
                mpvView?.showError(R.string.bad_search_exception)
            } catch (e: NoInternetException) {
                mpvView?.showError(R.string.no_internet_exception)
            }
            mpvView?.hideLoading()
        }
    }

}


