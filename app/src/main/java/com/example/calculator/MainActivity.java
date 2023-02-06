package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.*;
import android.view.View;
import android.widget.*;

import java.util.HashMap;


public class MainActivity extends AppCompatActivity {

    private TextView displayExpression, displayResult;
    private Button btnNum1, btnNum2, btnNum3, btnNum4, btnNum5, btnNum6, btnNum7, btnNum8, btnNum9,
            btnNum0, btnOpAdd, btnOpSub, btnOpDiv, btnOpMult, btnOpSign, btnOpDec, btnOpCalc,
            btnOpClr, btnOpDel;
    private final HashMap<Integer, Character> numBtnValMap = new HashMap<>();
    private double curNum, lastNum;
    private int curOpId;
    private String curNumString, lastNumString, curExpressionString;
    private char curOpChar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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


        numBtnValMap.put(R.id.btnNum1, '1');
        numBtnValMap.put(R.id.btnNum2, '2');
        numBtnValMap.put(R.id.btnNum3, '3');
        numBtnValMap.put(R.id.btnNum4, '4');
        numBtnValMap.put(R.id.btnNum5, '5');
        numBtnValMap.put(R.id.btnNum6, '6');
        numBtnValMap.put(R.id.btnNum7, '7');
        numBtnValMap.put(R.id.btnNum8, '8');
        numBtnValMap.put(R.id.btnNum9, '9');
        numBtnValMap.put(R.id.btnNum0, '0');


        // set listeners
        btnNum1.setOnClickListener(numBtnListener);
        btnNum2.setOnClickListener(numBtnListener);
        btnNum3.setOnClickListener(numBtnListener);
        btnNum4.setOnClickListener(numBtnListener);
        btnNum5.setOnClickListener(numBtnListener);
        btnNum6.setOnClickListener(numBtnListener);
        btnNum7.setOnClickListener(numBtnListener);
        btnNum8.setOnClickListener(numBtnListener);
        btnNum9.setOnClickListener(numBtnListener);
        btnNum0.setOnClickListener(numBtnListener);

        btnOpAdd.setOnClickListener(opBtnListener);
        btnOpSub.setOnClickListener(opBtnListener);
        btnOpDiv.setOnClickListener(opBtnListener);
        btnOpMult.setOnClickListener(opBtnListener);
        btnOpSign.setOnClickListener(signBtnListener);
        btnOpDec.setOnClickListener(decBtnListener);
        btnOpCalc.setOnClickListener(calculateBtnListener);
        btnOpClr.setOnClickListener(clearBtnListener);
        btnOpDel.setOnClickListener(deleteBtnListener);

        // init values and displays
        curNum = 0;
        lastNum = 0;
        curOpId = R.id.btnOpClr;
        updateExpressionString();
        updateExpressionDisplay();
        updateCurNumString();
        updateResultDisplay();

    }

    private void updateCurNumString() {
        if (curNum == 0) {
            curNumString = "0";
        }
        curNumString = Double.toString(curNum);
    }

    private void updateLastNumString() {
        lastNumString = Double.toString(lastNum);
    }

    @SuppressLint("NonConstantResourceId")
    private void updateCurOpChar() {
        switch (curOpId) {
            case R.id.btnOpAdd:
                curOpChar = '+';
                break;
            case R.id.btnOpSub:
                curOpChar = '-';
                break;
            case R.id.btnOpMult:
                curOpChar = '*';
                break;
            case R.id.btnOpDiv:
                curOpChar = '/';
                break;
        }
    }

    private void updateCurNum() {
        curNum = Double.parseDouble(curNumString);
    }

    private void updateResultDisplay() {

        // update result text
        displayResult.setText(curNumString);

        //update result text size, if necessary

        if (curNumString.length() >= 10) {
            displayResult.setTextSize(60);
        } else if (curNumString.length() > 6) {
            displayResult.setTextSize(displayResult.getTextSize() - 10);
        }
    }

    private void updateExpressionDisplay() {
        displayExpression.setText(curExpressionString);
    }

    @SuppressLint("NonConstantResourceId")
    private void updateExpressionString() {
        switch(curOpId) {
            case R.id.btnOpCalc:
                curExpressionString = lastNumString + " " + curOpChar + " " + curNumString;
                break;
            case R.id.btnOpClr:
                curExpressionString  = "";
                break;
            default:
                curExpressionString = lastNumString + " " + curOpChar;
                break;
        }

    }

    /* ==================================== EVENT LISTENERS ==================================== */

    // event listener for numeric buttons
    public View.OnClickListener numBtnListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            String temp = Character.toString(numBtnValMap.get(v.getId()));

            if(curNum == 0 || curOpId == R.id.btnOpCalc) {
                curNumString = temp;
            } else {
                curNumString += temp;
            }
            curNum = Double.parseDouble(curNumString);

            System.out.println(curNumString);

            updateResultDisplay();

        }
    };


    // event listener for operator buttons
    public View.OnClickListener opBtnListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            curOpId = v.getId();
            updateCurOpChar();

            if (curNum != 0) {
                lastNum = curNum;
                curNum = 0;
                updateLastNumString();
                updateCurNumString();
            }

            updateExpressionString();
            updateExpressionDisplay();
            updateResultDisplay();
        }
    };

    public View.OnClickListener decBtnListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            curNumString += ".";
            updateResultDisplay();
        }
    };

    // event listener for calculate button todo:
    public View.OnClickListener calculateBtnListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            curOpId = R.id.btnOpCalc;
            updateExpressionString();
            updateExpressionDisplay();
            double result = Calculator.calculate(lastNum, curNum, curOpChar);
            lastNum = curNum;
            curNum = result;
            updateCurNumString();
            updateResultDisplay();
        }
    };

    // event listener for operator buttons
    public View.OnClickListener clearBtnListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            curOpId = R.id.btnOpClr;
            curNum = 0;
            lastNum = 0;

            updateCurNumString();
            updateExpressionString();
            updateLastNumString();
            updateResultDisplay();
            updateExpressionDisplay();
        }
    };

    // event listener for delete button
    public View.OnClickListener deleteBtnListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(curNumString.length() > 1) {
                curNumString = curNumString.substring(0, curNumString.length() - 1);
            } else {
                curNumString = "0";
            }
            updateResultDisplay();
            updateCurNum();
        }
    };

    public View.OnClickListener signBtnListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            curNum = Calculator.sign(curNum);
            updateCurNumString();
            updateResultDisplay();
        }
    };



}