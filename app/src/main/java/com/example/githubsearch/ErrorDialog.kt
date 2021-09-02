package com.example.githubsearch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.githubsearch.databinding.LayoutErrorDialogBinding

class ErrorDialog(
    private val description : String,
    private val positiveAction : (() -> Unit)? = null
) : DialogFragment() {
    private lateinit var binding: LayoutErrorDialogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.TransparentDialog)
        isCancelable = false
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LayoutErrorDialogBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.description.text = description
        binding.positiveButton.setOnClickListener {
            positiveAction?.invoke()
            dismiss()
        }
        binding.cancelButton.setOnClickListener { dismiss() }
    }
}