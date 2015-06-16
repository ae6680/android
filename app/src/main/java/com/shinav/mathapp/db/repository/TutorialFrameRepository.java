package com.shinav.mathapp.db.repository;

import com.shinav.mathapp.db.mapper.TutorialFrameListMapper;
import com.shinav.mathapp.db.pojo.TutorialFrame;
import com.squareup.sqlbrite.SqlBrite;

import java.util.List;

import javax.inject.Inject;

import rx.functions.Action1;

import static com.shinav.mathapp.db.helper.Tables.TutorialFrame.TABLE_NAME;
import static com.shinav.mathapp.db.helper.Tables.TutorialFrame.TUTORIAL_KEY;

public class TutorialFrameRepository {

    @Inject SqlBrite db;
    @Inject TutorialFrameListMapper listMapper;

    @Inject
    public TutorialFrameRepository() { }

    public void findAllByParent(String tutorialKey, Action1<List<TutorialFrame>> action) {
        db.createQuery(
                TABLE_NAME,
                "SELECT * FROM " + TABLE_NAME +
                        " WHERE " + TUTORIAL_KEY + " = ?"
                , tutorialKey
        ).map(listMapper).first().subscribe(action);
    }

}
