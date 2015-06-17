package com.shinav.mathapp.firebase.mapper;

import com.firebase.client.DataSnapshot;

public abstract class FirebaseMapper<C1> {

    public abstract C1 fromDataSnapshot(DataSnapshot dataSnapshot);

    public String getString(DataSnapshot dataSnapshot, String attribute) {
        return dataSnapshot.child(attribute).getValue().toString();
    }

}
