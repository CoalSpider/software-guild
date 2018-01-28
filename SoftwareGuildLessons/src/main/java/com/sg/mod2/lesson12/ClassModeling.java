/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.mod2.lesson12;

/**
 *
 * @author Ben Norman
 */
public class ClassModeling {

}

class HouseGPS {

    private final float latituide;
    private final float longitude;
    private final String address;

    public HouseGPS(float latituide, float longitude, String address) {
        this.latituide = latituide;
        this.longitude = longitude;
        this.address = address;
    }

    public float getLatituide() {
        return latituide;
    }

    public float getLongitude() {
        return longitude;
    }

    public String getAddress() {
        return address;
    }
}

class House3DDesign {

    private final String objFile;

    public House3DDesign(String objFile) {
        this.objFile = objFile;
    }

    void render() {

    }

    void rotate() {

    }

    void translate() {

    }

    void scale() {

    }
}

class AirplaneFlightTraffic {

    private String destination;
    private String origin;
    private float departureTime;
    private float arrivalTimeEstimate;
    private boolean inAir;
    private Object gpsLoc;

    public AirplaneFlightTraffic(String destination, String origin, Object gpsLoc) {
        this.destination = destination;
        this.origin = origin;
        this.gpsLoc = gpsLoc;

        // derive other 
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public float getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(float departureTime) {
        this.departureTime = departureTime;
    }

    public float getArrivalTimeEstimate() {
        return arrivalTimeEstimate;
    }

    public void setArrivalTimeEstimate(float arrivalTimeEstimate) {
        this.arrivalTimeEstimate = arrivalTimeEstimate;
    }

    public boolean isInAir() {
        return inAir;
    }

    public void setInAir(boolean inAir) {
        this.inAir = inAir;
    }

}

class PlaneFlightSim {

    private Object pitchYawRollQuaternion; // orientation
    private Object veloctityVec3; // velocity
    private Object positionVec3; // position
    private Object[] routeNodes; // used to determine if player flew right way in level

    public PlaneFlightSim(Object[] routeNodes) {
        this.routeNodes = routeNodes;
        this.veloctityVec3 = null; // default no velocity
        this.pitchYawRollQuaternion = null; // no rotation like on runway
        this.positionVec3 = null; // default position 0,0,0
    }

    public Object getPitchYawRollQuaternion() {
        return pitchYawRollQuaternion;
    }

    public void setPitchYawRollQuaternion(Object pitchYawRollQuaternion) {
        this.pitchYawRollQuaternion = pitchYawRollQuaternion;
    }

    public Object getVeloctityVec3() {
        return veloctityVec3;
    }

    public void setVeloctityVec3(Object veloctityVec3) {
        this.veloctityVec3 = veloctityVec3;
    }

    public Object getPositionVec3() {
        return positionVec3;
    }

    public void setPositionVec3(Object positionVec3) {
        this.positionVec3 = positionVec3;
    }

    public Object[] getRouteNodes() {
        return routeNodes;
    }

    // dynamic route nodes
    public void setRouteNodes(Object[] routeNodes) {
        this.routeNodes = routeNodes;
    }
}
class CarInventorySystem{
    private final int uniqueID;
    private final int count;

    public CarInventorySystem(int uniqueID, int count) {
        this.uniqueID = uniqueID;
        this.count = count;
    }

    public int getUniqueID() {
        return uniqueID;
    }

    public int getCount() {
        return count;
    }
}

class CarVideoGame{
    // @see plane class
    // might add hp
}

class IceCreamManufacture{
    // control systems are usually close to the metal
    private final byte[] ingredients; // byte array for ingredients
    private final byte[] percentages; // percent mix for each ingredients
    private final byte[] order; // order of ingredients

    public IceCreamManufacture(byte[] ingredients, byte[] percentages, byte[] order) {
        this.ingredients = ingredients;
        this.percentages = percentages;
        this.order = order;
    }

    public byte[] getIngredients() {
        return ingredients;
    }

    public byte[] getPercentages() {
        return percentages;
    }

    public byte[] getOrder() {
        return order;
    }
    
}