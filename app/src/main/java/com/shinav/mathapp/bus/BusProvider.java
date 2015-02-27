package com.shinav.mathapp.bus;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

public class BusProvider {

    private static final Bus ANY_BUS = new Bus(ThreadEnforcer.ANY);
    private static final Bus UI_BUS = new Bus();

    private BusProvider() {}

    public static Bus getAnyBusInstance() {
        return ANY_BUS;
    }

    public static Bus getUIBusInstance () {
        return UI_BUS;
    }
}

