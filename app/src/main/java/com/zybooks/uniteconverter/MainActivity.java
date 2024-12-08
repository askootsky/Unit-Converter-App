package com.zybooks.uniteconverter;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //initialize objects
    Spinner spinnerUnitTypes, spinnerUnitToConvert, spinnerConvertToUnit;
    EditText numberToConvert;
    TextView conversionResult, explanationView, save1, save2, save3;
    Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    //--------------------------------------------------------------------------------------------------
        //assign our text
        explanationView = findViewById(R.id.explanation);
        String explanationText = getString(R.string.explanation);
        explanationView.setText(explanationText);

        numberToConvert = findViewById(R.id.numberToConvert);
        conversionResult = findViewById(R.id.conversionResult);

        save1 = findViewById(R.id.save1);
        save2 = findViewById(R.id.save2);
        save3 = findViewById(R.id.save3);

        numberToConvert.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
                // Optional: Do something before the text changes
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                // Try to parse the number from the EditText
                try {
                    // Convert the text input to a number
                    double input = Double.parseDouble(numberToConvert.getText().toString());

                    double result = convert(input);

                    String isTemp = spinnerUnitTypes.getSelectedItem().toString();
                    String tempValue = spinnerUnitToConvert.getSelectedItem().toString();

                    if (!isTemp.equals("Temperature")){
                        if (result < 0.00) {
                            conversionResult.setText("Length/Weight cannot be negative");
                            return;
                        }
                    }
                    if (isTemp.equals("Temperature")){
                        if (tempValue.equals("Fahrenheit")){
                            if (input < -459.67) {
                                conversionResult.setText("Fahr. cannot be lower than -459.67°");
                                return;
                            }
                        }
                        if (tempValue.equals("Celsius")){
                            if (input < -273.15) {
                                conversionResult.setText("Cels. cannot be lower than -273.15°");
                                return;
                            }
                        }
                        if (tempValue.equals("Kelvin")){
                            if (input < 0) {
                                conversionResult.setText("Kelv. cannot be lower than 0");
                                return;
                            }
                        }
                    }

                    String formattedResult = String.format("%.2f", result);

                    // Display the result in the TextView
                    conversionResult.setText(formattedResult);
                }
                catch (NumberFormatException e) {
                    // If input is not a valid number, display an error message
                    if (!numberToConvert.getText().toString().isEmpty()) {
                        conversionResult.setText("Invalid Num");
                    }
                    else {
                        conversionResult.setText("0.00");
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Optional: Do something after the text has changed
            }
        });
    //----------------------------------------------------------------------------------------------
        //BUTTON
        //assign button
        saveButton = findViewById(R.id.save);

        //create on click event that will save the current conversion
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String num1 = numberToConvert.getText().toString();
                String unit1 = spinnerUnitToConvert.getSelectedItem().toString();
                String num2 = conversionResult.getText().toString();
                String unit2 = spinnerConvertToUnit.getSelectedItem().toString();

                if (num2.equals("Invalid Num")) {
                    return;
                }
                if (num2.equals("Kelv. cannot be lower than 0")) {
                    return;
                }
                if (num2.equals("Cels. cannot be lower than -273.15°")) {
                    return;
                }
                if (num2.equals("Fahr. cannot be lower than -459.67°")) {
                    return;
                }
                if (num2.equals("Length/Weight cannot be negative")) {
                    return;
                }

                if (save1.getText().toString().isEmpty()) {
                    save1.setText(num1 + " " + unit1 + " ➔ " + num2 + " " + unit2);
                    return;
                }
                if (save2.getText().toString().isEmpty()) {
                    save2.setText(save1.getText().toString());
                    save1.setText(num1 + " " + unit1 + " ➔ " + num2 + " " + unit2);
                    return;
                }
                else {
                    save3.setText(save2.getText().toString());
                    save2.setText(save1.getText().toString());
                    save1.setText(num1 + " " + unit1 + " ➔ " + num2 + " " + unit2);
                }
            }
        });
    //----------------------------------------------------------------------------------------------
        //SPINNERS
        //assign our spinners
        spinnerUnitTypes = (Spinner) findViewById(R.id.spinner_unit_types);
        spinnerUnitToConvert = (Spinner) findViewById(R.id.spinner_unit_to_convert);
        spinnerConvertToUnit = (Spinner) findViewById(R.id.spinner_convert_to_unit);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.unit_types,
                android.R.layout.simple_spinner_item
        );
        spinnerUnitTypes.setAdapter(adapter);

        // Set an ItemSelectedListener on spinnerUnitTypes to update the other spinners and conversion
        spinnerUnitTypes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Update other spinners based on spinnerUnitTypes selection
                updateSpinners(position);
                //Update the conversion
                if (!numberToConvert.getText().toString().isEmpty()){
                    try {
                        // Convert the text input to a number
                        double input = Double.parseDouble(numberToConvert.getText().toString());

                        double result = convert(input);

                        String isTemp = spinnerUnitTypes.getSelectedItem().toString();
                        String tempValue = spinnerUnitToConvert.getSelectedItem().toString();

                        if (!isTemp.equals("Temperature")){
                            if (result < 0.00) {
                                conversionResult.setText("Length/Weight cannot be negative");
                                return;
                            }
                        }
                        if (isTemp.equals("Temperature")){
                            if (tempValue.equals("Fahrenheit")){
                                if (input < -459.67) {
                                    conversionResult.setText("Fahr. cannot be lower than -459.67°");
                                    return;
                                }
                            }
                            if (tempValue.equals("Celsius")){
                                if (input < -273.15) {
                                    conversionResult.setText("Cels. cannot be lower than -273.15°");
                                    return;
                                }
                            }
                            if (tempValue.equals("Kelvin")){
                                if (input < 0) {
                                    conversionResult.setText("Kelv. cannot be lower than 0");
                                    return;
                                }
                            }
                        }

                        String formattedResult = String.format("%.2f", result);

                        // Display the result in the TextView
                        conversionResult.setText(formattedResult);
                    }
                    catch (NumberFormatException e) {
                        // If input is not a valid number, display an error message
                        if (!numberToConvert.getText().toString().isEmpty()) {
                            conversionResult.setText("Invalid Number");
                        }
                        else {
                            conversionResult.setText("0.00");
                        }
                    }
                }
                else {
                    conversionResult.setText("0.00");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Optionally handle this case
            }
        });

        // Set ItemSelectedListener to other spinners to update conversion
        spinnerUnitToConvert.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                //Update the conversion
                if (!numberToConvert.getText().toString().isEmpty()){
                    try {
                        double input = Double.parseDouble(numberToConvert.getText().toString());

                        double result = convert(input);

                        String isTemp = spinnerUnitTypes.getSelectedItem().toString();
                        String tempValue = spinnerUnitToConvert.getSelectedItem().toString();

                        if (!isTemp.equals("Temperature")){
                            if (result < 0.00) {
                                conversionResult.setText("Length/Weight cannot be negative");
                                return;
                            }
                        }
                        if (isTemp.equals("Temperature")){
                            if (tempValue.equals("Fahrenheit")){
                                if (input < -459.67) {
                                    conversionResult.setText("Fahr. cannot be lower than -459.67°");
                                    return;
                                }
                            }
                            if (tempValue.equals("Celsius")){
                                if (input < -273.15) {
                                    conversionResult.setText("Cels. cannot be lower than -273.15°");
                                    return;
                                }
                            }
                            if (tempValue.equals("Kelvin")){
                                if (input < 0) {
                                    conversionResult.setText("Kelv. cannot be lower than 0");
                                    return;
                                }
                            }
                        }

                        String formattedResult = String.format("%.2f", result);

                        // Display the result in the TextView
                        conversionResult.setText(formattedResult);
                    }
                    catch (NumberFormatException e) {
                        // If input is not a valid number, display an error message
                        if (!numberToConvert.getText().toString().isEmpty()) {
                            conversionResult.setText("Invalid Num");
                        }
                        else {
                            conversionResult.setText("0.00");
                        }
                    }
                }
                else {
                    conversionResult.setText("0.00");
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Optionally handle this case
            }
        });
        spinnerConvertToUnit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                //Update the conversion
                if (!numberToConvert.getText().toString().isEmpty()){
                    try {
                        // Convert the text input to a number
                        double input = Double.parseDouble(numberToConvert.getText().toString());

                        double result = convert(input);

                        String isTemp = spinnerUnitTypes.getSelectedItem().toString();
                        String tempValue = spinnerUnitToConvert.getSelectedItem().toString();

                        if (!isTemp.equals("Temperature")){
                            if (result < 0.00) {
                                conversionResult.setText("Length/Weight cannot be negative");
                                return;
                            }
                        }
                        if (isTemp.equals("Temperature")){
                            if (tempValue.equals("Fahrenheit")){
                                if (input < -459.67) {
                                    conversionResult.setText("Fahr. cannot be lower than -459.67°");
                                    return;
                                }
                            }
                            if (tempValue.equals("Celsius")){
                                if (input < -273.15) {
                                    conversionResult.setText("Cels. cannot be lower than -273.15°");
                                    return;
                                }
                            }
                            if (tempValue.equals("Kelvin")){
                                if (input < 0) {
                                    conversionResult.setText("Kelv. cannot be lower than 0");
                                    return;
                                }
                            }
                        }

                        String formattedResult = String.format("%.2f", result);

                        // Display the result in the TextView
                        conversionResult.setText(formattedResult);
                    }
                    catch (NumberFormatException e) {
                        // If input is not a valid number, display an error message
                        if (!numberToConvert.getText().toString().isEmpty()) {
                            conversionResult.setText("Invalid Num");
                        }
                        else {
                            conversionResult.setText("0.00");
                        }
                    }
                }
                else {
                    conversionResult.setText("0.00");
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Optionally handle this case
            }
        });
    }
