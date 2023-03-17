package com.example.coinkeeper.presentation

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.coinkeeper.databinding.FragmentFinanceItemBinding
import com.example.coinkeeper.domain.entity.CategoryOperation
import com.example.coinkeeper.domain.entity.FinanceItem
import com.example.coinkeeper.presentation.adapters.SpinnerAdapter
import com.example.coinkeeper.presentation.viewmodels.FinanceItemViewModel
import com.example.coinkeeper.presentation.viewmodels.ViewModelFactory
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


class FinanceItemFragment : Fragment(), DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private lateinit var viewModel: FinanceItemViewModel
    private lateinit var onEditingFinishedListener: OnEditingFinishedListener
    private lateinit var spinnerAdapter: SpinnerAdapter

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val component by lazy {
        (requireActivity().application as CoinKeeperApp).component
    }
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    private var _binding: FragmentFinanceItemBinding? = null
    private val binding: FragmentFinanceItemBinding
        get() = _binding ?: throw RuntimeException("FragmentFinanceItemBinding == null")

    private var screenMode: String = MODE_UNKNOWN
    private var financeItemId: Int = FinanceItem.ID
    private var typeOperation: Int = 0
    private lateinit var spinner: Spinner
    private lateinit var categoryOperation: CategoryOperation

    private val calendar = Calendar.getInstance().apply {
        timeInMillis = System.currentTimeMillis()
    }



    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
        if (context is OnEditingFinishedListener) {
            onEditingFinishedListener = context
        } else {
            throw RuntimeException("Activity must implement OnEditingFinishedListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFinanceItemBinding.inflate(inflater, container, false)
        parseParams()
        return binding.root
    }

    private fun getCategoryOperation(categoryOperationId: Int) {
        viewModel.getCategoryOperationsById(categoryOperationId)
        viewModel.categoryOperation.observe(viewLifecycleOwner) {
            categoryOperation = it
        }
    }
    private fun initSpinner(
        spinner: Spinner,
        categoryOperationList: List<CategoryOperation>,
        selectedItem: Int = 0
    ) {
        spinnerAdapter = SpinnerAdapter(requireContext(), categoryOperationList)
        spinner.adapter = spinnerAdapter
        spinner.onItemSelectedListener =
            (object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?,
                    position: Int,
                    id: Long
                ) {}
                override fun onNothingSelected(parent: AdapterView<*>?) {}
            })
        spinner.setSelection(selectedItem)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addTextChangeListener()
        viewModel = ViewModelProvider(this, viewModelFactory)[FinanceItemViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        spinner = binding.spinnerCategoryOperation
        launchRightMode()
        observeViewModel()
        pickDate()
        setThisMoment()
    }

    private fun getCategoryOperationList(typeOperation: Int, selectedItem: Int = 0) {
        viewModel = ViewModelProvider(this)[FinanceItemViewModel::class.java]
        viewModel.getCategoryOperationByType(typeOperation).observe(requireActivity()) {
            initSpinner(spinner, it, selectedItem)
        }
    }
    private fun launchRightMode() {
        when (screenMode) {
            MODE_EDIT -> launchEditMode()
            MODE_ADD -> launchAddMode()
        }
    }

    private fun launchEditMode() {
        var oldBalance = 0
        viewModel.getFinanceItem(financeItemId)
        viewModel.financeItem.observe(viewLifecycleOwner) {
            getCategoryOperationList(it.typeOperation)
            binding.etName.setText(it.name)
            binding.etCount.setText(it.sum.toString())
            binding.etComment.setText(it.comment)
            oldBalance += it.sum
            typeOperation = it.typeOperation
            if (it.typeOperation == 1) {
                getCategoryOperationList(typeOperation, it.categoryOperationId - 1)
            } else {
                getCategoryOperationList(typeOperation, it.categoryOperationId - 4)
            }
            getCategoryOperation(it.categoryOperationId)
        }

        binding.saveButton.setOnClickListener {

            var position = binding.spinnerCategoryOperation.selectedItemPosition
            if (typeOperation == 1) {
                position += 1
            } else {
                position += 4
            }
            getCategoryOperation(position)
            viewModel.editFinanceItem(
                binding.etName.text?.toString(),
                binding.etCount.text?.toString(),
                binding.etComment.text?.toString(),
                position.toString(),
                categoryOperation.image_id.toString(),
                oldBalance,
                binding.etDate.text?.toString()
            )
        }
    }

    private fun launchAddMode() {
        getCategoryOperationList(typeOperation)
        binding.saveButton.setOnClickListener {
            var position = binding.spinnerCategoryOperation.selectedItemPosition
            if (typeOperation == 1) {
                position += 1
            } else {
                position += 4
            }
            coroutineScope.launch {
                getCategoryOperation(position)
                delay(15)
                viewModel.addFinanceItem(
                    binding.etName.text?.toString(),
                    binding.etCount.text?.toString(),
                    binding.etComment.text?.toString(),
                    typeOperation.toString(),
                    binding.etDate.text.toString(),
                    position.toString(),
                    categoryOperation.image_id.toString()
                )
            }
        }
    }


    private fun addTextChangeListener() {
        binding.etName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetErrorInputName()
            }

            override fun afterTextChanged(p0: Editable?) {}
        })

        binding.etCount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetErrorInputCount()
            }
            override fun afterTextChanged(p0: Editable?) {}
        })
    }

    private fun observeViewModel() {
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
                throw RuntimeException("Param financeItem id is absent")
            }
            financeItemId = args.getInt(FINANCE_ITEM_ID, FinanceItem.ID)
        }
        when (args.getString(OPERATION_TYPE)) {
            OPERATION_EXPENSE -> typeOperation = 0
            OPERATION_INCOME -> typeOperation = 1
        }
    }

    private fun pickDate() {
        binding.etDate.setOnClickListener {
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            DatePickerDialog(requireContext(), this, year, month, day ).show()
        }
    }

    override fun onDateSet(p0: DatePicker?, year: Int, month: Int, dayOfMounth: Int) {
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMounth)
        calendar.set(Calendar.MONTH, month)

        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)
        TimePickerDialog(requireContext(), this, hour, minute, true).show()
    }

    override fun onTimeSet(p0: TimePicker?, hourOfDay: Int, minute: Int) {
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
        calendar.set(Calendar.MINUTE, minute)
        val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        val selectedDateTime = "${dateFormat.format(calendar.time)} ${timeFormat.format(calendar.time)}"
        binding.etDate.text = selectedDateTime
    }

    @SuppressLint("SimpleDateFormat")
    private fun setThisMoment() {
        val sdf = SimpleDateFormat("dd.MM.yyyy kk:mm")
        val currentDate = sdf.format(Date())
        binding.etDate.text = currentDate.toString()
    }

    interface OnEditingFinishedListener {
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

        fun newInstanceAddItemExpense(): FinanceItemFragment {
            return FinanceItemFragment().apply {
                arguments = Bundle().apply {
                    putString(SCREEN_MODE, MODE_ADD)
                    putString(OPERATION_TYPE, OPERATION_EXPENSE)
                }
            }
        }

        fun newInstanceEditItem(financeItemId: Int): FinanceItemFragment {
            return FinanceItemFragment().apply {
                arguments = Bundle().apply {
                    putString(SCREEN_MODE, MODE_EDIT)
                    putInt(FINANCE_ITEM_ID, financeItemId)
                }
            }
        }
    }
}