
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import com.mobile.R
import com.mobile.com.mobile.mymobile26.FlashCard


@Composable
fun AddCardScreen(
    changeMessage: (String) -> Unit,
    insertFlashCard: (FlashCard) -> Unit,
    enWord: String,
    vnWord: String,
    updateAddEnWord: (String) -> Unit,
    updateAddVnWord: (String) -> Unit
) {



    LaunchedEffect(Unit) {
        changeMessage("Please, add a flash card.")
    }

    Column() {
        TextField(
            value = enWord,
            onValueChange = { updateAddEnWord(it) },
            modifier = Modifier.semantics { contentDescription = "enTextField" },
            label = { Text("en") }
        )
        TextField(
            value = vnWord,
            onValueChange = { updateAddVnWord(it) },
            modifier = Modifier.semantics { contentDescription = "vnTextField" },
            label = { Text("vn") }
        )
        Button(
                modifier = Modifier.semantics { contentDescription = "Add" },
                onClick = {
                    insertFlashCard(FlashCard(uid = 0, englishCard = enWord, vietnameseCard = vnWord))
                })
            {
                Text("Add")
            }
        }
}
