package xyz.eala.bitcoinconverter.currencies;

import java.text.DecimalFormat;

public class CurrencyType {

	private String name;
	private double unit = 1.0;
	private String convertedValue;
	private DecimalFormat numberFormat = new DecimalFormat("#.00");
	
	public CurrencyType() {
		super();
	}

	public CurrencyType(String name, double unit, String convertedValue) {
		super();
		this.name = name;
		this.unit = unit;
		this.convertedValue = numberFormat.format(new Double(convertedValue) * unit);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getUnit() {
		return unit;
	}

	public void setUnit(double unit) {
		this.unit = unit;
	}

	public String getConvertedValue() {
		return convertedValue;
	}

	public void setConvertedValue(String convertedValue) {
		this.convertedValue = convertedValue;
	}	

    @Override
    public String toString(){
        return "" + unit + " " + name + " : " + convertedValue + " Bitcoin";
    }
}
