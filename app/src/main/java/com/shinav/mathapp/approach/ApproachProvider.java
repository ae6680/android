package com.shinav.mathapp.approach;

import java.util.List;

public class ApproachProvider {
    private static List<Approach> currentApproach;

    public static List<Approach> getCurrentApproach() {
        return currentApproach;
    }

    public static void setCurrentApproach(List<Approach> currentApproach) {
        ApproachProvider.currentApproach = currentApproach;
    }

}
