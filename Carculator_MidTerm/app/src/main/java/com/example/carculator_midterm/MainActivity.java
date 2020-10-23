package com.example.carculator_midterm;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Stack;

public class MainActivity extends AppCompatActivity {

    TextView result;
    Stack<Integer> operationStack;
    double previousResult, currentResult;
    Calculation calculation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        result = findViewById(R.id.result);

        operationStack = new Stack<>();
        calculation = new Calculation();
    }

    // 연산자 연산 수행
    public void calculateNum(View v){
        int op = v.getId();

        if(operationStack.isEmpty()){
            if(op != R.id.equalBtn){
                operationStack.add(op);
                previousResult = Double.parseDouble(result.getText().toString());
                result.setText("0");
            }
        } else{
            int willCalculateOperation = operationStack.pop();
            if (op != R.id.equalBtn)
                operationStack.add(op);

            switch (willCalculateOperation){
                case R.id.addBtn:
                    currentResult = calculation.add(previousResult, Double.parseDouble(result.getText().toString()));
                    break;
                case R.id.subBtn:
                    currentResult = calculation.sub(previousResult, Double.parseDouble(result.getText().toString()));
                    break;
                case R.id.mulBtn:
                    currentResult = calculation.mul(previousResult, Double.parseDouble(result.getText().toString()));
                    break;
                case R.id.divBtn:
                    currentResult = calculation.div(previousResult, Double.parseDouble(result.getText().toString()));
                    break;
            }
            result.setText(roundNum(currentResult));
            previousResult = currentResult;
            currentResult = 0;
        }
    }

    // inputNumber
    @SuppressLint("ResourceType")
    public void inputNumber(View v){
        String calculationValue = result.getText().toString();
        if (calculationValue.length() > 10)
            return;

        if(calculationValue.equals("0")) { calculationValue = ""; }

        Button num = findViewById(v.getId());
        calculationValue += num.getText();
        result.setText(calculationValue);
    }

    // C
    public void clearAll(View v){
        previousResult = 0;
        currentResult = 0;
        operationStack.removeAllElements();
        result.setText(String.valueOf(Math.round(0)));
    }
    // CE
    public void clearCurrentValue(View v){

    }

    // ±
    public void convertToNegative(View v){
        double num = Double.parseDouble(result.getText().toString()) * -1;
        result.setText(roundNum(num));
    }

    // .
    @SuppressLint("SetTextI18n")
    public void appendPoint(View v){
        result.setText(result.getText() + ".");
    }

    // DEL
    public void deleteNum(View v){
        String num = result.getText().toString();
        if(num.length() > 1){
            result.setText(num.substring(0,num.length() - 1));
        }else{
            result.setText("0");
        }
    }

    // root
    public void root(View v){
        result.setText(String.valueOf(roundNum(Math.sqrt(Double.parseDouble(result.getText().toString())))));
    }

    // percentage
    public void percentage(View v){
        result.setText(String.valueOf(roundNum(Double.parseDouble(result.getText().toString()) / 100.0)));
    }

    // reciprocal
    public void reciprocal(View v){
        result.setText(String.valueOf(roundNum(1 / Double.parseDouble(result.getText().toString()))));
    }

    // power
    public void power(View v){
        result.setText(String.valueOf(roundNum(Math.pow(Double.parseDouble(result.getText().toString()),2))));
    }

    public String roundNum(double num){
        if(String.valueOf(num - (int)num).equals("0.0")){
            return String.valueOf(Math.round(num));
        }else{
            return String.valueOf(Math.round(num*1000)/1000.0);
        }
    }
}



