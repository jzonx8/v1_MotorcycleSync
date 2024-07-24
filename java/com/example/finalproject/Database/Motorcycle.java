package com.example.finalproject.Database;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.PropertyName;

@IgnoreExtraProperties
public class Motorcycle implements Parcelable {

    private String id;
    @PropertyName("Brand")
    private String brand;
    @PropertyName("Model")
    private String model;
    @PropertyName("Year")
    private String year;
    @PropertyName("Category")
    private String category;
    @PropertyName("Rating")
    private String rating;
    @PropertyName("Displacement")
    private String displacement;
    @PropertyName("Power")
    private String power;
    @PropertyName("Torque")
    private String torque;
    @PropertyName("EngineCylinder")
    private String engineCylinder;
    @PropertyName("EngineStroke")
    private String engineStroke;
    @PropertyName("Gearbox")
    private String gearbox;
    @PropertyName("Bore")
    private String bore;
    @PropertyName("Stroke")
    private String stroke;
    @PropertyName("FuelCapacity")
    private String fuelCapacity;
    @PropertyName("FuelSystem")
    private String fuelSystem;
    @PropertyName("FuelControl")
    private String fuelControl;
    @PropertyName("CoolingSystem")
    private String coolingSystem;
    @PropertyName("TransmissionType")
    private String transmissionType;
    @PropertyName("DryWeight")
    private String dryWeight;
    @PropertyName("Wheelbase")
    private String wheelbase;
    @PropertyName("SeatHeight")
    private String seatHeight;
    @PropertyName("FrontBrakes")
    private String frontBrakes;
    @PropertyName("RearBrakes")
    private String rearBrakes;
    @PropertyName("FrontTire")
    private String frontTire;
    @PropertyName("Price")
    private String price;
    @PropertyName("RearTire")
    private String rearTire;
    @PropertyName("FrontSuspension")
    private String frontSuspension;
    @PropertyName("RearSuspension")
    private String rearSuspension;
    @PropertyName("ColorOptions")
    private String colorOptions;
    @PropertyName("MinPrice")
    private String minPrice;
    @PropertyName("MaxPrice")
    private String maxPrice;
    private String weight; // Add weight field
    private String height; // Add height field
    @PropertyName("imageUrl")
    private String imageUrl;
    @PropertyName("imageUrl2")
    private String imageUrl2;


    public String getImageUrl() {
        return imageUrl;
    }
    public String getImageUrl2() { return imageUrl2; }


    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public void setImageUrl2(String imageUrl2) {
        this.imageUrl2 = imageUrl2;
    }

    // Default constructor required for calls to DataSnapshot.getValue(Motorcycle.class)
    public Motorcycle() {
    }

    public Motorcycle(String inputBrand, String inputYear, String inputMinPrice, String inputMaxPrice, String inputCategory, String inputFuelSystem, String inputPower, String inputSeatHeight, String inputDryWeight) {
        this.brand = inputBrand;
        this.year = inputYear;
        this.minPrice = inputMinPrice;
        this.maxPrice = inputMaxPrice;
        this.category = inputCategory;
        this.fuelSystem = inputFuelSystem;
        this.power = inputPower;
        this.seatHeight = inputSeatHeight;
        this.dryWeight = inputDryWeight;
    }

    protected Motorcycle(Parcel in) {
        price = in.readString();
        brand = in.readString();
        model = in.readString();
        year = in.readString();
        category = in.readString();
        rating = in.readString();
        displacement = in.readString();
        power = in.readString();
        torque = in.readString();
        engineCylinder = in.readString();
        engineStroke = in.readString();
        gearbox = in.readString();
        bore = in.readString();
        stroke = in.readString();
        fuelCapacity = in.readString();
        fuelSystem = in.readString();
        fuelControl = in.readString();
        coolingSystem = in.readString();
        transmissionType = in.readString();
        dryWeight = in.readString();
        wheelbase = in.readString();
        seatHeight = in.readString();
        frontBrakes = in.readString();
        rearBrakes = in.readString();
        frontTire = in.readString();
        rearTire = in.readString();
        frontSuspension = in.readString();
        rearSuspension = in.readString();
        colorOptions = in.readString();
        minPrice = in.readString();
        maxPrice = in.readString();
    }

    public static final Creator<Motorcycle> CREATOR = new Creator<Motorcycle>() {
        @Override
        public Motorcycle createFromParcel(Parcel in) {
            return new Motorcycle(in);
        }

        @Override
        public Motorcycle[] newArray(int size) {
            return new Motorcycle[size];
        }
    };

    public String getId() { return id; }
    public String getBrand() { return brand; }
    public String getModel() { return model; }
    public String getYear() { return year; }
    public String getCategory() { return category; }
    public String getRating() { return rating; }
    public String getDisplacement() { return displacement; }
    public String getPower() { return power; }
    public String getTorque() { return torque; }
    public String getEngineCylinder() { return engineCylinder; }
    public String getEngineStroke() { return engineStroke; }
    public String getGearbox() { return gearbox; }
    public String getBore() { return bore; }
    public String getStroke() { return stroke; }
    public String getFuelCapacity() { return fuelCapacity; }
    public String getFuelSystem() { return fuelSystem; }
    public String getFuelControl() { return fuelControl; }
    public String getCoolingSystem() { return coolingSystem; }
    public String getTransmissionType() { return transmissionType; }
    public String getDryWeight() { return dryWeight; }
    public String getWheelbase() { return wheelbase; }
    public String getSeatHeight() { return seatHeight; }
    public String getFrontBrakes() { return frontBrakes; }
    public String getPrice() { return price; }
    public String getRearBrakes() { return rearBrakes; }
    public String getFrontTire() { return frontTire; }
    public String getRearTire() { return rearTire; }
    public String getFrontSuspension() { return frontSuspension; }
    public String getRearSuspension() { return rearSuspension; }
    public String getColorOptions() { return colorOptions; }
    public String getMinPrice() { return minPrice; }
    public String getMaxPrice() { return maxPrice; }
    public String getWeight() { return weight; }

    public String getHeight() { return height; }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(brand);
        dest.writeString(model);
        dest.writeString(year);
        dest.writeString(category);
        dest.writeString(rating);
        dest.writeString(displacement);
        dest.writeString(power);
        dest.writeString(torque);
        dest.writeString(engineCylinder);
        dest.writeString(engineStroke);
        dest.writeString(gearbox);
        dest.writeString(bore);
        dest.writeString(stroke);
        dest.writeString(fuelCapacity);
        dest.writeString(fuelSystem);
        dest.writeString(fuelControl);
        dest.writeString(coolingSystem);
        dest.writeString(transmissionType);
        dest.writeString(dryWeight);
        dest.writeString(wheelbase);
        dest.writeString(seatHeight);
        dest.writeString(frontBrakes);
        dest.writeString(rearBrakes);
        dest.writeString(frontTire);
        dest.writeString(rearTire);
        dest.writeString(frontSuspension);
        dest.writeString(rearSuspension);
        dest.writeString(colorOptions);
        dest.writeString(minPrice);
        dest.writeString(maxPrice);
    }

}
