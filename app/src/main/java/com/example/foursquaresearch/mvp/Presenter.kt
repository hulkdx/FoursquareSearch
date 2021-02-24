package com.example.foursquaresearch.mvp

/**
 * Every presenter in the app must extends this abstract class.
 */
abstract class Presenter<V : MvpView> {
    var mpvView: V? = null
        private set

    open fun onCreate(mpvView: V) {
        this.mpvView = mpvView
    }

    open fun onDestroy() {
        mpvView = null
    }

    fun requireMvpView() = mpvView ?: throw RuntimeException("mvpView is null")
}
