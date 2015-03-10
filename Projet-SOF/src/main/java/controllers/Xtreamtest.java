package controllers;

import com.thoughtworks.xstream.XStream;

import domain.Formation;

public class Xtreamtest {
    public static void main(String[] args) {
        XStream xStream = new XStream();
        Object formation = new Formation();
        ((domain.Object) formation).setCode("Form2");
        ((domain.Object) formation).setName("Form2");

        System.out.println(xStream.toXML(formation));
    }
}
