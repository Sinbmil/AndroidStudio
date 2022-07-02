package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView text;
    CheckBox checkbox;
    Button BtnNum0,BtnNum1,BtnNum2,BtnNum3,BtnNum4,BtnNum5,BtnNum6,BtnNum7,BtnNum8,BtnNum9;
    Button BtnAdd,BtnSub,BtnMul,BtnDiv,BtnMod,BtnSquare,BtnDelete,BtnDot,BtnResult;
    double left,right,result;
    char lastChar;
    ArrayList<String> doubleList;
    ArrayList<String> operationList;
    String check;
    int cnt = 0; // 연산자의 개수를 알려주는 변수
    int b_cnt = 0; // 버튼 누른 횟수를 알려주는 변수
    int fin = 0; // =을 눌렀는지 안눌렀는지 판단하는 변수

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("계산기");

        text = (TextView) findViewById(R.id.text);
        checkbox = (CheckBox) findViewById(R.id.checkbox);

        // 숫자 버튼
        BtnNum0 = (Button) findViewById(R.id.BtnNum0);
        BtnNum1 = (Button) findViewById(R.id.BtnNum1);
        BtnNum2 = (Button) findViewById(R.id.BtnNum2);
        BtnNum3 = (Button) findViewById(R.id.BtnNum3);
        BtnNum4 = (Button) findViewById(R.id.BtnNum4);
        BtnNum5 = (Button) findViewById(R.id.BtnNum5);
        BtnNum6 = (Button) findViewById(R.id.BtnNum6);
        BtnNum7 = (Button) findViewById(R.id.BtnNum7);
        BtnNum8 = (Button) findViewById(R.id.BtnNum8);
        BtnNum9 = (Button) findViewById(R.id.BtnNum9);

        // 기호 버튼
        BtnAdd = (Button) findViewById(R.id.BtnAdd);
        BtnSub = (Button) findViewById(R.id.BtnSub);
        BtnMul = (Button) findViewById(R.id.BtnMul);
        BtnDiv = (Button) findViewById(R.id.BtnDiv);
        BtnMod = (Button) findViewById(R.id.BtnMod);
        BtnSquare = (Button) findViewById(R.id.BtnSquare);
        BtnDelete = (Button) findViewById(R.id.BtnDelete);
        BtnDot = (Button) findViewById(R.id.BtnDot);
        BtnResult = (Button) findViewById(R.id.BtnResult);

        left = 0;
        right = 0;
        result = 0;

        doubleList = new ArrayList<String>();
        operationList = new ArrayList<String>();
    }

    public boolean isNumber(String string) {  // 문자열을 소수점으로 변경할 수 있는지 확인하는 함수
        try {
            Double.valueOf(string);
            return true;
        } catch (NumberFormatException ne) {
            return false;
        }
    }

    public boolean signCheck(String text) { // 기호가 맞을시 문자열을 잘라주는 함수
        check = text.substring(text.length() - 1, text.length());
        if (check.equals("+") || check.equals("-") || check.equals("*") ||
                check.equals("/") || check.equals("%") || check.equals("^")) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onClick(View view) {
        String temp;
        if(checkbox.isChecked() == true){ // 체크박스 체크시 나머지, 제곱 버튼 보이기
            BtnMod.setVisibility(View.VISIBLE);
            BtnSquare.setVisibility(View.VISIBLE);
        }
        else{ // 체크를 안하면 나머지, 제곱 버튼 감추기
            BtnMod.setVisibility(View.INVISIBLE);
            BtnSquare.setVisibility(View.INVISIBLE);
        }

        switch (view.getId()) { // switch 문으로 getId를 통해 각 버튼 구분하기
            case R.id.BtnDelete: // DELETE
                text.setText(Command.Number0);
                left = right = result = 0;
                cnt = 0; // Delete 버튼을 누르므로 연산자 개수 초기화
                b_cnt = 0; // Delete 버튼을 누르므로 연산자 개수 초기화
                fin = 0; // Delete 버튼을 누르므로 연산자 개수 초기화
                break;

            case R.id.BtnNum1: // 숫자1
                b_cnt += 1;
                temp = text.getText().toString();
                // 현재 temp가 0이면 해당 숫자를 대입 -> 다른 숫자들도 똑같이 적용
                if (temp.equals(Command.Number0) || temp.equals(Command.Double0)) {
                    text.setText(Command.Number1);
                }else if(b_cnt == 1 && fin == 1){  // = 버튼을 누르고 새로운 숫자를 입력하면 기존에 있던 숫자는 지워짐 -> 다른 숫자들도 똑같이 적용
                    text.setText(Command.Number1);
                } else {
                    temp += Command.Number1; // 숫자 연속으로 더해주기 -> 다른 숫자들도 똑같이 적용
                    text.setText(temp);
                }
                break;

            case R.id.BtnNum2: // 숫자2
                b_cnt += 1;
                temp = text.getText().toString();
                if (temp.equals(Command.Number0) || temp.equals(Command.Double0)) {
                    text.setText(Command.Number2);
                } else if(b_cnt == 1 && fin == 1){
                    text.setText(Command.Number2);
                } else {
                    temp += Command.Number2;
                    text.setText(temp);
                }
                break;

            case R.id.BtnNum3: // 숫자3
                b_cnt += 1;
                temp = text.getText().toString();
                if (temp.equals(Command.Number0) || temp.equals(Command.Double0)) {
                    text.setText(Command.Number3);
                } else if(b_cnt == 1 && fin == 1){
                    text.setText(Command.Number3);
                } else {
                    temp += Command.Number3;
                    text.setText(temp);
                }
                break;

            case R.id.BtnNum4: // 숫자4
                b_cnt += 1;
                temp = text.getText().toString();
                if (temp.equals(Command.Number0) || temp.equals(Command.Double0)) {
                    text.setText(Command.Number4);
                } else if(b_cnt == 1 && fin == 1){
                    text.setText(Command.Number4);
                } else {
                    temp += Command.Number4;
                    text.setText(temp);
                }
                break;

            case R.id.BtnNum5: // 숫자5
                b_cnt += 1;
                temp = text.getText().toString();
                if (temp.equals(Command.Number0) || temp.equals(Command.Double0)) {
                    text.setText(Command.Number5);
                } else if(b_cnt == 1 && fin == 1){
                    text.setText(Command.Number5);
                } else {
                    temp += Command.Number5;
                    text.setText(temp);
                }
                break;

            case R.id.BtnNum6: // 숫자6
                b_cnt += 1;
                temp = text.getText().toString();
                if (temp.equals(Command.Number0) || temp.equals(Command.Double0)) {
                    text.setText(Command.Number6);
                }else if(b_cnt == 1 && fin == 1){
                    text.setText(Command.Number6);
                }  else {
                    temp += Command.Number6;
                    text.setText(temp);
                }
                break;

            case R.id.BtnNum7: // 숫자7
                b_cnt += 1;
                temp = text.getText().toString();
                if (temp.equals(Command.Number0) || temp.equals(Command.Double0)) {
                    text.setText(Command.Number7);
                } else if(b_cnt == 1 && fin == 1){
                    text.setText(Command.Number7);
                } else {
                    temp += Command.Number7;
                    text.setText(temp);
                }
                break;

            case R.id.BtnNum8: // 숫자8
                b_cnt += 1;
                temp = text.getText().toString();
                if (temp.equals(Command.Number0) || temp.equals(Command.Double0)) {
                    text.setText(Command.Number8);
                }else if(b_cnt == 1 && fin == 1){
                    text.setText(Command.Number8);
                }  else {
                    temp += Command.Number8;
                    text.setText(temp);
                }
                break;

            case R.id.BtnNum9: // 숫자9
                b_cnt += 1;
                temp = text.getText().toString();
                if (temp.equals(Command.Number0) || temp.equals(Command.Double0)) {
                    text.setText(Command.Number9);
                } else if(b_cnt == 1 && fin == 1){
                    text.setText(Command.Number9);
                } else {
                    temp += Command.Number9;
                    text.setText(temp);
                }
                break;

            case R.id.BtnNum0: // 숫자0
                b_cnt += 1;
                temp = text.getText().toString();
                if (temp.equals(Command.Number0) || temp.equals(Command.Double0)) {
                    text.setText(Command.Number0);
                } else if(b_cnt == 1 && fin == 1){
                    text.setText(Command.Number0);
                } else {
                    temp += Command.Number0;
                    text.setText(temp);
                }
                break;

            case R.id.BtnAdd: // 더하기 버튼
                temp = text.getText().toString();
                // 기호가 연속으로 올시 토스트로 에러 문자 출력
                lastChar = temp.charAt(temp.length() - 1);
                if (lastChar == '+' || lastChar == '-' || lastChar == '*' || lastChar == '/' ||
                        lastChar == '.' ||lastChar == '%' || lastChar == '^') {
                    Toast.makeText(MainActivity.this, Command.NO_ADD_SIGN, Toast.LENGTH_SHORT).show();
                    return;
                }

                if (signCheck(temp)) {
                    temp += Command.ADD;
                    text.setText(temp);
                    cnt += 1;
                    b_cnt += 1;
                } else {
                    Toast.makeText(MainActivity.this, Command.NO_ADD_SIGN, Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.BtnSub: // 빼기 버튼
                temp = text.getText().toString();
                // 기호가 연속으로 올시 토스트로 에러 문자 출력
                lastChar = temp.charAt(temp.length() - 1);
                if (lastChar == '+' || lastChar == '-' || lastChar == '*' || lastChar == '/' ||
                        lastChar == '.' ||lastChar == '%' || lastChar == '^') {
                    Toast.makeText(MainActivity.this, Command.NO_ADD_SIGN, Toast.LENGTH_SHORT).show();
                    return;
                }

                if (signCheck(temp)) {
                    temp += Command.SUB;
                    text.setText(temp);
                    cnt += 1;
                    b_cnt += 1;
                } else {
                    Toast.makeText(MainActivity.this, Command.NO_ADD_SIGN, Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.BtnMul: // 곱하기 버튼
                temp = text.getText().toString();
                // 기호가 연속으로 올시 토스트로 에러 문자 출력
                lastChar = temp.charAt(temp.length() - 1);
                if (lastChar == '+' || lastChar == '-' || lastChar == '*' || lastChar == '/' ||
                        lastChar == '.' ||lastChar == '%' || lastChar == '^') {
                    Toast.makeText(MainActivity.this, Command.NO_ADD_SIGN, Toast.LENGTH_SHORT).show();
                    return;
                }

                if (signCheck(temp)) {
                    temp += Command.MULTI;
                    text.setText(temp);
                    cnt += 1;
                    b_cnt += 1;
                } else {
                    Toast.makeText(MainActivity.this, Command.NO_ADD_SIGN, Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.BtnDiv: // 나누기 버튼
                temp = text.getText().toString();
                // 기호가 연속으로 올시 토스트로 에러 문자 출력
                lastChar = temp.charAt(temp.length() - 1);
                if (lastChar == '+' || lastChar == '-' || lastChar == '*' || lastChar == '/' ||
                        lastChar == '.' ||lastChar == '%' || lastChar == '^') {
                    Toast.makeText(MainActivity.this, Command.NO_ADD_SIGN, Toast.LENGTH_SHORT).show();
                    return;
                }

                if (signCheck(temp)) {
                    temp += Command.DIV;
                    text.setText(temp);
                    cnt += 1;
                    b_cnt += 1;
                } else {
                    Toast.makeText(MainActivity.this, Command.NO_ADD_SIGN, Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.BtnMod: // 나머지 버튼
                temp = text.getText().toString();
                // 기호가 연속으로 올시 토스트로 에러 문자 출력
                lastChar = temp.charAt(temp.length() - 1);
                if (lastChar == '+' || lastChar == '-' || lastChar == '*' || lastChar == '/' ||
                        lastChar == '.' ||lastChar == '%' || lastChar == '^') {
                    Toast.makeText(MainActivity.this, Command.NO_ADD_SIGN, Toast.LENGTH_SHORT).show();
                    return;
                }

                if (signCheck(temp)) {
                    temp += Command.MOD;
                    text.setText(temp);
                    cnt += 1;
                    b_cnt += 1;
                } else {
                    Toast.makeText(MainActivity.this, Command.NO_ADD_SIGN, Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.BtnSquare: // 제곱 버튼
                temp = text.getText().toString();
                // 기호가 연속으로 올시 토스트로 에러 문자 출력
                lastChar = temp.charAt(temp.length() - 1);
                if (lastChar == '+' || lastChar == '-' || lastChar == '*' || lastChar == '/' ||
                        lastChar == '.' ||lastChar == '%' || lastChar == '^') {
                    Toast.makeText(MainActivity.this, Command.NO_ADD_SIGN, Toast.LENGTH_SHORT).show();
                    return;
                }

                if (signCheck(temp)) {
                    temp += Command.SQUARE;
                    text.setText(temp);
                    cnt += 1;
                    b_cnt += 1;
                } else {
                    Toast.makeText(MainActivity.this, Command.NO_ADD_SIGN, Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.BtnDot: // 도트 버튼
                temp = text.getText().toString();
                // 기호가 연속으로 올시 토스트로 에러 문자 출력
                lastChar = temp.charAt(temp.length() - 1);
                if (lastChar == '+' || lastChar == '-' || lastChar == '*' || lastChar == '/' ||
                        lastChar == '.' ||lastChar == '%' || lastChar == '^') {
                    Toast.makeText(MainActivity.this, Command.NO_ADD_SIGN, Toast.LENGTH_SHORT).show();
                    return;
                }
                temp += Command.DOT;
                text.setText(temp);
                break;

            case R.id.BtnResult: // 결과 버튼
                if (cnt >= 3) { // 연산자 개수가 3이상이면 토스트로 오류 문자 알림
                    Toast.makeText(MainActivity.this, "연산자는 2개가 최대입니다.", Toast.LENGTH_SHORT).show();
                    return;
                }
                cnt = 0; // 연산이 끝났으므로 0으로 초기화
                b_cnt = 0; // 연산이 끝났으므로 0으로 초기화
                fin = 1; // =을 누르면 1로 초기화

                double tempLeft = 0;
                double tempRight = 0;
                double res = 0;
                temp = text.getText().toString();
                // 마지막에 숫자가 안오고 기호가 있을시 토스트로 '숫자 입력후 =을 누르세요' 토스트 발생
                lastChar = temp.charAt(temp.length() - 1);
                if (lastChar == '+' || lastChar == '-' || lastChar == '*' || lastChar == '/' ||
                        lastChar == '.' || lastChar == '%' || lastChar == '^') {
                    Toast.makeText(MainActivity.this, "숫자 입력 후 = 을 누르세요", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    char[] chr = temp.toCharArray();
                    int j = 0;
                    String tempDouble;
                    String tempString;
                    for (int i = 0; i < chr.length; i++) {
                        if (chr[i] == '+' || chr[i] == '-' || chr[i] == '*' || chr[i] == '/' || chr[i] == '%' || chr[i] == '^') {
                            operationList.add(Character.toString(chr[i]));
                            doubleList.add("0");
                            j++;
                        } else {
                            if (doubleList.size() == 0) {
                                doubleList.add(Character.toString(chr[i]));
                            } else {
                                if (doubleList.get(doubleList.size() - 1).equals("0")) {
                                    doubleList.remove(doubleList.size() - 1);
                                    doubleList.add(Character.toString(chr[i]));
                                } else {
                                    tempDouble = doubleList.get(doubleList.size() - 1);
                                    tempDouble += chr[i];
                                    doubleList.remove(doubleList.size() - 1);
                                    doubleList.add(tempDouble);
                                }
                            }
                        }
                    }

                    // 우선순위에 따라 곱셈,나눗셈,나머지,제곱 먼저 처리
                    // *, /, %, ^ 처리 for문
                    for (int i = 0; i < operationList.size(); i++) {
                        if (operationList.get(i).equals("*")) {
                            tempLeft = Double.valueOf(doubleList.get(i));
                            tempRight = Double.valueOf(doubleList.get(i + 1));
                            res = tempLeft * tempRight;
                            doubleList.remove(i + 1);
                            doubleList.set(i, String.valueOf(res));
                            operationList.remove(i);
                            i--;
                        } else if (operationList.get(i).equals("/")) {
                            tempLeft = Double.valueOf(doubleList.get(i));
                            tempRight = Double.valueOf(doubleList.get(i + 1));
                            res = tempLeft / tempRight;
                            doubleList.remove(i + 1);
                            doubleList.set(i, String.valueOf(res));
                            operationList.remove(i);
                            i--;
                        } else if (operationList.get(i).equals("%")) {
                            tempLeft = Double.valueOf(doubleList.get(i));
                            tempRight = Double.valueOf(doubleList.get(i + 1));
                            res = tempLeft % tempRight;
                            doubleList.remove(i + 1);
                            doubleList.set(i, String.valueOf(res));
                            operationList.remove(i);
                            i--;
                        } else if (operationList.get(i).equals("^")) {
                            tempLeft = Double.valueOf(doubleList.get(i));
                            tempRight = Double.valueOf(doubleList.get(i + 1));
                            res = Math.pow(tempLeft,tempRight);
                            doubleList.remove(i + 1);
                            doubleList.set(i, String.valueOf(res));
                            operationList.remove(i);
                            i--;
                        }
                    }

                    // +, - 처리 for문
                    for (int i = 0; i < operationList.size(); i++) {
                        if (operationList.get(i).equals("+")) {
                            tempLeft = Double.valueOf(doubleList.get(i));
                            tempRight = Double.valueOf(doubleList.get(i + 1));
                            res = tempLeft + tempRight;
                            doubleList.remove(i + 1);
                            doubleList.set(i, String.valueOf(res));
                            operationList.remove(i);
                            i--;
                        } else if (operationList.get(i).equals("-")) {

                            tempLeft = Double.valueOf(doubleList.get(i));
                            tempRight = Double.valueOf(doubleList.get(i + 1));
                            res = tempLeft - tempRight;
                            doubleList.remove(i + 1);
                            doubleList.set(i, String.valueOf(res));
                            operationList.remove(i);
                            i--;
                        }
                    }
                }

                // 최종 결과 출력 부분
                if ((String.valueOf(res).substring(String.valueOf(res).length() - 2, String.valueOf(res).length())).equals(".0")) {
                    temp = String.valueOf(res).substring(0, String.valueOf(res).length() - 2);
                } else {
                    temp = String.valueOf(res);
                }
                text.setText(temp);
                doubleList = new ArrayList<String>();
                operationList = new ArrayList<String>();
                break;
        }
    }
}