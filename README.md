# AddressParser


You can find in the main branch the program that parses the addresses based on some logic and patterns I noticed using if else statements and regex.<br>
Every reasoning is explained in the comments and there are some unit tests that try out the examples given in the challenge.

In the second branch, you can find the added route '/nlp' that uses the NLP library jpostal based on a C library called Libpostal.<br>
This is the original NLP library that I used : [https://github.com/openvenues/jpostal](https://github.com/openvenues/jpostal).

You can run the app in the main branch and try it out by running it as a simple spring boot app with <code> mvn spring-boot:run </code>.

As for the work in the second branch, it is quite a hassel to make it work, you need Linux and a lot of configuration, so you can see the demo [here](https://drive.google.com/file/d/1sW7-9SNS_5WvRYLd6c5J1Hz441GqUg6C/view?usp=sharing) or if you want to make it work, we can have a call and we will go through it.
