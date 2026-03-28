package com.sohaib.zoommeeting.oneToOne.presentation.ui

import com.sohaib.zoommeeting.R
import us.zoom.sdk.NewMeetingActivity

class ActivityMeeting : NewMeetingActivity() {

    override fun getLayout(): Int {
        return R.layout.activity_meeting
    }

    override fun getLayoutForTablet(): Int {
        return R.layout.activity_meeting_tablet
    }

    override fun isSensorOrientationEnabled(): Boolean {
        return false
    }
}