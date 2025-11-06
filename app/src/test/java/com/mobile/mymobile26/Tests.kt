package com.mobile.mymobile26

import AddCardScreen
import Navigator
import android.content.Context
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.StateRestorationTester
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.mobile.DataManager
import com.mobile.com.mobile.mymobile26.AnNamDatabase
import com.mobile.com.mobile.mymobile26.FlashCard
import com.mobile.com.mobile.mymobile26.FlashCardDao
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner


@RunWith(RobolectricTestRunner::class)
class MyComposeTest {
    //This creates a shell activity that doesn't have any pre-set content, allowing your tests to call setContent() to set up the UI for the test.
    @get:Rule
    //val instantTaskExecutorRule = InstantTaskExecutorRule()
    val composeTestRule = createComposeRule()

    val dummyAddFlashCard = fun(english: String, vietnamese: String): Int {
        return 0
    }
    val dummyGetAllFlashCards = fun(): List<FlashCard> {
        return emptyList()
    }
    val dummyDataManager = DataManager(
        addFlashCard = dummyAddFlashCard,
        getFlashCards = dummyGetAllFlashCards
    )

    private lateinit var db: AnNamDatabase
    private lateinit var flashCardDao: FlashCardDao

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AnNamDatabase::class.java).build()
        flashCardDao = db.flashCardDao()
    }

    //val navController =
    //    TestNavHostController(ApplicationProvider.getApplicationContext())
    // TestNavigator must be converted into a ComposeNavigator by using
    //         navController.navigatorProvider.addNavigator(ComposeNavigator())

    // Navigator
    // type: Navigation
    @Test
    fun homeStartDestination() {
        val navController =
            TestNavHostController(ApplicationProvider.getApplicationContext())
        navController.navigatorProvider.addNavigator(ComposeNavigator())
        composeTestRule.setContent {
            Navigator(
                navController = navController,
                dataManager = dummyDataManager
            )
        }
        assertEquals("home", navController.currentDestination?.route)
    }
    @Test
    fun clickOnStudyCards() {
        val navController =
            TestNavHostController(ApplicationProvider.getApplicationContext())
        navController.navigatorProvider.addNavigator(ComposeNavigator())

        composeTestRule.setContent {
            Navigator(navController, dummyDataManager)
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
        val navController =
            TestNavHostController(ApplicationProvider.getApplicationContext())
        navController.navigatorProvider.addNavigator(ComposeNavigator())

        composeTestRule.setContent {
            Navigator(navController, dummyDataManager)
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
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        navController.navigatorProvider.addNavigator(ComposeNavigator())

        composeTestRule.setContent {
            Navigator(navController, dummyDataManager)
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
        val stateRestorationTester = StateRestorationTester(composeTestRule)
        /*
        The StateRestorationTester class is used to test the state restoration for composable components without recreating activities.
        This makes tests faster and more reliable, as activity recreation is a complex process with multiple synchronization mechanisms:
        */
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        navController.navigatorProvider.addNavigator(ComposeNavigator())


        // Set content through the StateRestorationTester object.
        stateRestorationTester.setContent {
            Navigator(navController, dummyDataManager)
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

        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        navController.navigatorProvider.addNavigator(ComposeNavigator())

        composeTestRule.setContent {
            Navigator(navController, dummyDataManager)

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
        composeTestRule.setContent {
            AddCardScreen(
                changeMessage = {},
                dataManager = dummyDataManager,
            )
        }
        val textInput = "house"
        composeTestRule.onNodeWithContentDescription("enTextField").assertExists().performTextInput(textInput)
        composeTestRule.onNodeWithContentDescription("enTextField").assertTextEquals("en", textInput)
    }

    // AddCard
    @Test
    fun keepEnglishStringAfterRotation() {
        val stateRestorationTester = StateRestorationTester(composeTestRule)
        stateRestorationTester.setContent {
            AddCardScreen(
                changeMessage = {},
                dataManager = dummyDataManager,
            )
        }
        val textInput = "house"
        composeTestRule.onNodeWithContentDescription("enTextField").assertExists().performTextInput(textInput)

        // Simulate a config change.
        stateRestorationTester.emulateSavedInstanceStateRestore()
        composeTestRule.onNodeWithContentDescription("enTextField").assertTextEquals("en", textInput)
    }

    @Test
    fun clickOnAddCardSuccessful() {
        val dummyAddFlashCardSuccessful = fun(english: String, vietnamese: String): Int {
            return 200
        }
        val dummyGetAllFlashCards = fun(): List<FlashCard> {
            return emptyList()
        }
        val dummyDataManager = DataManager(
            addFlashCard = dummyAddFlashCardSuccessful,
            getFlashCards = dummyGetAllFlashCards
        )
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        navController.navigatorProvider.addNavigator(ComposeNavigator())

        composeTestRule.setContent {
            Navigator(
                navController = navController,
                dataManager = dummyDataManager
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
            .assertTextEquals("Flash card successfully added to your database.")

    }
    // AddCardScren
    // type: Logic

    @Test
    fun clickOnAddCardUnSuccessful() {
        val dummyAddFlashCardUnSuccessful =  fun(english:String, vietnamese: String): Int {
            return 501
        }
        val dummyGetAllFlashCards =  fun(): List<FlashCard> {
            return emptyList()
        }

        val dummyDataManager = DataManager(
            addFlashCard = dummyAddFlashCardUnSuccessful,
            getFlashCards = dummyGetAllFlashCards
        )
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        navController.navigatorProvider.addNavigator(ComposeNavigator())

        composeTestRule.setContent {
            Navigator(
                navController = navController,
                dataManager = dummyDataManager
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