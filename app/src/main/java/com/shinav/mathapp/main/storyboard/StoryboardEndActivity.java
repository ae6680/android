package com.shinav.mathapp.main.storyboard;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.shinav.mathapp.R;
import com.shinav.mathapp.db.repository.QuestionRepository;
import com.shinav.mathapp.injection.component.Injector;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

import static com.squareup.sqlbrite.SqlBrite.Query;

public class StoryboardEndActivity extends Activity {

    public static final float PASS_THRESHOLD = 0.55F;

    @InjectView(R.id.image) ImageView image;
    @InjectView(R.id.message) TextView message;
    @InjectView(R.id.ending_background) ImageView background;

    @InjectView(R.id.amount_pass) TextView amountPass;
    @InjectView(R.id.amount_fail) TextView amountFail;

    @Inject QuestionRepository questionRepository;

    private Observable<Integer> amountPassedObservable;
    private Observable<Integer> amountFailedObservable;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storyboard_end);

        ButterKnife.inject(this);
        Injector.getActivityComponent(this).inject(this);

        fetchAmountPassed();
        fetchAmountFailed();

        calculateResult();
    }

    private void fetchAmountPassed() {
        amountPassedObservable = questionRepository.findAmountPassed()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new AmountQueryReady()).first();
    }

    private void fetchAmountFailed() {
        amountFailedObservable = questionRepository.findAmountFailed()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new AmountQueryReady()).first();
    }

    private void calculateResult() {
        Observable.combineLatest(
                amountPassedObservable,
                amountFailedObservable,
                new AmountReady()
        )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .first().subscribe();
    }

    private void showPassLayout() {
        image.setImageResource(R.drawable.pass_icon);
        message.setText(getString(R.string.ending_message_pass));
        background.setImageResource(R.drawable.ending_background_pass);
    }

    private void showFailLayout() {
        image.setImageResource(R.drawable.fail_icon);
        message.setText(getString(R.string.ending_message_fail));
        background.setImageResource(R.drawable.ending_background_fail);
    }

    private void showPassAmount(int amount) {
        amountPass.setText(String.valueOf(amount));
    }

    private void showFailAmount(int amount) {
        amountFail.setText(String.valueOf(amount));
    }

    @OnClick(R.id.ok_button)
    public void onOkClicked() {
        onBackPressed();
    }

    private class AmountQueryReady implements Func1<Query, Integer> {

        @Override public Integer call(Query query) {
            Cursor c = query.run();
            try {
                return c.getCount();
            } finally {
                c.close();
            }
        }
    }

    private class AmountReady implements Func2<Integer, Integer, Void> {
        @Override public Void call(Integer amountPassed, Integer amountFailed) {

            float totalAmount = amountPassed + amountFailed;

            if (((float) amountPassed / totalAmount) > PASS_THRESHOLD) {
                showPassLayout();
            } else {
                showFailLayout();
            }

            showPassAmount(amountPassed);
            showFailAmount(amountFailed);

            return null;
        }
    }

}
