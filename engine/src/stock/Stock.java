package stock;

import currency.Currency;
import message.Message;
import message.builder.out.BuildOutput_StockDataBase;
import stock.database.StockDataBase;
import transaction.Transaction;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

/**
 * A Stock annotated with JAXB, to marshal / unmarshal a <tt>.xml</tt> file.
 *
 * @version 1.2
 */
@XmlAccessorType(XmlAccessType.FIELD) @XmlRootElement(name = "rse-stock")
public class Stock {

    @XmlElement(name = "rse-symbol") private String symbol;
    @XmlElement(name = "rse-company-name") private String companyName;

    /**
     * <i>price</i> is updated after each successful transaction, inside the
     * {@link Transaction#Transaction(Stock, String, long, long)} {@code
     * Constructor}.
     */
    @XmlElement(name = "rse-price") private long price;

    /**
     * A data-base of all the orders of the stock.
     * <p>Includes:</p>
     * <ul>
     *     <li>{@code awaitingBuyOrders}.</li>
     *     <li>{@code awaitingSellOrders}.</li>
     *     <li>{@code successfullyFinishedTransactions}.</li>
     * </ul>
     */
    @XmlElement(name = "rse-data-base") private StockDataBase dataBase =
            new StockDataBase();

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol() {
        this.symbol = symbol;
    }

    @Override public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }
        Stock stock = (Stock) o;
        return price == stock.price && Objects.equals(symbol, stock.symbol) &&
                Objects.equals(companyName, stock.companyName) &&
                Objects.equals(dataBase, stock.dataBase);
    }

    @Override public int hashCode() {
        return Objects.hash(symbol, companyName, price, dataBase);
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName() {
        this.companyName = companyName;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public StockDataBase getDataBase() {
        return dataBase;
    }

    public void setDataBase(StockDataBase dataBase) {
        this.dataBase = dataBase;
    }

    @Override public String toString() {
        return "Stock{" + "symbol='" + symbol + '\'' + ", companyName='" +
                companyName + '\'' + ", price='" +
                Currency.numberFormat.format(price) + '\'' +
                ", numOfTotalTransactions=" +
                dataBase.getSuccessfullyFinishedTransactions().getCollection()
                        .size() + ", [Total Transactions Period = " +
                Currency.numberFormat.format(dataBase.getTotalPeriod(
                        dataBase.getSuccessfullyFinishedTransactions()
                                .getCollection())) + "]}";
    }

    /**
     * Reveal details of all the transactions in this {@code Stock}, sorted by
     * {@link timestamp.TimeStamp}:
     * <p>Old is presented below, and New is presented above.</p>
     * <p>For each transaction show:</p>
     * <ul>
     *     <li>{@link timestamp.TimeStamp}.</li>
     *     <li>{@code quantity} of sold stocks.</li>
     *     <li>{@code price} of selling.</li>
     *     <li>{@code transaction's period}.</li>
     * </ul>
     *
     * <blockquote>Note: if there were'nt any transactions with {@code this}
     * {@link Stock}, return a message.</blockquote>
     *
     * @param addTitleTabs   add here the amount of 'tab's to insert before the
     *                       Title.
     * @param addContentTabs add here the amount of 'tab's to insert before the
     *                       Content.
     * @return {@link String} of all the {@link transaction.Transaction}s of
     * this {@code Stock}.
     */
    public String getTransactionsToString(String addTitleTabs,
                                          String addContentTabs) {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(addTitleTabs).append(" - ")
                .append("Transactions Made: ");

        stringBuilder.append("[Total Transactions Period = ")
                .append(Currency.numberFormat.format(dataBase.getTotalPeriod(
                        dataBase.getSuccessfullyFinishedTransactions()
                                .getCollection()))).append("]:");
        stringBuilder.append("\n");

        if (dataBase.getSuccessfullyFinishedTransactions().getCollection()
                .size() == 0) {
            stringBuilder.append(addContentTabs)
                    .append(Message.Out.StockDataBase.printEmpty(
                            BuildOutput_StockDataBase.TypeOfCollection.SUCCESSFULLY_FINISHED_TRANSACTIONS));

        } else {
            stringBuilder.append(dataBase.getSuccessfullyFinishedTransactions()
                    .toString(addContentTabs));
        }
        return stringBuilder.toString();
    }

    /**
     * Reveal details of all the orders in this {@code Stock} sorted by {@link
     * timestamp.TimeStamp}:
     * <p>Old is presented below, and New is presented above.</p>
     *
     * @param addTitleTabs   add here the amount of 'tab's to insert before the
     *                       Title.
     * @param addContentTabs add here the amount of 'tab's to insert before the
     *                       Content.
     * @return {@link String} of the presented {@link java.util.Collection}.
     */
    public String getAwaitingBuyOrdersToString(String addTitleTabs,
                                               String addContentTabs) {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(addTitleTabs).append(" - ")
                .append("Awaiting 'Buy' Orders: ");

        stringBuilder.append("[Total 'Buy' Orders Period = ")
                .append(Currency.numberFormat.format(dataBase.getTotalPeriod(
                        dataBase.getAwaitingBuyOrders().getCollection())))
                .append("]:");
        stringBuilder.append("\n");

        if (dataBase.getAwaitingBuyOrders().getCollection().size() == 0) {
            stringBuilder.append(addContentTabs)
                    .append(Message.Out.StockDataBase.printEmpty(
                            BuildOutput_StockDataBase.TypeOfCollection.AWAITING_BUY_ORDERS));

        } else {
            stringBuilder.append(dataBase.getAwaitingBuyOrders()
                    .toString(addContentTabs));
        }
        return stringBuilder.toString();
    }

    /**
     * Reveal details of all the orders in this {@code Stock} sorted by {@link
     * timestamp.TimeStamp}:
     * <p>Old is presented below, and New is presented above.</p>
     *
     * @param addTitleTabs   add here the amount of 'tab's to insert before the
     *                       Title.
     * @param addContentTabs add here the amount of 'tab's to insert before the
     *                       Content.
     * @return {@link String} of the presented {@link java.util.Collection}.
     */
    public String getAwaitingSellOrdersToString(String addTitleTabs,
                                                String addContentTabs) {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(addTitleTabs).append(" - ")
                .append("Awaiting 'Sell' Orders: ");

        stringBuilder.append("[Total 'Sell' Orders Period = ")
                .append(Currency.numberFormat.format(dataBase.getTotalPeriod(
                        dataBase.getAwaitingSellOrders().getCollection())))
                .append("]:");
        stringBuilder.append("\n");

        if (dataBase.getAwaitingSellOrders().getCollection().size() == 0) {
            stringBuilder.append(addContentTabs)
                    .append(Message.Out.StockDataBase.printEmpty(
                            BuildOutput_StockDataBase.TypeOfCollection.AWAITING_SELL_ORDERS));

        } else {
            stringBuilder.append(dataBase.getAwaitingSellOrders()
                    .toString(addContentTabs));
        }
        return stringBuilder.toString();
    }

}
