Enigma Machine
===============

The Enigma machine is a piece of spook hardware invented by a German and used by Britain's codebreakers as a way of deciphering German signals traffic during World War Two. It has been claimed that as a result of the information gained through this device, hostilities between Germany and the Allied forces were curtailed by two years.<br>
<a href="http://www.bbc.co.uk/history/topics/enigma">BBC - History</a><br/>

##How to use the enigma machine

####A - Using the console

#####ConsoleDriver.java

``` java
// Create machine
Enigma enigma = new Enigma(Enigma.I, Enigma.II, Enigma.III, Enigma.B);

// Configure rotors
enigma.getLeftRotor().setRotorHead('A');
enigma.getCenterRotor().setRotorHead('B');
enigma.getRightRotor().setRotorHead('C');

// Configure rings
enigma.getLeftRotor().setRingHead('D');
enigma.getCenterRotor().setRingHead('E');
enigma.getRightRotor().setRingHead('F');

System.out.println(enigma.type("HELLO WORLD"));
```

#####Output
```
EYGHG AIAJV
```

####B - Using the graphical user interface

#####GUIDriver.java
<img src="https://raw.githubusercontent.com/amirbawab/Enigma/master/screenshot/gui.png?token=AF-EkluaC14tnOjONm8g8r7BdKif20Ukks5Ux_KewA%3D%3D"/>
