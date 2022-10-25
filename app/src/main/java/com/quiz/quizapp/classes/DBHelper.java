package com.quiz.quizapp.classes;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.quiz.quizapp.classes.QuizContract.QuestionTable;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "quiz.db";
    private static final int VERSION = 1;
    private SQLiteDatabase db;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;
        String sql = createQuestionTable();
        db.execSQL(sql);
        populateQuestionsTable();

    }


    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int i1) {
        String sql = deleteTables();
        db.execSQL(sql);
        onCreate(db);


    }

    private void populateQuestionsTable() {
        Question q1 = new Question("A is correct", "A", "B", "C", 1);
        Question q2 = new Question("B is correct", "A", "B", "C", 2);
        Question q3 = new Question("C is correct", "A", "B", "C", 3);
        Question q4 = new Question("B is correct", "A", "B", "C", 2);
        addQuestions(q1, q2, q3, q4);

    }

    private void addQuestions(Question... questions) {
        for (Question question : questions) {
            ContentValues cv = new ContentValues();
            cv.put(QuestionTable.COLUMN_QUESTION, question.getQuestion());
            cv.put(QuestionTable.COLUMN_OPTION1, question.getOption1());
            cv.put(QuestionTable.COLUMN_OPTION2, question.getOption2());
            cv.put(QuestionTable.COLUMN_OPTION3, question.getOption3());
            cv.put(QuestionTable.COLUMN_ANSWER_NUMBER, question.getAnswerNumber());
            db.insert(QuestionTable.TABLE_NAME, null, cv);

        }

    }

    private String deleteTables() {
        return "DROP TABLE IF EXISTS " + QuestionTable.TABLE_NAME;

    }

    private String createQuestionTable() {
        return MessageFormat.format("CREATE TABLE IF NOT EXISTS {0} ({1} INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "{2} TEXT, " +
                        "{3} TEXT, " +
                        "{4} TEXT, " +
                        "{5} TEXT, " +
                        "{6} INTEGER" +
                        ");",
                QuestionTable.TABLE_NAME,
                QuestionTable._ID,
                QuestionTable.COLUMN_QUESTION,
                QuestionTable.COLUMN_OPTION1,
                QuestionTable.COLUMN_OPTION2,
                QuestionTable.COLUMN_OPTION3,
                QuestionTable.COLUMN_ANSWER_NUMBER

        );
    }

    @SuppressLint("Range")
    public List<Question> getAllQuestions() {
        List<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();

        try (Cursor c = db.rawQuery(SelectQuestionsSQL(), null)) {
            if (c.moveToFirst()) {
                while (c.moveToNext()) {
                    Question question = new Question();
                    question.setQuestion(c.getString(c.getColumnIndex(QuestionTable.COLUMN_QUESTION)));
                    question.setOption1(c.getString(c.getColumnIndex(QuestionTable.COLUMN_OPTION1)));
                    question.setOption2(c.getString(c.getColumnIndex(QuestionTable.COLUMN_OPTION2)));
                    question.setOption3(c.getString(c.getColumnIndex(QuestionTable.COLUMN_OPTION3)));
                    question.setAnswerNumber(c.getInt(c.getColumnIndex(QuestionTable.COLUMN_ANSWER_NUMBER)));
                    questionList.add(question);


                }
            }
            return questionList;


        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

    }

    private String SelectQuestionsSQL() {
        return "SELECT * FROM " + QuestionTable.TABLE_NAME;
    }
}
