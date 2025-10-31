package com.mobile.mymobile26

import Navigator
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.StateRestorationTester
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import com.mobile.DataManager
import com.mobile.com.mobile.mymobile26.AbstractDataManager
import com.mobile.com.mobile.mymobile26.FlashCard
import com.mobile.com.mobile.mymobile26.FlashCardDao
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test


class MyComposeTest {
    //This creates a shell activity that doesn't have any pre-set content, allowing your tests to call setContent() to set up the UI for the test.
    @get:Rule
    val composeTestRule = createComposeRule()


    // Navigator
    // type: Navigation
    @Test
    fun homeStartDestination() {
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        navController.navigatorProvider.addNavigator(ComposeNavigator())
        //val navController = rememberNavController()

        composeTestRule.setContent {
           // Navigator()
           //Navigator(navController, )
        }
        assertEquals("home", navController.currentDestination?.route)
    }

    // HomeScreen
    // type: Navigation
    @Test
    fun clickOnStudyCards() {
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        navController.navigatorProvider.addNavigator(ComposeNavigator())

        composeTestRule.setContent {
            //Navigator(navController)
        }
         composeTestRule.runOnUiThread {
            navController.navigate("home")
        }
        composeTestRule.onNodeWithContentDescription("navigateToStudyCards")
            .assertExists()
            //.assertTextEquals("Study Cards")
            .performClick();
        assertEquals("study_cards", navController.currentDestination?.route)
    }
    // HomeScreen
    // type: Navigation


   
    @Test
    fun clickOnAddCardSuccess() {
        val dummyAddFlashCardSuccessful =  fun(english:String, vietnamese: String): Int {
            return 200
        }
        val dummyGetAllFlashCards =  fun(): List<FlashCard> {
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
                dataManager = dummyDataManager,
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
    @Test
    fun clickOnAddCardUnSuccess() {
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

    // HomeScreen
    // type: Navigation
    @Test
    fun clickOnSearchCards() {
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        navController.navigatorProvider.addNavigator(ComposeNavigator())

        composeTestRule.setContent {
            //Navigator(navController)
            //Navigator()
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

    // AddCard
    // type: navigation-back
    @Test
    fun clickOnAddCardAndBack() {

        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        navController.navigatorProvider.addNavigator(ComposeNavigator())

        composeTestRule.setContent {
            //Navigator(navController)
            //Navigator()
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

    // new
    @Test
    fun typeOnEnTextInput() {
       composeTestRule.setContent {
            //AddCardScreen()
        }
        val textInput = "house"
        composeTestRule.onNodeWithContentDescription("English String").performTextInput(textInput)
        composeTestRule.onNodeWithContentDescription("English String").assertTextEquals("en", textInput)
        //composeTestRule.onNodeWithContentDescription("EnTextInput").assert(hasText(textInput, ignoreCase = true))
    }


    @Test
    fun keepEnglishStringAfterRotation() {
        val stateRestorationTester = StateRestorationTester(composeTestRule)
        stateRestorationTester.setContent {
            //AddCardScreen()
        }
        val textInput = "house"
        composeTestRule.onNodeWithContentDescription("English String").performTextInput(textInput)

        // Simulate a config change.
        stateRestorationTester.emulateSavedInstanceStateRestore()
        composeTestRule.onNodeWithContentDescription("English String").assertTextEquals("en", textInput)
    }

    // For the sake of example
    @Test
    fun restartEnglishStringAfterRotation() {
        val stateRestorationTester = StateRestorationTester(composeTestRule)
        stateRestorationTester.setContent {
           // AddCardScreen(
           //     changeMessage = {},
           //     addFlashCard = {}
            //)
        }
        val textInput = "house"
        composeTestRule.onNodeWithContentDescription("English String").performTextInput(textInput)

        // Simulate a config change.
        stateRestorationTester.emulateSavedInstanceStateRestore()
        composeTestRule.onNodeWithContentDescription("English String").assertTextEquals("en", "")
    }


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
           // Navigator(navController)
            //Navigator()
        }
        composeTestRule.runOnUiThread {
            navController.navigate("home")
        }
        // Simulate a config change.
        stateRestorationTester.emulateSavedInstanceStateRestore()
        assertEquals("home", navController.currentDestination?.route)

    }

}