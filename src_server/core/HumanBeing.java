package core;

import core.database.DataBaseHolder;
import core.utils.GeneratorID;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.Objects;

import static java.lang.Math.*;

/**
 * @author Volovich Alexey
 * Класс описывающий хранимые элементы коллекции. Полностью соответствует условию лабораторной.
 */

public class HumanBeing implements Comparable<HumanBeing>, Serializable {
    @Serial
    private static final long serialVersionUID = 7666811716926734602L;
    private Long id;
    private String name;
    private Coordinates coordinates;
    private java.time.LocalDateTime creationTime;
    private Boolean realHero;
    private Boolean hasToothpick;
    private float impactSpeed;
    private WeaponType weaponType;
    private Mood mood;
    private Car car;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public Boolean getRealHero() {
        return realHero;
    }

    public Boolean getHasToothpick() {
        return hasToothpick;
    }

    public float getImpactSpeed() {
        return impactSpeed;
    }

    public WeaponType getWeaponType() {
        return weaponType;
    }

    public Mood getMood() {
        return mood;
    }

    public Car getCar() {
        return car;
    }

    public HumanBeing(XMLStreamReader reader) throws XMLStreamException {
        new InnerXMLReader().readObject(reader);
    }

    /**
     * Функция заполняющая авто-генерируемые поля.
     */
    public void generateInfo(DataBaseHolder dataBaseHolder) {
        id = GeneratorID.next(dataBaseHolder);
        creationTime = java.time.LocalDateTime.now();
    }

    public HumanBeing(String name, Coordinates coordinates, Boolean realHero, Boolean hasToothpick, float impactSpeed, WeaponType weaponType, Mood mood, Car car, DataBaseHolder dataBaseHolder) {
        this.name = name;
        this.coordinates = coordinates;
        this.realHero = realHero;
        this.hasToothpick = hasToothpick;
        this.impactSpeed = impactSpeed;
        this.weaponType = weaponType;
        this.mood = mood;
        this.car = car;
    }

    public void update(HumanBeing e)
    {
        this.name = e.name;
        this.coordinates = e.coordinates;
        this.realHero = e.realHero;
        this.hasToothpick = e.hasToothpick;
        this.impactSpeed = e.impactSpeed;
        this.weaponType = e.weaponType;
        this.mood = e.mood;
        this.car = e.car;
    }

    /**
     * Функция сравнения из интерфейса Comparable.
     * @param humanBeing the object to be compared.
     * @return int
     */
    @Override
    public int compareTo(HumanBeing humanBeing) {
        if(realHero.compareTo(humanBeing.realHero)!=0)
            return realHero.compareTo(humanBeing.realHero);

        if(car==null)
        {
            if(humanBeing.car!=null)
                return -1;
        }
        else if(car.compareTo(humanBeing.car)!=0)
            return car.compareTo(humanBeing.car);

        if(weaponType==null)
        {
            if(humanBeing.weaponType!=null)
                return -1;
        } else if (weaponType != humanBeing.weaponType) {
            return weaponType.power - humanBeing.weaponType.power;
        }

        if(abs(impactSpeed - humanBeing.impactSpeed) > 0.0001)
            return (int) signum(impactSpeed - humanBeing.impactSpeed);

        if(hasToothpick.compareTo(humanBeing.hasToothpick)!=0)
            return hasToothpick.compareTo(humanBeing.hasToothpick);

        return (int) (id - humanBeing.id);
    }

    public static class Coordinates implements Serializable
    {
        private float x;
        private double y;

        public Coordinates(float x, double y) {
            this.x = x;
            this.y = y;
        }

        public float getX() {
            return x;
        }

        public double getY() {
            return y;
        }

        @Override
        public String toString() {
            return "Coordinates{" + '\n' +
                    "        x = " + x + '\n' +
                    "        y = " + y + '\n' +
                    "    }";
        }
    }
    public static class Car implements Comparable<Car>, Comparator<Car>, Serializable
    {
        private final String name;
        private final Boolean cool;

        public Car(String name, Boolean cool) {
            this.name = name;
            this.cool = cool;
        }

