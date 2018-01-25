package edu.pitt.cs.cs1635.bjk69.postagecalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.text.NumberFormat;

public class PostageCalculatorActivity extends AppCompatActivity {

    private Spinner mPostageSpinner;
    private ArrayAdapter<CharSequence> mAdapter;
    private Button mCalculateButton;
    private EditText mWeightField;
    private double mWeight;
    private String mPackageType;
    private TextView mCostView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCalculateButton = (Button) findViewById(R.id.calculate_button);
        mPostageSpinner = (Spinner) findViewById(R.id.postage_spinner);
        mWeightField = (EditText) findViewById(R.id.package_weight);
        mCostView  = (TextView) findViewById(R.id.cost_display);

        // Create an ArrayAdapter using the string array and a default spinner layout
        mAdapter = ArrayAdapter.createFromResource(this, R.array.postage_array,
                android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        mPostageSpinner.setAdapter(mAdapter);

        // Spinner Listener
        mPostageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                // An item was selected. You can retrieve the selected item using
                // parent.getItemAtPosition(pos)

                // On Selecting a spinner item
                mPackageType = parent.getItemAtPosition(pos).toString();
                checkFieldForEmptyValue();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });

        // Calculate listener
        mCalculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NumberFormat mFormatter = NumberFormat.getCurrencyInstance();
                String mDisplayCost = mFormatter.format(weightCalculate(mPackageType, mWeight));
                mCostView.setText("Package Cost: " + mDisplayCost);
            }
        });

        // Weight input listener
        mWeightField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int before, int count) {
                // Intentionally blank
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Intentionally blank
            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    double val = Double.parseDouble(s.toString());
                    if(mPackageType.equals("Letter (Stamped)") ||
                            mPackageType.equals("Letter (Metered)")){
                        if(val > 3.5) {
                            s.replace(0, s.length(),"3.5", 0, 3);
                            Toast.makeText(getApplicationContext(), "Letter weight must be 3.5 ounces or less.",
                                    Toast.LENGTH_SHORT).show();
                        } else if(val < 0.0) {
                            s.replace(0, s.length(), "0", 0, 1);
                            Toast.makeText(getApplicationContext(), "Weight must be positive.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        if (val > 13.0) {
                            s.replace(0, s.length(), "13", 0, 2);
                            Toast.makeText(getApplicationContext(), "Weight must be 13 ounces or less.",
                                    Toast.LENGTH_SHORT).show();
                        } else if (val < 0.0) {
                            s.replace(0, s.length(), "0", 0, 1);
                            Toast.makeText(getApplicationContext(), "Weight must be positive.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                    mWeight = val;
                } catch(NumberFormatException e) {
                    // Intentionally blank
                }
                checkFieldForEmptyValue();
            }
        });

        checkFieldForEmptyValue();
    }

    private double weightCalculate(String mPackageType, double mWeight){
        double mCost = 0;
        if(mPackageType.equals("Letter (Stamped)")) {
            if(mWeight > 1 && mWeight <= 3) {
                mCost = 0.5 + (mWeight-1) * 0.21;
            } else if(mWeight <= 1) {
                mCost = 0.5;
            } else {
                mCost = 1.13;
            }
        } else if(mPackageType.equals("Letter (Metered)")) {
            if(mWeight > 1 && mWeight <= 3) {
                mCost = 0.47 + (mWeight - 1) * 0.21;
            } else if(mWeight <= 1) {
                mCost = 0.47;
            } else {
                mCost = 1.1;
            }
        } else if(mPackageType.equals("Large Envelope")) {
            if(mWeight > 1) {
                mCost = 1 + (mWeight - 1) * 0.21;
            } else {
                mCost = 1;
            }
        } else {
            if(mWeight <= 4) {
                mCost = 3.5;
            } else if(mWeight <= 8 && mWeight > 4) {
                mCost = 3.75;
            } else if(mWeight > 8) {
                mCost = 3.75 + (mWeight-8) * 0.35;
            }
        }


        return mCost;
    }

    private void checkFieldForEmptyValue(){
        String mText = mWeightField.getText().toString();

        if(mText.equals("") || mPackageType.equals("(Selection)")){
            mCalculateButton.setEnabled(false);
            mCostView.setText("Package Cost: ");
        } else {
            mCalculateButton.setEnabled(true);
        }
    }


}


