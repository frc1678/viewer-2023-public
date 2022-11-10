package com.example.viewer_2022.fragments.live_picklist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.viewer_2022.PicklistApi
import com.example.viewer_2022.R
import com.example.viewer_2022.constants.Constants
import com.example.viewer_2022.databinding.FragmentLivePicklistBinding
import com.example.viewer_2022.fragments.offline_picklist.PicklistData
import com.example.viewer_2022.showError
import com.example.viewer_2022.showSuccess
import kotlinx.coroutines.launch


/**
 * The fragment for the live team orderings from the picklist.
 *
 * @see R.layout.fragment_live_picklist
 */
class LivePicklistFragment : Fragment() {

    private lateinit var binding: FragmentLivePicklistBinding

    private lateinit var adapter: LivePicklistRecyclerAdapter

    lateinit var picklistData: PicklistData

    /** Runs when the fragment is created, to inflate the layout. */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the picklist layout.
        binding = FragmentLivePicklistBinding.inflate(inflater, container, false)
        initAdapter()
        binding.btnPicklistRefresh.setOnClickListener {
            updateData()
        }
        updateData()

        return binding.root
    }


    /**
     * Initializes the adapter of the [ListView][android.widget.ListView].
     */
    private fun initAdapter() {
        adapter = LivePicklistRecyclerAdapter(this)
        binding.rvLivePicklist.layoutManager = LinearLayoutManager(context)
        binding.rvLivePicklist.adapter = adapter
    }

    private fun updateData() {
        lifecycleScope.launch {
            try {
                picklistData = PicklistApi.getPicklist(Constants.EVENT_KEY)
                context?.run {
                    showSuccess(context!!, "Picklist updated!")
                }
                updateAdapter()
            } catch (e: Throwable) {
                Log.e("picklist", "Error getting picklist", e)
                context?.run {
                    showError(context!!, "Error getting picklist: ${e.message}")
                }
            }
        }
    }


    private fun updateAdapter() {
        adapter.submitList(picklistData.ranking + picklistData.dnp)
        adapter.notifyDataSetChanged()
    }

}