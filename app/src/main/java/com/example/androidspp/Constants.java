package com.example.androidspp;

import java.util.UUID;

public final class Constants {
    public static final UUID MY_UUID =
            UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    public static class EXTRA {
        public static final String EXTRA_KEY_BT_DEVICE = "bt_device";
    }

    public static class RagefinderType {
        public static final int TYPE_TRUE_PULSE_360_B = 0;
        public static final int TYPE_PLRF = 1;
    }
}

