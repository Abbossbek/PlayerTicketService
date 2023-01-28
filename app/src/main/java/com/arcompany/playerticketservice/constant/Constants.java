package com.arcompany.playerticketservice.constant;

public class Constants {
    public static final int NOTIFICATION_ID_FOREGROUND_SERVICE = 8466503;
    public static final long DELAY_SHUTDOWN_FOREGROUND_SERVICE = 20000;
    public static final long DELAY_UPDATE_NOTIFICATION_FOREGROUND_SERVICE = 10000;

    public static class ACTION {
        public static final String PAUSE_ACTION = "music.action.pause";
        public static final String START_ACTION = "music.action.start";
        public static final String STOP_ACTION = "music.action.stop";

    }

    public static class STATE_SERVICE {

        public static final int STOP = 30;
        public static final int START = 20;
        public static final int PAUSE = 10;


    }
}
