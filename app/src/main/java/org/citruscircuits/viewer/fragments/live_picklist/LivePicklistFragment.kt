package org.citruscircuits.viewer.fragments.live_picklist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import org.citruscircuits.viewer.R
import org.citruscircuits.viewer.constants.Constants
import org.citruscircuits.viewer.data.PicklistApi
import org.citruscircuits.viewer.databinding.FragmentLivePicklistBinding
import org.citruscircuits.viewer.fragments.offline_picklist.OfflinePicklistFragment
import org.citruscircuits.viewer.fragments.offline_picklist.PicklistData
import org.citruscircuits.viewer.showError
import org.citruscircuits.viewer.showSuccess
import kotlinx.android.synthetic.main.fragment_offline_picklist.*
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

    private fun switchScreen() {
        val offlinePicklistFragment = OfflinePicklistFragment()
        val ft = requireFragmentManager().beginTransaction()
        if (requireFragmentManager().fragments.last().tag != "offlinepicklistFragment") ft.addToBackStack(null)
        ft.replace(R.id.nav_host_fragment, offlinePicklistFragment, "offlinepicklistFragment")
            .commit()
    }
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
//        binding.btnSwitchOnline.isEnabled = false
//        binding.btnSwitchOnline.isActivated = true
        context?.resources?.let { binding.btnSwitchOnline.setBackgroundColor(it.getColor(R.color.ToggleColor)) }
        context?.resources?.let { binding.btnSwitchOffline.setBackgroundColor(it.getColor(R.color.LightGray)) }

        binding.btnSwitchOffline.setOnClickListener {
            switchScreen()
        }
        binding.btnSwitchOnline.setOnClickListener {
            switchScreen()
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
                    showSuccess(requireContext(), "Picklist updated!")
                }
                updateAdapter()
            } catch (e: Throwable) {
                Log.e("picklist", "Error getting picklist", e)
                context?.run {
                    showError(requireContext(), "Error getting picklist: ${e.message}")
                }
            }
        }
    }


    private fun updateAdapter() {
        adapter.submitList(picklistData.ranking + picklistData.dnp)
        adapter.notifyDataSetChanged()
    }

}