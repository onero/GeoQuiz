package dk.adamino.geoquiz;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {

    public static final String EXTRA_ANSWER_IS_TRUE = "dk.adamino.geoquiz.answer_is_true";
    public static final String EXTRA_ANSWER_SHOWN = "dk.adamino.geoquiz.answer_is_shown";
    private boolean mAnswerIsTrue;
    private TextView mAnswerView;
    private Button mShowAnswerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);

        mAnswerView = findViewById(R.id.answer_text);
        mShowAnswerButton = findViewById(R.id.btnShowAnswer);

        mShowAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int resourceId = (mAnswerIsTrue == true) ?
                        R.string.true_button :
                        R.string.false_button;
                mAnswerView.setText(resourceId);
                setAnswerShownResult(true);
            }
        });
    }

    private void setAnswerShownResult(boolean isAnswerShown) {
        Intent data = new Intent();
        data.putExtra(CheatActivity.EXTRA_ANSWER_SHOWN, isAnswerShown);
        setResult(RESULT_OK, data);
    }

    public static Intent newIntent(Context packageContext, boolean answerIsTrue) {
        Intent intent = new Intent(packageContext, CheatActivity.class);
        intent.putExtra(CheatActivity.EXTRA_ANSWER_IS_TRUE, answerIsTrue);
        return intent;
    }

    public static boolean wasAnswerShown(Intent result) {
        return result.getBooleanExtra(CheatActivity.EXTRA_ANSWER_SHOWN, false);
    }
}
