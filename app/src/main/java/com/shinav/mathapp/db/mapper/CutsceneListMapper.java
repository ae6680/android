package com.shinav.mathapp.db.mapper;

import android.database.Cursor;

import com.shinav.mathapp.db.pojo.Cutscene;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import static com.squareup.sqlbrite.SqlBrite.Query;

public class CutsceneListMapper extends ListMapper<Cutscene> {

    @Inject CutsceneMapper mapper;

    @Inject
    public CutsceneListMapper() { }

    @Override public Cutscene fromCursor(Cursor cursor) {
        return mapper.fromCursor(cursor);
    }

}
