
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import com.mobile.R
import com.mobile.com.mobile.mymobile26.FlashCard


@Composable
fun AddCardScreen(
    changeMessage: (String) -> Unit,
    insertFlashCard: (FlashCard) -> Unit
) {
    var enWord by rememberSaveable { mutableStateOf("") }
    var vnWord by rememberSaveable { mutableStateOf("") }
    var code by remember { mutableIntStateOf(0) }


    val messageAddSuccessful = stringResource(id = R.string.add_successful)
    val messageAddUnSuccessful = stringResource(id = R.string.add_unsuccessful)


    LaunchedEffect(Unit) {
        changeMessage("Please, add a flash card.")
    }

    Column() {
        TextField(
            value = enWord,
            onValueChange = { enWord = it },
            modifier = Modifier.semantics { contentDescription = "enTextField" },
            label = { Text("en") }
        )
        TextField(
            value = vnWord,
            onValueChange = { vnWord = it },
            modifier = Modifier.semantics { contentDescription = "vnTextField" },
            label = { Text("vn") }
        )
        //
        if (code == 0) {
            Button(
                modifier = Modifier.semantics { contentDescription = "Add" },
                onClick = {
                    insertFlashCard(FlashCard(uid = 0, englishCard = enWord, vietnameseCard = vnWord))
                })
            {
                Text("Add")
            }
        } else {
            when (code) {
                200 -> {
                    enWord = ""
                    vnWord = ""
                    changeMessage(messageAddSuccessful)
                    Log.d("MANU", "Successful")
                    code = 0

                }

                501 -> {
                    changeMessage(messageAddUnSuccessful)
                    Log.d("MANU", "Unsuccessful")
                    code = 0
                }

                else -> {
                    Log.d("MANU", "Unexpected result")
                    code = 0
                }

            }
        }
    }

}
