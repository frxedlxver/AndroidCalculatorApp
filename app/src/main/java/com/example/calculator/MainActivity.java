package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.*;
import android.view.View;
import android.widget.*;

import java.text.DecimalFormat;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private enum ButtonTypes {
        NUM, OP_BINARY, OP_UNARY, DEL, CLEAR, CALC;
    }

    private TextView displayExpression, displayResult;
    private Button btnNum1, btnNum2, btnNum3, btnNum4, btnNum5, btnNum6, btnNum7, btnNum8, btnNum9,
            btnNum0, btnOpAdd, btnOpSub, btnOpDiv, btnOpMult, btnOpSign, btnOpDec, btnOpCalc,
            btnOpClr, btnOpDel;
    private double rightNum, leftNum, resultNum;
    private int curBinOpId;
    private ArrayList<Integer> btnHistory;
    private String rightNumString, leftNumString, resultString, curExpressionString;
    private char curBinOpChar;

    private final DecimalFormat displayDecimalFormat = new DecimalFormat("0.####");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // init history
        btnHistory = new ArrayList<>();

        // init views
        displayExpression = findViewById(R.id.displayExpression);
        displayResult = findViewById(R.id.displayResult);
        btnNum1 = findViewById(R.id.btnNum1);
        btnNum2 = findViewById(R.id.btnNum2);
        btnNum3 = findViewById(R.id.btnNum3);
        btnNum4 = findViewById(R.id.btnNum4);
        btnNum5 = findViewById(R.id.btnNum5);
        btnNum6 = findViewById(R.id.btnNum6);
        btnNum7 = findViewById(R.id.btnNum7);
        btnNum8 = findViewById(R.id.btnNum8);
        btnNum9 = findViewById(R.id.btnNum9);
        btnNum0 = findViewById(R.id.btnNum0);
        btnOpAdd = findViewById(R.id.btnOpAdd);
        btnOpSub = findViewById(R.id.btnOpSub);
        btnOpDiv = findViewById(R.id.btnOpDiv);
        btnOpMult = findViewById(R.id.btnOpMult);
        btnOpSign = findViewById(R.id.btnOpSign);
        btnOpDec = findViewById(R.id.btnOpDec);
        btnOpCalc = findViewById(R.id.btnOpCalc);
        btnOpClr = findViewById(R.id.btnOpClr);
        btnOpDel = findViewById(R.id.btnOpDel);


        // set listeners
        btnNum1.setOnClickListener(btnListener);
        btnNum2.setOnClickListener(btnListener);
        btnNum3.setOnClickListener(btnListener);
        btnNum4.setOnClickListener(btnListener);
        btnNum5.setOnClickListener(btnListener);
        btnNum6.setOnClickListener(btnListener);
        btnNum7.setOnClickListener(btnListener);
        btnNum8.setOnClickListener(btnListener);
        btnNum9.setOnClickListener(btnListener);
        btnNum0.setOnClickListener(btnListener);
        btnOpAdd.setOnClickListener(btnListener);
        btnOpSub.setOnClickListener(btnListener);
        btnOpDiv.setOnClickListener(btnListener);
        btnOpMult.setOnClickListener(btnListener);
        btnOpSign.setOnClickListener(btnListener);
        btnOpDec.setOnClickListener(btnListener);
        btnOpCalc.setOnClickListener(btnListener);
        btnOpClr.setOnClickListener(btnListener);
        btnOpDel.setOnClickListener(btnListener);

        // init values and displays
        btnOpClr.performClick();

    }

    /* ==================================== SPEC. METHODS ==================================== */

    private void setDefaultAndInitUI() {
        // set all values to default
        rightNum = 0;
        leftNum = 0;
        resultNum = 0;
        resultString = "0";
        rightNumString = "0";
        curExpressionString = "";
        curBinOpId = R.id.btnOpClr;

        //initialize UI elements
        updateExpressionDisplay();
        updateResultDisplay();
    }

    /* ==================================== SETTERS ==================================== */

    private void updateResultNumString() {

        resultString = displayDecimalFormat.format(Double.valueOf(resultNum));
    }

    private void updateLeftNumString() {
        leftNumString = Double.toString(leftNum);
    }

    private void updateRightNumString() {
        rightNumString = Double.toString(rightNum);
    }

    @SuppressLint("NonConstantResourceId")
    private void updateCurBinOpChar() {
        switch (curBinOpId) {
            case R.id.btnOpAdd:
                curBinOpChar = '+';
                break;
            case R.id.btnOpSub:
                curBinOpChar = '-';
                break;
            case R.id.btnOpMult:
                curBinOpChar = '*';
                break;
            case R.id.btnOpDiv:
                curBinOpChar = '/';
                break;
        }
    }
    @SuppressLint("NonConstantResourceId")
    private void updateExpressionString() {
        if (curBinOpId == R.id.btnOpCalc) {
            curExpressionString = leftNumString + " " + curBinOpChar + " " + rightNumString;
        } else {
            curExpressionString = leftNumString + " " + curBinOpChar;
        }

    }

    /* ==================================== DISPLAY CONTROL ==================================== */

    private void updateResultDisplay() {

        // update result text
        displayResult.setText(resultString);

        //update result text size, if necessary

        if (resultString.length() >= 10) {
            displayResult.setTextSize(60);
        } else if (resultString.length() > 6) {
            displayResult.setTextSize(displayResult.getTextSize() - 10);
        }
    }

    private void updateExpressionDisplay() {
        displayExpression.setText(curExpressionString);
    }

    private void updateDisplays() {
        updateExpressionDisplay();
        updateResultDisplay();
    }



    /* ==================================== EVENT LISTENERS ==================================== */

    public View.OnClickListener btnListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            int id = v.getId();
            btnHistory.add(id);

            ButtonTypes bType = getButtonType(id);

            switch (bType) {

                case NUM:
                    numButtonPressed(id);
                    break;
                case OP_BINARY:
                    binOpButtonPressed(id);
                    break;
                case OP_UNARY:
                    unOpButtonPressed(id);
                    break;
                case DEL:
                    delButtonPressed();
                    break;
                case CLEAR:
                    setDefaultAndInitUI();
                    break;
                case CALC:
                    calcBtnPressed();
                    break;
            }

        }

        private int getLastButtonPressed() {
            int btnHistoryLength = btnHistory.size();
            if (btnHistoryLength > 0) {
                return btnHistory.get(btnHistory.size() - 1);
            } else {
                return R.id.btnOpClr;
            }

        }






        private void numButtonPressed(int id) { // TESTED

            int num;
            if (id == R.id.btnNum1) {
                num = 1;
            } else if (id == R.id.btnNum2) {
                num = 2;
            } else if (id == R.id.btnNum3) {
                num = 3;
            } else if (id == R.id.btnNum4) {
                num = 4;
            } else if (id == R.id.btnNum5) {
                num = 5;
            } else if (id == R.id.btnNum6) {
                num = 6;
            } else if (id == R.id.btnNum7) {
                num = 7;
            } else if (id == R.id.btnNum8) {
                num = 8;
            } else if (id == R.id.btnNum9) {
                num = 9;
            } else /* if (id == R.id.btnNum0) */ {
                num = 0;
            }

            int lastButtonPressed = getLastButtonPressed();

            if (resultNum == 0) {
                resultNum = num;
                updateResultNumString();
            } else {
                resultString += Integer.toString(num);
                resultNum = Double.parseDouble(resultString);
            }

            updateResultDisplay();
        }

        private void binOpButtonPressed(int id) { //todo: test
            curBinOpId = id;
            updateCurBinOpChar();

            int lastBtnPressed = getLastButtonPressed();
            ButtonTypes lastBtnPressedType = getButtonType(lastBtnPressed);

            if (lastBtnPressedType == ButtonTypes.NUM || lastBtnPressedType == ButtonTypes.CALC
                || lastBtnPressedType == ButtonTypes.OP_UNARY || lastBtnPressedType == ButtonTypes.DEL) {
                leftNum = resultNum;
                resultNum = 0;
                rightNum = 0;
            }

            updateExpressionString();
            updateExpressionDisplay();
            updateResultNumString();
            updateResultDisplay();

            
        }


        private void unOpButtonPressed(int id) { // TESTED
            if (id == R.id.btnOpSign) {
                resultNum = Calculator.sign(resultNum);
                updateResultNumString();
            } else /* if (id == R.id.btnOpDec) */ {
                resultString += ".";
            }
            updateResultDisplay();
            
        }

        private void delButtonPressed() { // TESTED
            // remove last character
            int resultLength = resultString.length();
            
            if (resultLength > 1) {
                resultString = resultString.substring(0, (resultString.length() - 1));
                resultNum = Double.parseDouble(resultString);
            } else {
                resultNum = 0;
                updateResultNumString();
            }

            updateResultDisplay();
            
        }

        private void calcBtnPressed() {

        }

        public ButtonTypes getButtonType(int id) {
            if (id == R.id.btnNum1 || id == R.id.btnNum5 || id == R.id.btnNum2
                || id == R.id.btnNum3 || id == R.id.btnNum4 || id == R.id.btnNum6
                || id == R.id.btnNum7 || id == R.id.btnNum8 || id == R.id.btnNum9
                || id == R.id.btnNum0) {
                return ButtonTypes.NUM;
            } else if (id == R.id.btnOpAdd || id == R.id.btnOpSub || id == R.id.btnOpMult || id == R.id.btnOpDiv || id == R.id.btnOpCalc) {
                return ButtonTypes.OP_BINARY;
            } else if (id == R.id.btnOpSign || id == R.id.btnOpDec) {
                return ButtonTypes.OP_UNARY;
            } else if (id == R.id.btnOpClr)  {
                return ButtonTypes.CLEAR;
            } else if (id == R.id.btnOpDel) {
                return ButtonTypes.DEL;
            } else /* if (id == R.id.btnOpCalc) */ {
                return ButtonTypes.CALC;
            }
        }
    };


}