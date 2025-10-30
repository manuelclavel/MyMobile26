import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.mobile.com.mobile.mymobile26.FlashCard

@Composable
fun SearchCardsScreen(getAllFlashCards: () -> List<FlashCard>) {
    getAllFlashCards()
    Column() {
        Text("Search Cards Screen: Working on it")
    }
}