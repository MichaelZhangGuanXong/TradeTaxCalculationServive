# TradeTaxCalculationServive
This repo contains code to calculate tax to pay for stock trade operations

This is a Java maven project, could build as a Java .jar file to run as command line utility that accept Json String included stock trade opeartions from STDIN, and calculate tax payable and show Json result in screen.

The Java version is 17.

In order to build a jar file to run, 2 ways can apply:
1. From zip file
  1.1. Unzip .zip file into your local machine
  1.2. Open Intellij IDE to open pom.xml under unziped folder as project
  1.3. Open Maven Tool Window from right side, and expand "Lifecycle", then click build
  1.4. After completed, you can run it in command with built .jar file named TradeTaxCalculationService.jar in command line using command: java -jar TradeTaxCalculationService
  1.5. Or run in Intellij IDE

2. Clone project from Github - git clone https://github.com/MichaelZhangGuanXong/TradeTaxCalculationServive.git, then doing 1.3 - 1.5 like in way 1

For test cases, I used JUnit to cover 9 test cases that assignmment provided, and also add 2 extra cases to cover accumulation of loss and deduction. I aslo include Jacoco plugin in pom, so after build, test case coverage
report will be generated under target/ folder, you can open target/site/jacoco/index.html with web browser to review coverage.

Due to only can take some time at night to complete it, the code may not be good enough, need more time to polish.
