package com.final_project2.entity;

import java.io.Serializable;
import java.util.Objects;

public class Order implements Serializable {
    int id;
    Car car;
    User user;
    String seriesAndNumberOfThePassport;
    boolean withADriver;
    String dateStart;
    String dateEnd;
    double cost;
    boolean isDamaged;
    double damageCost;
    Status status;
    String managersComment;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getSeriesAndNumberOfThePassport() {
        return seriesAndNumberOfThePassport;
    }

    public void setSeriesAndNumberOfThePassport(String seriesAndNumberOfThePassport) {
        this.seriesAndNumberOfThePassport = seriesAndNumberOfThePassport;
    }

    public boolean isWithADriver() {
        return withADriver;
    }

    public void setWithADriver(boolean withADriver) {
        this.withADriver = withADriver;
    }

    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public boolean isDamaged() {
        return isDamaged;
    }

    public void setDamaged(boolean damaged) {
        isDamaged = damaged;
    }

    public double getDamageCost() {
        return damageCost;
    }

    public void setDamageCost(double damageCost) {
        this.damageCost = damageCost;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getManagersComment() {
        return managersComment;
    }

    public void setManagersComment(String managersComment) {
        this.managersComment = managersComment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id == order.id && withADriver == order.withADriver && Double.compare(order.cost, cost) == 0 && isDamaged == order.isDamaged && Double.compare(order.damageCost, damageCost) == 0 && Objects.equals(car, order.car) && Objects.equals(user, order.user) && Objects.equals(seriesAndNumberOfThePassport, order.seriesAndNumberOfThePassport) && Objects.equals(dateStart, order.dateStart) && Objects.equals(dateEnd, order.dateEnd) && status == order.status && Objects.equals(managersComment, order.managersComment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, car, user, seriesAndNumberOfThePassport, withADriver, dateStart, dateEnd, cost, isDamaged, damageCost, status, managersComment);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", car=" + car +
                ", user=" + user +
                ", seriesAndNumberOfThePassport='" + seriesAndNumberOfThePassport + '\'' +
                ", withADriver=" + withADriver +
                ", dateStart='" + dateStart + '\'' +
                ", dateEnd='" + dateEnd + '\'' +
                ", cost=" + cost +
                ", isDamaged=" + isDamaged +
                ", damageCost=" + damageCost +
                ", status=" + status +
                ", managersComment='" + managersComment + '\'' +
                '}';
    }

    public enum Status{
    AWAITING_PAYMENT, PAID, DENIED, PAYMENT_FOR_DAMAGE, ORDER_COMPLETE
}
}
