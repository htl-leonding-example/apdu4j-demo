package at.htl.nfc;

import apdu4j.core.BlockingBIBO;
import apdu4j.core.CommandAPDU;
import apdu4j.core.ResponseAPDU;
import apdu4j.pcsc.CardBIBO;
import apdu4j.pcsc.TerminalManager;
import apdu4j.pcsc.terminals.LoggingCardTerminal;

import javax.smartcardio.Card;
import javax.smartcardio.CardException;
import javax.smartcardio.TerminalFactory;
import javax.smartcardio.CardTerminal;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Main {

    public static void main(String[] args) {
        TerminalManager.fixPlatformPaths();

        CardTerminal myCardReader = null;

        TerminalFactory f = TerminalFactory.getDefault();
//        CardTerminal r = f.terminals().getTerminal("ACR122U");
//        r = LoggingCardTerminal.getInstance(r);
        // Now use javax.smartcardio as you normally do

        try {
            List<CardTerminal> cardTerminalList = f.terminals().list();
            for (CardTerminal cardTerminal : cardTerminalList) {
                System.out.println(cardTerminal.getName());
            }
            myCardReader = cardTerminalList.get(0);
            System.out.println(myCardReader.getName());
            System.out.println(myCardReader.isCardPresent() ? "Card is present" : "Card is NOT present");
            myCardReader = LoggingCardTerminal.getInstance(myCardReader, System.out);

            // Protocol -> https://de.wikipedia.org/wiki/Application_Protocol_Data_Unit
            // SCTool.java getBIBO(...)
            // Protokolle können sein: T=0, T=1, *
            Card card = myCardReader.connect("T=1");
            System.out.println("Vor: new BlockingBIBO");
            BlockingBIBO bibo = new BlockingBIBO(CardBIBO.wrap(card));

            System.out.println("Nach new BlockingBIBO");
            byte[][] apdus = {};
            List<byte[]> toCard = new ArrayList<>(Arrays.asList(apdus));
            System.out.println("Vor: for (byte[] s : toCard)");
            for (byte[] s : toCard) {
                System.out.println("In: for (byte[] s : toCard)");
                ResponseAPDU r = new ResponseAPDU(bibo.transceive(s));
                if (r.getSW() != 0x9000) {
                    System.out.println("Card returned " + String.format("%04X", r.getSW()) + ", exiting!");
                }
                System.out.println(r.getData());
            }

        } catch (CardException e) {
            e.printStackTrace();
        }

    }


}

