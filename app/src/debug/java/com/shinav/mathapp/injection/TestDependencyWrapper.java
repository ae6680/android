package com.shinav.mathapp.injection;

import com.shinav.mathapp.db.dataMapper.QuestionMapper;

import javax.inject.Inject;

public class TestDependencyWrapper {

    @Inject public QuestionMapper questionMapper;

}
