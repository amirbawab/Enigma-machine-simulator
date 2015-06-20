Enigma Machine 3.1
===============

####What's New in 3.1:
- Enhanced the quality of lines in wires mode

####What's New in 3.0:
- Restart and Exit from menu (File > Restart/Exit)
- Wires connection mode (Display > Wires connection)

####The Enigma Machine:
The Enigma machine is a piece of spook hardware invented by a German and used by Britain's codebreakers as a way of deciphering German signals traffic during World War Two. It has been claimed that as a result of the information gained through this device, hostilities between Germany and the Allied forces were curtailed by two years.<br>
<a href="http://www.bbc.co.uk/history/topics/enigma">BBC - History</a><br/>

####Quick link to the Enigma Machine 3.0 GUI (Scroll down for screenshots)
<a href="https://github.com/amirbawab/Enigma-machine-simulator/blob/master/Runnable%20JAR/Enigma%20Machine.jar?raw=true">Enigma Machine 3.1 (GUI)</a>

Note: This `jar` file is the same as the one in the `Runnable JAR` folder

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

###Display > Text box
<img src="https://raw.githubusercontent.com/amirbawab/Enigma-machine-simulator/master/screenshot/textbox.png"/>
<br/>
###Display > Keyboard
<img src="https://raw.githubusercontent.com/amirbawab/Enigma-machine-simulator/master/screenshot/keyboard.png"/>
<br/>
###Display > Wires connection
<img src="https://raw.githubusercontent.com/amirbawab/Enigma-machine-simulator/master/screenshot/wires.png"/>
