package com.example.foursquaresearch.features.search.presenter

import androidx.annotation.StringRes
import com.example.foursquaresearch.mvp.MvpView
import com.example.foursquaresearch.features.search.model.SearchResult

interface SearchContract {

    interface Presenter {
        fun submitSearch(query: String)
    }

    interface View : MvpView {
        fun showLoading()
        fun hideLoading()

        fun showSearchResult(result: List<SearchResult>)
        fun clearSearchResult()

        fun showError(@StringRes stringId: Int)
    }
}
