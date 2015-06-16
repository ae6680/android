package com.shinav.mathapp.db.repository;

import com.shinav.mathapp.db.mapper.StoryboardFrameListMapper;
import com.shinav.mathapp.db.pojo.StoryboardFrame;
import com.squareup.sqlbrite.SqlBrite;

import java.util.List;

import javax.inject.Inject;

import rx.functions.Action1;

import static com.shinav.mathapp.db.helper.Tables.StoryboardFrame.STORYBOARD_KEY;
import static com.shinav.mathapp.db.helper.Tables.StoryboardFrame.TABLE_NAME;

public class StoryboardFrameRepository {

    @Inject SqlBrite db;
    @Inject StoryboardFrameListMapper listMapper;

    @Inject
    public StoryboardFrameRepository() { }

    public void findAllByParent(String storyboardKey, Action1<List<StoryboardFrame>> action) {
        db.createQuery(
                TABLE_NAME,
                "SELECT * FROM " + TABLE_NAME +
                        " WHERE " + STORYBOARD_KEY + " = ?",
                storyboardKey
        )
                .map(listMapper)
                .first()
                .subscribe(action);
    }

}
