package com.ldcvia.domino.misc;

import lotus.domino.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class NotesBridge {

  // User / error messages
  protected static final String ERR_INIT = "error initialising NotesBridge: ";

  // Attribute names
  protected static final String FLD_FRM = "Form";

  // Private members club
  private Session session;
  private Database db;
  private boolean initialised;
  private String svr;
  private String path;

  // Constructor
  public NotesBridge(String svr, String path) {
    this.svr = svr;
    this.path = path;

    if (path == null || path.length() == 0) {
      throw new IllegalArgumentException(ERR_INIT + "path must be specified");
    }

    init();
  }

  /**
   * Shut down the NotesBridge instance (just closes the Notes thread at the moment).
   */
  public void close() {
    // TODO any other clean-up code here: recycling?
    NotesThread.stermThread();
  }

  public Database getDatabase() {
    return this.db;
  }

  /**
   * NotesBridge initialisation: attempts to open specified Notes database and establish a handle to
   * it for subsequent code.
   */
  private void init() {
    try {
      NotesThread.sinitThread();
      session = NotesFactory.createSession();
      db = session.getDatabase(svr, path);
      if (!db.isOpen()) {
        throw new RuntimeException(ERR_INIT + "cannot open specified database");
      } else {
        initialised = true;
      }
    } catch (NotesException e) {
      throw new RuntimeException(ERR_INIT + e.text);
    }
  }

  public Document createDocument(String formName) throws NotesException {
    Document tmp = null;

    if (initialised) {
      tmp = db.createDocument();
      tmp.replaceItemValue(FLD_FRM, formName);
    }

    return tmp;
  }

  public Session getSession() {
    return this.session;
  }

  public String getUserName() {
    String result = null;

    try {
      Name nn = session.createName(session.getEffectiveUserName());
      result = nn.getAbbreviated();
    } catch (NotesException meh) {
    }

    return result;
  }

  /**
   * Given a Notes / Domino database, derives a 'form list' from the supplied field. Typically
   * the supplied field would be the 'Form' field, and collections can be derived in the expected
   * way. However, in other instances we derive a 'form' attribute from a different field (e.g.
   * the h_PageType field in IBM Quickr).
   *
   * @param db              Domino database to check
   * @param collectionField name of the field to examine, expressed as a String
   * @return collection of unique 'form' names
   */
  public List<String> getAllFormsList(Database db, String collectionField) throws NotesException {
    List<String> results;
    Set<String> tmp = new HashSet<String>();

    DocumentCollection coll = db.search(collectionField + " <> \"\"", null, 0);

    Document doc = coll.getFirstDocument();
    while (doc != null) {
      tmp.add(doc.getItemValueString(collectionField));
      doc = coll.getNextDocument(doc);
    }

    if (tmp.size() > 0) {
      results = new ArrayList<String>(tmp);
      Collections.sort(results);
    } else {
      results = new ArrayList<String>();
    }

    return results;
  }

}
