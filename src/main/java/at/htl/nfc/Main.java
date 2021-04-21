package at.htl.nfc;

import apdu4j.pcsc.TerminalManager;
import apdu4j.pcsc.terminals.LoggingCardTerminal;
import javax.smartcardio.TerminalFactory;
import javax.smartcardio.CardTerminal;


public class Main {

    public static void main(String[] args) {
        TerminalManager.fixPlatformPaths();

        TerminalFactory f = TerminalFactory.getDefault();
        CardTerminal r = f.terminals().getTerminal("ACR122U");
        r = LoggingCardTerminal.getInstance(r);
        // Now use javax.smartcardio as you normally do
    }
}

