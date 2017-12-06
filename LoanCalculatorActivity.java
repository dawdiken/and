package com.example.david.final_exam_rev1;

import android.app.Activity;
import android.content.Intent;
import android.os.*;
import android.view.View;
import android.widget.*;
import org.json.JSONObject;

import java.text.DecimalFormat;
//
//
import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import org.json.*;
//
//public class LoanCalculatorActivity extends Activity {
//    private EditText mLoanAmount, mInterestRate, mLoanPeriod;
//    private TextView mMontlyPaymentResult, mTotalPaymentsResult;
//
//    /** Initializes the app when it is first created. */
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
////try{
////        String baseUrl = "http://10.12.17.61:8080/loan-calculator";
////        new LoanCalculatorActivity.HttpAsyncTask().execute(baseUrl, jsonString.toString());
////
////
////    } catch(Exception jse) {
////
////        mMontlyPaymentResult.setText("JSON exception: " +jse.getLocalizedMessage());
////        mTotalPaymentsResult.setText("");
////    }
//        setContentView(R.layout.activity_main);
//        mLoanAmount = (EditText)findViewById(R.id.loan_amount);
//        mInterestRate = (EditText)findViewById(R.id.interest_rate);
//        mLoanPeriod = (EditText)findViewById(R.id.loan_period);
//        mMontlyPaymentResult = (TextView)findViewById(R.id.monthly_payment_result);
//        mTotalPaymentsResult = (TextView)findViewById(R.id.total_payments_result);
//    }
//    private class HttpAsyncTask extends AsyncTask<String, String, String>{
//
//        @Override
//        protected String doInBackground(String... params) {
//            // Getting username and password from user input
//            String jsonString = "";
//
//
//            try {
//                jsonString = HttpUtils.urlContentPost(params[0],"loanInputs", params[1]);
//            } catch (Exception e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//            System.out.println(jsonString);
//            return jsonString;
//        }
//
//        @Override
//        protected void onPostExecute(String result){
//            try{
//
//                JSONObject jsonResult = new JSONObject(result);
//                mMontlyPaymentResult.setText(jsonResult.getString("formattedMonthlyPayment"));
//                mTotalPaymentsResult.setText(jsonResult.getString("formattedTotalPayments"));
//                mLoanAmount.setText(jsonResult.getString("loanAmount"));
//                mInterestRate.setText(jsonResult.getString("annualInterestRateInPercent"));
//                mLoanPeriod.setText(jsonResult.getString("loanPeriodInMonths"));
//            } catch (Exception e) {
//                mMontlyPaymentResult.setText("Illegal base URL");
//                mTotalPaymentsResult.setText("");
//
//                mMontlyPaymentResult.setText("Server error: " + e);
//                mTotalPaymentsResult.setText("");
//
//                mMontlyPaymentResult.setText("JSON exception: " +e.getLocalizedMessage());
//                mTotalPaymentsResult.setText("");
//            }
//        }
//    }
//
//    public void showLoanPayments(View clickedButton) {
//
//        double loanAmount = Integer.parseInt(mLoanAmount.getText().toString());
//        double interestRate = (Integer.parseInt(mInterestRate.getText().toString()));
//        double loanPeriod = Integer.parseInt(mLoanPeriod.getText().toString());
//        double r = interestRate/1200;
//        double r1 = Math.pow(r+1,loanPeriod);
//
//        double monthlyPayment = (double) ((r+(r/(r1-1))) * loanAmount);
//        double totalPayment = monthlyPayment * loanPeriod;
//
//        mMontlyPaymentResult.setText(new DecimalFormat("##.##").format(monthlyPayment));
//        mTotalPaymentsResult.setText(new DecimalFormat("##.##").format(totalPayment));
//    }
//}

//package com.example.david.myapplication;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

//import org.apache.http.client.ClientProtocolException;


import java.io.IOException;

/** Demonstrates the use of JSON for communicating with a remote HTTP server.
 *  Note that the JSON version that is built into Android is a bit obsolete.
 *  In particular, it lacks a JSONObject constructor that lets you pass a bean.
 *  So, on the Android side we use a Map instead, but the server-side code uses the simpler
 *  and more modern constructor.
 *  <p>
 *  From <a href="http://www.coreservlets.com/android-tutorial/">
 *  the coreservlets.com Android programming tutorial series</a>.
 */
public class LoanCalculatorActivity extends Activity {
    private EditText mBaseUrl, mLoanAmount, mInterestRate, mLoanPeriod;
    private TextView mMontlyPaymentResult, mTotalPaymentsResult;
//    SQLDemo2 inputs = new SQLDemo2();
//    DatabaseHandler db = new DatabaseHandler(this);
    DatabaseHandler db = new DatabaseHandler(this);



