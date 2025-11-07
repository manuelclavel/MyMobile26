package com.mobile.mymobile26

import Navigator
import android.database.sqlite.SQLiteConstraintException
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.StateRestorationTester
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import com.mobile.com.mobile.mymobile26.AbstractRepository
import com.mobile.com.mobile.mymobile26.FlashCard
import com.mobile.com.mobile.mymobile26.FlashCardResourceProvider
import com.mobile.com.mobile.mymobile26.ui.FlashCardViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner


class DummyRepository(override val allFlashCards: StateFlow<List<FlashCard>>) : AbstractRepository() {
    override suspend fun insert(flashCard: FlashCard) {
    }
    override suspend fun delete(flashCard: FlashCard) {
    }
}
class DummyRepositoryInsertSuccessful(override val allFlashCards: StateFlow<List<FlashCard>>) : AbstractRepository() {
    override suspend fun insert(flashCard: FlashCard) {
    }
    override suspend fun delete(flashCard: FlashCard) {
    }
}

class DummyRepositoryInsertUnSuccessful(override val allFlashCards: StateFlow<List<FlashCard>>) : AbstractRepository() {
    override suspend fun insert(flashCard: FlashCard) {
        throw SQLiteConstraintException()
    }
    override suspend fun delete(flashCard: FlashCard) {
    }
}


@RunWith(RobolectricTestRunner::class)
class MyComposeTest {
    //This creates a shell activity that doesn't have any pre-set content, allowing your tests to call setContent() to set up the UI for the test.
    @get:Rule
    //val instantTaskExecutorRule = InstantTaskExecutorRule()
    val composeTestRule = createComposeRule()


    //private lateinit var db: AnNamDatabase
    //private lateinit var flashCardDao: FlashCardDao



    val dummyRepositoryInsertSuccessful = DummyRepositoryInsertSuccessful(
        allFlashCards = MutableStateFlow(emptyList<FlashCard>())
    )

    val dummyRepositoryInsertUnSuccessful = DummyRepositoryInsertUnSuccessful(
        allFlashCards = MutableStateFlow(emptyList<FlashCard>())
    )


    //val navController =
    //    TestNavHostController(ApplicationProvider.getApplicationContext())
    // TestNavigator must be converted into a ComposeNavigator by using
    //         navController.navigatorProvider.addNavigator(ComposeNavigator())

    // Navigator
    // type: Navigation
    @Test
    fun homeStartDestination() {
        val resourceProvider = FlashCardResourceProvider(
            context = ApplicationProvider.getApplicationContext()
        )
        val dummyRepository = DummyRepository(
            allFlashCards = MutableStateFlow(emptyList<FlashCard>())
        )
        val flashCardViewModel = FlashCardViewModel(
            resourceProvider = resourceProvider,
            repository = dummyRepository
        )
        val navController =
            TestNavHostController(ApplicationProvider.getApplicationContext())
        navController.navigatorProvider.addNavigator(ComposeNavigator())
        composeTestRule.setContent {
            Navigator(
                navController = navController,
                flashCardViewModel = flashCardViewModel
            )
        }
        assertEquals("home", navController.currentDestination?.route)
    }

    @Test
    fun clickOnStudyCards() {
        val resourceProvider = FlashCardResourceProvider(
            context = ApplicationProvider.getApplicationContext()
        )
        val dummyRepository = DummyRepository(
            allFlashCards = MutableStateFlow(emptyList<FlashCard>())
        )
        val flashCardViewModel = FlashCardViewModel(
            resourceProvider = resourceProvider,
            repository = dummyRepository
        )
        val navController =
            TestNavHostController(ApplicationProvider.getApplicationContext())
        navController.navigatorProvider.addNavigator(ComposeNavigator())

        composeTestRule.setContent {
            Navigator(navController, flashCardViewModel)
        }
        composeTestRule.runOnUiThread {
            navController.navigate("home")
        }
        composeTestRule.onNodeWithContentDescription("navigateToStudyCards")
            .assertExists()
            .assertTextEquals("Study Cards")
            .performClick();
        assertEquals("study_cards", navController.currentDestination?.route)
    }
    // HomeScreen
    // type: Navigation

