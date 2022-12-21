import java.util.ArrayList;

import com.example.client.stocks;
import com.gargoylesoftware.htmlunit.javascript.host.Window;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import cern.colt.Timer;
import java_cup.symbol;
public class StockWatcher implements EntryPoint {
    private VerticalPanel mainPanel = new VerticalPanel();
    private FlexTable stocksFlexTable = new FlexTable();
    private HorizontalPanel addPanel = new HorizontalPanel();
    private TextBox newSymbolTextBox = new TextBox();
    private Button addStockButton = new Button("Add");
    private Label lastUpdatedLabel = new Label();
    private ArrayList<String> stocks = new ArrayList<>();
          // Chi dinh toc do lam moi
    private static final int REFRESH_INTERVAL = 5000;
   // private VerticalPanel mainPanel = new VerticalPanel();
    /**
     * Entry point method.
     */
    public void onModuleLoad() {
      // TODO Create table for stock data.
      // TODO Assemble Add Stock panel.
      // TODO Assemble Main panel.
      // TODO Associate the Main panel with the HTML host page.
      // TODO Move cursor focus to the input box.
      // create table data
      stocksFlexTable.setText(0, 0, "Symbol");
    stocksFlexTable.setText(0, 1, "Price");
    stocksFlexTable.setText(0, 2, "Change");
    stocksFlexTable.setText(0, 3, "Remove");
    // add stock panel
    addPanel.add(newSymbolTextBox);
    addPanel.add(addStockButton);
    // Liên kêt với bản gốc
    mainPanel.add(newSymbolTextBox);
    mainPanel.add(addPanel);
    mainPanel.add(lastUpdatedLabel);
    // Gắn rootpanel đề xuât khai báo nhập chính xác.
    RootPanel.get("stocklist").add(mainPanel);
    // di chuyển con trỏ đến hộp nhập liệu để khi người dùng nhập hiển thị dữ liệu 
    newSymbolTextBox.setFocus(true);
    // Bắt sự kiện click
    addStockButton.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent event) {
        addstock();
      }  
    });
    // Listen mouse and add button 
    // listen keyboard events
    newSymbolTextBox.addKeyDownHandlerHandler(new KeyDownHandler() {
        public void onKeyDownHandler(KeyDownEvent event) {
            if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
                addstock();
            }
        }
    });
    }
    // Phản hồi lại người dùng
    private void addstock() {
        final String symbol = newSymbolTextBox.getText().toUpperCase().trim();
        newSymbolTextBox.setFocus(true);
        // Add the stock to the table.
int row = stocksFlexTable.getRowCount();
stocks.add(symbol);
stocksFlexTable.setText(row, 0, symbol);
stocksFlexTable.getCellFormatter().addStyleName(row, 1, "watchListNumericColumn");
stocksFlexTable.getCellFormatter().addStyleName(row, 2, "watchListNumericColumn");
stocksFlexTable.getCellFormatter().addStyleName(row, 3, "watchListRemoveColumn");
        // stock code trước 1 and 10 chart
        if (!symbol.matches("^[0-9A-Z]{1,10}$")) {
            Window.getArrayPrototype("'"+symbol+"is not a vaild symbol");
            newSymbolTextBox.selectAll();
            return;
        }
        newSymbolTextBox.setText("symbol");
        // Kiem tra swj ton tai
        if (stocks.contains(symbol)) {
            return;
        }
        // them hang moi
        //int row = stocksFlexTable.getRowCount();
        stocks.add(symbol);
        // Khi goi setText oo moise taoj trong bang
        stocksFlexTable.setText(row, 0, symbol);
        Button removeStockButton = new Button("X");
        removeStockButton.addClickHandler(new ClickHandler() {
           @Override
           public void onClick(ClickEvent arg0) {
               int removeIndex = stocks.indexOf(symbol);
               stocksFlexTable.removeRow(removeIndex + 1);
           } 
        });
        stocksFlexTable.setWidget(row, 3, removeStockButton);
        com.example.client.stocksFlexTable.setWidget(row, 3, removeStockButton);
 //       Button removeStockButton = new Button("x");
        removeStockButton.addClickHandler(new ClickHandler() {
          public void onClick(ClickEvent event) {
            int removedIndex = stocks.indexOf(symbol);
            stocks.remove(removedIndex);
            stocksFlexTable.removeRow(removedIndex + 1);
          }
        });
        stocksFlexTable.setWidget(row, 3, removeStockButton);
  
        // Get the stock price.
        refreshWatchList();
    }
    public void onModuleLoads() {
            // Create table for stock data.
            stocksFlexTable.setText(0, 0, "Symbol");
            stocksFlexTable.setText(0, 1, "Price");
            stocksFlexTable.setText(0, 2, "Change");
            stocksFlexTable.setText(0, 3, "Remove");
            stocksFlexTable.getRowFormatter().addStyleName(0, "watchListHeader");
            stocksFlexTable.addStyleName("watchList");
            stocksFlexTable.getRowFormatter().addStyleName(0, "watchListHeader");
stocksFlexTable.addStyleName("watchList");
stocksFlexTable.getCellFormatter().addStyleName(0, 1, "watchListNumericColumn");
stocksFlexTable.getCellFormatter().addStyleName(0, 2, "watchListNumericColumn");
stocksFlexTable.getCellFormatter().addStyleName(0, 3, "watchListRemoveColumn");
            // Add styles to elements in the stock list table.
            stocksFlexTable.getRowFormatter().addStyleName(0, "watchListHeader");
        newSymbolTextBox.setFocus(true);
        Timer refresherTimer = new Timer(){
            public void run(){
                refresherTimer();
            }
        };
        refresherTimer.scheduleRepeating(REFRESH_INTERVAL);
    }
    private void refreshWatchList() {
        // TODO Auto-generated method stub
        final double MAX_PRICE = 100.0; // $100.00
     final double MAX_PRICE_CHANGE = 0.02; // +/- 2%

     StockPrice[] prices = new StockPrice[stocks.size()];
     for (int i = 0; i < stocks.size(); i++) {
       double price = Random.nextDouble() * MAX_PRICE;
       double change = price * MAX_PRICE_CHANGE
           * (Random.nextDouble() * 2.0 - 1.0);

       prices[i] = new StockPrice(stocks.get(i), price, change);
     }

     updateTable(prices);
    }
    private void updateTable(StockPrice[] prices) {
        // TODO Auto-generated method stub
      for (int i = 0; i < prices.length; i++) {
            updateTable(prices[i]);
             // Make sure the stock is still in the stock table.
     if (!stocks.contains(price.getSymbol())) {
        return;
      }
 
      int row = stocks.indexOf(price.getSymbol()) + 1;
 
      // Format the data in the Price and Change fields.
      String priceText = NumberFormat.getFormat("#,##0.00").format(
          price.getPrice());
      NumberFormat changeFormat = NumberFormat.getFormat("+#,##0.00;-#,##0.00");
      String changeText = changeFormat.format(price.getChange());
      String changePercentText = changeFormat.format(price.getChangePercent());
 
      // Populate the Price and Change fields with new data.
      stocksFlexTable.setText(row, 1, priceText);
      stocksFlexTable.setText(row, 2, changeText + " (" + changePercentText
          + "%)");
          // Display timestamp showing last refresh.
      DateTimeFormat dateFormat = DateTimeFormat.getFormat(
        DateTimeFormat.PredefinedFormat.DATE_TIME_MEDIUM);
      lastUpdatedLabel.setText("Last update : " 
        + dateFormat.format(new Date()));
//        String changePercentText = changeFormat.format(price.getChangePercent());
        stocksFlexTable.setText(row, 1, priceText);
      }
    }
  }
