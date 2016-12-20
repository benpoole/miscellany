/**
 * From https://ctrlq.org/code/19892-merge-multiple-google-documents Create an array of document IDs
 * and the code will merge said docs, using the first one as a base.
 */

function mergeDocs() {
  var docIDs = ['Blahblahblah-some-doc-id',
            	'another-doc-id-to-process',
            	'a-doc-id-goes-here-as-well',
            	'the-last-doc-id-for-processing'];

  var baseDoc = DocumentApp.openById(docIDs[0]);
  var body = baseDoc.getActiveSection();

  for (var i = 1; i < docIDs.length; ++i) {
    var otherBody = DocumentApp.openById(docIDs[i]).getActiveSection();
	   var totalElements = otherBody.getNumChildren();
	    for(var j = 0; j < totalElements; ++j) {
        var element = otherBody.getChild(j).copy();
        var type = element.getType();
        if(type == DocumentApp.ElementType.PARAGRAPH) {
          body.appendParagraph(element);
  	    } else if(type == DocumentApp.ElementType.TABLE) {
          body.appendTable(element);
  	    } else if(type == DocumentApp.ElementType.LIST_ITEM) {
          body.appendListItem(element);
  	    } else {
          throw new Error("Unknown element type: "+type);
        }
	    }
    }
  }

}
