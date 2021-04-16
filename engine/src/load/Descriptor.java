package load;

import stock.Stocks;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * This is the <i>SuperClass</i> of the schema <tt>.xml</tt> file - annotated
 * with JAXB, to marshal / unmarshal a <tt>.xml</tt> file.
 *
 * @version 1.1
 */
@XmlRootElement(name = "rizpa-stock-exchange-descriptor")
@XmlAccessorType(XmlAccessType.FIELD) public class Descriptor {

    /**
     * The {@code Stocks} of the <tt>.xml</tt> file.
     */
    @XmlElement(name = "rse-stocks") private Stocks stocks;

    public Stocks getStocks() {
        return stocks;
    }

    public void setStocks(Stocks stocks) {
        this.stocks = stocks;
    }

    @Override public String toString() {
        return "Descriptor{" + "stocks=\n" + stocks + '}';
    }

}
