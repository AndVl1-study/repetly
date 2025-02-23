package com.andvl.repetly.features.tutorprofile.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andvl.repetly.common.data.models.Id
import com.andvl.repetly.common.data.models.user.Tutor
import com.andvl.repetly.features.tutorprofile.data.TutorProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.net.HttpRetryException
import javax.inject.Inject


sealed interface TutorProfileUiState {
    data class Success(val tutor: Tutor) : TutorProfileUiState
    object Error : TutorProfileUiState
    object Loading : TutorProfileUiState
}

@HiltViewModel
class TutorProfileViewModel @Inject constructor(
    private val tutorProfileRepository: TutorProfileRepository
) : ViewModel() {
    var tutorProfileUiState: TutorProfileUiState by mutableStateOf(TutorProfileUiState.Loading)
        private set

    fun getTutorProfile(tutorId: Id) {
        viewModelScope.launch {
            tutorProfileUiState = TutorProfileUiState.Loading
            tutorProfileUiState = try {
                val tutor = withContext(Dispatchers.IO) {
                    tutorProfileRepository.getTutorProfile(tutorId)
                }
                TutorProfileUiState.Success(tutor)
            } catch (e: IOException) {
                TutorProfileUiState.Error
            } catch (e: HttpRetryException) {
                TutorProfileUiState.Error
            } catch (e: NoSuchElementException) {
                TutorProfileUiState.Error
            } catch (e: Exception) {
                TutorProfileUiState.Error
            }
        }
    }
}
