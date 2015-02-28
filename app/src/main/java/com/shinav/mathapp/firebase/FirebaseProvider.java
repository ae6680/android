package com.shinav.mathapp.firebase;

import com.firebase.client.Firebase;

public class FirebaseProvider {

    private static Firebase BASE_REF = new Firebase("https://arithmetic-exam-app.firebaseio.com/");

    public static Firebase getBaseRef() {
        return BASE_REF;
    }
}

