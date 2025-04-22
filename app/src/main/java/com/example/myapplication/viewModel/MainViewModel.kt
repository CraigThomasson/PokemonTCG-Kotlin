import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.fetchCardsBySetId
import com.example.myapplication.fetchPokemonSets
import com.example.myapplication.models.Card
import com.example.myapplication.models.Set
import com.example.myapplication.models.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val _cardUiState = MutableStateFlow<UiState<List<Card>>>(UiState.Loading)
    val cardUiState: StateFlow<UiState<List<Card>>> = _cardUiState

    private val _setUiState = MutableStateFlow<UiState<List<Set>>>(UiState.Loading)
    val setUiState: StateFlow<UiState<List<Set>>> = _setUiState

    fun loadCardsBySetId(apiKey: String, setId: String) {
        viewModelScope.launch {
            _cardUiState.value = UiState.Loading
            try {
                val cards = fetchCardsBySetId(apiKey, setId)
                _cardUiState.value = UiState.Success(cards)
            } catch (e: Exception) {
                _cardUiState.value = UiState.Error("Failed to load cards: ${e.message}")
            }
        }
    }

    fun loadSets(apiKey: String) {
        viewModelScope.launch {
            _setUiState.value = UiState.Loading
            try {
                val sets = fetchPokemonSets(apiKey)
                _setUiState.value = UiState.Success(sets)
            } catch (e: Exception) {
                _setUiState.value = UiState.Error("Failed to load sets: ${e.message}")
            }
        }
    }
}