@file:Suppress("PrivatePropertyName", "EXPERIMENTAL_API_USAGE")

package com.example.foursquaresearch.features.search.ui

import com.example.foursquaresearch.R
import com.example.foursquaresearch.anyKotlin
import com.example.foursquaresearch.data.respository.SearchRepository
import com.example.foursquaresearch.features.search.model.SearchResult
import com.example.foursquaresearch.features.search.presenter.SearchContract
import com.example.foursquaresearch.features.search.presenter.SearchPresenter
import com.example.foursquaresearch.utls.ApiErrorException
import com.example.foursquaresearch.utls.LocationHelper
import com.example.foursquaresearch.utls.NoInternetException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit

import org.mockito.Mockito.*
import org.junit.After


class SearchPresenterTest {
    // region constants ----------------------------------------------------------------------------
    // endregion constants -------------------------------------------------------------------------

    // region helper fields ------------------------------------------------------------------------

    @get:Rule
    var mockitoJUnit = MockitoJUnit.rule()!!

    @Mock
    lateinit var testSearchView: SearchContract.View

    @Mock
    lateinit var searchRepository: SearchRepository

    @Mock
    lateinit var locationHelper: LocationHelper

    // endregion helper fields ---------------------------------------------------------------------

    private lateinit var SUT: SearchPresenter

    @Before
    fun setup() {
        SUT = SearchPresenter(
            searchRepository,
            locationHelper
        )
        SUT.onCreate(testSearchView)

        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun tearDown() {
        SUT.onDestroy()
    }

    @Test
    fun `submit search when query is empty should clear search result`() =
        runBlocking {
            // Arrange
            val query = ""
            // Act
            SUT.submitSearch(query)
            // Assert
            verify(testSearchView, never()).showLoading()
            verify(testSearchView, never()).showSearchResult(anyKotlin())
            verify(testSearchView).clearSearchResult()
        }

    @Test
    fun `submit search when user last location is empty should show error failed_to_get_location`() =
        runBlocking {
            // Arrange
            val query = "item1"
            lastLocationIsEmpty()
            // Act
            SUT.submitSearch(query)
            // Assert
            verify(testSearchView).showLoading()
            verify(testSearchView).showError(R.string.failed_to_get_location)
            verify(testSearchView).hideLoading()
        }

    @Test
    fun `submit search when repository return items and last location is not empty should show search result`() =
        runBlocking {
            // Arrange
            val query = "item"
            val expectedResult = listOf(
                SearchResult(name = "item 1", address = "irrelevant", distance = "irrelevant"),
                SearchResult(name = "ITEM 1", address = "irrelevant", distance = "irrelevant"),
                SearchResult(name = "another ITeM", address = "irrelevant", distance = "irrelevant")
            )
            repositorySuccessful(expectedResult)
            lastLocationIsNotEmpty()
            // Act
            SUT.submitSearch(query)
            // Assert
            verify(testSearchView).showLoading()
            verify(testSearchView).showSearchResult(expectedResult)
            verify(testSearchView).hideLoading()
        }

    @Test
    fun `submit search when repository return empty list and last location is not empty should clearSearchResult`() =
        runBlocking {
            // Arrange
            val query = "item"
            val expectedResult = emptyList<SearchResult>()
            repositorySuccessful(expectedResult)
            lastLocationIsNotEmpty()
            // Act
            SUT.submitSearch(query)
            // Assert
            verify(testSearchView).showLoading()
            verify(testSearchView).clearSearchResult()
            verify(testSearchView).hideLoading()
        }

    @Test
    fun `submit search when last location is not empty and repositoryApiError should show bad_search_exception error`() =
        runBlocking {
            // Arrange
            val query = "item"
            lastLocationIsNotEmpty()
            repositoryApiError()
            // Act
            SUT.submitSearch(query)
            // Assert
            verify(testSearchView).showError(R.string.bad_search_exception)
        }

    @Test
    fun `submit search when last location is not empty and repositoryNoInternetError should show no_internet_exception error`() =
        runBlocking {
            // Arrange
            val query = "item"
            lastLocationIsNotEmpty()
            repositoryNoInternetError()
            // Act
            SUT.submitSearch(query)
            // Assert
            verify(testSearchView).showError(R.string.no_internet_exception)
        }

    // region helper methods -----------------------------------------------------------------------

    private suspend fun lastLocationIsNotEmpty() {
        `when`(locationHelper.getUserLastLocation()).thenReturn(
            LocationHelper.LatLng(
                "1.222",
                "2.333"
            )
        )
    }

    private suspend fun lastLocationIsEmpty() {
        `when`(locationHelper.getUserLastLocation()).thenReturn(null)
    }

    private suspend fun repositorySuccessful(result: List<SearchResult>) {
        `when`(searchRepository.search(anyKotlin(), anyKotlin()))
            .thenReturn(result)
    }

    private suspend fun repositoryApiError() {
        `when`(searchRepository.search(anyKotlin(), anyKotlin()))
            .thenThrow(ApiErrorException())
    }

    private suspend fun repositoryNoInternetError() {
        `when`(searchRepository.search(anyKotlin(), anyKotlin()))
            .thenThrow(NoInternetException())
    }

    // endregion helper methods --------------------------------------------------------------------
}
