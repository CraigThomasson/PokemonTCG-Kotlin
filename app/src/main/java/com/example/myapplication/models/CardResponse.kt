import com.example.myapplication.models.PokemonCard
import kotlinx.serialization.Serializable

@Serializable
data class CardResponse(
    val data: List<PokemonCard>
)