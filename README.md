📌 Overview

GitHubJavaAnalyzer is a Java console application (Eclipse project) designed to analyze Java source code directly from a GitHub repository.
The program clones a repository from a user-provided URL, extracts .java files containing classes, and generates detailed code metrics.

🚀 Features

Clone any public GitHub repository by entering its URL

Automatically detect and filter .java class files

Perform code analysis to calculate:

📖 Javadoc comment lines

💬 Other comment lines

🔹 Pure code lines (excluding comments & empty lines)

📊 LOC (total lines of code)

🔧 Number of functions/methods

📉 Comment Deviation Percentage

⚙️ Requirements

Java 8 or higher

Git installed on the system

Eclipse IDE (recommended for development & testing)

🖥️ Usage

Run the program from Eclipse or terminal.

Enter the GitHub repository URL when prompted.

The program will clone the repository, extract .java files, and analyze them.

Results will be displayed in the console.

Example Output
Class: ExampleClass.java
Javadoc lines: 12
Other comments: 8
Code lines: 54
LOC: 75
Functions: 6
Comment Deviation: 15%

🔮 Future Improvements

Export analysis results to CSV/JSON for reporting

Support private repositories (with authentication)

Add graphical interface (GUI) for easier usage

🤝 Contributing

Contributions, issues, and feature requests are welcome!
Feel free to fork the repo and submit pull requests.

📜 License

This project is licensed under the MIT License – free to use, modify, and distribute.
