package com.sarah.objectives.base

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.annotation.StringRes
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.google.android.material.datepicker.MaterialDatePicker
import com.sarah.objectives.callbacks.DialogListener
import com.sarah.objectives.utils.hide
import com.sarah.objectives.utils.show



abstract class BaseFragment<B : ViewDataBinding, R : BaseRepository> : Fragment() {

    protected lateinit var binding: B
    protected lateinit var factory: ViewModelFactory
    private var dialogListener:DialogListener? = null
    protected var dialog:AlertDialog.Builder?= null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = getFragmentBinding(inflater, container)
        factory = ViewModelFactory(getRepository())
        return binding.root
    }

    protected fun initListeners(listener: DialogListener) {
        this.dialogListener = listener
    }

    abstract fun getRepository(): R
    abstract fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?): B

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onPostInit()
    }

    abstract fun onPostInit()

    protected fun showDialog(@StringRes title: Int, @StringRes message: Int): AlertDialog.Builder {
        return AlertDialog.Builder(requireContext()).apply {
            setCancelable(false)
            setTitle(title)
            setMessage(message)
            show()
        }
    }

    protected fun objectiveDatePicker(@StringRes title: Int): MaterialDatePicker<Long> {
        return MaterialDatePicker.Builder
            .datePicker()
            .setTitleText(title)
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .build()
    }

    protected fun showProgressLoader(progressBar: ProgressBar){
        progressBar.show()
    }

    protected fun hideProgressLoader(progressBar: ProgressBar){
        progressBar.hide()
    }

    protected fun showDialog(title:String,message: String){
        dialog =  AlertDialog.Builder(requireContext()).apply {
            setTitle(title)
            setMessage(message)
            setPositiveButton("Continue"
            ) { _, _ ->
                dialogListener?.onOkay()
            }
            setNegativeButton("Cancel"
            ) { _, _ ->
                dialogListener?.onCancel()
            }
            show()

        }
    }
    protected fun dismiss() {
        dialog?.setOnDismissListener {
            it.cancel()
        }
    }


}