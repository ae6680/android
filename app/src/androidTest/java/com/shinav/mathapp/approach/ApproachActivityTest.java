package com.shinav.mathapp.questionApproach;

import android.content.Intent;
import android.support.test.runner.AndroidJUnit4;

import com.shinav.mathapp.db.pojo.QuestionApproach;
import com.shinav.mathapp.injection.TestDependencyWrapper;
import com.shinav.mathapp.injection.component.ApproachActivityComponent;
import com.shinav.mathapp.storytelling.Storyteller;
import com.shinav.mathapp.rule.ActivityRule;
import com.shinav.mathapp.rule.DITestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import rx.Subscription;
import rx.functions.Action1;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class ApproachActivityTest {

    public final DITestRule DITestRule = new DITestRule();

    public final ActivityRule<ApproachActivity> approachActivityRule =
            new ActivityRule<ApproachActivity>(ApproachActivity.class) {

                @Override protected Intent getLaunchIntent(String targetPackage, Class<ApproachActivity> activityClass) {
                    Intent intent = super.getLaunchIntent(targetPackage, activityClass);
                    intent.putExtra(Storyteller.TYPE_KEY, "questionKey");
                    return intent;
                }

            };

    @Rule public final TestRule rule = RuleChain.outerRule(DITestRule).around(approachActivityRule);

    private TestDependencyWrapper dependencies;

    @Before public void setUp() {
        ApproachActivity context = approachActivityRule.get();
        ApproachActivityComponent component = ApproachActivityComponent.Initializer.init(context, true);
        component.inject(DITestRule.dependencies);
        dependencies = DITestRule.dependencies;
    }

    @Test public void testApproachSetKey() throws Exception {

        QuestionApproach questionApproach = new QuestionApproach();
        questionApproach.setKey("hoi");

        when(dependencies.questionMapper.getByKey(any(String.class), any(Action1.class))
        )
                .thenReturn(Mockito.mock(Subscription.class));

        assertEquals("hoi", questionApproach.getKey());
    }

}
