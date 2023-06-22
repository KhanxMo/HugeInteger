# HugeInteger
HugeInteger is a Java class to handle very large integer values, even those beyond the range of built-in integer types. It is an ideal solution for use cases that require handling of really large numbers such as those in cryptography, physics, or any large scale calculations.

### Features
- **Flexible Initialization:** HugeInteger objects can be initialized in two ways:
  - Randomly generating a HugeInteger with a given number of digits.
  - Constructing a HugeInteger object from a given string.
- **Validations:** It validates if the input number is positive and if the input string is a valid number.

- **Sign Handling:** It considers the sign of the number and handles it accordingly.

### How It Works
**Class Variables**
- number[]: It is an integer array which stores the digits of the HugeInteger number.
- length: It denotes the number of digits in the HugeInteger number.
- pos: It is a boolean flag which denotes the sign of the HugeInteger number. If the flag is true then the number is positive, else it is negative.

**Constructors**
The HugeInteger class has two constructors:
1. public HugeInteger(int n): This constructor takes in an integer n as parameter and generates a random HugeInteger number with n digits.
2. public HugeInteger(String val): This constructor takes a string as parameter and creates a HugeInteger object from the given string.

### Usage
To use the HugeInteger class in your Java project, import the HugeInteger.java file into your project and simply create a new instance of the class using one of the available constructors.

```java
// Create a random HugeInteger with 5 digits
HugeInteger randomInteger = new HugeInteger(5);

// Create a HugeInteger from string
HugeInteger stringInteger = new HugeInteger("-12345");
```

### Future Scope
The HugeInteger class can be extended to include more mathematical operations such as addition, subtraction, multiplication, and division to further improve its utility.

### Contributing
Your contributions are always welcome! Please feel free to suggest improvements, report bugs, and make the project better.
