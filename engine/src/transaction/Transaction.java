package transaction;

import currency.Currency;
import engine.collection.Periodable;
import order.Order;
import stock.Stock;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

/**
 * This class represents a {@code Transaction} of {@link stock.Stock}s.
 * <p>A {@code Transaction} occurs when two {@link Order}(s) happen to
 * match successfully:</p>
 *
 * <p>A '{@code Buy}' {@link Order} and a '{@code Sell}' {@link
 * Order}.</p>
 *
 * <p>annotated with JAXB, to marshal / unmarshal a <tt>.xml</tt> file.</p>
 *
 * @version 1.0
 */
@XmlRootElement(name = "rse-transaction")
@XmlAccessorType(XmlAccessType.FIELD) public class Transaction
        implements Comparable<Transaction>, Periodable {

    /**
     * The {@code TimeStamp} of the {@code Transaction}'s execution.
     */
    private String timeStamp;

    /**
     * The quantity of the {@link stock.Stock}s sold in the {@code
     * Transaction}.
     */
    private long quantity;

    /**
     * Price of each sold {@link stock.Stock}.
     */
    private long price;

    public Transaction(Stock stock, String timeStamp, long quantity,
                       long price) {
        this.timeStamp = timeStamp;
        this.quantity = quantity;
        this.price = price;

        // forces update of the Stock's price:
        stock.setPrice(price);
    }

    /**
     * Must have a Default Constructor for {@code JAXBContext} <tt>.xml</tt>
     * load and save.
     */
    public Transaction() {}

    @Override public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }
        Transaction that = (Transaction) o;
        return quantity == that.quantity && price == that.price &&
                Objects.equals(timeStamp, that.timeStamp);
    }

    @Override public int hashCode() {
        return Objects.hash(timeStamp, quantity, price);
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    @Override public String toString() {
        return "Transaction{" + "timeStamp='" + timeStamp + '\'' +
                ", quantity=" + quantity + ", price=" +
                Currency.numberFormat.format(price) + ", transactionPeriod=" +
                Currency.numberFormat.format(getPeriod()) + '}';
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    /**
     * This method compares by the {@link #timeStamp} values of the {@code
     * Transaction}s.
     *
     * @param o the <i> other </i> {@code Transaction} to be compared to.
     * @return an {@code int} indicates if {@code this} {@code Transaction} is
     * less, equals, or greater than the <i> other </i> {@code Transaction}
     * given.
     */
    @Override public int compareTo(Transaction o) {

        // compare by 'timeStamp': the first is the most recent.
        int result = o.getTimeStamp().compareTo(this.getTimeStamp());
        if (result == 0) {

            // if 'timeStamps' are equal, insert the most recent on top.
            return -1;
        } else { return result; }
    }

    /**
     * The total <i>price worth</i> of this {@code Transaction} is: the
     * <tt>{@link #quantity}</tt> of the {@link stock.Stock}s times the
     * {@link #price} of each {@link stock.Stock} in the {@code Transaction}.
     *
     * @return {@code Transaction-Worth} <i>price</i> = <tt>Period</tt>.
     */
    @Override public long getPeriod() {
        return quantity * price;
    }

}
