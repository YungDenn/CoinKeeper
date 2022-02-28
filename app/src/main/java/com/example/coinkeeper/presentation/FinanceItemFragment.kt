package com.example.coinkeeper.presentation

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import com.example.coinkeeper.R
import com.example.coinkeeper.domain.FinanceItem
import com.google.android.material.textfield.TextInputLayout
import java.lang.RuntimeException

class FinanceItemFragment : Fragment() {


    private lateinit var viewModel: FinanceItemViewModel

    private lateinit var onEditingFinishedListener: OnEditingFinishedListener

    private lateinit var tilName: TextInputLayout
    private lateinit var tilCount: TextInputLayout
    private lateinit var etName: EditText
    private lateinit var etCount: EditText
    private lateinit var etComment: EditText
    private lateinit var buttonSave: Button

    private var screenMode: String = MODE_UNKNOWN
    private var financeItemId: Int = FinanceItem.UNDEFINED_ID
    private var typeOperation: Int = 0


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnEditingFinishedListener) {
            onEditingFinishedListener = context
        }else{
            throw RuntimeException("Activity must implement OnEditingFinishedListener")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseParams()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return layoutInflater.inflate(R.layout.fragment_finance_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
        addTextChangeListener()
        viewModel = ViewModelProvider(this)[FinanceItemViewModel::class.java]
        launchRightMode()
        observeViewModel()
    }

    private fun launchRightMode() {
        when (screenMode) {
            MODE_EDIT -> launchEditMode()
            MODE_ADD -> launchAddMode()
        }
    }

    private fun launchEditMode() {
        viewModel.getFinanceItem(financeItemId)
        viewModel.shopItem.observe(viewLifecycleOwner) {
            etName.setText(it.name)
            etCount.setText(it.sum.toString())
        }
        buttonSave.setOnClickListener {
            viewModel.editFinanceItem(etName.text?.toString(), etCount.text?.toString(), etComment.text?.toString())
        }
    }

    private fun launchAddMode() {
        buttonSave.setOnClickListener {
            viewModel.addFinanceItem(
                etName.text?.toString(),
                etCount.text?.toString(),
                etComment.text?.toString(),
                typeOperation.toString()
            )
        }
    }


    private fun addTextChangeListener() {
        etName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetErrorInputName()
            }

            override fun afterTextChanged(p0: Editable?) {}
        })

        etCount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetErrorInputCount()
            }

            override fun afterTextChanged(p0: Editable?) {}
        })
    }

    private fun observeViewModel() {
        viewModel.errorInputCount.observe(viewLifecycleOwner) {
            val message = if (it) {
                getString(R.string.error_input_count)
            } else {
                null
            }
            tilCount.error = message
        }

        viewModel.errorInputName.observe(viewLifecycleOwner) {
            val message = if (it) {
                getString(R.string.error_input_name)
            } else {
                null
            }
            tilName.error = message
        }
        viewModel.closeScreen.observe(viewLifecycleOwner) {
            onEditingFinishedListener.onEditingFinished()
        }
    }

    private fun parseParams() {
        val args = requireArguments()
        if (!args.containsKey(SCREEN_MODE)) {
            throw RuntimeException("Param screen mode is absent")
            //Если передан неизвестный параметр запуска экрана
        }
        val mode = args.getString(SCREEN_MODE)
        if (mode != MODE_EDIT && mode != MODE_ADD) {
            throw RuntimeException("Unknown screen mode $mode")
        }
        screenMode = mode
        if (screenMode == MODE_EDIT) {
            if (!args.containsKey(FINANCE_ITEM_ID)) {
                throw RuntimeException("Param shop item id is absent")
            }
            financeItemId = args.getInt(FINANCE_ITEM_ID, FinanceItem.UNDEFINED_ID)
        }
        val operation = args.getString(OPERATION_TYPE)
        if (operation == OPERATION_INCOME){
            typeOperation = 1
        }
        else{
            typeOperation = 0
        }
    }


    private fun initViews(view : View) {
        tilName = view.findViewById(R.id.til_name)
        tilCount = view.findViewById(R.id.til_count)
        etName = view.findViewById(R.id.et_name)
        etCount = view.findViewById(R.id.et_count)
        etComment = view.findViewById(R.id.et_comment)
        buttonSave = view.findViewById(R.id.save_button)

    }

    interface OnEditingFinishedListener{
        fun onEditingFinished()
    }

    companion object {
        private const val SCREEN_MODE = "extra_mode"
        private const val FINANCE_ITEM_ID = "extra_shop_item_id"
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_ADD = "mode_add"
        private const val MODE_UNKNOWN = ""
        private const val OPERATION_INCOME = "1"
        private const val OPERATION_EXPENSE = "0"
        private const val OPERATION_TYPE = "operation"

        fun newInstanceAddItemIncome(): FinanceItemFragment {
            return FinanceItemFragment().apply {
                arguments = Bundle().apply {
                    putString(SCREEN_MODE, MODE_ADD)
                    putString(OPERATION_TYPE, OPERATION_INCOME)
                }
            }
        }

        fun newInstanceEditItem(financeItemId: Int): FinanceItemFragment {
            //return ShopItemFragment(MODE_EDIT, shopItemId)
            return FinanceItemFragment().apply {
                arguments = Bundle().apply {
                    putString(SCREEN_MODE, MODE_EDIT)
                    putInt(FINANCE_ITEM_ID, financeItemId)
                }
            }
        }

    }
}