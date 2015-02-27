package com.shinav.mathapp.repository;

import android.content.res.Resources;

import com.shinav.mathapp.MyApplication;
import com.shinav.mathapp.R;
import com.shinav.mathapp.question.Question;

import java.util.List;

import io.realm.Realm;

public class RealmRepository {

    private static RealmRepository REPOSITORY = new RealmRepository();
    private Realm realm = Realm.getInstance(MyApplication.getAppContext());

    public static RealmRepository getInstance() {
        Resources res = MyApplication.getAppContext().getResources();

        // Create fake info
        Question question1 = new Question();
        question1.setValue(res.getString(R.string.first_question));
        question1.setTitle(res.getString(R.string.question_title));
        question1.setAnswer("150,5");

        Question question2 = new Question();
        question2.setValue(res.getString(R.string.second_question));
        question2.setTitle(res.getString(R.string.question_title));
        question2.setAnswer("3:15 PM");

        Realm realm = Realm.getInstance(MyApplication.getAppContext());
        realm.beginTransaction();

        realm.copyToRealm(question1);
        realm.copyToRealm(question2);

        realm.commitTransaction();


        return REPOSITORY;
    }

    public List<Question> getQuestions() {
        return realm.where(Question.class).findAll();
    }


}
