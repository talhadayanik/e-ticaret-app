package com.example.e_ticaretapp.ui.notifications

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.ArrayAdapter
import com.example.e_ticaretapp.R
import com.example.e_ticaretapp.databinding.FragmentNotificationsBinding
import com.example.e_ticaretapp.models.NotificationObject
import com.example.e_ticaretapp.models.Product
import java.util.Timer
import kotlin.concurrent.timerTask

class NotificationsFragment : Fragment() {

    companion object {
        fun newInstance() = NotificationsFragment()
    }

    private var _binding: FragmentNotificationsBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: ArrayAdapter<NotificationObject>

    lateinit var viewModel: NotificationsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val view = binding.root

        adapter = ArrayAdapter(requireContext(),android.R.layout.simple_list_item_1, listOf())
        binding.listNotification.adapter = adapter

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(NotificationsViewModel::class.java)
        subscribe()
        viewModel.getNotifications()
    }

    private fun subscribe(){
        viewModel.notificationList.observe(viewLifecycleOwner) { nList ->
            adapter = ArrayAdapter(requireContext(),android.R.layout.simple_list_item_1, nList)
            binding.listNotification.adapter = adapter
        }
    }

}