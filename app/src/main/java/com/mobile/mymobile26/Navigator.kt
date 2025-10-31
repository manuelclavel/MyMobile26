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
import com.mobile.DataManager


//@OptIn(ExperimentalMaterial3Api::class)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Navigator(
    navController: NavHostController,
    dataManager: DataManager
) {
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
                    dataManager = dataManager
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
                    dataManager =  dataManager
                )
            }
        }
    }
}


