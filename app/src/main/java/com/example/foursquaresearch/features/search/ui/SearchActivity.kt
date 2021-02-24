package com.example.foursquaresearch.features.search.ui

import android.os.Bundle
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foursquaresearch.R
import com.example.foursquaresearch.databinding.ActivitySearchBinding
import com.example.foursquaresearch.features.search.model.SearchResult
import com.example.foursquaresearch.features.search.presenter.SearchContract
import com.example.foursquaresearch.features.search.presenter.SearchPresenter
import com.example.foursquaresearch.utls.AndroidPermissionHelper
import com.example.foursquaresearch.utls.visibleOrGone
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SearchActivity : AppCompatActivity(), SearchContract.View {

    @Inject
    lateinit var searchPresenter: SearchPresenter

    @Inject
    lateinit var androidPermissionHelper: AndroidPermissionHelper


    private var binding: ActivitySearchBinding? = null

    // region life cycle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivitySearchBinding.inflate(layoutInflater).apply {
            setContentView(root)
            binding = this
        }
        askLocationPermission()
        searchPresenter.onCreate(this)

        setupUI()
    }

    override fun onDestroy() {
        super.onDestroy()
        androidPermissionHelper.onDestroy()
        searchPresenter.onDestroy()
        binding = null
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        androidPermissionHelper.onRequestPermissionsResult(requestCode, grantResults)
    }

    // endregion life cycle

    private fun setupUI() {
        binding?.search?.doOnTextChanged { text, _, _, _ ->
            searchPresenter.submitSearch(text.toString())
        }

        binding?.recyclerView?.layoutManager = LinearLayoutManager(this)
        binding?.recyclerView?.adapter = SearchAdapter()
    }

    // region permission

    private fun askLocationPermission() {
        androidPermissionHelper.checkPermissionLocation(
            this, ::onPermissionLocationGranted, ::onPermissionLocationDenied
        )
    }

    private fun onPermissionLocationGranted() {
        binding?.root?.visibleOrGone = true
        Toast.makeText(this, R.string.welcome, Toast.LENGTH_SHORT).show()
    }

    private fun onPermissionLocationDenied() {
        binding?.root?.visibleOrGone = false
        AlertDialog.Builder(this)
            .setMessage(R.string.location_denied)
            .setPositiveButton(R.string.retry) { _, _ -> askLocationPermission() }
            .show()
    }

    // endregion permission
    // region SearchContract.View

    override fun showLoading() {
        binding?.progressBar?.visibleOrGone = true
    }

    override fun hideLoading() {
        binding?.progressBar?.visibleOrGone = false
    }

    override fun showSearchResult(result: List<SearchResult>) {
        (binding?.recyclerView?.adapter as? SearchAdapter)?.submitList(result)
    }

    override fun clearSearchResult() {
        (binding?.recyclerView?.adapter as? SearchAdapter)?.submitList(emptyList())
    }

    override fun showError(@StringRes stringId: Int) {
        Toast.makeText(this, stringId, Toast.LENGTH_SHORT).show()
    }

    // endregion SearchContract.View

}