        public String getName() {
            return name;
        }

        public Boolean getCool() {
            return cool;
        }
        private int getPower()
        {
            int power = 0;
            if(cool!=null && cool)
                power+=100;
            if(name!=null)
                power+=name.length();
            return power;
        }

        @Override
        public int compareTo(Car o) {
            if(o == null)
                return 1;
            return getPower()-o.getPower();
        }

        @Override
        public String toString() {
            return "Car{" + '\n' +
                    "        name = " + name + '\n' +
                    "        cool = " + cool + '\n' +
                    "    }";
        }

        @Override
        public int compare(Car o1, Car o2) {
            if(o1 == null && o2==null)
                return 0;
            if(o1!=null)
                return o1.compareTo(o2);
            return -o2.compareTo(o1);
        }
    }
    public enum WeaponType implements Serializable{
        AXE(2),
        SHOTGUN(3),
        RIFLE(4),
        KNIFE(1);

        private final int power;

        public int getPower() {
            return power;
        }

        WeaponType(int power)
        {
            this.power = power;
        }
    }
    public enum Mood implements Serializable{
        SADNESS,
        SORROW,
        LONGING,
        GLOOM,
        APATHY
    }

    @Override
    public String toString() {
        return "\nHumanBeing{" + '\n' +
                "    id = " + id + '\n' +
                "    name = " + name + '\n' +
                "    coordinates = " + coordinates.toString() + '\n' +
                "    creationDate = " + creationTime.toLocalDate().toString() + '\n' +
                "    creationTime = " + creationTime.toLocalTime().withNano(0).toString() + '\n' +
                "    realHero = " + realHero + '\n' +
                "    hasToothpick = " + hasToothpick + '\n' +
                "    impactSpeed = " + impactSpeed + '\n' +
                "    weaponType = " + weaponType + '\n' +
                "    mood = " + mood + '\n' +
                "    car = " + car + '\n' +
                '}' + '\n';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HumanBeing that)) return false;
        return Float.compare(that.impactSpeed, impactSpeed) == 0 && id.equals(that.id) && name.equals(that.name) && coordinates.equals(that.coordinates) && creationTime.equals(that.creationTime) && realHero.equals(that.realHero) && hasToothpick.equals(that.hasToothpick) && weaponType == that.weaponType && mood == that.mood && Objects.equals(car, that.car);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, coordinates, creationTime, realHero, hasToothpick, impactSpeed, weaponType, mood, car);
    }

    /**
     * Класс выполняющий чтение из файла отдельного объекта класса HumanBeing. Является внутренним в целях избежания нарушения инкапсуляции.
     * @see core.file.FromXMLToObject
     */
    private class InnerXMLReader
    {
        public void readObject(XMLStreamReader reader) throws XMLStreamException {
            id = Long.valueOf(reader.getAttributeValue(0));
            name = reader.getAttributeValue(1);
            realHero = Boolean.valueOf(reader.getAttributeValue(2));
            hasToothpick = Boolean.valueOf(reader.getAttributeValue(3));
            impactSpeed = Float.parseFloat(reader.getAttributeValue(4));
            var s = reader.getAttributeValue(5);
            if(s.trim().length()==0)
                weaponType = null;
            else
                weaponType = WeaponType.valueOf(s);
            mood = Mood.valueOf(reader.getAttributeValue(6));
            reader.next();
            creationTime = LocalDateTime.of(LocalDate.parse(reader.getAttributeValue(0)), LocalTime.parse(reader.getAttributeValue(1)));
            reader.next();
            reader.next();
            coordinates = new Coordinates(Float.parseFloat(reader.getAttributeValue(0)), Double.parseDouble(reader.getAttributeValue(1)));
            reader.next();
            reader.next();
            var name = reader.getAttributeValue(0).trim();
            var cool = reader.getAttributeValue(1).trim();
            if(name.length()==0 && cool.length()==0)
                car = null;
            else if(name.length()==0)
                car = new Car(null,Boolean.valueOf(cool));
            else if(cool.length()==0)
                car = new Car(name,null);
            else
                car = new Car(name,Boolean.valueOf(cool));
        }
    }

}
