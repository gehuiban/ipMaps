package com.logan19gp.ipmaps;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.test.suitebuilder.annotation.MediumTest;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.junit.Before;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ActivityInstrumentationTestCase2<MapsActivity>
{
    Activity mActivity;
    private TextView ipFirst, ipSecond, ipThird, ipFourth, ipFirstB, ipSecondB, ipThirdB, ipFourthB, title;
    ImageView mapItView;

    public ApplicationTest()
    {
        super(MapsActivity.class);
    }

    @Before
    public void setUp() throws Exception
    {
        super.setUp();
        mActivity = getActivity();
        ipFirst = (TextView) mActivity.findViewById(R.id.ip_first);
        ipFirstB = (TextView) mActivity.findViewById(R.id.ip_first_b);
        ipSecond = (TextView) mActivity.findViewById(R.id.ip_second);
        ipSecondB = (TextView) mActivity.findViewById(R.id.ip_second_b);
        ipThird = (TextView) mActivity.findViewById(R.id.ip_third);
        ipThirdB = (TextView) mActivity.findViewById(R.id.ip_third_b);
        ipFourth = (TextView) mActivity.findViewById(R.id.ip_fourth);
        ipFourthB = (TextView) mActivity.findViewById(R.id.ip_fourth_b);
        title = (TextView) mActivity.findViewById(R.id.title);
        mapItView = (ImageView) mActivity.findViewById(R.id.button_map_it);
        mActivity = getActivity();
    }

    public void testPreconditions()
    {
        assertNotNull("mActivity is null", mActivity);
        assertNotNull("ipFirst is null", ipFirst);
        assertNotNull("ipSecond is null", ipSecond);
        assertNotNull("ipThird is null", ipThird);
        assertNotNull("ipFourth is null", ipFourth);
        assertNotNull("ipFirstB is null", ipFirstB);
        assertNotNull("ipSecondB is null", ipSecondB);
        assertNotNull("ipThirdB is null", ipThirdB);
        assertNotNull("ipFourthB is null", ipFourthB);
        assertNotNull("mapItView is null", mapItView);
    }

    @MediumTest
    public void testClickMeButton_clickButtonAndExpectInfoText()
    {
        assertTrue(View.VISIBLE == mapItView.getVisibility());
        TouchUtils.clickView(this, mapItView);
    }

    public void testMyFirstTestTextView_labelText()
    {
        final String expected = mActivity.getString(R.string.enter_ip);
        final String actual = title.getText().toString();
        assertEquals(expected, actual);
    }
}