## [Accuracy of Metric-Based vs. Machine Learning Approaches in Detecting Design Patterns](https://github.com/NilsDunlop/Thesis/blob/main/Report/Thesis%20230526.pdf)

The thesis aims to investigate the effectiveness of a metric-based approach compared to machine-learning methods in detecting design patterns in software code. Design patterns provide reusable solutions to common programming problems, which is crucial in improving software quality and maintainability. While machine learning methods have gained popularity in design pattern detection, they often present challenges due to their reliance on large labelled datasets and extensive training. To address these limitations, I propose a metric-based approach that overcomes the need for extensive training by extracting metrics from programs using scripts and evaluating patterns based on predetermined thresholds. By conducting a comparative analysis of the metric-based and machine-learning approaches on eighteen open-source projects in both C++ and Java, this study aims to assess the accuracy and practicality of each method. The findings demonstrate that the metric-based approach achieves comparable or better results in detecting design patterns without relying on AI. This research contributes to simplifying design pattern analysis and opens up possibilities for practical implementation in software development.

## Introduction

The primary objective of this study is to conduct a comprehensive comparative analysis between the metric-based approach and machine-learning methods using eighteen open-source projects in both C++ and Java. By evaluating the accuracy and practicality of each method, this research aims to determine the effectiveness of the metric-based approach in detecting design patterns without relying on AI. The findings from this analysis reveal that the metric-based approach achieves comparable or even superior results in pattern detection.

The research significantly contributes to the field by offering a practical alternative to the prevalent machine-learning methods, simplifying design pattern analysis, and enhancing software quality. The study emphasizes the potential of this approach to streamline design pattern analysis and improve software maintainability. By overcoming the challenges associated with extensive training and large datasets, the metric-based approach presents opportunities for practical implementation in software development.

In summary, this thesis explores the effectiveness of the metric-based approach in detecting design patterns, providing valuable insights into its accuracy and applicability. The comparative analysis conducted on diverse open-source projects establishes the viability of the metric-based approach as a practical solution, contributing to the advancement of software analysis and quality assurance techniques.

## Technologies Used

* Programming Languages: Java and Python
* Java AST: [Javaparser](https://github.com/javaparser/javaparser)
* C++ AST: [LLVM Clang](https://github.com/llvm/llvm-project)

## Repository Analysed

For this thesis research, a total of 18 repositories were analyzed. These repositories were selected from two different sources: PMART and open-source C++ repositories on GitHub. The repositories were selected due to their clearly labelled design patterns and broad usage in the field. Below is a list of the repositories analyzed:

### PMART Repositories
- [QuickUML](https://www.ptidej.net/tools/designpatterns/)
- [Lexi](https://www.ptidej.net/tools/designpatterns/)
- [JRefactory](https://www.ptidej.net/tools/designpatterns/)
- [Netbeans](https://www.ptidej.net/tools/designpatterns/)
- [JUnit](https://www.ptidej.net/tools/designpatterns/)
- [JHotDraw](https://www.ptidej.net/tools/designpatterns/)
- [MapperXML](https://www.ptidej.net/tools/designpatterns/)
- [Nutch](https://www.ptidej.net/tools/designpatterns/)
- [PMD](https://www.ptidej.net/tools/designpatterns/)

### C++ GitHub Repositories
- [RefactoringGuru](https://github.com/RefactoringGuru/design-patterns-cpp)
- [Apress](https://github.com/Apress/design-patterns-in-modern-cpp)
- [Yogykwan](https://github.com/yogykwan/design-patterns-cpp)
- [JakubVojvoda](https://github.com/JakubVojvoda/design-patterns-cpp)
- [DownDemo](https://github.com/downdemo/Design-Patterns-in-Cpp17)
- [Liu-Jianhao](https://github.com/liu-jianhao/Cpp-Design-Patterns)
- [Pezy](https://github.com/pezy/DesignPatterns)
- [Rhyspang](https://github.com/rhyspang/CPP-Design-Patterns)
- [Light-City](https://github.com/Light-City/CPlusPlusThings)

By analyzing repositories from both PMART and GitHub, we were able to achieve in-depth design pattern detection results in Java and C++.
## Installation

### Java Extraction
To perform the Java extraction, ensure that you have Java and Maven installed on your machine. If you haven't installed them yet, follow the instructions provided by their respective documentation.

### C++ Extraction
For the C++ extraction, please follow these steps:

1. Ensure that Python is installed on your machine. If you don't have Python installed, you can download and install it from the official Python website.
2. Once Python is installed, open your preferred command-line interface.
3. Install the external library C++ clang by executing the following command:
``` python
pip install clang
```

With the necessary installations completed, you are now ready to proceed with the using the project.
## Usage

### Java
1. Clone the repository containing the extraction scripts to your local machine and open it in your preferred Integrated Development Environment (IDE).
2. Navigate to the appropriate source folder depending on the extraction method you wish to use: <br> For the Javaparser approach, <pre><code>cd Scripts/Java/javaparser/src/main/java/nils/dunlop/thesis</pre></code> <br> For the Javasymbol solver approach, <br> <pre><code>cd Scripts/Java/javasymbolsolver/src/main/java/nils/dunlop/thesis</pre></code>
3. Open the MainRunner.java file within the chosen source folder.

4. Update the source folder path in the MainRunner.java file to specify the location of the code you want to analyze and save the changes. 

5. Compile the MainRunner.java file by running the following command: <br> <pre><code>javac MainRunner.java</pre></code>

### C++ Extraction
1. Clone the repository and open it in your preferred Integrated Development Environment (IDE).
2. Navigate to the Scripts/C++ directory within the cloned repository. <br> <pre><code>cd Scripts/C++</pre></code>
3. Open the main.py file, update the file paths to specify the location of the C++ code you want to analyze and save. 
4. Run the following command to start the C++ metric extraction process: <pre><code>python main.py</pre></code>

## Special Thanks

I extend my sincere gratitude to my supervisor, [Jennifer Horkoff](https://research.chalmers.se/en/person/jenho), for their guidance and support throughout this research project. I would also like to thank Gothenburg University and Chalmers AI Research Centre for providing the necessary resources and facilities for conducting this research.
