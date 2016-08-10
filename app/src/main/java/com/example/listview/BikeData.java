package com.example.listview;

import android.util.EventLogTags;

import java.util.Comparator;

/**
 * See builderpattern example project for how to do builders
 * they are essential when constructing complicated objects and
 * with many optional fields
 */
public class BikeData {
    public final String COMPANY;
    public final String MODEL;
    public final Double PRICE;
    public final String DESCRIPTION;
    public final String LOCATION;
    public final String DATE;
    public final String PICTURE;
    public final String LINK;
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        // TODO figure out how to print all bikedata out for dialogs
        return "Company: " + COMPANY +"\n"+"Model: " + MODEL +"\n"
                +"Price: " + PRICE.toString()+ "\n"+ "Location: "+ LOCATION + "\n"
                + "Date Listed: " + DATE + "\n" + "Description: " + DESCRIPTION +
        "\n" + "Link: Link to Website";
    }
    private BikeData(Builder b) {
        this.COMPANY = b.Company;
        this.MODEL = b.Model;
        this.PRICE = b.Price;
        this.DESCRIPTION = b.Description;
        this.LOCATION = b.Location;
        this.DATE = b.Date;
        this.PICTURE = b.Picture;
        this.LINK = b.Link;
    }

    /**
     * @author lynn builder pattern, see page 11 Effective Java UserData mydata
     *         = new
     *         UserData.Builder(first,last).addProject(proj1).addProject(proj2
     *         ).build()
     */
    public static class Builder {
        final String Company;
        final String Model;
        final Double Price;
        String Description;
        String Location;
        String Date;
        String Picture;
        String Link;

        // Model and price required
        Builder(String Company, String Model, Double Price) {
            this.Company = Company;
            this.Model = Model;
            this.Price = Price;
        }

        // the following are setters
        // notice it returns this bulder
        // makes it suitable for chaining
        Builder setDescription(String Description) {
            this.Description = Description;
            return this;
        }

        Builder setLocation(String Location) {
            this.Location = Location;
            return this;
        }

        Builder setDate(String Date) {
            this.Date = Date;
            return this;
        }

        Builder setPicture(String Picture) {
            this.Picture = Picture;
            return this;
        }

        Builder setLink(String Link) {
            this.Link = Link;
            return this;
        }

        // use this to actually construct Bikedata
        // without fear of partial construction
        public BikeData build() {
            return new BikeData(this);
        }
    }

}
class CompanyComparator implements Comparator<BikeData> {
    public int compare(BikeData myData1, BikeData myData2) {
        return (myData1.COMPANY.compareTo(myData2.COMPANY));
    }
}
class LocationComparator implements Comparator<BikeData> {
    public int compare(BikeData myData1, BikeData myData2) {
        return (myData1.LOCATION.compareTo(myData2.LOCATION));
    }
}
class PriceComparator implements Comparator<BikeData> {
    public int compare(BikeData myData1, BikeData myData2) {
        return (myData1.PRICE.compareTo(myData2.PRICE));
    }
}