    @Test
    fun clickOnAddCard() {
        val resourceProvider = FlashCardResourceProvider(
            context = ApplicationProvider.getApplicationContext()
        )
        val dummyRepository = DummyRepository(
            allFlashCards = MutableStateFlow(emptyList<FlashCard>())
        )
        val flashCardViewModel = FlashCardViewModel(
            resourceProvider = resourceProvider,
            repository = dummyRepository
        )
        val navController =
            TestNavHostController(ApplicationProvider.getApplicationContext())
        navController.navigatorProvider.addNavigator(ComposeNavigator())

        composeTestRule.setContent {
            Navigator(navController, flashCardViewModel)
        }
        composeTestRule.runOnUiThread {
            navController.navigate("home")
        }
        composeTestRule.onNodeWithContentDescription("navigateToAddCard")
            .assertExists()
            .assertTextEquals("Add Card")
            .performClick();
        assertEquals("add_card", navController.currentDestination?.route)
    }

    // HomeScreen
    // type: Navigation
    @Test
    fun clickOnSearchCards() {
        val resourceProvider = FlashCardResourceProvider(
            context = ApplicationProvider.getApplicationContext()
        )
        val dummyRepository = DummyRepository(
            allFlashCards = MutableStateFlow(emptyList<FlashCard>())
        )
        val flashCardViewModel = FlashCardViewModel(
            resourceProvider = resourceProvider,
            repository = dummyRepository
        )
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        navController.navigatorProvider.addNavigator(ComposeNavigator())

        composeTestRule.setContent {
            Navigator(navController, flashCardViewModel)
        }
        composeTestRule.runOnUiThread {
            navController.navigate("home")
        }
        composeTestRule.onNodeWithContentDescription("navigateToSearchCards")
            .assertExists()
            .assertTextEquals("Search Cards")
            .performClick();
        assertEquals("search_cards", navController.currentDestination?.route)
    }

    // home
    @Test
    fun homeScreenRetained_afterConfigChange() {
        val resourceProvider = FlashCardResourceProvider(
            context = ApplicationProvider.getApplicationContext()
        )
        val dummyRepository = DummyRepository(
            allFlashCards = MutableStateFlow(emptyList<FlashCard>())
        )
        val flashCardViewModel = FlashCardViewModel(
            resourceProvider = resourceProvider,
            repository = dummyRepository
        )
        val stateRestorationTester = StateRestorationTester(composeTestRule)
        /*
        The StateRestorationTester class is used to test the state restoration for composable components without recreating activities.
        This makes tests faster and more reliable, as activity recreation is a complex process with multiple synchronization mechanisms:
        */
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        navController.navigatorProvider.addNavigator(ComposeNavigator())


        // Set content through the StateRestorationTester object.
        stateRestorationTester.setContent {
            Navigator(navController, flashCardViewModel)
        }
        composeTestRule.runOnUiThread {
            navController.navigate("home")
        }
        // Simulate a config change.
        stateRestorationTester.emulateSavedInstanceStateRestore()
        assertEquals("home", navController.currentDestination?.route)
    }

    // AddCardScren
    // type: Logic


    // AddCard
    // type: navigation-back
    @Test
    fun clickOnAddCardAndBack() {
        val resourceProvider = FlashCardResourceProvider(
            context = ApplicationProvider.getApplicationContext()
        )
        val dummyRepository = DummyRepository(
            allFlashCards = MutableStateFlow(emptyList<FlashCard>())
        )
        val flashCardViewModel = FlashCardViewModel(
            resourceProvider = resourceProvider,
            repository = dummyRepository
        )

        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        navController.navigatorProvider.addNavigator(ComposeNavigator())

        composeTestRule.setContent {
            Navigator(navController, flashCardViewModel)

        }
        composeTestRule.runOnUiThread {
            navController.navigate("home")
        }
        composeTestRule.onNodeWithContentDescription("navigateToAddCard")
            .performClick();

        //composeTestRule.onNodeWithText("Back").assertExists().performClick()
        composeTestRule.onNodeWithContentDescription("navigateBack").assertExists().performClick();
        assertEquals("home", navController.currentDestination?.route)
    }


