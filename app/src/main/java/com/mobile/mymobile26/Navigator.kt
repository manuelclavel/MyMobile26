import android.database.sqlite.SQLiteConstraintException
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.mobile.com.mobile.mymobile26.FlashCard
import com.mobile.com.mobile.mymobile26.FlashCardDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext


//@OptIn(ExperimentalMaterial3Api::class)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Navigator(navController: NavHostController, dao: FlashCardDao) {
    val navigateToAddCard = fun() {
        navController.navigate("add_card")
    }
    val navigateToStudyCards = fun() {
        navController.navigate("study_cards")
    }
    val navigateToSearchCards = fun() {
        navController.navigate("search_cards")
    }
    var message by rememberSaveable { mutableStateOf("") }

    val changeMessage = fun (text:String){
        message = text
    }

    val getAllFlashCards =  fun(): List<FlashCard> {
        var flashCards : List<FlashCard> = emptyList()
       runBlocking {
            async {
                withContext(Dispatchers.IO) {
                    try {
                        flashCards = dao.getAll()
                        Log.d("MANU", flashCards.toString())
                    } catch (e: SQLiteConstraintException) {
                        // Handle specific Room exceptions like unique constraint violation
                        // Log the error or show a user-friendly message
                        Log.d("MANU", "Error getting flash cards: ${e.message}")
                    } catch (e: Exception) {
                        // Catch any other unexpected exceptions
                        Log.d("MANU", "An unexpected error occurred: ${e.message}")
                    }
                }
            }.await()
        }
        return flashCards
    }

    val addFlashCard =  fun(english:String, vietnamese: String): Int {
        var code = 0
        runBlocking {
            async {
                withContext(Dispatchers.IO) {
                    try {
                        dao.insertAll(
                            FlashCard(
                                uid = 0,
                                englishCard = english,
                                vietnameseCard = vietnamese
                            )
                        )
                        code = 200
                    } catch (e: SQLiteConstraintException) {
                        // Handle specific Room exceptions like unique constraint violation
                        // Log the error or show a user-friendly message
                        code = 501
                        Log.d("MANU", "Error inserting user: ${e.message}")
                    } catch (e: Exception) {
                        // Catch any other unexpected exceptions
                        code = 500
                        Log.d("MANU", "An unexpected error occurred: ${e.message}")
                    }
                }
            }.await()
        }
        return code
    }
    Scaffold(
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        Text(
                            //modifier = Modifier.semantics {
                            //    contentDescription = "user"
                            //},
                            text = "An Nam Study Room"
                        )
                    }
                },
                navigationIcon = {
                    val currentRoute =
                        navController.currentBackStackEntryAsState().value?.destination?.route
                    if (currentRoute != "home") {
                        Button(
                            modifier = Modifier.semantics{contentDescription="navigateBack"},
                            onClick = {
                                navController.navigateUp()
                            }) {
                            Text("Back")
                        }
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar(
                actions = {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .semantics {
                                contentDescription = "Message"
                            },
                        textAlign = TextAlign.Center,
                        text = message
                    )
                })
        }
    ) { innerPadding ->
        NavHost(
            modifier = Modifier.padding(innerPadding)
                .fillMaxWidth(),
            navController = navController,
            startDestination = "home"
        ) {
            // HOME
            composable(route = "home") {
                HomeScreen(
                    changeMessage = changeMessage,
                    navigateToAddCard = navigateToAddCard,
                    navigateToStudyCards = navigateToStudyCards,
                    navigateToSearchCards = navigateToSearchCards
                )
            }
            // ADD CARD
            composable(route = "add_card") {
                AddCardScreen(
                    changeMessage = changeMessage,
                    addFlashCard = addFlashCard
                )
            }
            // STUDY CARDS
            composable(route = "study_cards") {
                StudyCardsScreen(
                )
            }
            // SEARCH CARDS
            composable(route = "search_cards") {
                SearchCardsScreen(
                    getAllFlashCards =  getAllFlashCards
                )
            }
        }
    }
}


