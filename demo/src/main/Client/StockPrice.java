public abstract class StockPrice {
    private String symbol;
    private double price;
    private double change;
  
    public StockPrice() {
    }
  
    public StockPrice(String symbol, double price, double change) {
      this.symbol = symbol;
      this.price = price;
      this.change = change;
    }
  
    public String getSymbol() {
      return this.symbol;
    }
  
    public double getPrice() {
      return this.price;
    }
  
    public double getChange() {
      return this.change;
    }
  
    public double getChangePercent() {
      return 10.0 * this.change / this.price;
    }
  
    public void setSymbol(String symbol) {
      this.symbol = symbol;
    }
  
    public void setPrice(double price) {
      this.price = price;
    }
  
    public void setChange(double change) {
      this.change = change;
    }
    private void updateTable(StockPrice price) {
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
            
      }
      public double getChangePercents() {
        return 10.0 * this.change / this.price;
      }
}
