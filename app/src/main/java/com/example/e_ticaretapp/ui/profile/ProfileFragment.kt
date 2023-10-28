package com.example.e_ticaretapp.ui.profile

import android.graphics.drawable.ColorDrawable
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.e_ticaretapp.R
import com.example.e_ticaretapp.databinding.FragmentProfileBinding
import com.example.e_ticaretapp.models.Product

class ProfileFragment : Fragment() {

    companion object {
        fun newInstance() = ProfileFragment()
    }

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.txtFirstName.setOnClickListener {
            updateDialog(binding.txtFirstName.text.toString(), "firstName")
        }

        binding.txtLastName.setOnClickListener {
            updateDialog(binding.txtLastName.text.toString(), "lastName")
        }

        binding.txtAge.setOnClickListener {
            updateDialog(binding.txtAge.text.toString(), "age")
        }

        binding.txtGender.setOnClickListener {
            updateDialog(binding.txtGender.text.toString(), "gender")
        }

        binding.txtEmail.setOnClickListener {
            updateDialog(binding.txtEmail.text.toString(), "email")
        }

        binding.txtPhone.setOnClickListener {
            updateDialog(binding.txtPhone.text.toString(), "phone")
        }

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        subscribe()
        viewModel.getUserInfo()
    }

    private fun subscribe(){
        viewModel.userInfo.observe(viewLifecycleOwner) { userInfo ->
            Glide.with(requireContext()).load(userInfo.image).apply(RequestOptions.overrideOf(600)).into(binding.imgProfile)
            binding.txtFirstName.setText(userInfo.firstName)
            binding.txtLastName.setText(userInfo.lastName)
            binding.txtAge.setText(userInfo.age.toString())
            binding.txtGender.setText(userInfo.gender)
            binding.txtEmail.setText(userInfo.email)
            binding.txtPhone.setText(userInfo.phone)
        }
    }

    private fun updateDialog(text: String, textId: String){
        val builder = AlertDialog.Builder(requireContext())
        val view = layoutInflater.inflate(R.layout.dialog_update, null)
        val editBox = view.findViewById<EditText>(R.id.editBox)
        editBox.setText(text)

        builder.setView(view)
        val dialog = builder.create()

        view.findViewById<Button>(R.id.btnUpdate).setOnClickListener {
            viewModel.updateUser(editBox.text.toString(), textId)
            dialog.dismiss()
        }

        view.findViewById<Button>(R.id.btnCancel).setOnClickListener {
            dialog.dismiss()
        }
        if (dialog.window != null){
            dialog.window!!.setBackgroundDrawable(ColorDrawable(0))
        }
        dialog.show()
    }

}