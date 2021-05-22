package dummy;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;

@Component
@XmlRootElement(name = "coffee")
public class Coffee {

	private int coffee_id;
	private String brand;
    private String origin;
    private String characteristics;

    public Coffee() {
    }

    public Coffee(int coffee_id, String brand, String origin, String characteristics) {
		super();
		this.coffee_id = coffee_id;
		this.brand = brand;
		this.origin = origin;
		this.characteristics = characteristics;
	}

    @XmlElement(name = "coffee_id")
	public int getCoffee_id() {
		return coffee_id;
	}

	public void setCoffee_id(int coffee_id) {
		this.coffee_id = coffee_id;
	}

	@XmlElement(name = "brand")
	public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    @XmlElement(name = "origin")
    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    @XmlElement(name = "characteristics")
    public String getCharacteristics() {
        return characteristics;
    }

    public void setCharacteristics(String characteristics) {
        this.characteristics = characteristics;
    }

	@Override
	public String toString() {
		return "Coffee [coffee_id=" + coffee_id + ", brand=" + brand + ", origin=" + origin + ", characteristics="
				+ characteristics + "]";
	}
}
