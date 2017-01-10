package com.ldcvia.domino.misc;

import lotus.domino.DateTime;
import lotus.domino.Document;
import lotus.domino.NotesException;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Bridging class for running Domino Java code in a tool designed to sit outside of the
 * Notes / Domino run-time. Nothing special.
 */
public class NotesMain {

  public static void main(String[] args) {
    NotesBridge bridge = new NotesBridge("", "dev/play.nsf");

    try {
      Document doc = bridge.createDocument("Test");
      doc.replaceItemValue("Subject", "Test subject from bridge");
      doc.replaceItemValue("CreatedBy", bridge.getUserName());
      Calendar cal = new GregorianCalendar();
      DateTime dt = bridge.getSession().createDateTime(cal);
      doc.replaceItemValue("CreatedWhen", dt);
      doc.save(false, false, false);
    } catch (NotesException e) {
      System.err.println("Notes error: " + e.text);
    } finally {
      if (bridge != null) bridge.close();
    }
  }

}
