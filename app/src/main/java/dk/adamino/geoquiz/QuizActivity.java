package dk.adamino.geoquiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    public static final String TAG = "Adamino";
    public static final String KEY_INDEX = "index";
    public static final int REQUEST_CODE_CHEAT = 0;
    private Button mTrueButton, mFalseButton ,mNextButton, mPrevButton, mCheatButton;
    private TextView mQuestionTextView;
    private int mCurrentIndex = 0;
    private boolean mIsCheater;

    private Question[] mQuestionBank = new Question[]{
        new Question(R.string.question_australia, true),
        new Question(R.string.question_oceans, true),
        new Question(R.string.question_mideast, false),
        new Question(R.string.question_africa, false),
        new Question(R.string.question_asia, true)
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX);
        }

        setupViews();

        setupListeners();

        updateQuestion();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) return;

        if (requestCode == QuizActivity.REQUEST_CODE_CHEAT) {
            if (data == null) return;
            mIsCheater = CheatActivity.wasAnswerShown(data);
        }
    }

    private void setupListeners() {
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(false);

            }
        });
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(true);
            }
        });
        View.OnClickListener nextQuestionListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                mIsCheater = false;
                updateQuestion();
            }
        };
        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
                Intent iSolemnlySwearImUpToNoGood = CheatActivity.newIntent(QuizActivity.this, answerIsTrue);
                startActivityForResult(iSolemnlySwearImUpToNoGood, QuizActivity.REQUEST_CODE_CHEAT);
            }
        });

        mNextButton.setOnClickListener(nextQuestionListener);
        mQuestionTextView.setOnClickListener(nextQuestionListener);
        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + mQuestionBank.length -1) % mQuestionBank.length;
                updateQuestion();
            }
        });
    }

    private void setupViews() {
        mQuestionTextView = findViewById(R.id.question_text_view);
        mTrueButton = findViewById(R.id.true_button);
        mFalseButton = findViewById(R.id.false_button);
        mNextButton = findViewById(R.id.next_button);
        mPrevButton = findViewById(R.id.previous_button);
        mCheatButton = findViewById(R.id.btnCheat);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_INDEX, mCurrentIndex);
    }

    private void checkAnswer(boolean userPressedTrue) {
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();

        int messageResId = 0;
        messageResId = (userPressedTrue == answerIsTrue) ?
                R.string.correct_toast :
                R.string.incorrect_toast;

        if (mIsCheater) {
            messageResId = R.string.judgement;
        }
        
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
    }

    private void updateQuestion() {
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy called");
    }
}
