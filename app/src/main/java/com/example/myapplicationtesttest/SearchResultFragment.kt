package com.example.myapplicationtesttest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplicationtesttest.data.Contact
import com.example.myapplicationtesttest.data.ContactAdapter
import com.example.myapplicationtesttest.databinding.FragmentSearchResultsBinding

class SearchResultFragment : Fragment() {
    private lateinit var binding: FragmentSearchResultsBinding
    private lateinit var contactAdapter: ContactAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchResultsBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Get the filtered list of contacts from the arguments
        val contactList = arguments?.getParcelableArrayList<Contact>("contactList")

        // Initialize the RecyclerView and set the adapter
        contactAdapter = ContactAdapter((contactList ?: emptyList()) as ArrayList<Contact>)
        binding.recycler2.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = contactAdapter
        }
    }
}
