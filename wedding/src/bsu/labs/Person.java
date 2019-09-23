package bsu.labs;

import java.io.Serializable;

public class Person implements Serializable {
    private String name;
    enum Gender{
        female,
        male
    };
    private Gender gender;
    enum Sign{
        Aries, Taurus,
        Gemini, Cancer,
        Lion, Virgo,
        Libra, Scorpio,
        Sagittarius, Capricorn,
        Aquarius, Fish
    }
    private Sign sign;
    private int age;
    private Sign partnerSign;

    public Person(String name, Gender gender, Sign sign, int age, Sign partnerSign) {
        this.name = name;
        this.gender = gender;
        this.sign = sign;
        this.age = age;
        this.partnerSign = partnerSign;
    }

    public String getName() {
        return name;
    }

    public Sign getPartnerSign() {
        return partnerSign;
    }

    public void setPartnerSign(Sign partnerSign) {
        this.partnerSign = partnerSign;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Sign getSign() {
        return sign;
    }

    public void setSign(Sign sign) {
        this.sign = sign;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
    @Override
    public String toString() {
        return //"***********************\n"+
                "name = " + name + ',' +
                " gender = " + gender + ','+
                " sign = " + sign + ',' +
                " age = " + age + ',' +
                " partner sign = " + partnerSign;
                       // +"\n***********************\n";
    }
}
