package com.example.coinkeeper.presentation

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import com.example.coinkeeper.R
import com.example.coinkeeper.databinding.FragmentFinanceItemBinding
import com.example.coinkeeper.domain.FinanceItem
import java.lang.RuntimeException
import java.text.SimpleDateFormat
import java.util.*
import com.example.coinkeeper.domain.CategoryOperation


class FinanceItemFragment :
    Fragment(),
    DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener {

    private lateinit var viewModel: FinanceItemViewModel
    private lateinit var onEditingFinishedListener: OnEditingFinishedListener


    private var _binding: FragmentFinanceItemBinding? = null
    private val binding: FragmentFinanceItemBinding
        get() = _binding ?: throw RuntimeException("FragmentFinanceItemBinding == null")


    private lateinit var spinnerCountry:Spinner
    private lateinit var itemName:TextView
    private var mPosition = 0

    private var screenMode: String = MODE_UNKNOWN
    private var financeItemId: Int = FinanceItem.ID
    private var typeOperation: Int = 0

    private var day: Int = 0
    private var month: Int = 0
    private var year: Int = 0
    private var hour: Int = 0
    private var minute: Int = 0

    private var savedDay: Int = 0
    private var savedMonth: Int = 0
    private var savedYear: Int = 0
    private var savedHour: Int = 0
    private var savedMinute: Int = 0


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnEditingFinishedListener) {
            onEditingFinishedListener = context
        }else{
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
        val spinner = binding.spinner
        val categoryOperationList: List<CategoryOperation>
//        viewModel = ViewModelProvider(this)[FinanceItemViewModel::class.java]
//        viewModel.categoryOperations.observe(requireActivity()){
//            if (typeOperation == 0){
//                categoryOperationList = it.toList()
//                initSpinner(spinner, it)
//            }
//            else{
//                categoryOperationList = it.toList()
//                initSpinner(spinner, it)
//            }
//
//        }
        if (typeOperation == 1){
            categoryOperationList = dataProviderAdd()
            initSpinner(spinner, categoryOperationList)
        }
        else{
            categoryOperationList = dataProviderExpense()
            initSpinner(spinner, categoryOperationList)
        }
        return binding.root
    }

    private fun initSpinner(spinner: Spinner, categoryOperationList: List<CategoryOperation>) {

        val spinnerAdapter = SpinnerAdapter(requireContext(), categoryOperationList)
        spinner.adapter = spinnerAdapter

        spinner.onItemSelectedListener =
            (object : AdapterView.OnItemSelectedListener {

                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    mPosition = position
                    //Toast.makeText(requireContext(),"Item Selected: ${categoryOperationList[position].name}", Toast.LENGTH_SHORT ).show()
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            })
    }
    private fun dataProviderAdd():ArrayList<CategoryOperation>{
        val mList:ArrayList<CategoryOperation> = ArrayList()
        mList.add(CategoryOperation(1,"Зарплата", R.drawable.zp,1))
        mList.add(CategoryOperation(2,"Дивиденды", R.drawable.zp,1))
        mList.add(CategoryOperation(2,"Стипендия", R.drawable.zp,1))
        return mList

    }
    private fun dataProviderExpense():ArrayList<CategoryOperation>{
        val mList:ArrayList<CategoryOperation> = ArrayList()
        mList.add(CategoryOperation(1,"Магазины", R.drawable.store,2))
        mList.add(CategoryOperation(2,"Образование", R.drawable.store,2))
        mList.add(CategoryOperation(2,"Медицина", R.drawable.store,2))
        return mList

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addTextChangeListener()
        viewModel = ViewModelProvider(this)[FinanceItemViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        launchRightMode()
        observeViewModel()
        pickDate()
        setThisMoment()
    }

    private fun launchRightMode() {
        when (screenMode) {
            MODE_EDIT -> launchEditMode()
            MODE_ADD -> launchAddMode()
        }
    }

    private fun launchEditMode() {
        viewModel.getFinanceItem(financeItemId)
        viewModel.financeItem.observe(viewLifecycleOwner) {
            binding.etName.setText(it.name)
            binding.etCount.setText(it.sum.toString())
            binding.etComment.setText(it.comment)

        }

        binding.saveButton.setOnClickListener {
            viewModel.editFinanceItem(
                binding.etName.text?.toString(),
                binding.etCount.text?.toString(),
                binding.etComment.text?.toString()
            )
        }
    }

    private fun launchAddMode() {
        binding.saveButton.setOnClickListener {
            viewModel.addFinanceItem(
                binding.etName.text?.toString(),
                binding.etCount.text?.toString(),
                binding.etComment.text?.toString(),
                typeOperation.toString(),
                binding.etDate.toString(),
                "1"
            )
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
        val operation = args.getString(OPERATION_TYPE)
        if (operation == OPERATION_INCOME){
            typeOperation = 1
        } else{
            typeOperation = 0
        }
    }



    private fun getDateTimeCalendar(){
        val calendar = Calendar.getInstance()
        day = calendar.get(Calendar.DAY_OF_MONTH)
        month = calendar.get(Calendar.MONTH)
        year = calendar.get(Calendar.YEAR)
        hour = calendar.get(Calendar.HOUR_OF_DAY)
        minute = calendar.get(Calendar.MINUTE)
    }

    private fun pickDate(){
        binding.etDate.setOnClickListener{
            getDateTimeCalendar()
            DatePickerDialog(requireContext(),this, year, month, day).show()
        }
    }

    override fun onDateSet(p0: DatePicker?, year: Int, month: Int, dayOfMounth: Int) {
        savedDay = dayOfMounth
        savedMonth = month + 1
        savedYear = year

        getDateTimeCalendar()
        TimePickerDialog(requireContext(), this, hour, minute, true).show()
    }

    override fun onTimeSet(p0: TimePicker?, hourOfDay: Int, minute: Int) {
        savedHour = hourOfDay
        savedMinute = minute
        val dateText = "$savedDay/$savedMonth $savedHour:$savedMinute"
        val selectedDate = getString(R.string.current_date, dateText)
        binding.etDate.setText(selectedDate)
    }

    @SuppressLint("SimpleDateFormat")
    private fun setThisMoment(){
        val sdf = SimpleDateFormat("dd/MM kk:mm" )
        val currentDate = sdf.format(Date())
        val dateToET = getString(R.string.current_date, currentDate.toString())
        binding.etDate.setText(dateToET)
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