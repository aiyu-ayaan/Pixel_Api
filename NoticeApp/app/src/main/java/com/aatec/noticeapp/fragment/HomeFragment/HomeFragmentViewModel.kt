package com.aatec.noticeapp.fragment.HomeFragment

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aatec.noticeapp.data.Notice
import com.aatec.noticeapp.data.NoticeRepository
import com.aatec.noticeapp.utils.DataState
import com.aatec.noticeapp.utils.MainStateListener
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class HomeFragmentViewModel @ViewModelInject constructor(
    private val noticeRepository: NoticeRepository
) : ViewModel() {
    private val _dataState: MutableLiveData<DataState<List<Notice>>> = MutableLiveData()
    val dataState: LiveData<DataState<List<Notice>>>
        get() = _dataState

    @ExperimentalCoroutinesApi
    fun setStateListener(mainStateListener: MainStateListener) {
        viewModelScope.launch {
            when (mainStateListener) {
                MainStateListener.GetData -> {
                    noticeRepository.getNotice()
                        .onEach { dataState ->
                            _dataState.value = dataState
                        }.launchIn(viewModelScope)
                }
                MainStateListener.DoNoting -> {
                }
            }
        }
    }
}