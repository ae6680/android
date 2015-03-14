package com.shinav.mathapp.progress;

import com.shinav.mathapp.approach.Approach;
import com.shinav.mathapp.question.Question;

import java.util.List;

public class ProgressProvider {
    private static List<Approach> currentApproach;
    private static Question currentQuestion;

    public static List<Approach> getCurrentApproach() {
        return currentApproach;
    }

    public static void setCurrentApproach(List<Approach> currentApproach) {
        ProgressProvider.currentApproach = currentApproach;
    }

    public static Question getCurrentQuestion() {
        return currentQuestion;
    }

    public static void setCurrentQuestion(Question currentQuestion) {
        ProgressProvider.currentQuestion = currentQuestion;
    }
}
