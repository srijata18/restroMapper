package com.example.restro.dashboard.views

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import androidx.appcompat.widget.Toolbar
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.restro.R
import com.example.restro.dashboard.dataModel.Results
import com.example.restro.dashboard.viewModel.DashboardViewModel
import com.example.restro.databinding.ActivityMainBinding
import com.example.restro.utils.CustomAlertDialog
import com.example.restro.utils.FoodyThoughts
import com.example.restro.utils.gone
import com.example.restro.utils.visible
import com.example.restro.view.base.BaseActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.content_main.*
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

/*
* Launching activity, which is responsible for displaying the list of restaurants based on the
* user requirements. Search functionality is also added which can enhance the users need for  a
* particular restaurant.
* */
class DashBoardActivity : BaseActivity<ActivityMainBinding, DashboardViewModel>() {

    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.activity_main

    @Inject
    override lateinit var viewModel: DashboardViewModel
        internal set

    private var venueList = arrayListOf<Results>()
    private var activityMainBinding: ActivityMainBinding? = null
    private var restroAdapter: ScrollAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = viewDataBinding
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        setAdapter()
        setLiveListeners()
        loadData()
        setListeners()
    }

    private fun setListeners() {
        et_search?.imeOptions = EditorInfo.IME_ACTION_DONE
        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            Snackbar.make(view, getString(R.string.helpline), Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        srl_dashboard?.setOnRefreshListener(object : SwipeRefreshLayout.OnRefreshListener {
            override fun onRefresh() {
                loadData()
            }
        })
        et_search.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun afterTextChanged(editable: Editable) {
                if (editable.toString().length >= 2) {
                    filterUI()
                    filter(editable.toString())
                } else {
                    normalUI()
                }
            }
        })
    }

    private fun filterUI() {
        srl_dashboard?.isEnabled = false
        tv_nearby?.gone()
    }

    private fun normalUI() {
        srl_dashboard?.isEnabled = true
        tv_nearby?.visible()
        updateAdapterData()
    }

    private fun filter(text: String) {
        val filteredNames: ArrayList<Results> = ArrayList()
        for (s in venueList) {
            if (s.name.toLowerCase(Locale.ROOT).contains(text.toLowerCase(Locale.ROOT))) {
                filteredNames.add(s)
            }
        }
        restroAdapter?.updateAdapter(filteredNames)
    }

    private fun setAdapter() {
        val mLayoutManager = LinearLayoutManager(this)
        mLayoutManager.orientation = LinearLayoutManager.VERTICAL
        restroAdapter = ScrollAdapter(venueList)
        activityMainBinding?.apply {
            with(rv_restro_list){
                layoutManager = mLayoutManager
                adapter = restroAdapter
                isNestedScrollingEnabled = false
            }
        }
        restroAdapter?.notifyDataSetChanged()
    }

    private fun loadData() {
        if (checkForInternet()) {
            hideEmptyScreen()
            showHideProgress(true)
            viewModel.fetchDetails()
        } else {
            Snackbar.make(srl_dashboard, getString(R.string.nointernet), Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
            showHideProgress(false)
            if (venueList.isEmpty())
                showEmptyScreen()
        }
    }

    private fun setLiveListeners() {
        viewModel.receivedResponse.observe(this, Observer {
            val list = arrayListOf<Results>()
            it?.results?.let { it1 -> list.addAll(it1) }
            venueList = list
            updateAdapterData()
            restroAdapter?.notifyDataSetChanged()
            showHideProgress(false)
        })
        viewModel.errorModel.observe(this, Observer {
            it?.message?.let { it1 ->
                CustomAlertDialog.displayAlert(
                    this,
                    it1,
                    getString(R.string.ok)
                )
            }
            if (venueList.isEmpty())
                showEmptyScreen()
            showHideProgress(false)
        })
    }

    private fun showHideProgress(toShow: Boolean) {
        if (toShow) {
            if (srl_dashboard?.isRefreshing == false) {
                foody_msg?.text = FoodyThoughts.displayFoodThoughts()
                pb_progress?.visible()
                foody_msg?.visible()
            }
        } else {
            foody_msg?.gone()
            pb_progress?.gone()
            srl_dashboard?.isRefreshing = false
        }
    }

    private fun showEmptyScreen() {
        layout_emptystate?.visible()
        et_search?.gone()
        rv_restro_list?.gone()
        tv_nearby?.gone()
    }

    private fun hideEmptyScreen() {
        layout_emptystate?.gone()
        et_search?.visible()
        rv_restro_list?.visible()
        tv_nearby?.visible()
    }

    private fun updateAdapterData() {
        restroAdapter?.updateAdapter(venueList)
        if (venueList.isNotEmpty()) {
            rv_restro_list?.visible()
            foody_msg?.gone()
        } else {
            showNoData()
        }
    }

    private fun showNoData() {
        foody_msg?.visible()
        foody_msg?.text = getString(R.string.emptyList)
        rv_restro_list?.gone()
    }
}
