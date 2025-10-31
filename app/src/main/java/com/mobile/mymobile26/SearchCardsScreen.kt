import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.mobile.DataManager

@Composable
fun SearchCardsScreen(dataManager: DataManager) {
    dataManager.getFlashCards()
    Column() {
        Text("Search Cards Screen: Working on it")
    }
}