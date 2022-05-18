package com.example.itunessearchapp.ui.gallery

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.itunessearchapp.adapter.ItunesItemAdapter
import com.example.itunessearchapp.R
import com.example.itunessearchapp.data.ItunesList
import com.example.itunessearchapp.databinding.FragmentGalleryBinding

class GalleryFragment : Fragment(R.layout.fragment_gallery), ItunesItemAdapter.OnItemClickListener {

    private var _binding: FragmentGalleryBinding? = null
    private val binding get() = _binding
    private lateinit var recyclerAdapter: ItunesItemAdapter
    private val viewModel by viewModels<GalleryViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentGalleryBinding.bind(view)

        initRecyclerView()
        initViewModel()
        setHasOptionsMenu(true)

    }

    //binds recyclerView with recyclerAdapter
    private fun initRecyclerView() {
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context)
        binding?.recycleViewSearch?.layoutManager = layoutManager
        recyclerAdapter = ItunesItemAdapter(requireActivity(), this)
        binding?.recycleViewSearch?.adapter = recyclerAdapter
    }

    //sets observable live data
    @SuppressLint("NotifyDataSetChanged")
    private fun initViewModel() {
        viewModel.getLiveData().observe(viewLifecycleOwner) {
            if (it != null) {
                recyclerAdapter.setItemList(it)
                recyclerAdapter.notifyDataSetChanged()
            } else {
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
            }
        }
    }

    //sets binding variable to null when fragment is destroyed
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    //handles navigation between gallery and details fragment
    override fun onItemClick(position: Int, itemList: ItunesList) {
        Toast.makeText(context, itemList.results[position].artistName, Toast.LENGTH_SHORT).show()
        val action =
            GalleryFragmentDirections.actionGalleryFragmentToDetailsFragment(itemList.results[position])
        findNavController().navigate(action)
    }

    //implements search functionality with androidx menu
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.menu, menu)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    binding!!.recycleViewSearch.scrollToPosition(0)
                    viewModel.getSearchResults(query)
                    searchView.clearFocus()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }


}