    // AddCard
    @Test
    fun typeOnEnTextInput() {
        val resourceProvider = FlashCardResourceProvider(
            context = ApplicationProvider.getApplicationContext()
        )
        val dummyRepository = DummyRepository(
            allFlashCards = MutableStateFlow(emptyList<FlashCard>())
        )
        val flashCardViewModel = FlashCardViewModel(
            resourceProvider = resourceProvider,
            repository = dummyRepository
        )
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        navController.navigatorProvider.addNavigator(ComposeNavigator())

        composeTestRule.setContent {
            Navigator(navController, flashCardViewModel)
        }
        composeTestRule.runOnUiThread {
            navController.navigate("home")
        }
        composeTestRule.onNodeWithContentDescription("navigateToAddCard")
            .performClick();

        val textInput = "house"
        composeTestRule.onNodeWithContentDescription("enTextField").assertExists()
            .performTextInput(textInput)
        composeTestRule.onNodeWithContentDescription("enTextField")
            .assertTextEquals("en", textInput)
    }

    // AddCard
    @Test
    fun keepEnglishStringAfterRotation() {
        val resourceProvider = FlashCardResourceProvider(
            context = ApplicationProvider.getApplicationContext()
        )
        val dummyRepository = DummyRepository(
            allFlashCards = MutableStateFlow(emptyList<FlashCard>())
        )
        val flashCardViewModel = FlashCardViewModel(
            resourceProvider = resourceProvider,
            repository = dummyRepository
        )
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        navController.navigatorProvider.addNavigator(ComposeNavigator())

        val stateRestorationTester = StateRestorationTester(composeTestRule)
        stateRestorationTester.setContent {
            Navigator(navController, flashCardViewModel)
        }
        composeTestRule.runOnUiThread {
            navController.navigate("home")
        }
        composeTestRule.onNodeWithContentDescription("navigateToAddCard")
            .performClick();

        val textInput = "house"
        composeTestRule.onNodeWithContentDescription("enTextField").assertExists().performTextInput(textInput)

        // Simulate a config change.
        stateRestorationTester.emulateSavedInstanceStateRestore()
        composeTestRule.onNodeWithContentDescription("enTextField").assertTextEquals("en", textInput)
    }


    @Test
    fun clickOnAddCardSuccessful() {
        val resourceProvider = FlashCardResourceProvider(
            context = ApplicationProvider.getApplicationContext()
        )
        val dummyRepository = DummyRepositoryInsertSuccessful(
            allFlashCards = MutableStateFlow(emptyList<FlashCard>())
        )
        val flashCardViewModel = FlashCardViewModel(
            resourceProvider = resourceProvider,
            repository = dummyRepository
        )

        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        navController.navigatorProvider.addNavigator(ComposeNavigator())

        composeTestRule.setContent {
            Navigator(
                navController = navController,
                flashCardViewModel = flashCardViewModel
            )
        }
        composeTestRule.runOnUiThread {
            navController.navigate("home")
        }
        composeTestRule.onNodeWithContentDescription("navigateToAddCard")
            .performClick();
        
        composeTestRule.onNodeWithContentDescription("Add")
                .assertExists()
                .performClick()

        composeTestRule.onNodeWithContentDescription("Message")
            .assertExists()
            .assertTextEquals("Flash card successfully added to your database.")

    }
    // AddCardScren
    // type: Logic

    @Test
    fun clickOnAddCardUnSuccessful() {
        val resourceProvider = FlashCardResourceProvider(
            context = ApplicationProvider.getApplicationContext()
        )
        val dummyRepository = DummyRepositoryInsertUnSuccessful(
            allFlashCards = MutableStateFlow(emptyList<FlashCard>())
        )
        val flashCardViewModel = FlashCardViewModel(
            resourceProvider = resourceProvider,
            repository = dummyRepository
        )
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        navController.navigatorProvider.addNavigator(ComposeNavigator())

        composeTestRule.setContent {
            Navigator(
                navController = navController,
                flashCardViewModel = flashCardViewModel
            )
        }
        composeTestRule.runOnUiThread {
            navController.navigate("add_card")
        }

        composeTestRule.onNodeWithContentDescription("Add")
            .assertExists()
            .performClick()

        composeTestRule.onNodeWithContentDescription("Message")
            .assertExists()
            .assertTextEquals("Flash card already exists in your database.")
    }
    
}

