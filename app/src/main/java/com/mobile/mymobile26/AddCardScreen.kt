
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import com.mobile.DataManager
import com.mobile.R


@Composable
fun AddCardScreen(
    changeMessage: (String) -> Unit,
    dataManager: DataManager
) {
    var enWord by rememberSaveable { mutableStateOf("") }
    var vnWord by rememberSaveable { mutableStateOf("") }

    LaunchedEffect(Unit) {
       changeMessage("")
    }
    val messageAddSuccessful = stringResource(id = R.string.add_successful)
    val messageAddUnSuccessful = stringResource(id = R.string.add_unsuccessful)

    // just another another test and another
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
        Button(
            modifier = Modifier.semantics{contentDescription="Add"},
            onClick = {
                val code = dataManager.addFlashCard(enWord, vnWord)
                when (code) {
                    200 -> {
                        enWord = ""
                        vnWord = ""
                        changeMessage(messageAddSuccessful)
                        Log.d("MANU", "Successful")
                    }
                    501 -> {
                        changeMessage(messageAddUnSuccessful)
                        Log.d("MANU", "Unsuccessful")
                    }
                    else -> {
                        Log.d("MANU", "Unexpected result")
                    }
                }
            })
                {
                    Text("Add")
                }
            }
    }