//--------------------------------------------------------------------------------------------------
//FUNCTION FOR UPDATING SPINNERS
    private void updateSpinners(int position) {
        // Define the appropriate string array for spinner2 based on spinner1 selection
        String[] options = new String[0];  // Default empty array

        switch (position) {
            case 0: // "Category 1" selected in spinner1
                options = getResources().getStringArray(R.array.length_units);
                break;
            case 1: // "Category 2" selected in spinner1
                options = getResources().getStringArray(R.array.weight_units);
                break;
            case 2: // "Category 3" selected in spinner1
                options = getResources().getStringArray(R.array.temperature_units);
                break;
        }

        // Create a new adapter for spinner2 with the updated options
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, options);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerUnitToConvert.setAdapter(adapter2);

        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, options);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerConvertToUnit.setAdapter(adapter3);
    }
//--------------------------------------------------------------------------------------------------
    private Double convert(double input) {
        String fromUnit = spinnerUnitToConvert.getSelectedItem().toString();
        String toUnit = spinnerConvertToUnit.getSelectedItem().toString();

        if (fromUnit.equals("Feet")){
            switch (toUnit) {
                case "Feet":
                    return input;
                case "Meters":
                    return feetToMeters(input);
                case "Miles":
                    return feetToMiles(input);
                case "Kilometers":
                    return feetToKilometers(input);
            }
        }
        if (fromUnit.equals("Meters")){
            switch (toUnit) {
                case "Feet":
                    return metersToFeet(input);
                case "Meters":
                    return input;
                case "Miles":
                    return metersToMiles(input);
                case "Kilometers":
                    return metersToKilometers(input);
            }
        }
        if (fromUnit.equals("Miles")){
            switch (toUnit) {
                case "Feet":
                    return milesToFeet(input);
                case "Meters":
                    return milesToMeters(input);
                case "Miles":
                    return input;
                case "Kilometers":
                    return milesToKilometers(input);
            }
        }
        if (fromUnit.equals("Kilometers")){
            switch (toUnit) {
                case "Feet":
                    return kilometersToFeet(input);
                case "Meters":
                    return kilometersToMeters(input);
                case "Miles":
                    return kilometersToMiles(input);
                case "Kilometers":
                    return input;
            }
        }

        if (fromUnit.equals("Pounds")){
            switch (toUnit) {
                case "Pounds":
                    return input;
                case "Kilograms":
                    return poundsToKilos(input);
                case "Stone":
                    return poundsToStone(input);
            }
        }
        if (fromUnit.equals("Kilograms")){
            switch (toUnit) {
                case "Pounds":
                    return kilosToPounds(input);
                case "Kilograms":
                    return (input);
                case "Stone":
                    return kilosToStone(input);
            }
        }
        if (fromUnit.equals("Stone")){
            switch (toUnit) {
                case "Pounds":
                    return stoneToPounds(input);
                case "Kilograms":
                    return stoneToKilos(input);
                case "Stone":
                    return (input);
            }
        }

        if (fromUnit.equals("Fahrenheit")){
            switch (toUnit) {
                case "Fahrenheit":
                    return input;
                case "Celsius":
                    return fahrToCels(input);
                case "Kelvin":
                    return fahrToKelv(input);
            }
        }
        if (fromUnit.equals("Celsius")){
            switch (toUnit) {
                case "Fahrenheit":
                    return celsToFahr(input);
                case "Celsius":
                    return (input);
                case "Kelvin":
                    return celsToKelv(input);
            }
        }
        if (fromUnit.equals("Kelvin")){
            switch (toUnit) {
                case "Fahrenheit":
                    return kelvToFahr(input);
                case "Celsius":
                    return kelvToCels(input);
                case "Kelvin":
                    return (input);
            }
        }

        return 0.00;
    }

