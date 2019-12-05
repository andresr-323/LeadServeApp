package com.example.leadserve;

import android.view.View;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class AppUITest {

    private static final String EMAIL = "andresrodriguez1337@gmail.com";
    private static final String PASSWORD = "password";


    @Rule
    public ActivityTestRule<MainActivity> activityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    private Solo solo;


    @Before
    public void setUp() throws Exception {
        //setUp() is run before a test case is started.
        //This is where the solo object is created.
        solo = new Solo(InstrumentationRegistry.getInstrumentation(),
                activityTestRule.getActivity());
    }

    @After
    public void tearDown() throws Exception {
        //tearDown() is run after a test case has finished.
        //finishOpenedActivities() will finish all the activities that have been opened during the test execution.
        solo.finishOpenedActivities();
    }

    @Test
    public void testCorrectLogin() throws Exception {
        solo.unlockScreen();
//        solo.clickOnView(solo.getView(R.id.passText));
//        solo.clickOnView(solo.getView(R.id.emailText));
        solo.enterText(1, EMAIL);
        solo.enterText(0,PASSWORD);

        solo.clickOnButton("login");
        solo.assertCurrentActivity("Expected Loading class", Loading.class);
        solo.waitForActivity(Loading.class, 1000);
        solo.assertCurrentActivity("Expected homepage class", homepage.class);
    }

    @Test
    public void testIncorrectLogin() throws Exception {
        solo.unlockScreen();
        solo.clickOnView(solo.getView(R.id.emailText));
        solo.enterText(1, EMAIL);
        solo.clickOnView(solo.getView(R.id.passText));
        solo.enterText(0, "asdnmgs");

        solo.clickOnButton("login");

        solo.waitForDialogToOpen();
        Assert.assertTrue(solo.searchText("login not successful"));
    }

    @Test
    public void testIncorrectEmailFormat() throws Exception {
        solo.unlockScreen();
        solo.clickOnView(solo.getView(R.id.emailText));
        solo.enterText(1, EMAIL+".....com");
        solo.clickOnView(solo.getView(R.id.passText));
        solo.enterText(0, PASSWORD);

        solo.clickOnButton("login");

        solo.waitForDialogToOpen();
        Assert.assertTrue(solo.searchText("Email is not valid"));
    }

    @Test
    public void appFlow() throws Exception {
        solo.unlockScreen();
        solo.clickOnView(solo.getView(R.id.emailText));
        solo.enterText(1, EMAIL);
        solo.clickOnView(solo.getView(R.id.passText));
        solo.enterText(0, PASSWORD);

        solo.clickOnButton("login");
        solo.assertCurrentActivity("Expected Loading class", Loading.class);
        solo.waitForActivity(Loading.class, 1000);
        solo.assertCurrentActivity("Expected homepage class", homepage.class);
        solo.waitForActivity(homepage.class, 1000);

        solo.clickOnText("Navigation");
        solo.clickOnText("Events");
        solo.assertCurrentActivity("Expected EventActivity", EventsActivity.class);
        solo.waitForActivity(EventsActivity.class, 1000);
        solo.clickOnText("Future Events");
        solo.waitForActivity(EventsActivity.class, 1000);
        solo.clickOnText("end year party");
        solo.assertCurrentActivity("Expected DisplayEventActivity", DisplayEventActivity.class);
        solo.waitForActivity(DisplayEventActivity.class, 1000);
        solo.goBack();
        solo.assertCurrentActivity("Expected EventActivity", EventsActivity.class);
        solo.waitForActivity(EventsActivity.class, 1000);
        solo.goBack();
        solo.assertCurrentActivity("Expected homepage class", homepage.class);
        solo.waitForActivity(homepage.class, 1000);

        solo.clickOnText("Navigation");
        solo.clickOnText("Progress");
        solo.assertCurrentActivity("Expected Progress Activity class", ProgressActivity.class);
        solo.waitForActivity(homepage.class, 1000);
        solo.clickOnText("Tier One Progress");
        Assert.assertTrue(solo.searchText("LEAD 1000"));
        solo.clickOnText("Tier Two Progress");
        Assert.assertFalse(solo.searchText("LEAD 2000"));
        solo.clickOnText("Tier Three Progress");
        Assert.assertTrue(solo.searchText("LEAD 3000"));
        solo.goBack();
        solo.assertCurrentActivity("Expected homepage class", homepage.class);
        solo.waitForActivity(homepage.class, 1000);

        solo.clickOnText("Navigation");
        solo.clickOnText("My Information");
        solo.assertCurrentActivity("Expected MyInfoActivity class", MyInfoActivity.class);
        solo.waitForActivity(MyInfoActivity.class, 1000);
        Assert.assertTrue(solo.searchText("Tier Number:"));
        Assert.assertTrue(solo.searchText("Advisor:"));
        Assert.assertTrue(solo.searchText("Campus:"));
        Assert.assertTrue(solo.searchText("Major:"));
        Assert.assertTrue(solo.searchText("Expected Grad Date:"));
        Assert.assertTrue(solo.searchText("Hometown:"));
        solo.goBack();
        solo.assertCurrentActivity("Expected homepage class", homepage.class);
        solo.waitForActivity(homepage.class, 1000);

        solo.clickOnText("Navigation");
        solo.clickOnText("Logout");
        solo.assertCurrentActivity("Expected MainActivity class", MainActivity.class);
    }

    @Test
    public void testMessaging() throws Exception {
        solo.unlockScreen();
        solo.clickOnView(solo.getView(R.id.emailText));
        solo.enterText(1, EMAIL);
        solo.clickOnView(solo.getView(R.id.passText));
        solo.enterText(0, PASSWORD);

        solo.clickOnButton("login");
        solo.assertCurrentActivity("Expected Loading class", Loading.class);
        solo.waitForActivity(Loading.class, 500);
        solo.assertCurrentActivity("Expected homepage class", homepage.class);
        solo.clickOnText("Kira Bowden");
        solo.assertCurrentActivity("Expected MessagesActivity class", MessagesActivity.class);
        solo.clickOnView(solo.getView(R.id.message_text));
        solo.enterText(0,"This is Robotium testing out the messaging");
        solo.clickOnView(solo.getView(R.id.send_message));
        Assert.assertTrue(solo.searchText("This is Robotium testing out the messaging"));

    }
}
