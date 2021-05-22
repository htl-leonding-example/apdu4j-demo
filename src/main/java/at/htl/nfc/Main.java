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

        try {
            List<CardTerminal> cardTerminalList = f.terminals().list();
            for (CardTerminal cardTerminal : cardTerminalList) {
                System.out.println(cardTerminal.getName());
            }
            myCardReader = cardTerminalList.get(0);
            System.out.println(myCardReader.getName());
            System.out.println(myCardReader.isCardPresent()?"Card is present":"Card is NOT present");
            myCardReader = LoggingCardTerminal.getInstance(myCardReader, System.out);

        } catch (CardException e) {
            e.printStackTrace();
        }

    }
}

