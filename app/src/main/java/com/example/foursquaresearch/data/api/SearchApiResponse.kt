package com.example.foursquaresearch.data.api

data class SearchApiResponse(
    val meta: Meta,
    val response: Response
) {
    data class Meta(
        val code: Int,
        val requestId: String
    )

    data class Response(
        val confident: Boolean,
        val venues: List<Venue>
    ) {
        data class Venue(
            val location: Location?,
            val name: String?
        ) {

            data class Location(
                val address: String?,
                val distance: Int?
            )

        }
    }
}