//CONVERSION FUNCTIONS
    //LENGTH ---------------------------------------------------------------------------------------
    private Double feetToMeters(double input) {
        if ((input * 0.3048) < 0.00) {
            return -1.00;
        }
        if ((input * 0.3048) < 0.01) {
            return 0.00;
        }
        return (input * 0.3048);
    }
    private Double feetToMiles(double input) {
        if ((input / 5280) < 0.00) {
            return -1.00;
        }
        if ((input / 5280) < 0.01) {
            return 0.00;
        }
        else {
            return input/5280;
        }
    }
    private Double feetToKilometers(double input) {
        if ((input * 0.0003048) < 0.00) {
            return -1.00;
        }
        if ((input * 0.0003048) < 0.01) {
            return 0.00;
        }
        else {
            return (input * 0.0003048);
        }
    }
    private Double metersToFeet(double input) {
        if ((input * 3.28084) < 0.00) {
            return -1.00;
        }
        if ((input * 3.28084)<0.01){
            return 0.00;
        }
        return (input * 3.28084);
    }
    private Double metersToMiles(double input) {
        if ((input / 1609.344) < 0.00) {
            return -1.00;
        }
        if ((input / 1609.344)<0.01){
            return 0.00;
        }
        return (input / 1609.344);
    }
    private Double metersToKilometers(double input) {
        if ((input / 1000) < 0.00) {
            return -1.00;
        }
        if ((input / 1000)<0.01){
            return 0.00;
        }
        return (input / 1000);
    }
    private Double milesToFeet(double input) {
        if ((input * 5280) < 0.00) {
            return -1.00;
        }
        if ((input * 5280)<0.01){
            return 0.00;
        }
        return (input * 5280);
    }
    private Double milesToMeters(double input) {
        if ((input * 1609.344) < 0.00) {
            return -1.00;
        }
        if ((input * 1609.344)<0.01){
            return 0.00;
        }
        return (input * 1609.344);
    }
    private Double milesToKilometers(double input) {
        if ((input * 1.609344) < 0.00) {
            return -1.00;
        }
        if ((input * 1.609344)<0.01){
            return 0.00;
        }
        return (input * 1.609344);
    }
    private Double kilometersToFeet(double input) {
        if ((input * 3280.84) < 0.00) {
            return -1.00;
        }
        if ((input * 3280.84)<0.01){
            return 0.00;
        }
        return (input * 3280.84);
    }
    private Double kilometersToMiles(double input) {
        if ((input * 0.6213712) < 0.00) {
            return -1.00;
        }
        if ((input * 0.6213712)<0.01){
            return 0.00;
        }
        return (input * 0.6213712);
    }
    private Double kilometersToMeters(double input) {
        if ((input * 1000) < 0.00) {
            return -1.00;
        }
        if ((input * 1000)<0.01){
            return 0.00;
        }
        return (input * 1000);
    }
    //WEIGHT ---------------------------------------------------------------------------------------
    private Double poundsToKilos(double input) {
        if ((input * 0.4535924) < 0.00) {
            return -1.00;
        }
        if ((input * 0.4535924)<0.01){
            return 0.00;
        }
        return (input * 0.4535924);
    }
    private Double poundsToStone(double input) {
        if ((input * 0.07142857) < 0.00) {
            return -1.00;
        }
        if ((input * 0.07142857)<0.01){
            return 0.00;
        }
        return (input * 0.07142857);
    }
    private Double kilosToPounds(double input) {
        if ((input * 2.204623) < 0.00) {
            return -1.00;
        }
        if ((input * 2.204623)<0.01){
            return 0.00;
        }
        return (input * 2.204623);
    }
    private Double kilosToStone(double input) {
        if ((input * 0.157473) < 0.00) {
            return -1.00;
        }
        if ((input * 0.157473)<0.01){
            return 0.00;
        }
        return (input * 0.157473);
    }
    private Double stoneToPounds(double input) {
        if ((input * 14) < 0.00) {
            return -1.00;
        }
        if ((input * 14)<0.01){
            return 0.00;
        }
        return (input * 14);
    }
    private Double stoneToKilos(double input) {
        if ((input * 6.350293) < 0.00) {
            return -1.00;
        }
        if ((input * 6.350293)<0.01){
            return 0.00;
        }
        return (input * 6.350293);
    }
    //TEMP -----------------------------------------------------------------------------------------
    private Double fahrToCels(double input) {
        return ((input - 32) * 5/9);
    }
    private Double fahrToKelv(double input) {
        return (((input - 32) * 5/9) + 273.15);
    }
    private Double celsToFahr(double input) {
        return ((input * 1.8) + 32);
    }
    private Double celsToKelv(double input) {
        return (input + 273.15);
    }
    private Double kelvToFahr(double input) {
//        return (((input - 273.15) * 1.8) + 32)
        return (input - 273.15) * 9 / 5 + 32;
    }
    private Double kelvToCels(double input) {
        return (input - 273.15);
    }
}