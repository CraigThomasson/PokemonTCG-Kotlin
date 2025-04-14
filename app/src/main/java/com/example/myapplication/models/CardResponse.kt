import com.example.myapplication.models.Card
import kotlinx.serialization.Serializable

@Serializable
data class CardResponse(
    val data: List<Card>
)