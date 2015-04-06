package com.shinav.mathapp.injection;

import com.shinav.mathapp.db.mapper.QuestionMapper;

import javax.inject.Inject;

public class TestDependencyWrapper {

    @Inject public QuestionMapper questionMapper;

}
