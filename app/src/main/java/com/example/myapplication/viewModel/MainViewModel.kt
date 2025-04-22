import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.fetchCardsBySetId
import com.example.myapplication.models.Card
import com.example.myapplication.models.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<List<Card>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<Card>>> = _uiState

    fun loadCardsBySetId(apiKey: String, setId: String) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val cards = fetchCardsBySetId(apiKey, setId)
                _uiState.value = UiState.Success(cards)
            } catch (e: Exception) {
                _uiState.value = UiState.Error("Failed to load cards: ${e.message}")
            }
        }
    }
}