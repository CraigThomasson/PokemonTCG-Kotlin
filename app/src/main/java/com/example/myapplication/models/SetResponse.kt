import com.example.myapplication.models.Set
import kotlinx.serialization.Serializable

@Serializable
data class SetResponse(
    val data: List<Set>
)