**Unit Converter App for Android**

Created by Austin Skootsky

---

**How to Use the App**

This is a simple app for converting units.

The topmost dropdown menu allows the user to choose what category of units they want to convert among. Right now, the app offers a choice among: Length, Weight, and Temperature.

When a category is selected, the other two dropdown menus will update to display units in the selected category. For example, if the user selects "Weight", the other two dropdown menus will update to display the options "Pounds," "Kilograms," and "Stone."

The user can type a number, with or without a decimal, into the first field. The conversion result will automatically update as a number is typed, based on which units were selected in second two dropdown menus. The conversion will also automatically update if a new unit is selected in either dropdown.

A user can use the "Save Conversion" button to store the current conversion to a text field on the bottom. This is useful if a user wants to compare the results of multiple different conversions at once. Up to 3 conversions can be saved. Saving another conversion after that will remove the first conversion (First In, First Out).

---
**Notes**

1. The conversion result has precision up to 2 decimal places. If the conversion result would be <0.01, the app will simply display 0.00.

2. The app will not allow physically impossible inputs. For Length and Weight, a negative input will not be allowed. For any Temperature unit, an input that would be below 0 Kelvin is not allowed.

3. The Save Conversion button will not save invalid conversions, such as when the user inputs something other than a number or inputs a Temperature value that would be below 0 Kelvin, etc.

---
**Units Supported**

Length:

    • Feet
    • Meters
    • Miles
    • Kilometers

Weight:

    • Pounds
    • Kilograms
    • Stone

Temperature:

    • Fahrenheit
    • Celsius
    • Kelvin