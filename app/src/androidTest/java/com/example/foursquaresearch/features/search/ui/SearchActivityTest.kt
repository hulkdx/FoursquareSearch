package com.example.foursquaresearch.features.search.ui

import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.TypeTextAction
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.example.foursquaresearch.R
import com.example.foursquaresearch.atPosition
import com.example.foursquaresearch.data.api.SearchApi
import com.example.foursquaresearch.data.api.SearchApiResponse
import com.example.foursquaresearch.di.NetworkModule
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.junit.MockitoJUnit

@HiltAndroidTest
@UninstallModules(NetworkModule::class)
class SearchActivityTest {
    // region constants ----------------------------------------------------------------------------
    // endregion constants -------------------------------------------------------------------------

    // region helper fields ------------------------------------------------------------------------

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var mockitoJUnit = MockitoJUnit.rule()!!

    /**
     * We uninstall NetworkModule and use Fake api to test the classes
     */
    private val _searchApiMock = SearchApiMock()

    @BindValue
    @JvmField
    val searchApiMock: SearchApi = _searchApiMock

    // endregion helper fields ---------------------------------------------------------------------

    @Before
    fun setup() {
        launchActivity<SearchActivity>()
    }

    @Test
    fun when_onTextChange_show_a_list_of_items_in_recyclerview() {
        // Arrange
        val query = "item 1"
        val resultName0 = "ITEM 1"
        val resultName1 = "item 12"
        val resultName2 = "23 iTeM 1"
        val result = createSearchApiResponseWith(resultName0, resultName1, resultName2)
        _searchApiMock.searchApiResponse = result

        // Act
        onView(withId(R.id.search)).perform(TypeTextAction(query))

        // Asserts
        onView(withId(R.id.recyclerView)).check(matches(isDisplayed()))
        onView(withId(R.id.recyclerView)).check(
            matches(atPosition(0, hasDescendant(withText(resultName0))))
        )
        onView(withId(R.id.recyclerView)).check(
            matches(atPosition(1, hasDescendant(withText(resultName1))))
        )
        onView(withId(R.id.recyclerView)).check(
            matches(atPosition(2, hasDescendant(withText(resultName2))))
        )
    }

    // region helper methods -----------------------------------------------------------------------

    private fun createSearchApiResponseWith(vararg name: String): SearchApiResponse {
        return SearchApiResponse(
            meta = SearchApiResponse.Meta(200, ""),
            response = SearchApiResponse.Response(
                true,
                name.map {
                    SearchApiResponse.Response.Venue(
                        name = it,
                        location = null
                    )
                }
            )
        )
    }

    // endregion helper methods --------------------------------------------------------------------
    // region helper classes -----------------------------------------------------------------------

    class SearchApiMock : SearchApi {

        lateinit var searchApiResponse: SearchApiResponse

        override suspend fun search(
            clientId: String,
            clientSecret: String,
            version: String,
            latlang: String,
            query: String
        ): SearchApiResponse {
            return searchApiResponse
        }

    }

    // endregion helper classes --------------------------------------------------------------------

}
