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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.mobile.com.mobile.mymobile26.FlashCard
import com.mobile.com.mobile.mymobile26.ShowCardScreen
import com.mobile.com.mobile.mymobile26.ui.FlashCardViewModel


//@OptIn(ExperimentalMaterial3Api::class)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Navigator(
    navController: NavHostController,
    flashCardViewModel: FlashCardViewModel
) {
    val flashCardUiState by flashCardViewModel.uiState.collectAsState()

    val navigateToAddCard = fun() {
        navController.navigate("add_card")
    }
    val navigateToStudyCards = fun() {
        navController.navigate("study_cards")
    }
    val navigateToSearchCards = fun() {
        navController.navigate("search_cards")
    }

    //fun selectFlashCard(flashCardId:Int){
    //    flashCardViewModel.selectItem(flashCardId)
    //}

    val navigateToShowCard = fun(flashCardId: Int) {
        flashCardViewModel.selectItem(flashCardId)
        navController.navigate("show_card")
    }

    val changeMessage = fun (text:String){
        flashCardViewModel.updateCurrentMessage(text)
    }
    val updateAddEnWord = fun (text:String){
        flashCardViewModel.updateAddEnWord(text)
        }
    val updateAddVnWord = fun (text:String){
        flashCardViewModel.updateAddVnWord(text)
    }


    val deleteFlashCard = fun (flashCard:FlashCard){
        flashCardViewModel.deleteFlashCard(flashCard)
    }

    val insertFlashCard = fun (flashCard: FlashCard) {
        flashCardViewModel.insertFlashCard(flashCard)
    }

    val flashCards: List<FlashCard>
    by flashCardViewModel.allFlashCards.collectAsStateWithLifecycle()

    val selectedFlashCard: FlashCard?
    by flashCardViewModel.selectedItem.collectAsStateWithLifecycle()







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
                        text = flashCardUiState.currentMessage
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
                    enWord = flashCardUiState.currentEnWord,
                    vnWord = flashCardUiState.currentVnWord,
                    updateAddEnWord = updateAddEnWord,
                    updateAddVnWord = updateAddVnWord,
                    insertFlashCard = insertFlashCard
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
                    flashCards = flashCards,
                    selectedItem = navigateToShowCard
                )
            }
            // SHOW CARDS
            composable(route = "show_card") {
                ShowCardScreen(
                    flashCard = selectedFlashCard,
                    deleteFlashCard = deleteFlashCard
                )
            }
        }
    }
}