    /** Initializes the app when it is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//       new SQLDemo2();
//        SQLDemo2 inputs = new SQLDemo2();
        String help = "";
//        new DataBaseAsyncTask().execute();

//        inputs.insertSomeDbData();
        mBaseUrl = (EditText)findViewById(R.id.base_url);
        mLoanAmount = (EditText)findViewById(R.id.loan_amount);
        mInterestRate = (EditText)findViewById(R.id.interest_rate);
        mLoanPeriod = (EditText)findViewById(R.id.loan_period);
        mMontlyPaymentResult = (TextView)findViewById(R.id.monthly_payment_result);
        mTotalPaymentsResult = (TextView)findViewById(R.id.total_payments_result);





//        db.openDatabase();

        // Inserting Contacts
//        Log.d("Insert: ", "Inserting ..");
//        db.addContact("9100000000");
//        db.addContact(new Contact("Srinivas", "9199999999"));
//        db.addContact(new Contact("Tommy", "9522222222"));
//        db.addContact(new Contact("Karthik", "9533333333"));

        // Reading all contacts
//        Log.d("Reading: ", "Reading all contacts..");
//        List<Contact> contacts = db.getAllContacts();
//
//        for (Contact cn : contacts) {
//            String log = "Id: "+cn.getID()+" ,Name: " + cn.getName() + " ,Phone: " +
//                    cn.getPhoneNumber();
//            // Writing Contacts to log
//            Log.d("Name: ", log);


        }



    public void showLoanPayments(View clickedButton) {
        String baseUrl = mBaseUrl.getText().toString();
        String amount = mLoanAmount.getText().toString();
        String rate = mInterestRate.getText().toString();
        String months = mLoanPeriod.getText().toString();
        LoanInputs inputs = new LoanInputs(amount, rate, months);
        JSONObject inputsJson = new JSONObject(inputs.getInputMap());
        String base = "http://192.168.0.11:8080/loan-calculator";
//        System.out.println(inputsJson);
        try {
//            call async( )
//            String jsonString = HttpUtils.urlContentPost(baseUrl, "loanInputs", inputsJson.toString());
//           new HttpAsyncTask().execute(baseUrl, jsonString.toString());
//            String jsonString = HttpUtils.urlContentPost(baseUrl, "loanInputs", inputsJson.toString());
//            new HttpAsyncTask().execute(baseUrl, inputsJson.toString());
            new HttpAsyncTask().execute(base, inputsJson.toString());
//            String hey = "hey";
//            Context contextNew = this;
//            DatabaseHandler hello = new DatabaseHandler(contextNew);
////            new DatabaseHandler(,1);
//            hello();

        } catch(Exception jse) {

            mMontlyPaymentResult.setText("JSON exception: " +jse.getLocalizedMessage());
            mTotalPaymentsResult.setText("");
        }

        String name = mMontlyPaymentResult.getText().toString();
        db.addContact(name);

    }
    private class HttpAsyncTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            // Getting username and password from user input
            String jsonString = "";


            try {
                jsonString = HttpUtils.urlContentPost(params[0],"loanInputs", params[1]);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            System.out.println(jsonString);
            return jsonString;
        }

        @Override
        protected void onPostExecute(String result){
            try{

                JSONObject jsonResult = new JSONObject(result);
                mMontlyPaymentResult.setText(jsonResult.getString("formattedMonthlyPayment"));
                mTotalPaymentsResult.setText(jsonResult.getString("formattedTotalPayments"));
                mLoanAmount.setText(jsonResult.getString("loanAmount"));
                mInterestRate.setText(jsonResult.getString("annualInterestRateInPercent"));
                mLoanPeriod.setText(jsonResult.getString("loanPeriodInMonths"));
            } catch (Exception e) {
                mMontlyPaymentResult.setText("Illegal base URL");
                mTotalPaymentsResult.setText("");

                mMontlyPaymentResult.setText("Server error: " + e);
                mTotalPaymentsResult.setText("");

                mMontlyPaymentResult.setText("JSON exception: " +e.getLocalizedMessage());
                mTotalPaymentsResult.setText("");
            }
        }
    }

    private class DataBaseAsyncTask extends AsyncTask<Context,Void,Void> {

        @Override
        protected Void  doInBackground(Context... contexts) {
            // Getting username and password from user input
            String jsonString = "";


//            SQLDemo2 inputs = new SQLDemo2();
            try {
//                inputs.openDatabase();

//                inputs.creat();

//                inputs.openDatabase();
//                openDatabase(); // open (create if needed) database
//            inputs.dropTable(); // if needed drop table tblAmigos
//            inputs.insertSomeDbData(); // create-populate tblAmigos
//                jsonString = HttpUtils.urlContentPost(params[0], "loanInputs", params[1]);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            System.out.println(jsonString);
            return null;
        }


        protected void onPostExecute(String result) {
//            try{


//                JSONObject jsonResult = new JSONObject(result);
//                mMontlyPaymentResult.setText(jsonResult.getString("formattedMonthlyPayment"));
//                mTotalPaymentsResult.setText(jsonResult.getString("formattedTotalPayments"));
//                mLoanAmount.setText(jsonResult.getString("loanAmount"));
//                mInterestRate.setText(jsonResult.getString("annualInterestRateInPercent"));
//                mLoanPeriod.setText(jsonResult.getString("loanPeriodInMonths"));
//            } catch (Exception e) {
//                mMontlyPaymentResult.setText("Illegal base URL");
//                mTotalPaymentsResult.setText("");
//
//                mMontlyPaymentResult.setText("Server error: " + e);
//                mTotalPaymentsResult.setText("");
//
//                mMontlyPaymentResult.setText("JSON exception: " +e.getLocalizedMessage());
//                mTotalPaymentsResult.setText("");
//            }
//        }
        }
    }



}
