package com.example.client;

import com.example.shared.FieldVerifier;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.thirdparty.guava.common.collect.Table;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class module implements EntryPoint {
  /**
   * The message displayed to the user when the server cannot be reached or
   * returns an error.
   */
  private static final String SERVER_ERROR = "An error occurred while "
      + "attempting to contact the server. Please check your network "
      + "connection and try again.";

  private static final Object addStockButton = null;

  /**
   * Create a remote service proxy to talk to the server-side Greeting service.
   */
  private final GreetingServiceAsync greetingService = GWT.create(GreetingService.class);

  private final Messages messages = GWT.create(Messages.class);

  /**
   * This is the entry point method.
   * @param symbol 
   */
  public void onModuleLoad(Object symbol) {
    final Button sendButton = new Button( messages.sendButton() );
    final TextBox nameField = new TextBox();
   
    nameField.setText( messages.nameField() );
    final Label errorLabel = new Label();
    // Create table for stock data.
  stocksFlexTable.setText(0, 0, "Symbol");
  stocksFlexTable.setText(0, 1, "Price");
  stocksFlexTable.setText(0, 2, "Change");
  stocksFlexTable.setText(0, 3, "Remove");
    stocksFlexTable.setCellPadding(6);
  // Add styles to elements in the stock list table.
  ((stocksFlexTable) stocksFlexTable.getRowFormatter()).addStyleName(0, "watchListHeader");
    // We can add style names to widgets
    sendButton.addStyleName("sendButton");
    // Add styles to elements in the stock list table.
((stocksFlexTable) stocksFlexTable.getRowFormatter()).addStyleName(0, "watchListHeader");
stocksFlexTable.addStyleName("watchList");
// Add styles to elements in the stock list table.
((stocksFlexTable) stocksFlexTable.getRowFormatter()).addStyleName(0, "watchListHeader");
stocksFlexTable.addStyleName("watchList");
((stocksFlexTable) stocksFlexTable.getCellFormatter()).addStyleName(0, 1, "watchListNumericColumn");
((stocksFlexTable) stocksFlexTable.getCellFormatter()).addStyleName(0, 2, "watchListNumericColumn");
    // Add the nameField and sendButton to the RootPanel
    // Use RootPanel.get() to get the entire body element
    RootPanel.get("nameFieldContainer").add(nameField);
    RootPanel.get("sendButtonContainer").add(sendButton);
    RootPanel.get("errorLabelContainer").add(errorLabel);
// Assemble Add Stock panel.
//addPanel.add(newSymbolTextBox);
addPanel.add(addStockButton);
addPanel.addStyleName("addPanel");
 
    // Focus the cursor on the name field when the app loads
    nameField.setFocus(true);
    nameField.selectAll();
    // Add the stock to the table.
int row = stocksFlexTable.getRowCount();
stocks.add(symbol);
stocksFlexTable.setText(row, 0, symbol);
((stocksFlexTable) stocksFlexTable.getCellFormatter()).addStyleName(row, 1, "watchListNumericColumn");
((stocksFlexTable) stocksFlexTable.getCellFormatter()).addStyleName(row, 2, "watchListNumericColumn");
((stocksFlexTable) stocksFlexTable.getCellFormatter()).addStyleName(row, 3, "watchListRemoveColumn");
    // Create the popup dialog box
    final DialogBox dialogBox = new DialogBox();
    dialogBox.setText("Remote Procedure Call");
    dialogBox.setAnimationEnabled(true);
    final Button closeButton = new Button("Close");
    // We can set the id of a widget by accessing its Element
    closeButton.getElement().setId("closeButton");
    final Label textToServerLabel = new Label();
    final HTML serverResponseLabel = new HTML();
    VerticalPanel dialogVPanel = new VerticalPanel();
    dialogVPanel.addStyleName("dialogVPanel");
    dialogVPanel.add(new HTML("<b>Sending name to the server:</b>"));
    dialogVPanel.add(textToServerLabel);
    dialogVPanel.add(new HTML("<br><b>Server replies:</b>"));
    dialogVPanel.add(serverResponseLabel);
    dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
    dialogVPanel.add(closeButton);
    dialogBox.setWidget(dialogVPanel);
Object newSymbolTextBox;
   // Add a handler to close the DialogBox
    closeButton.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent event) {
        dialogBox.hide();
        sendButton.setEnabled(true);
        sendButton.setFocus(true);
      }
    });

    // Create a handler for the sendButton and nameField
    class MyHandler implements ClickHandler, KeyUpHandler {
      protected final String price = null;
      protected final UIObject changeWidget = null;
      protected final String priceText = null;
      protected final String changeText = null;

      /**
       * Fired when the user clicks on the sendButton.
       */
      public void onClick(ClickEvent event) {
        sendNameToServer();
      }

      /**
       * Fired when the user types in the nameField.
       */
      public void onKeyUp(KeyUpEvent event) {
        if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
          sendNameToServer();
        }
      }

      /**
       * Send the name from the nameField to the server and wait for a response.
       */
      private void sendNameToServer() {
        // First, we validate the input.
        errorLabel.setText("");
        String textToServer = nameField.getText();
        if (!FieldVerifier.isValidName(textToServer)) {
          errorLabel.setText("Please enter at least four characters");
          return;
        }

        // Then, we send the input to the server.
        sendButton.setEnabled(false);
        textToServerLabel.setText(textToServer);
        serverResponseLabel.setText("");
        greetingService.greetServer(textToServer, new AsyncCallback<String>() {
          public void onFailure(Throwable caught) {
            // Show the RPC error message to the user
            dialogBox.setText("Remote Procedure Call - Failure");
            serverResponseLabel.addStyleName("serverResponseLabelError");
            serverResponseLabel.setHTML(SERVER_ERROR);
            dialogBox.center();
            closeButton.setFocus(true);
          }

          public void onSuccess(String result) {
            dialogBox.setText("Remote Procedure Call");
            serverResponseLabel.removeStyleName("serverResponseLabelError");
            serverResponseLabel.setHTML(result);
            dialogBox.center();
            closeButton.setFocus(true);
          }
          public void removeStyleName(stocksFlexTable stocksFlexTable){
            // Add styles to elements in the stock list table.
((com.example.client.stocksFlexTable) stocksFlexTable.getRowFormatter()).addStyleName(0, "watchListHeader");
stocksFlexTable.addStyleName("watchList");
((com.example.client.stocksFlexTable) stocksFlexTable.getCellFormatter()).addStyleName(0, 1, "watchListNumericColumn");
((com.example.client.stocksFlexTable) stocksFlexTable.getCellFormatter()).addStyleName(0, 2, "watchListNumericColumn");
((com.example.client.stocksFlexTable) stocksFlexTable.getCellFormatter()).addStyleName(0, 3, "watchListRemoveColumn");
      // Add a button to remove this stock from the table.
  Button removeStockButton = new Button("x");
  removeStockButton.addStyleDependentName("remove");    
}
          public void addStock(){
            // Add the stock to the table.
// int row = stocksFlexTable.getRowCount();
// stocks.add(symbol);
// stocksFlexTable.setText(row, 0, symbol);
// stocksFlexTable.getCellFormatter().addStyleName(row, 1, "watchListNumericColumn");
// stocksFlexTable.getCellFormatter().addStyleName(row, 2, "watchListNumericColumn");
// stocksFlexTable.getCellFormatter().addStyleName(row, 3, "watchListRemoveColumn");
// Add the stock to the table.
int row = stocksFlexTable.getRowCount();
//stocks.add(symbol);
//stocksFlexTable.setText(row, 0, symbol);
stocksFlexTable.setWidget(row, 2, new Label());
((stocksFlexTable) stocksFlexTable.getCellFormatter()).addStyleName(row, 1, "watchListNumericColumn");
((stocksFlexTable) stocksFlexTable.getCellFormatter()).addStyleName(row, 2, "watchListNumericColumn");
((stocksFlexTable) stocksFlexTable.getCellFormatter()).addStyleName(row, 3, "watchListRemoveColumn");          
}
public void updateTable(){
 // stocksFlexTable.setText(3, 1, priceText);
// Populate the Price and Change fields with new data.
stocksFlexTable.setText(3, 1, priceText);
Label changeWidget = (Label)stocksFlexTable.getWidget(3, 2);
changeWidget.setText(changeText + " (" + changeText + "%)");
}
/**
 * 
 */
public void changeWidget(){
  // Change the color of text in the Change field based on its value.
String changeStyleName = "noChange";
// if (price.getChangePercent() <1) {
//   changeStyleName = "negativeChange";
// }
// else if (price.getChangePercent() > 0.1f) {
//   changeStyleName = "positiveChange";
// }

changeWidget.setStyleName(changeStyleName);
}
        });
      }
    }

    // Add a handler to send the name to the server
    MyHandler handler = new MyHandler();
    sendButton.addClickHandler(handler);
    nameField.addKeyUpHandler(handler);
  }

  @Override
  public void onModuleLoad() {
    // TODO Auto-generated method stub
    
  }
}
