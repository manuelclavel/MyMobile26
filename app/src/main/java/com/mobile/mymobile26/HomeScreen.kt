import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.mobile.com.mobile.mymobile26.FlashCard
import com.mobile.com.mobile.mymobile26.FlashCardDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun HomeScreen(
    navigateToAddCard: () -> Unit,
    navigateToStudyCards: () -> Unit,
    navigateToSearchCards: () -> Unit,
    changeMessage: (String) -> Unit,
) {
    val scope = rememberCoroutineScope()
    changeMessage("Copyright © Mobile Programming, 2025")
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {

        Button(modifier = Modifier.semantics{contentDescription="navigateToStudyCards"},
            onClick = {
                navigateToStudyCards()
                })
        { Text("Study Cards", modifier = Modifier.semantics{contentDescription="StudyCards"},) }
        Button(modifier = Modifier.semantics{contentDescription="navigateToAddCard"},
            onClick = {
            navigateToAddCard()
        }) {
            Text("Add Card", modifier = Modifier.semantics{contentDescription="AddCard"},)
        }
        Button(modifier = Modifier.semantics{contentDescription="navigateToSearchCards"},onClick = {
           navigateToSearchCards()
        }) { Text("Search Cards", modifier = Modifier.semantics{contentDescription="SearchCards"},) }

    }
}
