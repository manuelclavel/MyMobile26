
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mobile.DataManager

import com.mobile.com.mobile.mymobile26.FlashCard
@Composable
fun WordList(
    words: List<FlashCard>,
    selectedItem: (Int) -> Unit
) {

    LazyColumn(
        modifier = Modifier.padding(16.dp)
    ) {
        items(items = words,
            key = { flashCard ->
                flashCard.uid
            }
        ) { flashCard ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(width = 1.dp, color = Color.LightGray)
                    .padding(6.dp)
                    .clickable(onClick = {
                        selectedItem(flashCard.uid)
                    }
                    )
            ) {
                Column(modifier = Modifier.padding(6.dp))
                { Text(flashCard.englishCard.toString()) }
                Column(modifier = Modifier.padding(6.dp)) {Text(" = ")}
                Column(modifier = Modifier.padding(6.dp))
                { Text(flashCard.vietnameseCard.toString()) }
            }
        }
    }

}


//@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchCardsScreen(dataManager: DataManager) {
    val flashCards = dataManager.getFlashCards()
    //Column() {
    //    Text("Search Cards Screen: Working on it")
    //}
    //Text(flashCards.toString())
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Spacer(
            modifier = Modifier.size(16.dp)
        )

        WordList(
                words = flashCards,
                selectedItem = {}
            )
        }

}