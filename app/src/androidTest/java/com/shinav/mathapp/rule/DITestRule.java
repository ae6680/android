package com.shinav.mathapp.rule;

import android.support.test.InstrumentationRegistry;

import com.shinav.mathapp.MyApplication;
import com.shinav.mathapp.injection.TestDependencyWrapper;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public class DITestRule implements TestRule {

    public TestDependencyWrapper dependencies = new TestDependencyWrapper();

    @Override
    public Statement apply(final Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                MyApplication app = (MyApplication) InstrumentationRegistry
                        .getInstrumentation().getTargetContext().getApplicationContext();

                app.setMockMode(true);

                base.evaluate();
            }
        };
    }
}
